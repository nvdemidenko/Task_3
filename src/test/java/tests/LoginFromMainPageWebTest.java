package tests;

import driver.WebDrivers;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.ProfilePage;
import api.UserClient;
import models.User;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Allure для JUnit 5
@ExtendWith(io.qameta.allure.junit5.AllureJunit5.class)
public class LoginFromMainPageWebTest {
    private WebDriver driver;
    private UserClient userClient;
    private User user;
    private String authToken;

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        driver = WebDrivers.getDriver(browser);
        driver.manage().window().maximize();

        userClient = new UserClient();
        createTestUser();
    }

    private void createTestUser() {
        user = new User("auto_" + System.currentTimeMillis() + "@yandex.ru", "password123", "TestUser");
        Response response = userClient.register(user);
        authToken = response.then().extract().path("accessToken");
    }

    @AfterEach
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

        // ИСПРАВЛЕНО: Теперь login возвращает ProfilePage (согласно LoginPage.java)
        ProfilePage profilePage = loginPage.login(user.getEmail(), user.getPassword()).openProfile();

        // ИСПРАВЛЕНО: assertTrue(условие, сообщение)
        assertTrue(profilePage.isLogoutButtonDisplayed(), "Не удалось войти в аккаунт");
    }
}