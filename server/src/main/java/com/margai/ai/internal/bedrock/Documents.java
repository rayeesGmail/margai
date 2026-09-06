package com.margai.ai.internal.bedrock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import software.amazon.awssdk.core.SdkNumber;
import software.amazon.awssdk.core.document.Document;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.ObjectNode;

/**
 * The SDK's {@link Document} (tool schemas and tool inputs on the Converse API) ↔ Jackson
 * {@link JsonNode}. Integral numbers stay integral both ways so a schema {@code integer} keeps
 * validating after the round trip.
 */
final class Documents {

    private static final JsonNodeFactory NODES = JsonNodeFactory.instance;

    private Documents() {
    }

    static Document fromJson(JsonNode node) {
        if (node == null || node.isNull() || node.isMissingNode()) {
            return Document.fromNull();
        }
        if (node.isObject()) {
            Map<String, Document> fields = new LinkedHashMap<>();
            node.properties().forEach(entry -> fields.put(entry.getKey(), fromJson(entry.getValue())));
            return Document.fromMap(fields);
        }
        if (node.isArray()) {
            List<Document> items = new ArrayList<>();
            node.forEach(item -> items.add(fromJson(item)));
            return Document.fromList(items);
        }
        if (node.isBoolean()) {
            return Document.fromBoolean(node.asBoolean());
        }
        if (node.isNumber()) {
            return Document.fromNumber(SdkNumber.fromBigDecimal(node.decimalValue()));
        }
        return Document.fromString(node.asString());
    }

    static JsonNode toJson(Document document) {
        if (document == null || document.isNull()) {
            return NODES.nullNode();
        }
        if (document.isMap()) {
            ObjectNode object = NODES.objectNode();
            document.asMap().forEach((key, value) -> object.set(key, toJson(value)));
            return object;
        }
        if (document.isList()) {
            ArrayNode array = NODES.arrayNode();
            document.asList().forEach(item -> array.add(toJson(item)));
            return array;
        }
        if (document.isBoolean()) {
            return NODES.booleanNode(document.asBoolean());
        }
        if (document.isNumber()) {
            BigDecimal number = document.asNumber().bigDecimalValue();
            BigDecimal plain = number.stripTrailingZeros();
            if (plain.scale() <= 0 && plain.precision() - plain.scale() <= 18) {
                return NODES.numberNode(plain.longValueExact());
            }
            return NODES.numberNode(number);
        }
        return NODES.textNode(document.asString());
    }
}
