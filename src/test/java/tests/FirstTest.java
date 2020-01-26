package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class FirstTest {

    private AppiumDriver<MobileElement> driver;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.1");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "AndroidTest");
        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "org.wikipedia");
        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "org.wikipedia.main.MainActivity");
        caps.setCapability(MobileCapabilityType.APP, "/Users/ivan/Desktop/OtusAppium/src/test/resources/Apps/wiki.apk");


        URL appiumURL = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new AppiumDriver<MobileElement>(appiumURL, caps);

    }

    @AfterMethod
    public void  tearDown() {
        driver.quit();
        //driver.resetApp();
    }


    @Test
    public void searchTest() throws InterruptedException {
        WebElement element = waitForElementPresent(By.id("fragment_onboarding_skip_button"), 5);
        element.click();
        WebElement searchClick = waitForElementPresent(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), 5);
        searchClick.click();
        WebElement searchInput = waitForElementPresent(By.id("search_src_text"), 3);
        searchInput.sendKeys("Java");
        Thread.sleep(1000);


    }

    @Test
    public void rotationTest() throws InterruptedException {
        WebElement element = waitForElementPresent(By.id("fragment_onboarding_skip_button"), 5);
        element.click();
        WebElement searchClick = waitForElementPresent(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), 5);
        searchClick.click();
        WebElement searchInput = waitForElementPresent(By.id("search_src_text"), 3);
        searchInput.sendKeys("Java");
        WebElement searchElement = waitForElementPresent(By.xpath("//*[contains(@text, 'Java (programming language)')]"), 5);
        Thread.sleep(5000);
        String expectedTitle = "Java (programming language)1";
        String currentTitle = searchElement.getAttribute("text");
        Assert.assertEquals( expectedTitle, currentTitle, String.format("Expected title: [%s] current tittle [%s]", expectedTitle, currentTitle));
        Assert.assertTrue( currentTitle == "Java (programming language)1" , "Все пропало");
    }

    @Test
    public void backGroundTest() {
        driver.runAppInBackground(Duration.ofSeconds(3));
    }


    @Test
    public void swipeTest() throws InterruptedException {
        WebElement element = waitForElementPresent(By.id("fragment_onboarding_skip_button"), 5);
        element.click();
        WebElement searchClick = waitForElementPresent(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), 5);
        searchClick.click();
        WebElement searchInput = waitForElementPresent(By.id("search_src_text"), 3);
        searchInput.sendKeys("Java");
        Thread.sleep(50000);
        scrollUp(2);
        scrollUp(2);
        scrollUp(2);
        scrollUp(2);
    }



    private WebElement waitForElementPresent( By by, int timeoutInSeconds ) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
         return wait.until(
                 ExpectedConditions.presenceOfElementLocated(by)
         );
    }

    private boolean waitForElementNotPresent (By by, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    protected void scrollUp(int scrollTime) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width/2;
        int start_y = (int) (size.height* 0.9);
        int finish_y = (int) (size.height* 0.2);
        action
                .press(PointOption.point(x, start_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(scrollTime)))
                .moveTo(PointOption.point(x, finish_y))
                .release()
                .perform();

    }

    protected void scrollUpQuik(){
        scrollUp(200);
    }

    protected void scrollForElement (By by, int maxScrolls ) {
        int scrollCount = 0;
        while (driver.findElements(by).size() == 0) {
            if(scrollCount > maxScrolls) {
                WebElement element = waitForElementPresent(by, 5);
                return;
            }
            scrollUpQuik();
            scrollCount++;
        }
    }

    protected void  swipeElementLeft(By by) {
            WebElement element = waitForElementPresent(by, 5);

            int left_x = element.getLocation().getX();
            int upper_y = element.getLocation().getY();

            int right_x = left_x + element.getSize().getWidth();
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) /2;

            TouchAction action = new TouchAction(driver);
            action
                    .press(PointOption.point(right_x, middle_y ))
                    .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                    .moveTo(PointOption.point(left_x, middle_y))
                    .release()
                    .perform();

    }

    protected void  swipeElementRight(By by) {
        WebElement element = waitForElementPresent(by, 5);

        int left_x = element.getLocation().getX();
        int upper_y = element.getLocation().getY();

        int right_x = left_x + element.getSize().getWidth();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) /2;

        TouchAction action = new TouchAction(driver);
        action
                .press(PointOption.point(left_x, middle_y ))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(right_x, middle_y))
                .release()
                .perform();

    }






}
