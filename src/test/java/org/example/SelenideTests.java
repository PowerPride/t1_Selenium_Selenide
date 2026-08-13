package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.switchTo;

public class SelenideTests {

    @Test
    void task2_1_addProduct() {
        String productName = "Selenide товар";
        String productPrice = "120";

        open("http://localhost:8080/admin");

        $("#username").sendKeys("admin");
        $("#password").sendKeys("secret123");
        $("button[type='submit']").click();

        $("#n-name").sendKeys(productName);
        $("#n-price").sendKeys(productPrice);
        $("#add-btn").click();

        open("http://localhost:8080/");

        $(".product-card[data-name='" + productName + "']")
                .shouldBe(visible);
    }

    @Test
    void task2_2_addToCart() {
        open("http://localhost:8080/");

        String productName = $(".product-card h4").getText();

        $(".product-card button[data-action='add-to-cart']").click();

        $("#open-cart-btn").click();

        $("#cart-items")
                .shouldBe(visible)
                .shouldHave(text(productName));
    }

    @Test
    void task2_3_wrongLogin() {
        open("http://localhost:8080/admin");

        $("#username").sendKeys("wrong");
        $("#password").sendKeys("wrong");
        $("button[type='submit']").click();

        $("#username").shouldBe(visible);
    }

    @Test
    void task2_4_cartAfterRefresh() {
        open("http://localhost:8080/");

        String productName = $(".product-card h4").getText();

        $(".product-card button[data-action='add-to-cart']").click();

        refresh();

        $("#open-cart-btn").click();

        $("#cart-items")
                .shouldHave(text(productName));
    }

    @Test
    void task2_5_alertOver300() {
        open("http://localhost:8080/");

        $(".product-card[data-price='4040'] button[data-action='add-to-cart']")
                .click();

        $("#open-cart-btn").click();

        $("#makeOrder").click();

        switchTo().alert().accept();
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}
