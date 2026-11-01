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
import pages.ProfilePage;
import api.UserClient;
import models.User;

import static org.junit.Assert.assertTrue;

public class LoginFromMainPageWebTest {
    private WebDriver driver;
    private UserClient userClient;
    private User user; // Переименовано из userData
    private String authToken;

    @Before
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        driver = WebDrivers.getDriver(browser);
        driver.manage().window().maximize();

        userClient = new UserClient();
        createTestUser();
    }

    private void createTestUser() {
        user = new User("auto_" + System.currentTimeMillis() + "@yandex.ru", "password123", "TestUser");
        var response = userClient.register(user);
        authToken = response.then().extract().path("accessToken");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (authToken != null) {
            userClient.delete(authToken);
        }
    }

    @Test
    @DisplayName("Вход по кнопке 'Войти в аккаунт' на главной")
    @Description("Проверка логина через кнопку Войти в аккаунт на главной странице")
    public void shouldLoginFromMainPage() {
        LoginPage loginPage = new MainPage(driver).open().goToLogin();

        // ИСПРАВЛЕНО: Теперь login возвращает ProfilePage
        ProfilePage profilePage = loginPage.login(user.getEmail(), user.getPassword()).openProfile();

        // ИСПРАВЛЕНО: Проверяем наличие кнопки выхода в профиле
        assertTrue("Не удалось войти в аккаунт", profilePage.isLogoutButtonDisplayed());
    }
}