package com.margai.ai.internal;

import com.github.victools.jsonschema.generator.Option;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;
import com.github.victools.jsonschema.module.jackson.JacksonSchemaModule;
import com.margai.ai.api.InvalidOutputException;
import com.margai.ai.api.Usage;
import com.networknt.schema.Schema;
import com.networknt.schema.SchemaRegistry;
import com.networknt.schema.SpecificationVersion;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.json.JsonMapper;

/**
 * The structured-output contract (TECH_PLAN §4.11, DECISIONS D3.21 and D5): one JSON schema is
 * derived from the output record — snake_case property names, every component required except
 * {@link Optional} ones (optional and nullable), no additional properties, enums as their
 * constant names — and serves both as the forced tool's input schema and as the schema
 * the model's answer is validated against before it is decoded into the record. Required-ness
 * and types are the validator's job; Jackson only rejects unknown properties and null
 * primitives. A mismatch is an {@link InvalidOutputException} that carries the validation
 * messages the repair retry puts in context.
 */
public final class StructuredOutput {

    private final JsonMapper mapper = JsonMapper.builder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
            .build();

    private final SchemaGenerator generator;
    private final SchemaRegistry registry = SchemaRegistry.withDefaultDialect(SpecificationVersion.DRAFT_2020_12);
    private final Map<Class<?>, Compiled> compiled = new ConcurrentHashMap<>();

    public StructuredOutput() {
        SchemaGeneratorConfigBuilder config = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12,
                OptionPreset.PLAIN_JSON)
                .with(new JacksonSchemaModule())
                .with(Option.FORBIDDEN_ADDITIONAL_PROPERTIES_BY_DEFAULT)
                .without(Option.SCHEMA_VERSION_INDICATOR);
        config.forFields()
                .withRequiredCheck(field -> !field.getType().isInstanceOf(Optional.class))
                .withNullableCheck(field -> field.getType().isInstanceOf(Optional.class))
                .withPropertyNameOverrideResolver(field -> snakeCase(field.getName()));
        this.generator = new SchemaGenerator(config.build());
    }

    /** {@code finalAnswer} → {@code final_answer}: the same rule as Jackson's SNAKE_CASE strategy. */
    static String snakeCase(String camel) {
        StringBuilder snake = new StringBuilder(camel.length() + 4);
        for (int i = 0; i < camel.length(); i++) {
            char c = camel.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    snake.append('_');
                }
                snake.append(Character.toLowerCase(c));
            } else {
                snake.append(c);
            }
        }
        return snake.toString();
    }

    /** The JSON schema of {@code type}; generated once per record. */
    public JsonNode schemaFor(Class<?> type) {
        return compiledFor(type).node();
    }

    public String schemaJson(Class<?> type) {
        return schemaFor(type).toString();
    }

    /** Parses model text as JSON; anything else is invalid output. */
    public JsonNode parse(String json, Usage usage, String modelId) {
        try {
            return mapper.readTree(json);
        } catch (JacksonException e) {
            throw new InvalidOutputException(List.of("not valid JSON: " + e.getOriginalMessage()), json, usage, modelId);
        }
    }

    public String toJson(Object value) {
        return mapper.writeValueAsString(value);
    }

    /**
     * A tree as plain Java values ({@link Map}, {@link List}, String, Number, Boolean, null).
     * Provider SDKs carry JSON in their own tree types; handing them plain values keeps their
     * JSON library — Jackson 2 in the Anthropic SDK — out of this module's imports entirely.
     */
    public Object toPlain(JsonNode node) {
        return mapper.convertValue(node, Object.class);
    }

    /** The inverse: plain Java values, as an SDK hands them back, as a tree. */
    public JsonNode fromPlain(Object value) {
        return mapper.convertValue(value, JsonNode.class);
    }

    /** Validates against the record's schema, then decodes; {@code usage} and {@code modelId} attribute a failure. */
    public <T> T decode(Class<T> type, JsonNode json, Usage usage, String modelId) {
        List<String> errors = validate(type, json);
        if (!errors.isEmpty()) {
            throw new InvalidOutputException(errors, json.toString(), usage, modelId);
        }
        try {
            return mapper.treeToValue(json, type);
        } catch (JacksonException e) {
            throw new InvalidOutputException(List.of("does not map onto " + type.getSimpleName() + ": "
                    + e.getOriginalMessage()), json.toString(), usage, modelId);
        }
    }

    /** Validation messages, empty when {@code json} conforms to the schema of {@code type}. */
    public List<String> validate(Class<?> type, JsonNode json) {
        List<String> messages = new ArrayList<>();
        for (com.networknt.schema.Error error : compiledFor(type).schema().validate(json)) {
            messages.add(error.getInstanceLocation() + ": " + error.getMessage());
        }
        return messages;
    }

    private Compiled compiledFor(Class<?> type) {
        return compiled.computeIfAbsent(type, t -> {
            JsonNode node = generator.generateSchema(t);
            Schema schema = registry.getSchema(node);
            schema.initializeValidators();
            return new Compiled(node, schema);
        });
    }

    private record Compiled(JsonNode node, Schema schema) {
    }
}
