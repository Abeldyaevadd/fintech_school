package ru.lesson1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.example.TestClass.testFunction;

public class TestExample {

    @Test
    public void testExampleMethod() {
        double result = testFunction(2);
        assertEquals(result, 4, "Wrong value!");
    }
}