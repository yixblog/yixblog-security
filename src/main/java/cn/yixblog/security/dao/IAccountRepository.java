package cn.yixblog.security.dao;

import cn.yixblog.security.core.beans.CachedAccount;

import java.util.List;

/**
 * repository to cache logged account ssids
 * Created by Yixian on 14-1-19.
 */
public interface IAccountRepository {

    /**
     * query for account by the ssid of the account,the ssid shall be only one so that this method shall return the account object or null
     *
     * @param ssid ssid of account
     * @return object or null
     */
    public CachedAccount queryForAccountBySSID(String ssid);

    /**
     * query for account by the account id,if no result shall return an empty list
     *
     * @param id account id
     * @return list of accounts or an empty list
     */
    public List<CachedAccount> queryForAccountByID(String id);

    /**
     * save an account object into cache
     *
     * @param account account object
     */
    public void cacheAccount(CachedAccount account);

    /**
     * remove the account object that ssid matches
     *
     * @param ssid matching ssid
     */
    public void removeCache(String ssid);
}
