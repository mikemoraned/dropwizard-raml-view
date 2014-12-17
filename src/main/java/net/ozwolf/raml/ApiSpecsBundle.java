package net.ozwolf.raml;

import net.ozwolf.raml.configuration.ApiSpecsConfiguration;
import net.ozwolf.raml.resources.ApiResource;
import com.yammer.dropwizard.Bundle;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;
import org.raml.model.Raml;
import org.raml.parser.visitor.RamlDocumentBuilder;

import static net.ozwolf.raml.configuration.ApiSpecsConfiguration.configuration;

/**
 * # RAML Docs Bundle
 *
 * This bundle is a DropWizard bundle that displays a RAML API specification
 *
 * ## Example Usage
 *
 * ```java
 * bootstrap.addBundle(ApiSpecificationBundle.bundle("apispecs/apispecs.raml"));
 * ```
 */
public class ApiSpecsBundle implements Bundle {
    private final String specificationFile;
    private final ApiSpecsConfiguration configuration;

    private ApiSpecsBundle(String specificationFile, ApiSpecsConfiguration configuration) {
        this.specificationFile = specificationFile;
        this.configuration = configuration;
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle("/raml-docs-assets", "/api/assets"));
    }

    @Override
    public void run(Environment environment) {
        environment.addResource(ApiResource.resource(specificationFile, configuration));
    }

    /**
     * Instantiate the RAML API documentation bundle.
     *
     * @param specificationFile The path to the specification resource
     * @return The DropWizard bundle
     */
    public static ApiSpecsBundle bundle(String specificationFile) {
        return new ApiSpecsBundle(specificationFile, configuration());
    }

    /**
     * Instantiate the RAML API documentation bundle with a custom configuration.
     *
     * @param specificationFile The path to the specification resource
     * @param configuration     The API specs configuration
     * @return The DropWizard bundle.
     */
    public static ApiSpecsBundle bundle(String specificationFile, ApiSpecsConfiguration configuration) {
        return new ApiSpecsBundle(specificationFile, configuration);
    }
}
