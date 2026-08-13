package org.example;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTests {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void task1_1_addProduct() {
        String productName = "Selenium товар";
        String productPrice = "100";

        driver.get("http://localhost:8080/admin");

        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("secret123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        driver.findElement(By.id("n-name")).sendKeys(productName);
        driver.findElement(By.id("n-price")).sendKeys(productPrice);
        driver.findElement(By.id("add-btn")).click();

        driver.get("http://localhost:8080/");

        assertTrue(
                driver.findElement(
                        By.cssSelector(".product-card[data-name='" + productName + "']")
                ).isDisplayed()
        );
    }

    @Test
    void task1_2_addToCart() {
        driver.get("http://localhost:8080/");

        String productName = driver
                .findElement(By.cssSelector(".product-card h4"))
                .getText();

        driver.findElement(
                By.cssSelector(".product-card button[data-action='add-to-cart']")
        ).click();

        driver.findElement(By.id("open-cart-btn")).click();

        assertTrue(
                driver.findElement(By.id("cart-items"))
                        .getText()
                        .contains(productName)
        );
    }

    @Test
    void task1_3_wrongLogin() {
        driver.get("http://localhost:8080/admin");

        driver.findElement(By.id("username")).sendKeys("wrong");
        driver.findElement(By.id("password")).sendKeys("wrong");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        assertTrue(
                driver.findElement(By.id("username")).isDisplayed()
        );
    }

    @Test
    void task1_4_cartAfterRefresh() {
        driver.get("http://localhost:8080/");

        String productName = driver
                .findElement(By.cssSelector(".product-card h4"))
                .getText();

        driver.findElement(
                By.cssSelector(".product-card button[data-action='add-to-cart']")
        ).click();

        driver.navigate().refresh();

        driver.findElement(By.id("open-cart-btn")).click();

        assertTrue(
                driver.findElement(By.id("cart-items"))
                        .getText()
                        .contains(productName)
        );
    }
}
