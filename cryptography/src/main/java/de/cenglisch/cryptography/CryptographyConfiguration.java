package de.cenglisch.cryptography;

import de.cenglisch.cryptography.processor.PostProcessor;
import de.cenglisch.cryptography.processor.PreProcessor;
import de.cenglisch.cryptography.processor.QueryProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Configuration
public class CryptographyConfiguration {

    @Bean
    CryptographyAspect cryptographyAspect(
            QueryProcessor queryProcessor,
            @Qualifier("pseudonymizationPreProcessor") Optional<PreProcessor> pseudonymizationPreProcessor,
            @Qualifier("pseudonymizationPostProcessor") Optional<PostProcessor> pseudonymizationPostProcessor,
            @Qualifier("dsgvoPreProcessor") Optional<PreProcessor> dsgvoPreProcessor,
            @Qualifier("dsgvoPostProcessor") Optional<PostProcessor> dsgvoPostProcessor
    ) {
        final Collection<PreProcessor> preProcessors = new ArrayList<>();
        final Collection<PostProcessor> postProcessors = new ArrayList<>();

        pseudonymizationPreProcessor.ifPresent(preProcessors::add);
        pseudonymizationPostProcessor.ifPresent(postProcessors::add);

        dsgvoPreProcessor.ifPresent(preProcessors::add);
        dsgvoPostProcessor.ifPresent(postProcessors::add);

        return new CryptographyAspect(queryProcessor, preProcessors, postProcessors);
    }
}
