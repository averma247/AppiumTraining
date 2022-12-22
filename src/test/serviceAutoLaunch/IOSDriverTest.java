import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class IOSDriverTest {
    IOSDriver<IOSElement> iosdriver;
    WebDriverWait wait;
    @BeforeTest
    public void initIOSDriver() throws MalformedURLException, InterruptedException {
        DesiredCapabilities cap = new DesiredCapabilities();
        System.out.println("Setting desired capabilities  -------------------------");
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        cap.setCapability("deviceName","iPhone 14");
        cap.setCapability("platformVersion","16.2");
        cap.setCapability("automationName","XCUITest");
        cap.setCapability("app","/Users/ajayverma/UIKitCatalog.app");

        iosdriver = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4723"),cap);
        Thread.sleep(10000);
        System.out.println("Application launched successfully -----------------------");

        iosdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(iosdriver,10);

    }

    @Test
    public void inspectIOSElements(){
        iosdriver.findElementByAccessibilityId("Alert Views").click();
        iosdriver.findElementByXPath("//XCUIElementTypeStaticText[@name=\"Text Entry\"]").click();
        iosdriver.findElementByXPath("//XCUIElementTypeTextField").sendKeys("Hello World");
        iosdriver.findElementByAccessibilityId("OK").click();
        iosdriver.findElementByAccessibilityId("Confirm / Cancel").click();
        iosdriver.findElementByXPath("//XCUIElementTypeButton[@name=\"Confirm\"]").click();

    }

    @AfterTest
    public void tearDown(){

        iosdriver.quit();
    }

}
