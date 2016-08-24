package sessionTwoTest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by alex on 8/17/16.
 */
public class BasePage {

    // <editor-fold desc="Fields">
    protected WebDriver driver;

    @FindBy(xpath = "//a[@class='logo-img ']")
    private WebElement pageLogo;

    @FindBy(xpath = ".//*[@id='bs-example-navbar-collapse-1']/ul/li")
    private List<WebElement> menuList;

    @FindBy(xpath = ".//*[@id='bs-example-navbar-collapse-1']/ul/li[1]/ul/li")
    private List<WebElement> vanzariList;
    // </editor-fold>

    /**
     * @param driver: will be the instance received from the test
     * Using AjaxElementLocatorFactory we will override the basic element wait from Selenium with one that knows about the ajax elements
     * Also PageFactory.initElements() has the role to active all the elements from the current page in order to be visible inside the test
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    // <editor-fold desc="Actions">
    public void clickPageLogo() {
        pageLogo.click();
    }

    public void clickOnMenuOption(String option) {
        for( WebElement element : menuList) {
            if(element.getText().equals(option)) {
                element.click();
                break;
            }
        }
    }

    public void clickOnListOption(List<WebElement> elementList, String option) {
        for(WebElement element : elementList) {
            if(element.getText().equals(option)) {
                element.click();
                break;
            }
        }
    }

    /**
     * @param category this will be the category under Vanzari list that the user wants to access
     * @param option this will be the option under the chosen category
     * The logic under this implementation is to get the big parent element that contains a list of the categories under Vanzari Tab
     * And then to go inside the parent element and take the required elements using a path to the child element
     */
    public void clickOnVanzariOptionUnderCategory(String category, String option) {
        for(WebElement elemVanzari : vanzariList) {
            String vanzariCategoryName = elemVanzari.findElement(By.xpath("ul/li[1]/a")).getText().toLowerCase();
            if(vanzariCategoryName.equals(category.toLowerCase())) {
                for(WebElement elemUnderCategory : elemVanzari.findElements(By.xpath("ul/li"))) {
                    if(elemUnderCategory.findElement(By.xpath("a")).getAttribute("title").toLowerCase().equals(option.toLowerCase())) {
                        elemUnderCategory.click();
                        break;
                    }
                }

                break;
            }
        }
    }

    public void assertThatTitleEqualsValue(String value) {
        assertTrue("Page title: " + this.driver.getTitle() + "should equal: " + value,
                value.toLowerCase().equals(this.driver.getTitle().toLowerCase()));
    }

    public void closeDriver() {
        this.driver.close();
        this.driver.quit();
    }
    // </editor-fold>

    // <editor-fold desc="Utils">

    /**
     * This is an implementation using javascriptExecutor in order to wait for the page DOM to be completed
     * Using JavaScript call and the ExpectedCondition<> class
     */
    public void waitForPageToLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
    }

    /**
     * @param element this will be the dynamic element that we want to wait
     * There are cases when we have some ajax elements and even if the DOM is fully loaded some elements are still talking with the server
     * So in order to avoid time-outs or Element not found exception we use this dynamic wait
     */
    public void waitForElementToBeVisible(WebElement element) {
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public String getTitle() {
        return this.getTitle();
    }

    public void goToURL(String URL) {
        this.driver.get(URL);
    }

    public void maximizeWindow() {
        this.driver.manage().window().maximize();
    }

    public void setImplicitTimeout(int seconds) {
        this.driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public String getWindowHandle() {
        return this.driver.getWindowHandle();
    }

    public void switchWindow() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }
    }

    public void switchToParrentWindow(String parentHandle) {
        this.driver.switchTo().window(parentHandle);
    }

    /**
     * @param element - desired element that you want to modify
     * @param tag - desired html tag that you want to modify e.g. <value
     * @param value - desired value that you want inside the tag
     * There are cases when you want to modify a value inside a tag, thing that you can not do with selenium so you can make a javaScript that updates the value
     */
    public void setElementTagValue(WebElement element, String tag, String value) {
        JavascriptExecutor js = (JavascriptExecutor)this.driver;
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, tag, value);
    }
    // </editor-fold>
}
