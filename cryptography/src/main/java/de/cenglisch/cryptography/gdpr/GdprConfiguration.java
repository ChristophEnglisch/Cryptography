package de.cenglisch.cryptography.gdpr;

import de.cenglisch.cryptography.ReflectionHelper;
import de.cenglisch.cryptography.decryption.Decrypter;
import de.cenglisch.cryptography.encryption.Encrypter;
import de.cenglisch.cryptography.processor.PostProcessor;
import de.cenglisch.cryptography.processor.PreProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GdprConfiguration {
    private static final Logger log = LoggerFactory.getLogger(GdprConfiguration.class);

    private final ReflectionHelper reflectionHelper;
    private final Encrypter encrypter;
    private final Decrypter decrypter;

    public GdprConfiguration(ReflectionHelper reflectionHelper, Encrypter encrypter, Decrypter decrypter) {
        this.reflectionHelper = reflectionHelper;
        this.encrypter = encrypter;
        this.decrypter = decrypter;
    }

    @Bean
    @ConditionalOnProperty(name = "cryptography.gdpr-encryption.active", havingValue = "true")
    public PreProcessor gdprPreProcessor() {
        return new de.cenglisch.cryptography.gdpr.GdprPreProccesor(reflectionHelper, encrypter);
    }

    @Bean
    @ConditionalOnProperty(name = "cryptography.gdpr-encryption.active", havingValue = "true")
    public PostProcessor gdprPostProcessor() {
        return new GdprPostProcessor(reflectionHelper, decrypter);
    }
}
