package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//label[text()='Email']/following-sibling::input")
    private WebElement emailField;

    @FindBy(xpath = "//label[text()='Пароль']/following-sibling::input")
    private WebElement passwordField;

    @FindBy(xpath = "//button[text()='Войти']")
    private WebElement loginButton;

    @FindBy(xpath = "//a[text()='Зарегистрироваться']")
    private WebElement registerLink;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Ввести email: {email} и пароль: {password}")
    public MainPage login(String email, String password) {
        waitForVisible(emailField);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        loginButton.click();
        return new MainPage(driver);
    }

    @Step("Кликнуть на ссылку 'Зарегистрироваться'")
    public RegisterPage openRegisterPage() {
        waitForVisible(registerLink);
        registerLink.click();
        return new RegisterPage(driver);
    }

    @Step("Проверить отображение кнопки 'Войти'")
    public boolean isLoginButtonDisplayed() {
        waitForVisible(loginButton);
        return loginButton.isDisplayed();
    }
}