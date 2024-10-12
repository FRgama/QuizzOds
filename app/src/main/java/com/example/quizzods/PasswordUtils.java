package com.example.quizzods;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import android.util.Base64;


public class PasswordUtils {
    // Gera um salt aleatório
    public static String getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return bytesToHex(salt);
    }

    // Gera um hash da senha utilizando o salt
    public static String generateSecurePassword(String password, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            generatedPassword = bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    // Verifica se a senha fornecida corresponde ao hash armazenado
    public static boolean verifyPassword(String password, String storedHashedPassword, String storedSalt) {
        try {
            String hashedPassword = generateSecurePassword(password, storedSalt);
            return hashedPassword.equals(storedHashedPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Em caso de erro, retorna false
        }
    }

    // Converte bytes para representação hexadecimal
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

