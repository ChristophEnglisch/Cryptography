package de.cenglisch.sample.common.adapter.port.secondary.database.cryptography;

import de.cenglisch.sample.common.adapter.port.secondary.database.cryptography.pseudonymization.Pseudonymize;
import de.cenglisch.sample.common.domain.DsgvoRelevant;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

@Component
public class CryptographyHelper {
    public Optional<String> determineFieldValue(Object object, Field field) {
        try {
            Method getterMethod = object.getClass().getMethod(determineGetterMethodName(field));
            return Optional.of(getterMethod.invoke(object).toString());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NullPointerException e) {
            return Optional.empty();
        }
    }

    public boolean fieldMustBePseudomized(Field field) {
        return field.isAnnotationPresent(Pseudonymize.class);
    }

    public boolean fieldIsDsgvoRelevant(Field field) {
        return field.isAnnotationPresent(DsgvoRelevant.class);
    }

    public Class<?> determineReferencedEntity(Field field) {
        return field.getAnnotation(Pseudonymize.class).entity();
    }

    private String determineGetterMethodName(Field field) {
        return "get" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
    }

    private String determineSetterMethodName(Field field) {
        return "set" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
    }

    public void fieldSetValue(Object entity, Field field, String value) {
        try {
            Method setter = entity.getClass().getMethod(determineSetterMethodName(field), field.getType());
            setter.invoke(entity, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
