package com.margai.ai.internal;

import com.margai.ai.api.AiClient;
import com.margai.ai.api.AiRequest;
import com.margai.ai.api.AiResponse;
import com.margai.ai.api.EmbedRequest;
import com.margai.ai.api.Usage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import tools.jackson.databind.JsonNode;

/**
 * The default {@link AiClient} outside the {@code bedrock} profile (TECH_PLAN §4.1, DEV_SPEC
 * §13.7 item 6). Answers come from fixtures {@code ai-fixtures/<prompt>.<case>.json} — the
 * output object only — under main resources (the runtime default, shipped in the image) and test
 * resources (extra cases). The case is {@code variables.fixture_case} when present; otherwise a
 * deterministic hash of the rendered user prompt picks among the public cases, so the same
 * question always gets the same answer. Cases whose name starts with {@code _} are reachable only
 * by name (failure cases); a repair retry prefers {@code <prompt>.<case>.repaired.json}.
 *
 * <p>Usage is realistic so the ledger and the breaker are exercised in every profile (DECISIONS
 * D5): ≈ 4 characters per token, the first call per prompt version in this JVM writes the system
 * prefix to the "cache", later calls read it. Rows carry the configured tier model id.
 */
public final class FakeAiClient implements AiClient {

    static final String LOCATION = "classpath*:ai-fixtures/*.json";
    static final String CASE_VARIABLE = "fixture_case";
    static final int VECTOR_DIMENSIONS = 1024;
    static final int IMAGE_TOKENS = 1500;
    private static final Pattern FILENAME = Pattern.compile("([a-z][a-z0-9_]*)\\.(_?[a-z0-9_]+?)(\\.repaired)?\\.json");

    private final AiProperties properties;
    private final PromptRegistry prompts;
    private final StructuredOutput codec;
    private final Map<String, TreeMap<String, String>> fixtures = new TreeMap<>();
    private final Map<String, TreeMap<String, String>> repaired = new TreeMap<>();
    private final Set<String> warmPrompts = ConcurrentHashMap.newKeySet();

    public FakeAiClient(AiProperties properties, PromptRegistry prompts, StructuredOutput codec,
            ResourcePatternResolver resolver) {
        this.properties = properties;
        this.prompts = prompts;
        this.codec = codec;
        try {
            for (Resource resource : resolver.getResources(LOCATION)) {
                register(resource.getFilename(), resource.getContentAsString(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("could not read ai-fixtures", e);
        }
    }

    private void register(String filename, String json) {
        Matcher match = FILENAME.matcher(filename);
        if (!match.matches()) {
            throw new IllegalStateException("fixture file name must be <prompt>.<case>[.repaired].json: " + filename);
        }
        Map<String, TreeMap<String, String>> target = match.group(3) == null ? fixtures : repaired;
        if (target.computeIfAbsent(match.group(1), k -> new TreeMap<>()).put(match.group(2), json) != null) {
            throw new IllegalStateException("fixture appears twice on the classpath: " + filename);
        }
    }

    @Override
    public <T> AiResponse<T> complete(AiRequest<T> request) {
        long started = System.nanoTime();
        RenderedPrompt prompt = prompts.render(request.prompt(), request.variables());
        String caseName = chooseCase(request, prompt);
        String json = fixture(request.prompt().name(), caseName, request.repair() != null);
        String modelId = properties.modelFor(request.tier());
        Usage usage = usage(prompt, json, request.images().size());
        JsonNode node = codec.parse(json, usage, modelId);
        T output = codec.decode(request.outputType(), node, usage, modelId);
        return new AiResponse<>(output, usage, modelId, Duration.ofNanos(System.nanoTime() - started), null);
    }

    @Override
    public AiResponse<float[]> embed(EmbedRequest request) {
        long started = System.nanoTime();
        Random random = new Random(request.text().hashCode());
        float[] vector = new float[VECTOR_DIMENSIONS];
        double norm = 0;
        for (int i = 0; i < vector.length; i++) {
            vector[i] = (float) random.nextGaussian();
            norm += vector[i] * vector[i];
        }
        float scale = (float) (1 / Math.sqrt(norm));
        for (int i = 0; i < vector.length; i++) {
            vector[i] *= scale;
        }
        Usage usage = new Usage(tokens(request.text()), 0, 0, 0);
        return new AiResponse<>(vector, usage, properties.embed().model(),
                Duration.ofNanos(System.nanoTime() - started), null);
    }

    /** Public case names for a prompt, in name order. */
    public List<String> cases(String prompt) {
        List<String> names = new ArrayList<>();
        for (String name : fixtures.getOrDefault(prompt, new TreeMap<>()).keySet()) {
            if (!name.startsWith("_")) {
                names.add(name);
            }
        }
        return names;
    }

    private String chooseCase(AiRequest<?> request, RenderedPrompt prompt) {
        String name = request.prompt().name();
        Object explicit = request.variables().get(CASE_VARIABLE);
        if (explicit != null) {
            String caseName = explicit.toString();
            if (!fixtures.getOrDefault(name, new TreeMap<>()).containsKey(caseName)) {
                throw new IllegalStateException("no fixture ai-fixtures/" + name + "." + caseName
                        + ".json (cases: " + fixtures.getOrDefault(name, new TreeMap<>()).keySet() + ")");
            }
            return caseName;
        }
        List<String> candidates = cases(name);
        if (candidates.isEmpty()) {
            throw new IllegalStateException("no fixture for prompt " + name + ": add ai-fixtures/" + name
                    + ".<case>.json under src/main/resources (runtime default) or src/test/resources (test case)");
        }
        return candidates.get(Math.floorMod(prompt.user().hashCode(), candidates.size()));
    }

    private String fixture(String prompt, String caseName, boolean repair) {
        if (repair) {
            String fixed = repaired.getOrDefault(prompt, new TreeMap<>()).get(caseName);
            if (fixed != null) {
                return fixed;
            }
        }
        return fixtures.get(prompt).get(caseName);
    }

    private Usage usage(RenderedPrompt prompt, String output, int images) {
        int system = tokens(prompt.system());
        boolean warm = !warmPrompts.add(prompt.name() + ".v" + prompt.version());
        return new Usage(tokens(prompt.user()) + images * IMAGE_TOKENS, tokens(output),
                warm ? system : 0, warm ? 0 : system);
    }

    static int tokens(String text) {
        return Math.max(1, text.length() / 4);
    }
}
