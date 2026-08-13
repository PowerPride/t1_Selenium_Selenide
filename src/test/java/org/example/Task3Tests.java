package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class Task3Tests {

    @Test
    void task3_1_buyThreeItems() {
        open("http://localhost:8080/");

        var cards = $$(".product-card");

        var product = cards.stream()
                .filter(card -> Integer.parseInt(card.getAttribute("data-price")) <= 100)
                .findFirst()
                .orElseThrow();

        product.$(".qty-input").clear();
        product.$(".qty-input").sendKeys("3");
        product.$("button[data-action='add-to-cart']").click();

        $("#open-cart-btn").click();
        $("#makeOrder").click();

        $("#toast-container")
                .shouldHave(text("Заказ принят в обработку"));
    }

    @Test
    void task3_2_checkTotalPrice() {
        open("http://localhost:8080/");

        var cards = $$(".product-card")
                .shouldHave(sizeGreaterThanOrEqual(2));

        int firstPrice = Integer.parseInt(
                cards.get(0).getAttribute("data-price")
        );

        int secondPrice = Integer.parseInt(
                cards.get(1).getAttribute("data-price")
        );

        int expectedTotal = firstPrice + secondPrice;

        cards.get(0)
                .$("button[data-action='add-to-cart']")
                .click();

        cards.get(1)
                .$("button[data-action='add-to-cart']")
                .click();

        $("#open-cart-btn").click();

        $("#total-price")
                .shouldHave(text(String.valueOf(expectedTotal)));
    }

    @Test
    void task3_3_addProductNotification() {
        String productName = "Товар " + System.currentTimeMillis();

        open("http://localhost:8080/admin");

        $("#username").sendKeys("admin");
        $("#password").sendKeys("secret123");
        $("button[type='submit']").click();

        $("#n-name").sendKeys(productName);
        $("#n-price").sendKeys("90");
        $("#add-btn").click();

        $("#toast-container")
                .shouldHave(text("Товар успешно добавлен!"));
    }

    @Test
    void task3_4_editProduct() {
        String productName = "Товар для редактирования " + System.currentTimeMillis();
        String updatedName = productName + " обновлён";

        open("http://localhost:8080/admin");

        $("#username").sendKeys("admin");
        $("#password").sendKeys("secret123");
        $("button[type='submit']").click();

        // Создаём товар специально для этого теста
        $("#n-name").sendKeys(productName);
        $("#n-price").sendKeys("50");
        $("#add-btn").click();

        // Находим ID созданного товара
        String productId = $("input[value='" + productName + "']")
                .getAttribute("id")
                .replace("nm-", "");

        // Редактируем название и цену
        $("#nm-" + productId).clear();
        $("#nm-" + productId).sendKeys(updatedName);

        $("#pr-" + productId).clear();
        $("#pr-" + productId).sendKeys("70");

        $("button[data-action='update'][data-id='" + productId + "']")
                .click();

        // Проверяем изменения на витрине
        open("http://localhost:8080/");

        $(".product-card[data-id='" + productId + "']")
                .shouldHave(text(updatedName))
                .shouldHave(text("70"));
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}
