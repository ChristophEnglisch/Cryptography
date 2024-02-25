package de.cenglisch.cryptography.unit;

import de.cenglisch.cryptography.ReflectionHelper;
import de.cenglisch.cryptography.environment.AbsencesEntity;
import de.cenglisch.cryptography.environment.EmployeeEntity;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class ReflectionHelperTest {
    ReflectionHelper reflectionHelper = new ReflectionHelper();

    @Test
    public void testFieldMustBePseudomized() throws NoSuchFieldException {
        Field employeeIdField = AbsencesEntity.class.getDeclaredField("employeeId");
        assertTrue(reflectionHelper.fieldMustBePseudomized(employeeIdField));

        Field idField = EmployeeEntity.class.getDeclaredField("id");
        assertFalse(reflectionHelper.fieldMustBePseudomized(idField));
    }

    @Test
    public void testFieldIsGdprRelevant() throws NoSuchFieldException {
        Field firstNameField = EmployeeEntity.class.getDeclaredField("firstName");
        assertTrue(reflectionHelper.fieldIsGdprRelevant(firstNameField));

        Field idField = AbsencesEntity.class.getDeclaredField("id");
        assertFalse(reflectionHelper.fieldIsGdprRelevant(idField));
    }

    @Test
    public void testDetermineReferencedEntity() throws NoSuchFieldException {
        Field employeeIdField = AbsencesEntity.class.getDeclaredField("employeeId");
        assertEquals(EmployeeEntity.class, reflectionHelper.determineReferencedEntity(employeeIdField));
    }
}