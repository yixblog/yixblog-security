package cn.yixblog.security.tools;

import org.apache.commons.lang.RandomStringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * generate a password by time and cache them
 * Created by dyb on 14-1-23.
 */
public class PasswordProvider {
    private volatile PasswordPair currentPassword;
    private int pwdOverTimeMinutes = 30;
    private ConcurrentHashMap<Long, PasswordPair> passwords = new ConcurrentHashMap<>();
    private Timer timer = new Timer(true);

    public void setPwdOverTimeMinutes(int minutes) {
        this.pwdOverTimeMinutes = minutes;
    }

    private TimerTask newPasswordTask = new TimerTask() {
        @Override
        public void run() {
            String newPasword = RandomStringUtils.random(20, true, true);
            long time = System.currentTimeMillis();
            PasswordPair pair = new PasswordPair(newPasword, time);
            passwords.put(time, pair);
            currentPassword = pair;
        }
    };

    private TimerTask clearCacheTask = new TimerTask() {
        @Override
        public void run() {
            List<Long> overtimeKeys = new ArrayList<>();
            for (Map.Entry<Long, PasswordPair> entry : passwords.entrySet()) {
                if (!entry.getValue().available(pwdOverTimeMinutes)) {
                    overtimeKeys.add(entry.getKey());
                }
            }
            for (Long genTime : overtimeKeys) {
                passwords.remove(genTime);
            }
        }
    };

    {
        timer.schedule(newPasswordTask, 0, 300_000);//new password per 5 minutes
        timer.schedule(clearCacheTask, 0, 600_000);//do clear per 10 minutes
    }


    public PasswordPair getCurrentPassword() {
        return currentPassword;
    }

    public String getPassword(String pwdTime) {
        long genTime = Long.valueOf(pwdTime, 16);
        PasswordPair pair = passwords.get(genTime);
        if (pair != null && pair.available(pwdOverTimeMinutes)) {
            return pair.getPassword();
        }
        return null;
    }
}
