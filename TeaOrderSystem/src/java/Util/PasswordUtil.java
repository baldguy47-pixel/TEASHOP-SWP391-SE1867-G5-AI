/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nhatm
 */
public class PasswordUtil {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";

    //Generate password
    public static String generatePassword() {
        SecureRandom random = new SecureRandom();

        // Define regex patterns for each required character type
        String upperPattern = "(?=.*[" + UPPER + "])";
        String lowerPattern = "(?=.*[" + LOWER + "])";
        String digitPattern = "(?=.*[" + DIGITS + "])";

        // Combine the patterns into a single regex pattern
        String passwordPattern = upperPattern + lowerPattern + digitPattern + ".{3,}";

        Pattern pattern = Pattern.compile(passwordPattern);

        while (true) {
            StringBuilder password = new StringBuilder();
            int passwordLength = 6;

            // Generate a random password
            for (int i = 0; i < passwordLength; i++) {
                String characterSet = UPPER + LOWER + DIGITS;
                int randomIndex = random.nextInt(characterSet.length());
                char randomChar = characterSet.charAt(randomIndex);
                password.append(randomChar);
            }
            // Check if the generated password meets the regex pattern
            Matcher matcher = pattern.matcher(password.toString());
            if (matcher.matches()) {
                return password.toString();
            }
        }
    }

    
    public static String hashPasswordMD5(String password) {
        try {
            // Create a MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Update the digest with the password bytes
            md.update(password.getBytes());

            // Calculate the MD5 hash
            byte[] digest = md.digest();

            // Convert the byte array to a hexadecimal string
            BigInteger bigInt = new BigInteger(1, digest);
            String hashedPassword = bigInt.toString(16);

            // Ensure leading zeros are added to maintain the proper length
            while (hashedPassword.length() < 32) {
                hashedPassword = "0" + hashedPassword;
            }

            return hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("hashPasswordMD5: " + e.getMessage());
            return null;
        }
    }
}
