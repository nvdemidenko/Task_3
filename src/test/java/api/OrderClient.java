package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {

    private static final String ORDERS_PATH = "/api/orders";

    @Step("GET /api/ingredients — получить список ингредиентов")
    public Response getIngredients() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get("/api/ingredients");
    }

    @Step("POST /api/orders с токеном — создать заказ от имени пользователя")
    public Response createOrder(String authToken, Order orderData) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", authToken)
                .body(orderData)
                .when()
                .post(ORDERS_PATH);
    }

    @Step("POST /api/orders без токена — создать заказ анонимно")
    public Response createOrderWithoutAuth(Order orderData) {
        return given()
                .spec(getBaseSpec())
                .body(orderData)
                .when()
                .post(ORDERS_PATH);
    }

    @Step("GET /api/orders с токеном — получить список заказов пользователя")
    public Response getOrders(String authToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", authToken)
                .when()
                .get(ORDERS_PATH);
    }

    @Step("GET /api/orders без токена — получить список заказов анонимно")
    public Response getOrdersWithoutAuth() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_PATH);
    }
}