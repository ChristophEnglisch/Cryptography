package de.cenglisch.cryptography.decryption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
public class DecryptionConfig {

    @Value("${app.encryption.algorithm}")
    private String algorithm;

    @Value("${app.encryption.key}")
    private String key;

    @Bean
    public Decrypter decrypter() {
        byte[] decodedKey = Base64.getDecoder().decode(key);

        switch (algorithm) {
            case "AES-GCM":
                return new AesGcmDecrypter(decodedKey);
        }
        throw new RuntimeException("Encryption Algorithm not defined");
    }
}
