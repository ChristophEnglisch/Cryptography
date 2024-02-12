package de.cenglisch.cryptography;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "de.cenglisch.cryptography")
@ConditionalOnClass(CryptographyCondition.class)
public class CryptographyAutoConfiguration {
}
