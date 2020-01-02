import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import io.appium.java_client.TouchAction;
import java.lang.Thread;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.MobileElement;

public class Main {
    public static void main(String[] args) throws MalformedURLException, Exception {
        GardenTrackTest();

    }

    public static void GardenTrackTest() throws MalformedURLException, Exception {

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        caps.setCapability("platformName", "iOS");
        caps.setCapability("deviceName", "-- YOUR iDevice Name --");
        caps.setCapability("platformVersion", "-- Your iOS Version --");
        caps.setCapability("udid", "-- YOUR iDevice UDID Here --"); // ADD YOUR UDID!
        caps.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "muhammedgul.GardenTrack");

        URL url = new URL("http://127.0.0.1:4723/wd/hub");


        IOSDriver driver = new IOSDriver(url,caps);
        //IOSDriver driver = new IOSDriver<MobileElement>(url,caps);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);



        // Capturing & Saving new Plant
        System.out.println("Testing Started -> Saving New Plant");
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"GardenTrack\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeButton").click();
        driver.findElementByName("PhotoCapture").click();
        driver.findElementByName("Use Photo").click();
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"GardenTrack\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTextField").sendKeys("MY PLANT");
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"GardenTrack\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTextView").sendKeys("Sample plant note");
        driver.findElementByName("Done").click();
        driver.findElementByName("SAVE PLANT").click();

        Thread.sleep(1000);
        driver.findElementByName("My Plants").click();
        confirmStringExistsOnPage(driver.getPageSource(), "MY PLANT");
        driver.findElementByName("blackBack2d").click();


        // Adding Garden
        System.out.println("Testing Started -> Saving New Garden");
        TouchAction action = new TouchAction(driver);
        driver.findElementByName("My Gardens").click();
        driver.findElementByName("+").click();
        driver.findElementByName("MY PLANT").click();
        driver.findElementByName("NEXT").click();

        PointOption a,b,c,d;

        a = PointOption.point(80,270);
        b = PointOption.point(325,270);
        c = PointOption.point(300,450);
        d = PointOption.point(80,450);


        // Presses between long presses are for preventing the screen's not allowing multiple long presses. (Screen Doesn't allow to long press multiple times even by hand.)
        Thread.sleep(1000);
        action.press(a).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).release().perform();
        action.press(b).perform();
        action.press(b).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).release().perform();
        action.press(c).perform();
        action.press(c).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).release().perform();
        action.press(d).perform();
        action.press(d).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).release().perform();
        Thread.sleep(500);
        // action.press(PointOption.point(200,430)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5))).release().perform();

        driver.findElementByName("NEXT").click();
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"GardenTrack\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTextField").sendKeys("MY GARDEN");
        driver.findElementByName("Done").click();
        driver.findElementByName("SAVE GARDEN").click();
        Thread.sleep(1500); // for showing success message

        driver.findElementByName("My Gardens").click();
        confirmStringExistsOnPage(driver.getPageSource(), "MY GARDEN");
        driver.findElementByName("blackBack2d").click();

        System.out.println("Testing Started -> Garden Deletion");
        driver.findElementByName("My Gardens").click();
        driver.findElementByName("Details").click();
        driver.findElementByName(" Delete").click();
        driver.findElementByName("Delete").click();
        driver.findElementByName("My Gardens").click();
        confirmStringDoesntExistOnPage(driver.getPageSource(),"MY GARDEN");
        driver.findElementByName("blackBack2d").click();

        // Check if deleting a plant works
        System.out.println("Testing Started -> Plant Deletion");
        driver.findElementByName("My Plants").click();
        driver.findElementByName("Show details").click();
        driver.findElementByName(" Delete plant").click();
        driver.findElementByName("Delete").click();
        driver.findElementByName("My Plants").click();
        confirmStringDoesntExistOnPage(driver.getPageSource(),"MY PLANT");
        driver.findElementByName("blackBack2d").click();



        System.out.println("TEST COMPLETED SUCCESSFULLY. TERMINATING...");
        return;
    }


    public static void confirmStringExistsOnPage(String pageSource, String keyword) {
        if (!pageSource.contains(keyword)) {
            System.out.println("Keyword '" + keyword + "' not found, expected to be found, test failed. Terminating...");
            System.exit(0);
        } else {
            System.out.println("Keyword '" + keyword + "' found, success.");
        }
    }
    public static void confirmStringDoesntExistOnPage(String pageSource, String keyword) {
        if (pageSource.contains(keyword)) {
            System.out.println("Keyword '" + keyword + "'  found, expected to be not found, test failed. Terminating...");
            System.exit(0);
        } else {
            System.out.println("Keyword '" + keyword + "' not found, success.");
        }
    }



}
