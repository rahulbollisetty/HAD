package org.had.consentservice.service;


import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

@Service
public class EncryptionDecryptionService {

    private static final String SECRET_KEY_ALGORITHM = "AES";
    private static final String ENCRYPTION_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final int SALT_LENGTH = 16; // 16 bytes for AES

    public void encryptFile(Path inputFile, String doctorId, String patientId) throws Exception {
        byte[] salt = generateSalt();
        byte[] key = generateKey(doctorId, patientId, salt);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, SECRET_KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        try (InputStream inputStream = Files.newInputStream(inputFile, StandardOpenOption.READ)) {
            byte[] inputBytes = inputStream.readAllBytes();
            byte[] encryptedBytes = cipher.doFinal(inputBytes);
            Files.write(inputFile, concatenateArrays(salt, encryptedBytes), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            inputFile.toFile().setReadable(true, false); // Set file to be readable by owner only
        }
        // Set file permissions to be readable only by owner
        Files.setPosixFilePermissions(inputFile, PosixFilePermissions.fromString("r--r-----"));
    }

    public String decryptFile(Path inputFile, String doctorId, String patientId) throws Exception {
        byte[] encryptedBytesWithSalt = Files.readAllBytes(inputFile);
        byte[] salt = extractSalt(encryptedBytesWithSalt);
        byte[] key = generateKey(doctorId, patientId, salt);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, SECRET_KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = removeSalt(encryptedBytesWithSalt);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes); // Convert decrypted bytes to String (assuming it's JSON)
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    private static byte[] generateKey(String doctorId, String patientId, byte[] salt) throws Exception {
        String combinedId = doctorId + patientId;
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(combinedId.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return tmp.getEncoded();
    }

    private static byte[] concatenateArrays(byte[] array1, byte[] array2) {
        byte[] result = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    private static byte[] extractSalt(byte[] encryptedBytesWithSalt) {
        byte[] salt = new byte[SALT_LENGTH];
        System.arraycopy(encryptedBytesWithSalt, 0, salt, 0, SALT_LENGTH);
        return salt;
    }

    private static byte[] removeSalt(byte[] encryptedBytesWithSalt) {
        byte[] encryptedBytes = new byte[encryptedBytesWithSalt.length - SALT_LENGTH];
        System.arraycopy(encryptedBytesWithSalt, SALT_LENGTH, encryptedBytes, 0, encryptedBytes.length);
        return encryptedBytes;
    }
}
