package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProfilePage extends BasePage {

    @FindBy(xpath = "//button[text()='Выход']")
    private WebElement logoutButton;

    @FindBy(xpath = "//p[text()='Конструктор']/parent::a")
    private WebElement constructorLink;

    @FindBy(xpath = "//div[contains(@class, 'AppHeader_header')]//a[@href='/']")
    private WebElement logoButton;

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    @Step("Кликнуть кнопку 'Выход'")
    public LoginPage goToLogin() {
        waitForVisible(logoutButton);
        logoutButton.click();
        return new LoginPage(driver);
    }

    // --- ОБЪЯВЛЕНИЕ МЕТОДА openMainPage ---
    // Этот метод кликает по ссылке "Конструктор" (constructorLink)
    @Step("Кликнуть на 'Конструктор' в шапке")
    public MainPage openMainPage() {
        waitForVisible(constructorLink);
        constructorLink.click();
        return new MainPage(driver);
    }

    // --- ОБЪЯВЛЕНИЕ МЕТОДА openHomePage ---
    // Этот метод кликает по логотипу (logoButton)
    @Step("Кликнуть на логотип Stellar Burgers")
    public MainPage openHomePage() {
        waitForVisible(logoButton);
        logoButton.click();
        return new MainPage(driver);
    }

    @Step("Проверить, что отображается кнопка выхода (вход в профиль успешен)")
    public boolean isLogoutButtonDisplayed() {
        waitForVisible(logoutButton);
        return logoutButton.isDisplayed();
    }
}