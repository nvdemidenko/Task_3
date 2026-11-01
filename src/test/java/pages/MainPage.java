package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage {

    @FindBy(xpath = "//button[text()='Войти в аккаунт']")
    private WebElement loginButton;

    @FindBy(xpath = "//p[text()='Личный Кабинет']/parent::a")
    private WebElement personalAccountButton;

    @FindBy(xpath = "//span[text()='Булки']/parent::div")
    private WebElement bunsTab;

    @FindBy(xpath = "//span[text()='Соусы']/parent::div")
    private WebElement saucesTab;

    @FindBy(xpath = "//span[text()='Начинки']/parent::div")
    private WebElement fillingsTab;

    @FindBy(xpath = "//button[text()='Оформить заказ']")
    private WebElement orderButton;

    @FindBy(xpath = "//div[contains(@class, 'current')]/span[text()='Булки']")
    private WebElement activeBunsTab;

    @FindBy(xpath = "//div[contains(@class, 'current')]/span[text()='Соусы']")
    private WebElement activeSaucesTab;

    @FindBy(xpath = "//div[contains(@class, 'current')]/span[text()='Начинки']")
    private WebElement activeFillingsTab;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть главную страницу")
    public MainPage open() {
        driver.get(BASE_URL);
        return this;
    }

    @Step("Кликнуть по кнопке 'Войти в аккаунт'")
    public LoginPage goToLogin() {
        waitForVisible(loginButton);
        loginButton.click();
        return new LoginPage(driver); // Здесь все верно
    }

    @Step("Перейти в личный кабинет (авторизован)")
    public ProfilePage openProfile() {
        waitForVisible(personalAccountButton);
        personalAccountButton.click();
        return new ProfilePage(driver);
    }

    // Метод goToLoginFromHeader удален как дублирующий,
    // если он нужен для теста, можно оставить его, вызвав внутри openProfile()

    @Step("Перейти на вкладку 'Булки'")
    public MainPage openBunsTab() {
        waitForVisible(bunsTab);
        bunsTab.click();
        return this;
    }

    @Step("Перейти на вкладку 'Соусы'")
    public MainPage openSaucesTab() {
        waitForVisible(saucesTab);
        saucesTab.click();
        return this;
    }

    @Step("Перейти на вкладку 'Начинки'")
    public MainPage openFillingsTab() {
        waitForVisible(fillingsTab);
        fillingsTab.click();
        return this;
    }

    // Методы проверки (оставлены без @Step, так как это чистые геттеры/проверки)
    public boolean isBunsTabActive() {
        waitForVisible(activeBunsTab);
        return activeBunsTab.isDisplayed();
    }

    public boolean isSaucesTabActive() {
        waitForVisible(activeSaucesTab);
        return activeSaucesTab.isDisplayed();
    }

    public boolean isFillingsTabActive() {
        waitForVisible(activeFillingsTab);
        return activeFillingsTab.isDisplayed();
    }
}