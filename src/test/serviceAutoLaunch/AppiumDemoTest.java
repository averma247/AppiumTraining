import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class AppiumDemoTest {

    AndroidDriver<AndroidElement> driver;
    WebDriverWait wait;



    @BeforeTest
    public void initAndroidDriver() throws MalformedURLException, InterruptedException {
        DesiredCapabilities cap = new DesiredCapabilities();
        System.out.println("Setting desired capabilities  -------------------------");
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        cap.setCapability("deviceName","emulator-5554");
        cap.setCapability("platformVersion","13");
        cap.setCapability("autoGrantPermissions", "true");
        cap.setCapability("noReset", "true");
        //cap.setCapability("autoDismissAlerts", "true");
        //cap.setCapability("autoAcceptAlerts", "true");
        cap.setCapability("appPackage","io.appium.android.apis");
        cap.setCapability("appActivity","io.appium.android.apis.ApiDemos");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub/"),cap);
        Thread.sleep(10000);
        System.out.println("Application launched successfully -----------------------");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver,10);

    }


    @Test(priority = 0)
    public  void handling_Alert_PopUps() throws InterruptedException {
        //------- Handling Alert Pop-ups. ---------------//
        driver.findElementByAccessibilityId("App").click();
        driver.findElementByAccessibilityId("Alert Dialogs").click();

        //driver.navigate().back();
        driver.findElementByXPath("//android.widget.Button[@content-desc=\"OK Cancel dialog with a message\"]").click();
        Thread.sleep(1000);
        driver.findElementByXPath("//android.widget.Button[contains(@text,'OK')]").click();
        Thread.sleep(1000);

        driver.findElementByXPath("//android.widget.Button[@content-desc=\"OK Cancel dialog with a message\"]").click();
        driver.findElementByXPath("//android.widget.Button[contains(@text,'Cancel')]").click();

        driver.navigate().back();
        driver.navigate().back();

    }


    @Test(priority = 1)
    public void handling_Tap_And_LongPress(){

        //------------------ Tap and Long Press ---------------------//
        AndroidElement views = driver.findElementByAccessibilityId("Views");
        AndroidTouchAction touch = new AndroidTouchAction(driver);
        touch.tap(TapOptions.tapOptions().withElement(ElementOption.element(views))).perform();

        AndroidElement expandableLists = driver.findElementByAccessibilityId("Expandable Lists");
        touch.tap(TapOptions.tapOptions().withElement(ElementOption.element(expandableLists))).perform();

        AndroidElement customAdapter = driver.findElementByAccessibilityId("1. Custom Adapter");
        touch.tap(TapOptions.tapOptions().withElement(ElementOption.element(customAdapter))).perform();

        //Long Press
        AndroidElement peopleNames = driver.findElementByXPath("//android.widget.TextView[@text='People Names']");

        LongPressOptions longPressOptions = new LongPressOptions();
        longPressOptions.withDuration(Duration.ofSeconds(5)).withElement(ElementOption.element(peopleNames));
        touch.longPress(longPressOptions).release().perform();

        driver.navigate().back();
        driver.navigate().back();

    }

    @Test(priority = 2)
    public void handling_Scrolling(){
        //----------------------- Scrolling ---------------------------//

        AndroidElement views = driver.findElementByAccessibilityId("Views");
        AndroidTouchAction touch = new AndroidTouchAction(driver);
        touch.tap(TapOptions.tapOptions().withElement(ElementOption.element(views))).perform();


        //vertical scrolling
        //AndroidElement list = driver.findElementById("android:id/text1");

        MobileElement listItem = driver.findElement(
                MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("+
                        "new UiSelector().description(\"Switches\"));"));

        System.out.println(listItem.getLocation());
        listItem.click();

    }

    @Test
    public void handling_Drag_And_Drop(){

        AndroidElement views = driver.findElementByAccessibilityId("Views");
        AndroidTouchAction touch = new AndroidTouchAction(driver);
        touch.tap(TapOptions.tapOptions().withElement(ElementOption.element(views))).perform();


        //vertical scrolling
        //AndroidElement list = driver.findElementById("android:id/text1");

        MobileElement listItem = driver.findElement(
                MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("+
                        "new UiSelector().description(\"Drag and Drop\"));"));

        listItem.click();
        AndroidElement source=driver.findElementById("io.appium.android.apis:id/drag_dot_1");
        LongPressOptions longPressOptions = new LongPressOptions();
        longPressOptions.withDuration(Duration.ofSeconds(5)).withElement(ElementOption.element(source));
        touch.longPress(longPressOptions).release().perform();

        AndroidElement destination=driver.findElementById("io.appium.android.apis:id/drag_dot_hidden");
        TouchAction action = new TouchAction(driver);
        //drag and drop action.
        action.longPress(ElementOption.element(source)).moveTo(ElementOption.element(destination)).release().perform();
        System.out.println("Element has been dropped at destination location.");
        wait.until(ExpectedConditions.visibilityOfElementLocated(new By.ById("io.appium.android.apis:id/drag_text")));
        String dragText=driver.findElementById("io.appium.android.apis:id/drag_text").getText();
        Assert.assertTrue(dragText.contains("drag_dot_1"),"Assert Failed as the actual condition is False");


    }


    @AfterTest
    public void tearDown() {
        driver.quit();
    }




}
