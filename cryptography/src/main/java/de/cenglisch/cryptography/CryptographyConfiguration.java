package de.cenglisch.cryptography;

import de.cenglisch.cryptography.processor.PostProcessor;
import de.cenglisch.cryptography.processor.PreProcessor;
import de.cenglisch.cryptography.processor.QueryProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CryptographyConfiguration {

    private final QueryProcessor queryProcessor;

    public CryptographyConfiguration(QueryProcessor queryProcessor) {
        this.queryProcessor = queryProcessor;
    }

    @Bean
    public CryptographyAspect cryptographyAspect(
            @Autowired(required = false) @Qualifier("pseudonymizationPreProcessor") PreProcessor pseudonymizationPreProcessor,
            @Autowired(required = false) @Qualifier("pseudonymizationPostProcessor") PostProcessor pseudonymizationPostProcessor,
            @Autowired(required = false) @Qualifier("dsgvoPreProcessor") PreProcessor dsgvoPreProcessor,
            @Autowired(required = false) @Qualifier("dsgvoPostProcessor") PostProcessor dsgvoPostProcessor
    ) {
        final List<PreProcessor> preProcessors = new ArrayList<>();
        final List<PostProcessor> postProcessors = new ArrayList<>();

        if (pseudonymizationPreProcessor != null) {
            preProcessors.add(pseudonymizationPreProcessor);
        }
        if (pseudonymizationPostProcessor != null) {
            postProcessors.add(pseudonymizationPostProcessor);
        }
        if (dsgvoPreProcessor != null) {
            preProcessors.add(dsgvoPreProcessor);
        }
        if (dsgvoPostProcessor != null) {
            postProcessors.add(dsgvoPostProcessor);
        }

        return new CryptographyAspect(queryProcessor, preProcessors, postProcessors);
    }
}
