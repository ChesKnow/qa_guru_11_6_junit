package guru.qa;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Class with simple tests")
public class SimpleTests {

    @Test
    @DisplayName("Awaiting positive results")
    void simpleGreenTest() {
        assertTrue(3>2);
    }

    @Test
    @DisplayName("Awaiting negative results")
    void simpleYellowTest() {
        assertTrue(2 > 3);
    }

    @Test
    @Disabled("bug: JIRA-234345")
    void simpleRedTest() {
        throw new IllegalStateException("Broken:");
    }
}
