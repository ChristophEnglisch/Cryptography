package de.cenglisch.cryptography.performance;

import de.cenglisch.cryptography.decryption.AesGcmDecrypter;
import de.cenglisch.cryptography.encryption.AesGcmEncrypter;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class DecryptionPerformanceTest {

    private AesGcmDecrypter decrypter;
    private String encryptedValue;

    @Setup
    public void setup() {
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);
        this.decrypter = new AesGcmDecrypter(key);

        // Initialisiere den Encrypter mit demselben Schlüssel
        AesGcmEncrypter encrypter = new AesGcmEncrypter(key);

        // Verschlüssle einen Testwert
        String testValue = "Hello Worlsd";
        this.encryptedValue = encrypter.encrypter(testValue);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String decrypt() {
        // Die Zeitmessung erfolgt nur hier, für die Entschlüsselung
        return decrypter.decrypt(encryptedValue);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(DecryptionPerformanceTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
