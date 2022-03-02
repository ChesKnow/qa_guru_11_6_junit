package guru.qa;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;

public class ParameterizedSearchTest {

    @BeforeEach
    void precondition() {
        Selenide.open("https://ya.ru/");
    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }
    @ValueSource(strings = {"Selenide", "JUnit 5"})
    @ParameterizedTest(name = "Проверка отображаемый результатов поиска в яндексе слова \"{0}\"")
    void commonSearchTest(String testData) {
        Selenide.$("#text").setValue(testData);
        Selenide.$("button[type='submit']").click();
        Selenide.$$("li.serp-item").find(text(testData)).shouldBe(visible);
    }

    @CsvSource(value = {
            "Selenide| Concise UI tests with Java",
            "JUnit 5| Intellij IDEA"
    },
    delimiterString = "|")
    @ParameterizedTest(name = "Проверка отображаемый результатов поиска в яндексе слова \"{0}\"")
    void complexSearchTest(String testData, String expectedText) {
        Selenide.$("#text").setValue(testData);
        Selenide.$("button[type='submit']").click();
        Selenide.$$("li.serp-item").find(text(expectedText)).shouldBe(visible);
    }

    static Stream<Arguments> mixedArgumentsTest() {
        return Stream.of(
                Arguments.of("Selenide", List.of(1,2,4), true),
                Arguments.of("JUnit 5", List.of(5,6,7), false)
        );
    }

    @MethodSource(value = "mixedArgumentsTest")
    @ParameterizedTest(name = "Name {2}")
    void mixedArgumentTest(String firstArg, List<Integer> secondArgs, boolean aBooleanValue) {
        System.out.println("String: " + firstArg + "List: " + secondArgs.toString() + "boolean: " + aBooleanValue);
    }

}
