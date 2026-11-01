package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends BasePage {

    @FindBy(xpath = "//label[text()='Имя']/following-sibling::input")
    private WebElement nameField;

    @FindBy(xpath = "//label[text()='Email']/following-sibling::input")
    private WebElement emailField;

    @FindBy(xpath = "//label[text()='Пароль']/following-sibling::input")
    private WebElement passwordField;

    @FindBy(xpath = "//button[text()='Зарегистрироваться']")
    private WebElement registerButton;

    @FindBy(xpath = "//p[text()='Некорректный пароль']")
    private WebElement invalidPasswordError;

    @FindBy(xpath = "//a[text()='Войти']")
    private WebElement loginLink;

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Step("Заполнить форму регистрации: имя - {name}, email - {email}, пароль - {password}")
    public RegisterPage fillRegisterForm(String name, String email, String password) {
        waitForVisible(nameField);
        nameField.sendKeys(name);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        return this;
    }

    @Step("Кликнуть кнопку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        // ВАЖНО: Используем ожидание кликабельности для стабильности
        wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        registerButton.click();
    }

    // --- ИСКОМЫЙ МЕТОД ---
    @Step("Проверить отображение кнопки 'Войти' на странице регистрации")
    public boolean isLoginButtonDisplayed() {
        waitForVisible(loginLink);
        return loginLink.isDisplayed();
    }

    @Step("Проверить наличие ошибки некорректного пароля")
    public boolean isInvalidPasswordErrorDisplayed() {
        waitForVisible(invalidPasswordError);
        return invalidPasswordError.isDisplayed();
    }
}