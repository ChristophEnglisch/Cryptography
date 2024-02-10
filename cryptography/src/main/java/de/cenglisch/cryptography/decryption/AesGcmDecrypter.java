package de.cenglisch.cryptography.decryption;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AesGcmDecrypter implements Decrypter {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;
    private final byte[] key;

    public AesGcmDecrypter(byte[] key) {
        if (key.length != 32) {
            throw new IllegalArgumentException("Invalid key size.");
        }
        this.key = key;
    }

    @Override
    public String decrypt(String encryptedValue) {
        try {
            byte[] encryptedWithIv = Base64.getDecoder().decode(encryptedValue);
            byte[] iv = new byte[IV_LENGTH_BYTE];
            System.arraycopy(encryptedWithIv, 0, iv, 0, iv.length);
            byte[] encrypted = new byte[encryptedWithIv.length - iv.length];
            System.arraycopy(encryptedWithIv, iv.length, encrypted, 0, encrypted.length);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, parameterSpec);
            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("Decryption error", e);
        }
    }
}
