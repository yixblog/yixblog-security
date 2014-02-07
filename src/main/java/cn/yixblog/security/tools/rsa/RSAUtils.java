package cn.yixblog.security.tools.rsa;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * util of RSA
 * Created by dyb on 14-2-7.
 */
public class RSAUtils {
    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", new BouncyCastleProvider());
            final int keySize = 1024;
            generator.initialize(keySize, new SecureRandom());
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException ignored) {
        }
        return null;
    }

    public static byte[] encrypt(Key pk, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, pk);
            int blockSize = cipher.getBlockSize();
            int outputSize = cipher.getOutputSize(data.length);
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize) {
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                } else {
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
                }
                i++;
            }
            return raw;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | ShortBufferException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(Key pk, byte[] raw) {
        try {
            Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, pk);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            int blockSize = cipher.getBlockSize();
            int j = 0;
            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            return bout.toByteArray();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getKeyHexString(Key key) {
        return Hex.encodeHexString(key.getEncoded());
    }

    public static PublicKey getPublicKeyFromHexString(String hex) {
        try {
            byte[] keyEncoded = Hex.decodeHex(hex.toCharArray());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyEncoded);
            KeyFactory factory = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
            return factory.generatePublic(keySpec);
        } catch (DecoderException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PrivateKey getPrivateKeyFromHexString(String hex) {
        try {
            byte[] keyEncoded = Hex.decodeHex(hex.toCharArray());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyEncoded);
            KeyFactory factory = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
            return factory.generatePrivate(keySpec);
        } catch (DecoderException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
