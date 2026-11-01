package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RecoveryPasswordPage extends BasePage {

    @FindBy(xpath = "//a[text()='Войти']")
    private WebElement loginLink;

    public RecoveryPasswordPage(WebDriver driver) {
        super(driver);
    }

    @Step("Кликнуть на ссылку 'Войти'")
    public LoginPage goToLogin() {
        waitForVisible(loginLink);
        loginLink.click();
        return new LoginPage(driver);
    }
}