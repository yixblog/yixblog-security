package cn.yixblog.security.repo;

import cn.yixblog.security.core.beans.CachedAccount;
import cn.yixblog.security.dao.IAccountRepository;

import java.util.*;

/**
 * repository impl
 * Created by Yixian on 14-1-19.
 */
public class MemoryAccountRepo implements IAccountRepository {
    private Map<String,CachedAccount> dataMap = new Hashtable<>();
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                cleanCache();
            }
        },600000);
    }

    /**
     * minutes available for an account
     */
    private int availableMinutes = 30;

    public void setAvailableMinutes(int availableMinutes) {
        this.availableMinutes = availableMinutes;
    }

    @Override
    public CachedAccount queryForAccountBySSID(String ssid) {
        return dataMap.get(ssid);
    }

    @Override
    public List<CachedAccount> queryForAccountByID(String id) {
        List<CachedAccount> accounts = new ArrayList<>();
        for (Map.Entry<String,CachedAccount> entry:dataMap.entrySet()){
            if(entry.getValue().getId().equals(id)){
                accounts.add(entry.getValue());
            }
        }
        return accounts;
    }

    @Override
    public synchronized void cacheAccount(CachedAccount account) {
        dataMap.put(account.getSSID(),account);
    }

    @Override
    public synchronized void removeCache(String ssid) {
        dataMap.remove(ssid);
    }

    private synchronized void cleanCache(){
        List<String> ssidToRemove = new ArrayList<>();
        for (Map.Entry<String,CachedAccount> entry:dataMap.entrySet()){
            if(!entry.getValue().available(availableMinutes)){
                ssidToRemove.add(entry.getKey());
            }
        }
        for (String ssid:ssidToRemove){
            removeCache(ssid);
        }
    }


}
