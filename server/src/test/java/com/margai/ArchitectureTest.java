package com.margai;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.margai.ai.api.AiClient;
import com.margai.ai.api.RouteDecision;
import com.margai.ai.api.RouterVerdict;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestController;

/**
 * Architecture rules beyond Modulith's module boundaries (TECH_PLAN §1.4, §4.2, §4.13, §8.1):
 * the AWS SDK confined to the two integration packages (Bedrock in {@code ai}, SES in
 * {@code auth} since the D7 ruling) and each service SDK to its own, the single entry point to
 * the AI seam, the router as the only producer of routed decisions, and controllers in
 * {@code web} packages. Main classes only.
 */
class ArchitectureTest {

    private static final JavaClasses CLASSES = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.margai");

    @Test
    void onlyTheTwoIntegrationPackagesImportTheAwsSdk() {
        noClasses().that().resideOutsideOfPackages("com.margai.ai.internal.bedrock..", "com.margai.auth.internal.email..")
                .should().dependOnClassesThat().resideInAPackage("software.amazon.awssdk..")
                .because("the AWS SDK is confined to the packages that talk to a service (TECH_PLAN §1.4; D7 ruling: SES in auth)")
                .check(CLASSES);
    }

    @Test
    void onlyTheBedrockPackageImportsTheBedrockSdk() {
        noClasses().that().resideOutsideOfPackage("com.margai.ai.internal.bedrock..")
                .should().dependOnClassesThat().resideInAPackage("software.amazon.awssdk.services.bedrock..")
                .because("only ai imports software.amazon.awssdk.services.bedrock* (TECH_PLAN §1.4), and inside ai only its bedrock package")
                .check(CLASSES);
    }

    @Test
    void onlyTheEmailPackageImportsTheSesSdk() {
        noClasses().that().resideOutsideOfPackage("com.margai.auth.internal.email..")
                .should().dependOnClassesThat().resideInAPackage("software.amazon.awssdk.services.sesv2..")
                .because("only auth imports an OTP delivery client (TECH_PLAN §1.4), and inside auth only its email package")
                .check(CLASSES);
    }

    @Test
    void onlyTheAiModulesInternalsAndTasksTouchAiClient() {
        noClasses().that().resideOutsideOfPackages("com.margai.ai.api..", "com.margai.ai.internal..", "com.margai.ai.tasks..")
                .should().dependOnClassesThat().areAssignableTo(AiClient.class)
                .because("feature modules call task classes in ai.tasks, never the seam (TECH_PLAN §4.1)")
                .check(CLASSES);
    }

    @Test
    void onlyTheDifficultyRouterProducesARoutedDecision() {
        noClasses().that().doNotHaveFullyQualifiedName("com.margai.ai.tasks.DifficultyRouter")
                .should().callMethod(RouteDecision.class, "router", RouterVerdict.class)
                .because("REASON only via the router, verification() or generation() (TECH_PLAN §4.2, DECISIONS D3.23)")
                .check(CLASSES);
    }

    @Test
    void controllersLiveInWebPackages() {
        classes().that().areAnnotatedWith(RestController.class)
                .should().resideInAPackage("..web..")
                .because("controllers live in <module>.web and are thin (TECH_PLAN §1.4)")
                .allowEmptyShould(true)
                .check(CLASSES);
    }
}
