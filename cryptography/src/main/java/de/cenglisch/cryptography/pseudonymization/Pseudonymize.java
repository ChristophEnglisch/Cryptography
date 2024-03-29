package de.cenglisch.cryptography.pseudonymization;

import jakarta.persistence.Entity;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Pseudonymize {
    Class<?> entity() default Entity.class;
}
