package de.cenglisch.cryptography.pseudonymization;

import de.cenglisch.cryptography.ReflectionHelper;
import de.cenglisch.cryptography.encryption.Encrypter;
import de.cenglisch.cryptography.processor.PostProcessor;
import de.cenglisch.cryptography.processor.PreProcessor;
import de.cenglisch.cryptography.processor.QueryProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PseudonymizationConfiguration {

    private static final Logger log = LoggerFactory.getLogger(PseudonymizationConfiguration.class);

    private final ReflectionHelper reflectionHelper;
    private final PseudonymizationStorage pseudonymizationStorage;
    private final Encrypter encrypter;

    public PseudonymizationConfiguration(ReflectionHelper reflectionHelper, PseudonymizationStorage pseudonymizationStorage, Encrypter encrypter) {
        this.reflectionHelper = reflectionHelper;
        this.pseudonymizationStorage = pseudonymizationStorage;
        this.encrypter = encrypter;
    }

    @Bean
    public PseudonymizationPreProcessor pseudonymizationService() {
        return new PseudonymizationPreProcessor(reflectionHelper, pseudonymizationStorage, encrypter);
    }

    @Bean
    public QueryProcessor queryProcessor() {
        return pseudonymizationService();
    }

    @Bean
    @ConditionalOnProperty(name = "cryptography.pseudonymize.active", havingValue = "true")
    public PreProcessor pseudonymizationPreProcessor() {
        return pseudonymizationService();
    }

    @Bean
    @ConditionalOnProperty(name = "cryptography.pseudonymize.active", havingValue = "true")
    public PostProcessor pseudonymizationPostProcessor() {
        return new PseudonimizationPostProcessor(reflectionHelper, pseudonymizationStorage);
    }
}
