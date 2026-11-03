package tests;

import driver.WebDrivers;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Allure для JUnit 5 (необходимо для @Step в PageObject)
@ExtendWith(io.qameta.allure.junit5.AllureJunit5.class)
public class MainPageWebTest {
    private WebDriver driver;
    private final String browser = System.getProperty("browser", "chrome");

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

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    @Description("Проверка переключения вкладки конструктора на Соусы")
    public void shouldOpenSaucesTab() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.openSaucesTab();

        // ИСПРАВЛЕНО: assertTrue(условие, сообщение)
        assertTrue(mainPage.isSaucesTabActive(), "Вкладка 'Соусы' не стала активной");
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    @Description("Проверка переключения вкладки конструктора на Начинки")
    public void shouldOpenFillingsTab() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.openFillingsTab();

        assertTrue(mainPage.isFillingsTabActive(), "Вкладка 'Начинки' не стала активной");
    }

    @Test
    @DisplayName("Переход к разделу 'Булки'")
    @Description("Проверка переключения вкладки конструктора на Булки после другой вкладки")
    public void shouldOpenBunsTab() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.openSaucesTab();
        mainPage.openBunsTab();

        assertTrue(mainPage.isBunsTabActive(), "Вкладка 'Булки' не стала активной");
    }
}