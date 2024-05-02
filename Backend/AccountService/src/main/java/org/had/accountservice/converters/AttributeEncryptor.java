package org.had.accountservice.converters;

import jakarta.persistence.AttributeConverter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class AttributeEncryptor implements AttributeConverter<Integer, String> {

    private static final String AES = "AES";
    private static final String SECRET = "secret-key-12345";

    private final Key key;
    private final Cipher cipher;

    public AttributeEncryptor() throws Exception {
        key = new SecretKeySpec(SECRET.getBytes(), AES);
        cipher = Cipher.getInstance(AES);
    }

    @Override
    public String convertToDatabaseColumn(Integer attribute) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(attribute.toString().getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Integer convertToEntityAttribute(String dbData) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = Base64.getDecoder().decode(dbData);
            byte[] decryptedData = cipher.doFinal(decryptedBytes);
            return Integer.parseInt(new String(decryptedData));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}