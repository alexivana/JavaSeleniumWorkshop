package sessionTwoTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by alex on 8/18/16.
 */
public class SessionTwoTest {
    ChromeDriver driver;
    HomePage homePage;

    @Before
    public void beforeTest() {
        System.setProperty("webdriver.chrome.driver", ".//src//main//resources//chromedriver");
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
    }

    @Test
    public void sessionTwoTest() {
        homePage.goToURL("http://www.imobiliare.ro/");
        homePage.maximizeWindow();
        homePage.waitForPageToLoad();
        //homePage.insertValueInSearchTextBox("Bucuresti");
        //homePage.clickOnSearchBtn();
        homePage.clickOnMenuOption("Vânzări");
        homePage.clickOnVanzariOptionUnderCategory("Apartamente de vânzare", "Apartamente de vânzare Bucuresti");
    }

    @After
    public void afterTest() {
        homePage.closeDriver();
    }
}
