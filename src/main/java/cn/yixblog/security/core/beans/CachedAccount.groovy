package cn.yixblog.security.core.beans

import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.lang.RandomStringUtils

/**
 * Created by Yixian on 14-1-19.
 */
class CachedAccount {
    String id;
    String uid;
    long lastActiveTime;
    String ipAddress;
    String randomString = RandomStringUtils.random(20, true, true);

    String getSSID() {
        return DigestUtils.md5Hex("$id##$uid##$ipAddress##$randomString");
    }

    boolean available(int availableMinutes) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar lastActiveCalendar = Calendar.getInstance();
        lastActiveCalendar.setTimeInMillis(lastActiveTime)
        lastActiveCalendar.add(Calendar.MINUTE, availableMinutes);
        return lastActiveCalendar.after(currentCalendar);
    }
}
