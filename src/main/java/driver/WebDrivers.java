package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDrivers {

    // Приватный конструктор для утилитарного класса
    private WebDrivers() {}

    public static WebDriver getDriver(String browserName) {
        if (browserName == null) {
            browserName = "chrome";
        }

        ChromeOptions options = new ChromeOptions();

        switch (browserName.toLowerCase()) {
            case "yandex":
                String browserBinaryPath = System.getProperty("yandex.path", System.getenv("YANDEX_PATH"));
                if (browserBinaryPath != null && !browserBinaryPath.isEmpty()) {
                    options.setBinary(browserBinaryPath);
                } else {
                    String defaultMacPath = "/Applications/Yandex.app/Contents/MacOS/Yandex";
                    if (new java.io.File(defaultMacPath).exists()) {
                        options.setBinary(defaultMacPath);
                    }
                }
                return new ChromeDriver(options);

            case "chrome":
            default:
                return new ChromeDriver(options);
        }
    }
}