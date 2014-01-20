package cn.yixblog.security.core;

import cn.yixblog.security.core.beans.CacheResult;
import cn.yixblog.security.core.beans.CachedAccount;

/**
 * Account CRUD interface
 * Created by Yixian on 14-1-19.
 */
public interface IAccountManager {
    public CachedAccount getLoginAccount(String ssidCode,String ipAddress);

    public CacheResult saveAccount(CachedAccount account);
}
