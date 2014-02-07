package cn.yixblog.security;

import cn.yixblog.security.tools.rsa.RSAUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * rsa utils test
 * Created by Yixian on 14-2-7.
 */
public class RSAUtilsTest {

    @Test
    public void testRSA() {
        String randomStr = RandomStringUtils.random(300, true, true);
        System.out.println("random str generated:" + randomStr);
        KeyPair keyPair = RSAUtils.generateKeyPair();
        PublicKey pubKey = keyPair.getPublic();
        String publicKeyHexString = RSAUtils.getKeyHexString(pubKey);
        PrivateKey priKey = keyPair.getPrivate();
        System.out.println("public key:" + publicKeyHexString);
        byte[] encrypted = RSAUtils.encrypt(priKey,randomStr.getBytes());
        PublicKey reformPubKey = RSAUtils.getPublicKeyFromHexString(publicKeyHexString);
        byte[] decrypted = RSAUtils.decrypt(reformPubKey,encrypted);
        System.out.println("decrypted String result:"+new String(decrypted));
    }
}
