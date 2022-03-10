package guru.qa;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.Keys;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;

@DisplayName("Сайт Почты России")
public class ParametrMailTrackingTest {


    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }

    @ValueSource(strings = {"RF776045417SG", "RF775860395SG", "RF775827050SG"})
    @ParameterizedTest(name = "для \"{0}\"")
    @DisplayName("Проверка отображения трекинга почтовых отправлений")
    void commonSearchTest(String testData) {
        Selenide.open("https://www.pochta.ru/");
        Selenide.$("#barcode").setValue(testData).pressEnter();
        Selenide.$$("#page-tracking").find(text(testData)).shouldBe(visible);
    }

    @CsvSource(value = {
            "г Москва|обл Калининградская, г Калининград|2|2-4 дня",
            "г Москва|край Приморский, г Владивосток|2|8-10 дней"
    },
            delimiterString = "|")
    @ParameterizedTest(name = "посылки обыкновенной \n" +
            "   весом 2 кг из Москвы в {0}")
    @DisplayName("Проверка сроков доставки на сайте")
    void complexSearchTest(String addressFrom, String addressTo, String weight, String expectedDeliveryDate) {
        Selenide.open("https://www.pochta.ru/parcels/");
        Selenide.$("input[name=AddressFrom]").sendKeys("" + Keys.LEFT_ALT+Keys.ENTER+Keys.DELETE);
        Selenide.$("input[name=AddressFrom]").sendKeys(addressFrom);
        Selenide.$(withText(addressFrom)).click();
        ;
        Selenide.$("input[name=AddressTo]").setValue(addressTo);
        Selenide.$(withText(addressTo)).click();
        Selenide.$("#Weight").sendKeys(String.valueOf(weight)+Keys.ENTER);
        Selenide.$("#parcel-summary").find(withText(expectedDeliveryDate));

    }

    static Stream<Arguments> mixedArgumentsTest() {
        return Stream.of(
                Arguments.of("г Москва","обл Калининградская, г Калининград",2, "2-4 дня"),
                Arguments.of("г Москва","край Приморский, г Владивосток",2,"8-10 дней")
        );
    }
    @Disabled
    @MethodSource(value = "mixedArgumentsTest")
    @ParameterizedTest(name = "{3}")
    @DisplayName("Проверка сроков доставки на сайте")
    void mixedArgumentTest(String addressFrom, String addressTo, int weight, String expectedDeliveryDate) {

        Selenide.$("input[name=AddressFrom]").sendKeys("" + Keys.LEFT_ALT+Keys.ENTER+Keys.DELETE);
        Selenide.$("input[name=AddressFrom]").sendKeys(addressFrom);
        Selenide.$(withText(addressFrom)).click();
        ;
        Selenide.$("input[name=AddressTo]").setValue(addressTo);
        Selenide.$(withText(addressTo)).click();
        Selenide.$("#Weight").sendKeys(String.valueOf(weight)+Keys.ENTER);
        Selenide.$("#parcel-summary").find(withText(expectedDeliveryDate));
    }
}
