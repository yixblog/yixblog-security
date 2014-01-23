package cn.yixblog.security;

import org.junit.Test;

/**
 * Created by dyb on 14-1-23.
 */
public class HexTest {
    @Test
    public void testHex(){
        long time = System.currentTimeMillis();
        System.out.println(time);
        String hex = Long.toHexString(time);
        System.out.println(hex);
        System.out.println(Long.valueOf(hex,16));
    }
}
