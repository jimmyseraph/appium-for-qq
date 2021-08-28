package vip.testops.ui.cases;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class QQDemoTest {

    private static AndroidDriver<AndroidElement> driver;

    @BeforeAll
    public static void initDriver() {
        File app = new File("app/qqlite_4.0.4.1135_537092077.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android"); // 指定被测平台是android系统
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2); // 指定自动化框架为UiAutomator2
        capabilities.setCapability(MobileCapabilityType.UDID, "LKX7N17C04003807"); // 被测手机的设备号，通过命令行adb devices查看
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath()); // 被测App的安装包，一旦指定安装包，appium会自动安装该软件
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, true); // 指定在完成测试后，全部重置app，并卸载掉
//        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_DURATION, 10000); // 设置app启动超时时间
        capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true); // 自动授权，这样在首次启动app时，不会弹出是否需要授权各种权限的提示
        URL url = null;
        try {
            url = new URL("http://127.0.0.1:4723/wd/hub"); // appium server的访问地址
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver = new AndroidDriver<>(url, capabilities); // 实例化driver
        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS); // 设置全局超时时间
    }

    @AfterAll
    public static void clean() {
        driver.closeApp();
        driver.quit();
    }

    @Test
    public void testQQ(){
        // 执行登录操作
        driver.findElementById("com.tencent.qqlite:id/dialogRightBtn").click();
        driver.findElementById("com.tencent.qqlite:id/btn_login").click();
        driver.findElementByAccessibilityId("请输入QQ号码或手机或邮箱").sendKeys("1234567");
        driver.findElementByAccessibilityId("请输入密码").sendKeys("1234567");
        driver.findElementById("com.tencent.qqlite:id/login").click();
        try {
            Thread.sleep(5000); // 这个等待，只是为了暂停一下，可以看清楚操作之后的界面，没有实际意义
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 之后的断言省略了
    }
}
