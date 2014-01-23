package cn.yixblog.security.services

import cn.yixblog.security.core.IAccountManager
import cn.yixblog.security.core.beans.CacheResult
import cn.yixblog.security.core.beans.CachedAccount
import cn.yixblog.security.tools.PasswordPair
import cn.yixblog.security.tools.PasswordProvider
import cn.yixblog.security.tools.crypts.AESCrypt
import cn.yixblog.security.tools.crypts.MyCrypt

/**
 * SSID manager
 * Created by Yixian on 14-1-19.
 */
class AccountManager implements IAccountManager {
    PasswordProvider pwdProvider;
    int availableMinutes;

    @Override
    CacheResult checkAccountLegal(String ssidCode, String timeCode) {
        String password = pwdProvider.getPassword(timeCode);
        if (password != null) {
            MyCrypt crypt = new AESCrypt(password);
            String ssid = crypt.decrypt(ssidCode);
            CachedAccount account = CachedAccount.parseFromSSID(ssid);
            if (account != null && account.available(availableMinutes)) {
                account.setLastActiveTime(System.currentTimeMillis());
                return generateSSIDCode(account)
            }
        }
        return new CacheResult(success: false, msg: '非法请求')
    }

    private CacheResult generateSSIDCode(CachedAccount account) {
        PasswordPair newPwd = pwdProvider.getCurrentPassword();
        MyCrypt crypt = new AESCrypt(newPwd.getPassword());
        return new CacheResult(account: account, ssidCode: crypt.encrypt(account.getSSID()), timeCode: newPwd.getGenerateTimeHex(), success: true, msg: '校验成功');
    }

    @Override
    CacheResult generateSSID(String id, String uid) {
        CachedAccount account = new CachedAccount(id: id,uid: uid,lastActiveTime: System.currentTimeMillis());
        return generateSSIDCode(account)
    }
}
