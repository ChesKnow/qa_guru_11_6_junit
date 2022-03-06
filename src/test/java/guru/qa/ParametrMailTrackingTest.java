package guru.qa;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.Keys;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;

public class ParametrMailTrackingTest {
    @BeforeEach
    void precondition() {
        Selenide.open("https://www.pochta.ru/parcels/");
    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }

    @ValueSource(strings = {"RF776045417SG", "RF775860395SG", "RF775827050SG"})
    @ParameterizedTest(name = "Проверка отображения трекинга почтовых отправлений на сайте Почты России для \"{0}\"")
    void commonSearchTest(String testData) {
        Selenide.$("#barcode").setValue(testData).pressEnter();
        Selenide.$$("#page-tracking").find(text(testData)).shouldBe(visible);
    }

    @CsvSource(value = {
            "обл Калининградская, г Калининград|2|2-4 дня",
            "край Приморский, г Владивосток|2|8-10 дней"
    },
            delimiterString = "|")
    @ParameterizedTest(name = "Проверка правильности расчета срока доставки посылки обыкновенной \n" +
            "   весом 2 кг из Москвы в {0}")
    void complexSearchTest(String addressCity, String weight, String expectedDeliveryDate) {
        Selenide.$("input[name=AddressTo]").sendKeys(addressCity+ Keys.ENTER);
        Selenide.$("#Weight").scrollTo().sendKeys(weight+Keys.ENTER);
        Selenide.$("#parcel-summary").shouldHave(text(expectedDeliveryDate));

    }

    static Stream<Arguments> mixedArgumentsTest() {
        return Stream.of(
                Arguments.of("обл Калининградская, г Калининград",2, "2-4 дня"),
                Arguments.of("край Приморский, г Владивосток",2,"8-10 дней")
        );
    }
    @MethodSource(value = "mixedArgumentsTest")
    @ParameterizedTest(name = "{2}")
    void mixedArgumentTest(String addressCity, int weight, String expectedDeliveryDate) {

        Selenide.$("input[name=AddressTo]").setValue(addressCity).click();
        Selenide.$(withText(addressCity)).click();
        Selenide.$("#Weight").sendKeys(String.valueOf(weight)+Keys.ENTER);
        Selenide.$("#parcel-summary").shouldHave(text(expectedDeliveryDate));
    }
}
