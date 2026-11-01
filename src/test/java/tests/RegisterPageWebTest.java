package tests;

import driver.WebDrivers;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Подключаем Allure для JUnit 5
@ExtendWith(io.qameta.allure.junit5.AllureJunit5.class)
public class RegisterPageWebTest {
    private WebDriver driver;
    private String userEmail;
    private String browser = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        driver = WebDrivers.getDriver(browser);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // --- ВСПОМОГАТЕЛЬНЫЙ МЕТОД ---
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

        // ИСПРАВЛЕНО: сначала условие, потом сообщение
        assertTrue(registerPage.isLoginButtonDisplayed(), "Не произошел переход на страницу входа");
    }

    @Test
    @DisplayName("Ошибка при регистрации с некорректным паролем (менее 6 символов)")
    @Description("Проверка появления ошибки для пароля короче 6 символов")
    public void shouldShowErrorForShortPassword() {
        // Используем хелпер
        RegisterPage registerPage = openRegisterPage();

        registerPage.fillRegisterForm("Тест", "test@yandex.ru", "123");
        registerPage.clickRegisterButton();

        // ИСПРАВЛЕНО: сначала условие, потом сообщение
        assertTrue(registerPage.isInvalidPasswordErrorDisplayed(), "Не отображается ошибка некорректного пароля");
    }
}