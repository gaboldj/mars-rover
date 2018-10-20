package app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import constants.PlateauSize;
import testenv.TestHelper;

public class InputHandlerTest extends TestHelper {

    private InputHandler underTest = new InputHandler();

    @Test
    public void test_configurePlateau_with_valid_user_input() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        String userInput = xValue + " " + yValue;

        // When:
        underTest.configurePlateau(userInput);

        // Then:
        assertThat(PlateauSize.maxXValue).isEqualTo(xValue);
        assertThat(PlateauSize.maxYValue).isEqualTo(yValue);
    }

    @Test
    public void test_configurePlateau_with_invalid_xValue() {
        // Given:
        int xValue = -1 * anyXCoordinate();
        int yValue = anyYCoordinate();
        String userInput = xValue + " " + yValue;

        // When:
        try {
            underTest.configurePlateau(userInput);
        } catch (IllegalArgumentException ex) {
            // Then:
            assertThat(ex.getMessage()).contains("Invalid plateau size");
            return;
        }

        fail("Exception expected!");
    }

    @Test
    public void test_configurePlateau_with_invalid_yValue() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = -1 * anyYCoordinate();
        String userInput = xValue + " " + yValue;

        // When:
        try {
            underTest.configurePlateau(userInput);
        } catch (IllegalArgumentException ex) {

            // Then:
            assertThat(ex.getMessage()).contains("Invalid plateau size");
            return;
        }

        fail("Exception expected!");
    }

    @Test
    public void test_configurePlateau_with_insufficient_values() {
        // Given:
        int xValue = anyXCoordinate();
        String userInput = xValue + " ";

        // When:
        try {
            underTest.configurePlateau(userInput);
        } catch (IllegalArgumentException ex) {

            // Then:
            assertThat(ex.getMessage()).contains("Invalid plateau size");
            return;
        }

        fail("Exception expected!");
    }

    @Test
    public void test_configurePlateau_with_too_many_values() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        int another = anyXCoordinate();
        String userInput = xValue + " " + yValue + " " + another;

        // When:
        try {
            underTest.configurePlateau(userInput);
        } catch (IllegalArgumentException ex) {

            // Then:
            assertThat(ex.getMessage()).contains("Invalid plateau size");
            return;
        }

        fail("Exception expected!");
    }
}
