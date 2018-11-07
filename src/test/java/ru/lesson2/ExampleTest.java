package ru.lesson2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import ru.annotation.Smoke;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.example.TestClass.testFunction;

@Tag("pow")
@DisplayName("TestExampleClass")
public class ExampleTest {

    @Test
    @Smoke
    public void smokeExampleTest(){
        System.out.println("\n============ Run smoke tests ============\n");
        double result = testFunction(2);
        assertEquals(4, result,"Wrong value!");
        assertTrue(result==4, "Wrong value!");
        assertNotNull(result, "Wrong value!");
    }

    @Test
    @Disabled("Disabled test")
    public void disabledExampleTest(){
        double result = testFunction(2);
        assertEquals(4, result,"Wrong value!");
    }

    @Test
    @Tag("integration")
    public void tagExampleTest(){
        System.out.println("\n============ Run integration tests ============\n");
        double result = testFunction(2);
        assertEquals(4, result,"Wrong value!");
    }

    @ParameterizedTest(name = "Check value = ''{0}''")
    @DisplayName("Check pow function with value source")
    @ValueSource(doubles = {2, 4})
    @Disabled("Disabled test")
    public void testExampleValueTest(double value){
        double result = testFunction(value);
        assertNotNull(result, "Wrong value!");
    }

    @ParameterizedTest(name = "Check pow(''{0}'') = ''{1}''")
    @DisplayName("Check pow function with method source")
    @MethodSource("testExampleProvider")
    public void testExampleMethodTest(double value, double expectedResult){
        double result = testFunction(value);
        assertEquals(expectedResult, result, "Wrong value!");
    }

    private static Stream<Arguments> testExampleProvider() {
        double value = new Random().nextDouble();
        return Stream.of(
                Arguments.of(2.0, 4.0),
                Arguments.of(value, (value*value)));
    }
}