package tests;

import driver.WebDrivers;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.MainPage;

import static org.junit.Assert.assertTrue;

public class MainPageWebTest {
    private WebDriver driver;
    private final String browser = System.getProperty("browser", "chrome");

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

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    @Description("Проверка переключения вкладки конструктора на Соусы")
    public void shouldOpenSaucesTab() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.openSaucesTab(); // Используем обновленное имя метода из PageObject

        assertTrue("Вкладка 'Соусы' не стала активной", mainPage.isSaucesTabActive());
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    @Description("Проверка переключения вкладки конструктора на Начинки")
    public void shouldOpenFillingsTab() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.openFillingsTab(); // Используем обновленное имя метода из PageObject

        assertTrue("Вкладка 'Начинки' не стала активной", mainPage.isFillingsTabActive());
    }

    @Test
    @DisplayName("Переход к разделу 'Булки'")
    @Description("Проверка переключения вкладки конструктора на Булки после другой вкладки")
    public void shouldOpenBunsTab() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.openSaucesTab(); // сначала переходим в соусы
        mainPage.openBunsTab();   // возвращаемся на булки

        assertTrue("Вкладка 'Булки' не стала активной", mainPage.isBunsTabActive());
    }
}