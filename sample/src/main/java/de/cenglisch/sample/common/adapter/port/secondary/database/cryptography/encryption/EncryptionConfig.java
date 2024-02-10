package de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.encryption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Base64;

@Configuration
public class EncryptionConfig {

    @Value("${app.encryption.key}")
    private String key;

    @Value("${app.encryption.algorithm}")
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
