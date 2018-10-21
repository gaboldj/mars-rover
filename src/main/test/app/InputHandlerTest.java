package app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import constants.Instruction;
import constants.Orientation;
import constants.PlateauSize;
import model.Rover;
import testenv.TestHelper;

public class InputHandlerTest extends TestHelper {

    private InputHandler underTest = new InputHandler();

    //
    // CONFIGURE PLATEAU
    //
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

    //
    // CHECK INPUT VALUES FOR ROVER
    //
    @Test
    public void test_checkInputValuesAndDeployRover_with_valid_user_input() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = anyOrientation();
        String userInput = xValue + " " + yValue + " " + orientation;

        // When:
        Rover rover = underTest.checkInputValuesAndDeployRover(userInput);

        // Then:
        assertThat(rover.getxValue()).isEqualTo(xValue);
        assertThat(rover.getyValue()).isEqualTo(yValue);
        // INFO: More tests for deploying the rover can be found in {@link RoverTest}
    }
    
    @Test
    public void test_checkInputValuesAndDeployRover_with_lower_case_orientation() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        String userInput = xValue + " " + yValue + " e";

        // When:
        Rover rover = underTest.checkInputValuesAndDeployRover(userInput);

        // Then:
        assertThat(rover.getxValue()).isEqualTo(xValue);
        assertThat(rover.getyValue()).isEqualTo(yValue);
    }

    @Test
    public void test_checkInputValuesAndDeployRover_with_invalid_xValue() {
        // Given:
        int xValue = -1 * anyXCoordinate();
        int yValue = anyYCoordinate();
        String userInput = xValue + " " + yValue;

        // When:
        try {
            underTest.checkInputValuesAndDeployRover(userInput);
        } catch (IllegalArgumentException ex) {
            // Then:
            assertThat(ex.getMessage()).contains("Invalid coordinates given");
            return;
        }

        fail("Exception expected!");
    }

    @Test
    public void test_checkInputValuesAndDeployRover_with_invalid_yValue() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = -1 * anyYCoordinate();
        String userInput = xValue + " " + yValue;

        // When:
        try {
            underTest.checkInputValuesAndDeployRover(userInput);
        } catch (IllegalArgumentException ex) {

            // Then:
            assertThat(ex.getMessage()).contains("Invalid coordinates given");
            return;
        }

        fail("Exception expected!");
    }

    @Test
    public void test_checkInputValuesAndDeployRover_with_insufficient_values() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        String userInput = xValue + " " + yValue;

        // When:
        try {
            underTest.checkInputValuesAndDeployRover(userInput);
        } catch (IllegalArgumentException ex) {

            // Then:
            assertThat(ex.getMessage()).contains("Invalid coordinates given");
            return;
        }

        fail("Exception expected!");
    }

    @Test
    public void test_checkInputValuesAndDeployRover_with_too_many_values() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = anyOrientation();
        int another = anyXCoordinate();
        String userInput = xValue + " " + yValue + " " + orientation + " " + another;

        // When:
        try {
            underTest.checkInputValuesAndDeployRover(userInput);
        } catch (IllegalArgumentException ex) {

            // Then:
            assertThat(ex.getMessage()).contains("Invalid coordinates given");
            return;
        }

        fail("Exception expected!");
    }
    
    //
    // EXTRACT INSTRUCTIONS
    //
    @Test
    public void test_extractInstructions_with_single_instruction() {
        // Given:
        Instruction instruction = anyInstruction();
        String userInput = instruction.toString();

        // When:
        List<Instruction> resultList = underTest.extractInstructions(userInput);

        // Then:
        assertThat(resultList).hasSize(1);
        assertThat(resultList.get(0)).isEqualTo(instruction);
    }
    
    @Test
    public void test_extractInstructions_with_multiple_instructions() {
        // Given:
        Instruction instruction1 = anyInstruction();
        Instruction instruction2 = anyInstruction();
        Instruction instruction3 = anyInstruction();
        String userInput = instruction1.toString()+instruction2.toString()+instruction3.toString();

        // When:
        List<Instruction> resultList = underTest.extractInstructions(userInput);

        // Then:
        assertThat(resultList).hasSize(3);
        assertThat(resultList.get(0)).isEqualTo(instruction1);
        assertThat(resultList.get(1)).isEqualTo(instruction2);
        assertThat(resultList.get(2)).isEqualTo(instruction3);
    }
    
    @Test
    public void test_extractInstructions_with_lower_case_letters() {
        // Given:
        String userInput = "lrm";

        // When:
        List<Instruction> resultList = underTest.extractInstructions(userInput);

        // Then:
        assertThat(resultList).hasSize(3);
        assertThat(resultList.get(0)).isEqualTo(Instruction.L);
        assertThat(resultList.get(1)).isEqualTo(Instruction.R);
        assertThat(resultList.get(2)).isEqualTo(Instruction.M);
    }
    
    @Test
    public void test_extractInstructions_with_blanks() {
        // Given:
        Instruction instruction1 = anyInstruction();
        Instruction instruction2 = anyInstruction();
        Instruction instruction3 = anyInstruction();
        String userInput = instruction1.toString()+" "+instruction2.toString()+instruction3.toString()+" ";

        // When:
        List<Instruction> resultList = underTest.extractInstructions(userInput);

        // Then:
        assertThat(resultList).hasSize(3);
        assertThat(resultList.get(0)).isEqualTo(instruction1);
        assertThat(resultList.get(1)).isEqualTo(instruction2);
        assertThat(resultList.get(2)).isEqualTo(instruction3);
    }
    
    @Test
    public void test_extractInstructions_without_input() {
        // Given:
        String userInput = "";

        // When:
        List<Instruction> resultList = underTest.extractInstructions(userInput);

        // Then:
        assertThat(resultList).hasSize(0);
    }

    @Test
    public void test_extractInstructions_with_invalid_instruction() {
        // Given:
        String userInput = "LRZ";

        // When:
        try {
            underTest.extractInstructions(userInput);
        } catch (IllegalArgumentException ex) {
            // Then:
            assertThat(ex.getMessage()).contains("Invalid instructions given");
            return;
        }

        fail("Exception expected!");
    }

}
