package de.cenglisch.cryptography.gdpr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GdprRelevant {
    ProtectionLevel protectionLevel() default ProtectionLevel.HIGH;
}