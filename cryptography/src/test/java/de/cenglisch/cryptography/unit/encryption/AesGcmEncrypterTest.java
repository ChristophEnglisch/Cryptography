package de.cenglisch.cryptography.unit.encryption;

import de.cenglisch.cryptography.encryption.AesGcmEncrypter;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.security.SecureRandom;

public class AesGcmEncrypterTest {
    @Test
    public void testEncrypterWithValidKey() {
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);
        AesGcmEncrypter encrypter = new AesGcmEncrypter(key);
        String originalText = "Test Message";
        String encryptedText = encrypter.encrypter(originalText);
        Assertions.assertNotNull(encryptedText);
        Assertions.assertNotEquals(originalText, encryptedText);
    }

    @Test
    public void testEncrypterWithInvalidKeySize() {
        byte[] shortKey = new byte[16];
        Assertions.assertThrows(IllegalArgumentException.class, () -> new AesGcmEncrypter(shortKey));
    }

    @Test
    public void testEncryptionConsistency() {
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);
        AesGcmEncrypter encrypter = new AesGcmEncrypter(key);
        String originalText = "Consistent Message";
        String encryptedText1 = encrypter.encrypter(originalText);
        String encryptedText2 = encrypter.encrypter(originalText);
        Assertions.assertNotEquals(encryptedText1, encryptedText2);
    }
}