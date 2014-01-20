package cn.yixblog.security;

import cn.yixblog.security.tools.crypts.AESCrypt;
import cn.yixblog.security.tools.crypts.MyCrypt;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * Created by Yixian on 14-1-19.
 */
public class AESCryptTest {
    @Test
    public void testAESCrypt(){
        MyCrypt crypt = new AESCrypt("password");
        String content = "hello world";
        System.out.println("before encrypt:"+content);
        String encrypted = crypt.encrypt(content);
        System.out.println("after encrypt:"+encrypted);
        String result = crypt.decrypt(encrypted);
        System.out.println("after decrypt:"+result);
    }

    @Test
    public void testMD5(){
        String content = "hello world";
        System.out.println(DigestUtils.md5Hex(content));
    }
}
