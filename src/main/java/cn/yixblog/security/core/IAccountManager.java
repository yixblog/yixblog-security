package cn.yixblog.security.core;

import cn.yixblog.security.core.beans.CacheResult;

/**
 * Account CRUD interface
 * Created by Yixian on 14-1-19.
 */
public interface IAccountManager {
    public CacheResult checkAccountLegal(String ssidCode, String timeCode);

    public CacheResult generateSSID(String id, String uid);
}
