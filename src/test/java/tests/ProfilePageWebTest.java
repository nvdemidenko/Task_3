package tests;

import driver.WebDrivers;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
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

public class ProfilePageWebTest {
    private WebDriver driver;
    private UserClient userClient;
    private User user;
    private String accessToken;
    private final String browser = System.getProperty("browser", "chrome");

    @Before
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

    @After
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
        return loginPage.login(user.getEmail(), user.getPassword()).openProfile();
    }

    @Test
    @DisplayName("Переход в личный кабинет по клику")
    @Description("Проверка перехода авторизованного пользователя в личный кабинет")
    public void shouldOpenProfileFromMain() {
        ProfilePage profilePage = loginUser();
        // Исправлена опечатка: profileProfile -> profilePage
        assertTrue("Не удалось войти в аккаунт", profilePage.isLogoutButtonDisplayed());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на 'Конструктор'")
    @Description("Проверка возврата из личного кабинета в конструктора через кнопку в шапке")
    public void shouldOpenMainFromProfileByLink() {
        ProfilePage profilePage = loginUser();
        MainPage mainPage = profilePage.openMainPage();
        assertTrue("Не произошел переход в конструктор", mainPage.isBunsTabActive());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на логотип")
    @Description("Проверка возврата из личного кабинета в конструктора через логотип Stellar Burgers")
    public void shouldOpenMainFromProfileByLogo() {
        ProfilePage profilePage = loginUser();
        MainPage mainPage = profilePage.openHomePage();
        assertTrue("Не произошел переход по логотипу в конструктор", mainPage.isBunsTabActive());
    }

    @Test
    @DisplayName("Выход из аккаунта")
    @Description("Проверка выхода из личного кабинета по кнопке Выйти")
    public void shouldLogoutFromProfile() {
        ProfilePage profilePage = loginUser();
        LoginPage loginPage = profilePage.goToLogin();
        assertTrue("Не произошел выход из аккаунта", loginPage.isLoginButtonDisplayed());
    }
}