package de.cenglisch.cryptography.pseudonymization;

import de.cenglisch.cryptography.CryptographyHelper;
import de.cenglisch.cryptography.encryption.Encrypter;
import de.cenglisch.cryptography.processor.PostProcessor;
import de.cenglisch.cryptography.processor.PreProcessor;
import de.cenglisch.cryptography.processor.QueryProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class PseudonymizationConfiguration {

    private final CryptographyHelper cryptographyHelper;
    private final Storage storage;
    private final Encrypter encrypter;

    public PseudonymizationConfiguration(CryptographyHelper cryptographyHelper, Storage storage, Encrypter encrypter) {
        this.cryptographyHelper = cryptographyHelper;
        this.storage = storage;
        this.encrypter = encrypter;
    }

    @Bean
    PseudonymizationService pseudonymizationService() {
        return new PseudonymizationService(cryptographyHelper, storage, encrypter);
    }

    @Bean
    QueryProcessor queryProcessor() {
        return pseudonymizationService();
    }

    @Bean
    Optional<PreProcessor> pseudonymizationPreProcessor(@Value("${cryptography.pseudonymize.active}") boolean isActive) {
        if (!isActive) {
            return Optional.empty();
        }
        return Optional.of(pseudonymizationService());
    }

    @Bean
    Optional<PostProcessor> pseudonymizationPostProcessor(@Value("${cryptography.pseudonymize.active}") boolean isActive) {
        if (!isActive) {
            return Optional.empty();
        }
        return Optional.of(new DissolvePseudonimizationService(cryptographyHelper, storage));
    }
}
