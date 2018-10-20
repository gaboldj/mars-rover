package app;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.Scanner;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import constants.Instruction;
import constants.Orientation;
import constants.PlateauCoordinates;
import model.Rover;

public class InputHandler {
    Scanner scanner;

    public void process() {
        scanner = new Scanner(System.in);

        // Configuring the plateau
        System.out.println(
                "Enter the size of plateau (positive X-coordinate and Y-coordinate, seperated by blank)");
        configurePlateau(readUserInput());

        // Preparing the first rover
        System.out.println(
                "Enter the deploy position of the first rover and its orientation (N, E, S, W) - seperated by blanks");
        Rover rover1 = checkInputValuesAndDeployRover(readUserInput());
        System.out.println("Enter the instructions (M, L, R) for the first rover - without seperation");
        List<Instruction> instructionsRover1 = extractInstructions(readUserInput());

        // Preparing the second rover
        System.out.println(
                "Enter the deploy position of the second rover and its orientation (N, E, S, W) - seperated by blanks");
        Rover rover2 = checkInputValuesAndDeployRover(readUserInput());
        System.out.println("Enter the instructions (M, L, R) for the second rover - without seperation");
        List<Instruction> instructionsRover2 = extractInstructions(readUserInput());

        // Execute commands
        rover1.executeInstructions(instructionsRover1);
        rover2.executeInstructions(instructionsRover2);
    }

    /**
     * Splits the given user input into single instructions and converts each to
     * the designated type ({@link Instruction}).
     * 
     * @param instructionInput Given user input
     * @return List of {@link Instruction}s
     */
    private List<Instruction> extractInstructions(String instructionInput) {
        List<Instruction> instructionList = Lists.newArrayList();
        String[] splittedInput = instructionInput.toUpperCase().split(""); // splits into single characters as String
        try {
            for (String instruction : splittedInput) {
                instructionList.add(Instruction.valueOf(instruction));
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid instructions given: " + instructionInput);
        }
        return instructionList;
    }

    /**
     * Checks and splits the given user input into the rover's coordinates and
     * its orientation plus deploys the rover.
     * 
     * @param userInput Given user input
     * @return The deployed Rover
     */
    private Rover checkInputValuesAndDeployRover(String userInput) {
        List<String> inputValues = Splitter.on(' ').splitToList(userInput);
        // @formatter:off
        checkArgument(
                inputValues.size() == 3 && 
                Integer.valueOf(inputValues.get(0)) >= PlateauCoordinates.minXValue &&
                Integer.valueOf(inputValues.get(1)) >= PlateauCoordinates.minYValue, 
                "Invalid coordinates given!");
        // @formatter:on
        Orientation orientation = Orientation.valueOf(inputValues.get(2));
        return deployRover(inputValues.get(0), inputValues.get(1), orientation);
    }

    /**
     * Deploys the rover.
     * 
     * @param Given X- and Y-coordinates
     * @param orientation Given {@link Orientation}
     * @return The deployed rover
     */
    private Rover deployRover(String xValue, String yValue, Orientation orientation) {
        Rover rover = new Rover();
        rover.setDeployPosition(Integer.valueOf(xValue), Integer.valueOf(yValue), orientation);
        return rover;
    }

    /**
     * Constitutes the plateau where the rover(s) operate. An Exception will be
     * thrown if the given plateau size is invalid.
     * 
     * @param userInput
     */
    private void configurePlateau(String userInput) {
        List<String> plateauInput = Splitter.on(' ').splitToList(userInput);
        // @formatter:off
        checkArgument(
                plateauInput.size() == 2 && 
                Integer.valueOf(plateauInput.get(0)) >= PlateauCoordinates.minXValue &&
                Integer.valueOf(plateauInput.get(1)) >= PlateauCoordinates.minYValue, 
                "Invalid plateau size!");
        // @formatter:on
        PlateauCoordinates.setMaxXValue(Integer.valueOf(plateauInput.get(0)));
        PlateauCoordinates.setMaxYValue(Integer.valueOf(plateauInput.get(1)));
    }

    /**
     * Performs the user's input of the console.
     * 
     * @return The user's input in upper case lettes and without leading and
     *         trailing whitespace characters.
     */
    private String readUserInput() {
        return this.scanner.nextLine().toUpperCase().trim();
    }
}
