package de.cenglisch.cryptography.encryption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Base64;

@Configuration
public class EncryptionConfig {

    @Value("${cryptography.encryption.key}")
    private String key;

    @Value("${cryptography.encryption.algorithm}")
    private String encryptionAlgorithm;

    @Bean
    public Encrypter encrypter() {
        byte[] encrypterKey = Base64.getDecoder().decode(key);

        switch (encryptionAlgorithm) {
            case "AES-GCM":
                return new AesGcmEncrypter(encrypterKey);
        }
        throw new RuntimeException("Encryption Algorithm not defined");
    }
}
