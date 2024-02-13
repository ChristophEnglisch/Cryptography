package de.cenglisch.cryptography.dsgvo;

import de.cenglisch.cryptography.CryptographyHelper;
import de.cenglisch.cryptography.decryption.Decrypter;
import de.cenglisch.cryptography.encryption.Encrypter;
import de.cenglisch.cryptography.processor.PostProcessor;
import de.cenglisch.cryptography.processor.PreProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class DsgvoConfiguration {

    private final CryptographyHelper cryptographyHelper;
    private final Encrypter encrypter;
    private final Decrypter decrypter;

    public DsgvoConfiguration(CryptographyHelper cryptographyHelper, Encrypter encrypter, Decrypter decrypter) {
        this.cryptographyHelper = cryptographyHelper;
        this.encrypter = encrypter;
        this.decrypter = decrypter;
    }

    @Bean
    Optional<PreProcessor> dsgvoPreProcessor(@Value("${cryptography.dsgvo-encryption.active}") boolean isActive) {
        if (!isActive) {
            return Optional.empty();
        }
        return Optional.of(new DsgvoEncryptionService(cryptographyHelper, encrypter));
    }

    @Bean
    Optional<PostProcessor> dsgvoPostProcessor(@Value("${cryptography.dsgvo-encryption.active}") boolean isActive) {
        if (!isActive) {
            return Optional.empty();
        }
        return Optional.of(new DsgvoDecryptionService(cryptographyHelper, decrypter));
    }
}
