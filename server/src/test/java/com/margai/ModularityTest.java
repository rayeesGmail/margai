package com.margai;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModule;
import org.springframework.modulith.core.ApplicationModules;

/**
 * Architecture test (TECH_PLAN §8.1, DECISIONS D3.14): Spring Modulith verifies the module
 * boundaries of §1.3–§1.4 — no cycles, only {@code api} packages used across modules, and
 * only the dependencies each module declares in its {@code package-info}.
 */
class ModularityTest {

    private static final ApplicationModules MODULES = ApplicationModules.of(MargaiApplication.class);

    @Test
    void moduleBoundariesHold() {
        MODULES.verify();
    }

    @Test
    void modulesAreDetected() {
        // A green verify() over zero modules would prove nothing; pin the modules D4, D5 and D7 introduce.
        assertThat(MODULES.stream().map(ApplicationModule::getIdentifier).map(Object::toString))
                .contains("common", "account", "curriculum", "practice", "ai", "auth");
    }
}
