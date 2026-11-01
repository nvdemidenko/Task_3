package tests;

import driver.WebDrivers;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;

import static org.junit.Assert.assertTrue;

public class RegisterPageWebTest {
    private WebDriver driver;
    private String userEmail;
    private String browser = System.getProperty("browser", "chrome");

    @Before
    public void setUp() {
        driver = WebDrivers.getDriver(browser);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // --- ВСПОМОГАТЕЛЬНЫЙ МЕТОД (решение проблемы дублирования) ---
    private RegisterPage openRegisterPage() {
        MainPage mainPage = new MainPage(driver).open();
        LoginPage loginPage = mainPage.goToLogin();
        return loginPage.openRegisterPage();
    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    @Description("Проверка успешной регистрации с валидными данными")
    public void shouldRegisterUserSuccessfully() {
        userEmail = "auto_" + System.currentTimeMillis() + "@yandex.ru";

        // Используем хелпер
        RegisterPage registerPage = openRegisterPage();

        registerPage.fillRegisterForm("Тест", userEmail, "password123");
        registerPage.clickRegisterButton();

        // После успешной регистрации мы должны оказаться на странице входа
        assertTrue("Не произошел переход на страницу входа", registerPage.isLoginButtonDisplayed());
    }

    @Test
    @DisplayName("Ошибка при регистрации с некорректным паролем (менее 6 символов)")
    @Description("Проверка появления ошибки для пароля короче 6 символов")
    public void shouldShowErrorForShortPassword() {
        // Используем хелпер
        RegisterPage registerPage = openRegisterPage();

        registerPage.fillRegisterForm("Тест", "test@yandex.ru", "123");
        registerPage.clickRegisterButton();

        assertTrue("Не отображается ошибка некорректного пароля", registerPage.isInvalidPasswordErrorDisplayed());
    }
}