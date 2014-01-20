package cn.yixblog.security.tools.crypts;

/**
 * crypt object
 * Created by Yixian on 14-1-19.
 */
public interface MyCrypt {
    public String encrypt(String content);

    public String decrypt(String content);
}
