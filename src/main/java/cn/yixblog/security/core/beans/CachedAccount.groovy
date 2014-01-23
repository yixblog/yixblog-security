package cn.yixblog.security.core.beans
/**
 * Created by Yixian on 14-1-19.
 */
class CachedAccount {
    String id;
    String uid;
    long lastActiveTime;

    String getSSID() {
        return "$id###$uid###${Long.toHexString(lastActiveTime)}";
    }

    static final CachedAccount parseFromSSID(String ssid) {
        String[] values = ssid.split("###");
        if (values.length != 3) {
            return null;
        }
        return new CachedAccount(id: values[0], uid: values[1], lastActiveTime: Long.valueOf(values[2], 16))
    }


    boolean available(int availableMinutes) {
        return System.currentTimeMillis() - lastActiveTime < availableMinutes * 60_000;
    }
}
