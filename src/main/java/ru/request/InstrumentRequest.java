package ru.request;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public abstract class InstrumentRequest {

    @Step("Выполняем запрос /instruments/{instrumentId}")
    public static Response getInstrumentRequest(String instrumentId, String requestId, String systemCode, int code) {
        return given().spec(RequestModel.getRequestSpecification())
                .pathParam("instrument_id", instrumentId)
                .queryParam("request_id", requestId)
                .queryParam("system_code", systemCode)
                .get("/instruments/{instrument_id}")
                .then()
                .specification(RequestModel.getResponseSpecification())
                .assertThat()
                .statusCode(code)
                .extract().response();
    }
}