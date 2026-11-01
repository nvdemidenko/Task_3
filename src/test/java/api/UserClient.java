package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Credentials;
import models.User;

import static io.restassured.RestAssured.given;

public class UserClient extends RestClient {

    private static final String USER_REGISTER_PATH = "/api/auth/register";
    private static final String USER_LOGIN_PATH = "/api/auth/login";
    private static final String USER_PATH = "/api/auth/user";

    @Step("POST /api/auth/register — создать пользователя")
    public Response register(User userData) {
        return given()
                .spec(getBaseSpec())
                .body(userData)
                .when()
                .post(USER_REGISTER_PATH);
    }

    @Step("POST /api/auth/login — авторизовать пользователя")
    public Response login(Credentials credentialsData) {
        return given()
                .spec(getBaseSpec())
                .body(credentialsData)
                .when()
                .post(USER_LOGIN_PATH);
    }

    @Step("DELETE /api/auth/user с токеном — удалить пользователя")
    public Response delete(String authToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", authToken)
                .when()
                .delete(USER_PATH);
    }

    @Step("PATCH /api/auth/user с токеном — обновить данные пользователя")
    public Response update(String authToken, User userData) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", authToken)
                .body(userData)
                .when()
                .patch(USER_PATH);
    }

    @Step("PATCH /api/auth/user без токена — обновить данные пользователя анонимно")
    public Response updateWithoutAuth(User userData) {
        return given()
                .spec(getBaseSpec())
                .body(userData)
                .when()
                .patch(USER_PATH);
    }
}