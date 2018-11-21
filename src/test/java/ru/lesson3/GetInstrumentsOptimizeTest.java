package ru.lesson3;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.entity.TradeInfo;
import ru.request.RequestModel;

import static io.restassured.RestAssured.given;

public class GetInstrumentsOptimizeTest {

    @Test
    public void getInstrumentsSimpleTest(){
        given().spec(RequestModel.getRequestSpecification())
                .pathParam("instrument_id", "TCS_SPBXM")
                .queryParam("request_id", "6f994192-e701-11e8-9f32-f2801f1b9fd1")
                .queryParam("system_code", "T-API")
                .get("/instruments/{instrument_id}")
                .then()
                .specification(RequestModel.getResponseSpecification())
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void getInstrumentsExtractTest(){
        TradeInfo tradeInfo = given().spec(RequestModel.getRequestSpecification())
                .pathParam("instrument_id", "TCS_SPBXM")
                .queryParam("request_id", "6f994192-e701-11e8-9f32-f2801f1b9fd1")
                .queryParam("system_code", "T-API")
                .get("/instruments/{instrument_id}")
                .then()
                .specification(RequestModel.getResponseSpecification())
                .assertThat()
                .statusCode(200)
                .body("instrument_id", Matchers.equalTo("TCS_SPBXM"))
                .extract()
                .as(TradeInfo.class);
        System.out.println("Result: " + tradeInfo);
    }

    @Test
    public void getInstrumentsErrorTest(){
        given().spec(RequestModel.getRequestSpecification())
                .pathParam("instrument_id", "QWERTY")
                .queryParam("request_id", "6f994192-e701-11e8-9f32-f2801f1b9fd1")
                .queryParam("system_code", "T-API")
                .get("/instruments/{instrument_id}")
                .then()
                .specification(RequestModel.getResponseSpecification())
                .statusCode(404)
                .body("error", Matchers.equalTo("unable to get instrument: unable to query " +
                        "instrument status: instrument not found"));
    }

    @Test
    public void getInstrumentsExtractCfiCodeTest(){
        String cfiCode = given().spec(RequestModel.getRequestSpecification())
                .pathParam("instrument_id", "TCS_SPBXM")
                .queryParam("request_id", "6f994192-e701-11e8-9f32-f2801f1b9fd1")
                .queryParam("system_code", "T-API")
                .get("/instruments/{instrument_id}")
                .then()
                .specification(RequestModel.getResponseSpecification())
                .assertThat()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("instrument_info.json"))
                .extract()
                .jsonPath()
                .getString("cfi_code");
        System.out.println("Result: " + cfiCode);
    }
}
