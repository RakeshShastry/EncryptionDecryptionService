package com.example.model;

import org.springframework.util.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AES256Alg {
    private static String algorithmName= "AES";

    public String encryptData(String keyValue, String plainText)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String encryptedText = "";
        try{
            Cipher encryptedcipher = Cipher.getInstance("AES");
            byte[] encryptkey = generateKey(keyValue);
            SecretKeySpec secretKey = new SecretKeySpec(encryptkey, "AES");
            encryptedcipher.init(Cipher.ENCRYPT_MODE,secretKey);
            byte[] inputbytes= plainText.getBytes();
            byte[] outputbytes = encryptedcipher.doFinal(inputbytes);
            encryptedText = Base64Utils.encodeToString(outputbytes);
        }
        catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException ex){
            System.out.println(ex);
        }
        return  encryptedText;
    }

    public String decryptData(String keyValue, String encryptedText)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        String decryptedText = "";
        try {
            Cipher decryptedcipher = Cipher.getInstance(algorithmName);
            byte[] decryptkey = generateKey(keyValue);
            SecretKeySpec secretKey = new SecretKeySpec(decryptkey, algorithmName);
            decryptedcipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptedByte = Base64Utils.decode(encryptedText.getBytes());
            byte[] decryptedByte = decryptedcipher.doFinal(encryptedByte);
            decryptedText = new String(decryptedByte, "UTF-8");
        }
        catch (NoSuchPaddingException| NoSuchAlgorithmException| InvalidKeyException| BadPaddingException| IllegalBlockSizeException| UnsupportedEncodingException ex){
            System.out.println("Key is invalid could you please check the key!!!!");
        }
        return decryptedText;
    }

    public byte[] generateKey(String keyValue) throws NoSuchAlgorithmException {
        byte[] keys = keyValue.getBytes();
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        keys = sha.digest(keys);
        keys = Arrays.copyOf(keys,32);
        return keys;
    }
}
