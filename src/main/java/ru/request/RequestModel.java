package ru.request;

import io.restassured.authentication.*;
import io.restassured.builder.*;
import io.restassured.filter.log.*;
import io.restassured.http.ContentType;
import io.restassured.specification.*;

import static ru.EndPoints.*;

public abstract class RequestModel {

    public static RequestSpecification getRequestSpecification() {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(USER_LOGIN);
        authScheme.setPassword(USER_PASS);
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(BASE_PATH)
                .setAuth(authScheme)
               // .addHeader("Authorization", "Basic ZmludGVjaDoxcTJ3M2Uh") //for token auth demonstrate
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                //.addFilter(new ErrorLoggingFilter())  //for error demonstrate
                .build();
    }

    public static ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }

}
