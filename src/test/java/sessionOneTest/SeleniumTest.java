package sessionOneTest;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by alexandru.ivana on 8/10/2016.
 */
public class SeleniumTest {
    public static ChromeDriverService service;
    public WebDriver driver;

    @Before
    public void beforeTest() {
        // Set system property to point to the chromedriver.exe location as chromedriver requires a driver to run
        System.setProperty("webdriver.chrome.driver", ".//src//main//resources//chromedriver");
    }

    @Test
    public void firstTest() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(".//src//main//resources//chromedriver"))
                .usingAnyFreePort()
                .build();

        service.start();

        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());

        driver.get("http://www.adswizz.com/");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.manage().window().maximize();

        WebElement aboutUsMenu = driver.findElement(By.xpath("//a[contains(.,'About us')]"));

        aboutUsMenu.click();
        assertEquals(driver.getTitle(), "Adswizz");

        // Getting the pal element, not exactly the best way to do it
        WebElement pal = driver.findElement(By.xpath("//p[contains(.,'Philippe A. Leroux')]"));

        assertTrue(pal.isDisplayed());

        List<WebElement> menuElements = driver.findElements(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li"));

        for(WebElement element : menuElements) {
            if(element.getText().equals("Contact us")) {
                element.click();
                break;
            }
        }

        Select select = new Select(driver.findElement(By.xpath("//form/section/div/div/div/div[1]/span[4]/div/select")));
        select.selectByVisibleText("Commercial Broadcaster");

        driver.quit();
    }
}
