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

// Подключаем Allure для JUnit 5
@ExtendWith(io.qameta.allure.junit5.AllureJunit5.class)
public class ProfilePageWebTest {
    private WebDriver driver;
    private UserClient userClient;
    private User user;
    private String accessToken;
    private final String browser = System.getProperty("browser", "chrome");

    @BeforeEach
    public void setUp() {
        driver = WebDrivers.getDriver(browser);
        driver.manage().window().maximize();

        userClient = new UserClient();
        createTestUser();
    }

    private void createTestUser() {
        user = new User("auto_" + System.currentTimeMillis() + "@yandex.ru", "password123", "TestUser");
        Response response = userClient.register(user);
        accessToken = response.jsonPath().getString("accessToken");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    private ProfilePage loginUser() {
        MainPage mainPage = new MainPage(driver).open();
        LoginPage loginPage = mainPage.goToLogin();
        // Метод login() в LoginPage (JUnit 5 версии) должен возвращать MainPage,
        // поэтому мы вызываем openProfile() в конце цепочки
        return loginPage.login(user.getEmail(), user.getPassword()).openProfile();
    }

    @Test
    @DisplayName("Переход в личный кабинет по клику")
    @Description("Проверка перехода авторизованного пользователя в личный кабинет")
    public void shouldOpenProfileFromMain() {
        ProfilePage profilePage = loginUser();
        // Исправлен assertTrue: (условие, сообщение)
        assertTrue(profilePage.isLogoutButtonDisplayed(), "Не удалось войти в аккаунт");
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на 'Конструктор'")
    @Description("Проверка возврата из личного кабинета в конструктора через кнопку в шапке")
    public void shouldOpenMainFromProfileByLink() {
        ProfilePage profilePage = loginUser();
        MainPage mainPage = profilePage.openMainPage();
        assertTrue(mainPage.isBunsTabActive(), "Не произошел переход в конструктор");
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на логотип")
    @Description("Проверка возврата из личного кабинета в конструктора через логотип Stellar Burgers")
    public void shouldOpenMainFromProfileByLogo() {
        ProfilePage profilePage = loginUser();
        MainPage mainPage = profilePage.openHomePage();
        assertTrue(mainPage.isBunsTabActive(), "Не произошел переход по логотипу в конструктор");
    }

    @Test
    @DisplayName("Выход из аккаунта")
    @Description("Проверка выхода из личного кабинета по кнопке Выйти")
    public void shouldLogoutFromProfile() {
        ProfilePage profilePage = loginUser();
        LoginPage loginPage = profilePage.goToLogin();
        assertTrue(loginPage.isLoginButtonDisplayed(), "Не произошел выход из аккаунта");
    }
}