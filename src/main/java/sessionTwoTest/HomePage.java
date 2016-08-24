package sessionTwoTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by alex on 8/18/16.
 */
public class HomePage extends BasePage {
    // <editor-fold desc="Fields">
    @FindBy(xpath = ".//*[@id='b_cautator_locatie_val']")
    private WebElement searchTextBox;

    @FindBy(xpath = ".//*[@id='b_cautator_form']/div[5]/input")
    private WebElement searchBtn;
    // </editor-fold>

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // <editor-fold desc="Actions">
    public void insertValueInSearchTextBox(String location) {
        searchTextBox.clear();
        searchTextBox.sendKeys(location);
    }

    public void clickOnSearchBtn() {
        searchBtn.click();
    }
    // </editor-fold>
}
