package utils;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/";

    public static String generatePassword() {
        SecureRandom random = new SecureRandom();

        return String.format("%s%s%s%s",
                UPPER_CASE.charAt(random.nextInt(UPPER_CASE.length())),
                UPPER_CASE.toLowerCase().substring(1, 8),
                SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())),
                DIGITS.substring(0, 4));
    }
}
