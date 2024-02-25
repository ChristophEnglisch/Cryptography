package de.cenglisch.cryptography.unit.decryption;

import de.cenglisch.cryptography.decryption.AesGcmDecrypter;
import de.cenglisch.cryptography.encryption.AesGcmEncrypter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Base64;

class AesGcmDecrypterTest {
    @Test
    void testDecryptWithInvalidKey() {
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);
        byte[] wrongKey = new byte[32];
        new SecureRandom().nextBytes(wrongKey);

        AesGcmEncrypter encrypter = new AesGcmEncrypter(key);
        AesGcmDecrypter decrypterWithWrongKey = new AesGcmDecrypter(wrongKey);

        String originalText = "This is a secret message";
        String encryptedText = encrypter.encrypter(originalText);

        Assertions.assertThrows(RuntimeException.class, () -> decrypterWithWrongKey.decrypt(encryptedText));
    }

    @Test
    void testDecryptWithAlteredCipherText() {
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);

        AesGcmEncrypter encrypter = new AesGcmEncrypter(key);
        AesGcmDecrypter decrypter = new AesGcmDecrypter(key);

        String originalText = "This is a secret message";
        String encryptedText = encrypter.encrypter(originalText);

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        encryptedBytes[5] ^= 1; // Flip one bit
        String alteredEncryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

        Assertions.assertThrows(RuntimeException.class, () -> decrypter.decrypt(alteredEncryptedText));
    }

    @Test
    void testDecryptWithInvalidSizeKey() {
        byte[] invalidKey = new byte[16];
        Assertions.assertThrows(IllegalArgumentException.class, () -> new AesGcmDecrypter(invalidKey));
    }
}

