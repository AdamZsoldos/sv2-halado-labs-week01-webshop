package webshop;

public class UserValidator {

    private final static String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public static void validateUser(String username, String password, String email, String address) {
        validateNotBlank(username, "Username");
        validateNotBlank(password, "Password");
        validateNotBlank(email, "Email");
        validateEmail(email);
        validateNotBlank(address, "Address");
    }

    private static void validateNotBlank(String s, String fieldName) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is blank");
        }
    }

    private static void validateEmail(String email) {
        if (!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}
