package cn.yixblog.security.tools;

/**
 * Created by dyb on 14-1-23.
 */
public class PasswordPair {
    private String password;
    private long generateTime;

    public PasswordPair(String password, long generateTime) {
        this.password = password;
        this.generateTime = generateTime;
    }

    public String getPassword() {
        return password;
    }

    public long getGenerateTime() {
        return generateTime;
    }

    public String getGenerateTimeHex() {
        return Long.toHexString(generateTime);
    }

    public boolean available(int minutes) {
        return System.currentTimeMillis() - generateTime < minutes * 60_000;
    }
}
