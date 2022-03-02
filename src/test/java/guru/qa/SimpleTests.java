package guru.qa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("SimpleTests")
class SimpleTests {

    @Test
    void simpleGreenTest() {
        assertTrue(3>2);
    }

    @Test
    void simpleYellowTest() {
        assertTrue(2>3);
    }

    @Test
    void simpleRedTest() {
        throw new IllegalStateException("Broken:");
    }
}
