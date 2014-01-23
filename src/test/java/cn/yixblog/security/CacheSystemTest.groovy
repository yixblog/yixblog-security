package cn.yixblog.security

import cn.yixblog.security.core.IAccountManager
import cn.yixblog.security.core.beans.CacheResult
import cn.yixblog.security.core.beans.CachedAccount
import cn.yixblog.security.services.AccountManager
import cn.yixblog.security.tools.PasswordProvider
import com.alibaba.fastjson.JSON
import org.junit.Before
import org.junit.Test

/**
 * Created by Yixian on 14-1-19.
 */
class CacheSystemTest {
    private IAccountManager accountManager;

    @Before
    void prepareSystem() {
        accountManager = new AccountManager();
        PasswordProvider provider = new PasswordProvider();
        provider.setPwdOverTimeMinutes(30);
        accountManager.setPwdProvider(provider);
        accountManager.setAvailableMinutes(10);
    }

    @Test
    void testSystem() {
        CacheResult result = accountManager.generateSSID("user:233", "testuser1");
        String ssidCode = result.ssidCode;
        String timeCode = result.timeCode;
        println("ssid=$ssidCode,time=$timeCode");
        println("check status:${JSON.toJSONString(accountManager.checkAccountLegal(ssidCode, timeCode))}")
        Thread.sleep(1000);
        println("check status2:${JSON.toJSONString(accountManager.checkAccountLegal(ssidCode, timeCode))}")
    }
}
