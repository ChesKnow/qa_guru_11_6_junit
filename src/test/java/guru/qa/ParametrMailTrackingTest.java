package guru.qa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import helpers.Attach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.closeWebDriver;

@DisplayName("Сайт Почты России")
public class ParametrMailTrackingTest {

    @BeforeAll
    static void startPage() {

        Configuration.browserSize = "1920x1080";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
    }

    @AfterEach
    void addAttachment() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
    }

    @ValueSource(strings = {"RF776045417SG", "RF775860395SG", "RF775827050SG"})
    @ParameterizedTest(name = "для \"{0}\"")
    @DisplayName("Проверка отображения трекинга почтовых отправлений")
    void commonSearchTest(String testData) {
        Selenide.open("https://www.pochta.ru/");
        Selenide.$("#barcode").scrollTo().setValue(testData).pressEnter();
        Selenide.$$("#page-tracking").find(text(testData)).shouldBe(visible);
    }

}
