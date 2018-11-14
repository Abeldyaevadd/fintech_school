package ru.lesson3;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.entity.TradeInfo;

import static io.restassured.RestAssured.given;

public class GetInstrumentsTest {

    @Test
    public void getInstrumentsSimpleTest(){
        given()
                .log().all()
                .baseUri("https://fintech-trading-qa.tinkoff.ru")
                .basePath("/v1/md")
                .header("Authorization", "Basic ZmludGVjaDoxcTJ3M2Uh")
                .pathParam("instrument_id", "TCS_SPBXM")
                .queryParam("request_id", "6f994192-e701-11e8-9f32-f2801f1b9fd1")
                .queryParam("system_code", "T-API")
                .get("/instruments/{instrument_id}")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void getInstrumentsExtractTest(){
        TradeInfo tradeInfo = given()
                .log().all()
                .baseUri("https://fintech-trading-qa.tinkoff.ru")
                .basePath("/v1/md")
                .header("Authorization", "Basic ZmludGVjaDoxcTJ3M2Uh")
                .pathParam("instrument_id", "TCS_SPBXM")
                .queryParam("request_id", "6f994192-e701-11e8-9f32-f2801f1b9fd1")
                .queryParam("system_code", "T-API")
                .get("/instruments/{instrument_id}")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("instrument_id", Matchers.equalTo("TCS_SPBXM"))
                .extract()
                .jsonPath()
                .getObject("$", TradeInfo.class);
        System.out.println("Result: " + tradeInfo);
    }

    @Test
    public void getInstrumentsErrorTest(){
        given()
                .log().all()
                .baseUri("https://fintech-trading-qa.tinkoff.ru")
                .basePath("/v1/md")
                .header("Authorization", "Basic ZmludGVjaDoxcTJ3M2Uh")
                .pathParam("instrument_id", "Qwerty")
                .queryParam("request_id", "6f994192-e701-11e8-9f32-f2801f1b9fd1")
                .queryParam("system_code", "T-API")
                .get("/instruments/{instrument_id}")
                .then()
                .log().all()
                .assertThat()
                .statusCode(404)
                .body("error", Matchers.equalTo("unable to get instrument: unable to query " +
                        "instrument status: instrument not found"));
    }
}
