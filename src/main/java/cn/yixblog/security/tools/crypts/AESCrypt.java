package cn.yixblog.security.tools.crypts;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * used for des
 * Created by Yixian on 14-1-19.
 */
public class AESCrypt implements MyCrypt {
    private String password;

    public AESCrypt(String password) {
        this.password = password;
    }

    @Override
    public String encrypt(String content) {
        try {
            Cipher cipher = createCipher(Cipher.ENCRYPT_MODE);
            byte[] bytes = cipher.doFinal(content.getBytes());
            return Hex.encodeHexString(bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Cipher createCipher(int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128, new SecureRandom(password.getBytes()));
        SecretKey secretKey = keyGen.generateKey();
        byte[] encodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(encodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(mode, key);
        return cipher;
    }

    @Override
    public String decrypt(String content) {
        try {
            Cipher cipher = createCipher(Cipher.DECRYPT_MODE);
            return new String(cipher.doFinal(Hex.decodeHex(content.toCharArray())));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | DecoderException e) {
            e.printStackTrace();
        }
        return null;
    }

}
