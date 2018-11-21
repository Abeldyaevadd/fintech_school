package ru.lesson3;

import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import ru.entity.TradeInfo;
import ru.request.RequestModel;

import static io.restassured.RestAssured.given;
import static ru.request.InstrumentRequest.getInstrumentRequest;
@Epic("Allure examples")
@Feature("Описание инструмента по идентификатору")
@Story("Улучшенный вариант тестов")
@DisplayName("Тесты получения информации по ценным бумагам")
public class GetInstrumentsOptimizeTest {

    @Test
    @DisplayName("Получение информации о ценной бумаге")
    @Description("Позитивный сценарий запроса информации о ценной бумаге по ее instrument_id")
    public void getInstrumentsSimpleTest(){
        given().spec(RequestModel.getRequestSpecification())
                .pathParam("instrument_id", "TCS_SPBXM")
                .queryParam("request_id", "6f994192-e701-11e8-9f32-f2801f1b9fd1")
                .queryParam("system_code", "T-API")
                .get("/instruments/{instrument_id}")
                .then()
                .specification(RequestModel.getResponseSpecification())
                .assertThat()
                .statusCode(404);
    }

    @Test
    @DisplayName("Получение информации о ценной бумаге с созданием класса TradeInfo")
    @Description("Позитивный сценарий запроса информации о ценной бумаге по ее instrument_id")
    public void getInstrumentsExtractTest(){
        TradeInfo tradeInfo = getInstrumentRequest("TCS_SPBXM",
                "6f994192-e701-11e8-9f32-f2801f1b9fd1", "T-API", 200)
                .then()
                .body("instrument_id", Matchers.equalTo("TCS_SPBXM"))
                .extract()
                .as(TradeInfo.class);
        System.out.println("Result: " + tradeInfo);
        createAttachment(tradeInfo.toString());
    }

    @Test
    @DisplayName("Несуществующий instrument_id")
    @Description("Получение ошибки при попытке получить информацию о ценной бумаге по несуществующему instrument_id")
    public void getInstrumentsErrorTest(){
        getInstrumentRequest("QWERTY",
                "6f994192-e701-11e8-9f32-f2801f1b9fd1", "T-API", 404)
                .then()
                .body("error", Matchers.equalTo("unable to get instrument: unable to query " +
                        "instrument status: instrument not found"));
    }

    @Test
    @Link("https://fintech-trading-qa.tinkoff.ru/v1/md/docs/#/Instruments/md-instrument")
    @Issue("TST-123")
    @TmsLink("123456789")
    @DisplayName("Получение информации о ценной бумаге с получением cfi_code")
    @Description("Позитивный сценарий запроса информации о ценной бумаге по ее instrument_id")
    public void getInstrumentsExtractCfiCodeTest(){
        String cfiCode = getInstrumentRequest("TCS_SPBXM",
                "6f994192-e701-11e8-9f32-f2801f1b9fd1", "T-API", 200)
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("instrument_info.json"))
                .extract()
                .jsonPath()
                .getString("cfi_code");
        System.out.println("Result: " + cfiCode);
    }

    @Attachment("Request")
    private byte[] createAttachment(String attachment) {
        return attachment.getBytes();
    }
}
