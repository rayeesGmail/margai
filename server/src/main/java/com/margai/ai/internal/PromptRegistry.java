package com.margai.ai.internal;

import com.margai.ai.api.PromptRef;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;
import org.stringtemplate.v4.misc.STMessage;

/**
 * Loads every {@code prompts/<name>.v<N>.stg} at startup (TECH_PLAN §4.12; DECISIONS D5: group
 * files with a {@code system} and a {@code user} template), refuses duplicates, and resolves the
 * active version per prompt from configuration, else the highest present. A group whose name
 * starts with {@code _} is a fragment group — named model-facing fragments such as the tool
 * description and the repair message, no system/user prompt — rendered through
 * {@link #renderFragment}. Rendering uses StringTemplate 4 with {@code <…>} delimiters; a
 * template error fails loudly rather than rendering a half prompt.
 */
public final class PromptRegistry {

    static final String LOCATION = "classpath*:prompts/*.stg";
    static final String FRAGMENT_PREFIX = "_";
    private static final Pattern FILENAME = Pattern.compile("(_?[a-z][a-z0-9_]*)\\.v(\\d+)\\.stg");
    private static final Set<String> TEMPLATES = Set.of("system", "user");

    private final Map<String, TreeMap<Integer, STGroup>> groups = new LinkedHashMap<>();
    private final Map<String, Integer> active = new LinkedHashMap<>();

    /**
     * @param sources            file name → group file text
     * @param configuredVersions prompt name → active version from {@code margai.ai.prompts}
     */
    public PromptRegistry(Map<String, String> sources, Map<String, Integer> configuredVersions) {
        sources.forEach(this::register);
        configuredVersions.forEach((name, version) -> {
            TreeMap<Integer, STGroup> versions = groups.get(name);
            if (versions == null || !versions.containsKey(version)) {
                throw new IllegalStateException("margai.ai.prompts." + name + ".version=" + version
                        + " but no prompts/" + name + ".v" + version + ".stg exists");
            }
            active.put(name, version);
        });
        groups.forEach((name, versions) -> active.putIfAbsent(name, versions.lastKey()));
    }

    public static PromptRegistry fromClasspath(ResourcePatternResolver resolver, Map<String, Integer> configuredVersions) {
        Map<String, String> sources = new TreeMap<>();
        try {
            for (Resource resource : resolver.getResources(LOCATION)) {
                String filename = resource.getFilename();
                if (sources.put(filename, resource.getContentAsString(StandardCharsets.UTF_8)) != null) {
                    throw new IllegalStateException("prompt file appears twice on the classpath: " + filename);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("could not read prompt templates", e);
        }
        return new PromptRegistry(sources, configuredVersions);
    }

    private void register(String filename, String text) {
        Matcher match = FILENAME.matcher(filename);
        if (!match.matches()) {
            throw new IllegalStateException("prompt file name must be <name>.v<N>.stg: " + filename);
        }
        String name = match.group(1);
        int version = Integer.parseInt(match.group(2));
        STGroup group = new STGroupString(filename, text, '<', '>');
        group.setListener(new FailFast(filename));
        group.load();
        if (!name.startsWith(FRAGMENT_PREFIX)) {
            for (String template : TEMPLATES) {
                if (!group.isDefined(template)) {
                    throw new IllegalStateException(filename + " must define the " + template + "(v) template");
                }
            }
        }
        TreeMap<Integer, STGroup> versions = groups.computeIfAbsent(name, k -> new TreeMap<>());
        if (versions.put(version, group) != null) {
            throw new IllegalStateException("duplicate prompt " + name + " v" + version);
        }
    }

    public Set<String> names() {
        return new TreeSet<>(groups.keySet());
    }

    public int activeVersion(String name) {
        Integer version = active.get(name);
        if (version == null) {
            throw new IllegalArgumentException("unknown prompt: " + name + " (known: " + names() + ")");
        }
        return version;
    }

    /** The active version, or empty for a prompt this registry does not know (the ledger never throws). */
    public OptionalInt versionIfKnown(String name) {
        Integer version = active.get(name);
        return version == null ? OptionalInt.empty() : OptionalInt.of(version);
    }

    /** Proves at construction time that a task's prompt exists. */
    public PromptRef require(String name) {
        activeVersion(name);
        return PromptRef.named(name);
    }

    public RenderedPrompt render(PromptRef ref, Map<String, Object> variables) {
        int version = activeVersion(ref.name());
        STGroup group = groups.get(ref.name()).get(version);
        return new RenderedPrompt(ref.name(), version,
                render(group, "system", variables), render(group, "user", variables));
    }

    /** One fragment of a {@code _}-prefixed group, e.g. {@code _protocol} / {@code repair}. */
    public String renderFragment(String groupName, String template, Map<String, Object> variables) {
        if (!groupName.startsWith(FRAGMENT_PREFIX)) {
            throw new IllegalArgumentException("not a fragment group (no leading _): " + groupName);
        }
        STGroup group = groups.get(groupName).get(activeVersion(groupName));
        if (!group.isDefined(template)) {
            throw new IllegalArgumentException("fragment group " + groupName + " defines no " + template + "(v)");
        }
        return render(group, template, variables);
    }

    private static String render(STGroup group, String template, Map<String, Object> variables) {
        ST st = group.getInstanceOf(template);
        st.add("v", variables);
        return st.render().strip();
    }

    private record FailFast(String filename) implements STErrorListener {

        @Override
        public void compileTimeError(STMessage msg) {
            throw new IllegalStateException("prompt " + filename + ": " + msg);
        }

        @Override
        public void runTimeError(STMessage msg) {
            throw new IllegalStateException("prompt " + filename + ": " + msg);
        }

        @Override
        public void IOError(STMessage msg) {
            throw new IllegalStateException("prompt " + filename + ": " + msg);
        }

        @Override
        public void internalError(STMessage msg) {
            throw new IllegalStateException("prompt " + filename + ": " + msg);
        }
    }
}
