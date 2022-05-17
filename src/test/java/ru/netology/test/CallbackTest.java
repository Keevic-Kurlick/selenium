package ru.netology.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp2() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldTestPositiveCase() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Андрей");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79998883344");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldTestValidationEmptyName() {
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79998883344");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.tagName("button")).click();
        String sub = driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", sub);
    }

    @Test
    public void shouldTestValidationEmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Андрей");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.tagName("button")).click();
        String sub = driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", sub);
    }
    @Test
    public void shouldTestValidationInvalidName() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("John");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79998883344");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.tagName("button")).click();
        String sub = driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", sub);
    }

    @Test
    public void shouldTestValidationInvalidPhone() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Андрей");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("telefon");
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.tagName("button")).click();
        String sub = driver.findElement(By.cssSelector("[data-test-id=\"phone\"] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", sub);
    }

    @Test
    public void shouldTestValidationEmptyCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Андрей");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79012345678");
        driver.findElement(By.tagName("button")).click();
        assertNotNull(driver.findElement(By.className("input_invalid1")));
    }

    @Test
    public void shouldTestValidationEmptyAll() {
        driver.findElement(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        driver.findElement(By.tagName("button")).click();
        String sub = driver.findElement(By.cssSelector("[data-test-id=\"name\"] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", sub);
    }
}
