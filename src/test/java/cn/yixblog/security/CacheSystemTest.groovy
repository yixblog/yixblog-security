package cn.yixblog.security

import cn.yixblog.security.core.IAccountManager
import cn.yixblog.security.core.beans.CacheResult
import cn.yixblog.security.core.beans.CachedAccount
import cn.yixblog.security.dao.IAccountRepository
import cn.yixblog.security.repo.MemoryAccountRepo
import cn.yixblog.security.services.AccountManager
import com.alibaba.fastjson.JSON
import org.junit.Before
import org.junit.Test

/**
 * Created by Yixian on 14-1-19.
 */
class CacheSystemTest {
    private IAccountManager accountManager;
    @Before
    void prepareSystem(){
        IAccountRepository repo = new MemoryAccountRepo();

        accountManager = new AccountManager();
        accountManager.setPassword("asdfghjkl");
        accountManager.setRepository(repo);
    }

    @Test
    void testSystem(){
        CacheResult result =accountManager.saveAccount(new CachedAccount(id: "user:233",uid: "testuser1",ipAddress: "localhost"));
        String ssidCode = result.ssidCode;
        println("ssid=$ssidCode");
        println("check status:${JSON.toJSONString(accountManager.getLoginAccount(ssidCode,"localhost"))}")
        Thread.sleep(1000);
        println("check status2:${JSON.toJSONString(accountManager.getLoginAccount(ssidCode,"localhost"))}")
    }
}
