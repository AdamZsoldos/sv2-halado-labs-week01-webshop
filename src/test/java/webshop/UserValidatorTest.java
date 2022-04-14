package webshop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    @Test
    void testValidate() {
        assertDoesNotThrow(() ->
                UserValidator.validateUser("jill_doe", "1234", "jill@doe.com", "120 Example Way"));
    }

    @Test
    void testValidateNullUsername() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                UserValidator.validateUser(null, "1234", "jill@doe.com", "120 Example Way"));
        assertEquals("Username is blank", e.getMessage());
    }

    @Test
    void testValidateBlankPassword() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                UserValidator.validateUser("jill_doe", "     ", "jill@doe.com", "120 Example Way"));
        assertEquals("Password is blank", e.getMessage());
    }

    @Test
    void testValidateEmailWithoutAtSign() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                UserValidator.validateUser("jill_doe", "1234", "jill#doe.com", "120 Example Way"));
        assertEquals("Invalid email", e.getMessage());
    }

    @Test
    void testValidateEmailWithoutDot() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                UserValidator.validateUser("jill_doe", "1234", "jill@doe#com", "120 Example Way"));
        assertEquals("Invalid email", e.getMessage());
    }
}
