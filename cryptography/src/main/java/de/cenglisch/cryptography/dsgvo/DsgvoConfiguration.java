package de.cenglisch.cryptography.dsgvo;

import de.cenglisch.cryptography.CryptographyHelper;
import de.cenglisch.cryptography.decryption.Decrypter;
import de.cenglisch.cryptography.encryption.Encrypter;
import de.cenglisch.cryptography.processor.PostProcessor;
import de.cenglisch.cryptography.processor.PreProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class DsgvoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DsgvoConfiguration.class);

    private final CryptographyHelper cryptographyHelper;
    private final Encrypter encrypter;
    private final Decrypter decrypter;

    public DsgvoConfiguration(CryptographyHelper cryptographyHelper, Encrypter encrypter, Decrypter decrypter) {
        this.cryptographyHelper = cryptographyHelper;
        this.encrypter = encrypter;
        this.decrypter = decrypter;
    }
    @Bean
    @ConditionalOnProperty(name = "cryptography.dsgvo-encryption.active", havingValue = "true")
    public PreProcessor dsgvoPreProcessor() {
        return new DsgvoEncryptionService(cryptographyHelper, encrypter);
    }

    @Bean
    @ConditionalOnProperty(name = "cryptography.dsgvo-encryption.active", havingValue = "true")
    public PostProcessor dsgvoPostProcessor() {
        return new DsgvoDecryptionService(cryptographyHelper, decrypter);
    }
}
