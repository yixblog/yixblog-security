package cn.yixblog.security.services;

import cn.yixblog.security.core.IAccountManager;
import cn.yixblog.security.core.beans.CacheResult;
import cn.yixblog.security.core.beans.CachedAccount;
import cn.yixblog.security.dao.IAccountRepository;
import cn.yixblog.security.tools.crypts.AESCrypt;
import cn.yixblog.security.tools.crypts.MyCrypt;

/**
 * SSID manager
 * Created by Yixian on 14-1-19.
 */
class AccountManager implements IAccountManager {
    IAccountRepository repository;
    /**
     * is one account is unique in session,default is false
     */
    boolean uniqueSession = false;
    /**
     * when uniqueSession is true and the same account logged in ,keep the previous session available and refuse the new request
     * default is false
     */
    boolean keepPreviousSession = false;
    private MyCrypt crypt;

    /**
     * set the AES password;required!
     * @param password AES password
     */
    void setPassword(String password) {
        crypt = new AESCrypt(password);
    }

    @Override
    CachedAccount getLoginAccount(String ssidCode, String ipAddress) {
        String ssid = crypt.decrypt(ssidCode);
        CachedAccount account = repository.queryForAccountBySSID(ssid);
        if (ipAddress.equals(account.getIpAddress())) {
            account.setLastActiveTime(System.currentTimeMillis());
            repository.cacheAccount(account);
            return account;
        }
        return null;
    }

    @Override
    CacheResult saveAccount(CachedAccount account) {
        if (uniqueSession) {
            List<CachedAccount> oldAccounts = repository.queryForAccountByID(account.getId());
            if (oldAccounts.size() > 0) {
                if (keepPreviousSession) {
                    return new CacheResult(success: false, msg: "本账户已在其他地方登陆");
                } else {
                    oldAccounts.each {
                        repository.removeCache(it.getSSID())
                    }
                }
            }
        }
        String ssid = account.getSSID();
        account.setLastActiveTime(System.currentTimeMillis());
        repository.cacheAccount(account);
        return new CacheResult(success: true, ssidCode: crypt.encrypt(ssid))
    }

}
