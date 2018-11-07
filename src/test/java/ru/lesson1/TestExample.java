package ru.lesson1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static ru.lesson1.TestClass.testFunction;

public class TestExample {

    @Test
    public void testExampleMethod(){
        double result = testFunction(2);
        assertEquals("Wrong value!", result, 4, 0);
    }
}