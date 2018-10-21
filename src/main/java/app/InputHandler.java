package app;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.Scanner;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import constants.Instruction;
import constants.Orientation;
import constants.PlateauSize;
import model.Rover;

public class InputHandler {
    Scanner scanner;

    public InputHandler() {
    }

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

        // Execute commands and register finished rovers
        rover1.executeInstructions(instructionsRover1);
        RoverRegistry.registerRover(1L, rover1);
        rover2.executeInstructions(instructionsRover2);
        RoverRegistry.registerRover(2L, rover2);
    }

    /**
     * Performs the user's input of the console.
     * 
     * @return The user's input in upper case lettes and without leading and
     *         trailing whitespace characters.
     */
    private String readUserInput() {
        return this.scanner.nextLine().trim();
    }

    /**
     * Constitutes the plateau where the rover(s) will operate. An exception
     * will be thrown if the given plateau size is invalid.
     * 
     * @param userInput
     */
    @VisibleForTesting
    void configurePlateau(String userInput) {
        List<String> plateauInput = Splitter.on(' ').omitEmptyStrings().splitToList(userInput);
        // @formatter:off
        checkArgument(
                plateauInput.size() == 2 && 
                Integer.valueOf(plateauInput.get(0)) >= PlateauSize.minXValue &&
                Integer.valueOf(plateauInput.get(1)) >= PlateauSize.minYValue, 
                "Invalid plateau size!");
        // @formatter:on
        PlateauSize.setMaxXValue(Integer.valueOf(plateauInput.get(0)));
        PlateauSize.setMaxYValue(Integer.valueOf(plateauInput.get(1)));
    }

    /**
     * Checks and splits the given user input into the rover's coordinates and
     * its orientation plus deploys the rover.
     * 
     * @param userInput Given user input
     * @return The deployed Rover
     */
    @VisibleForTesting
    Rover checkInputValuesAndDeployRover(String userInput) {
        List<String> inputValues = Splitter.on(' ').omitEmptyStrings().splitToList(userInput);
        // @formatter:off
        checkArgument(
                inputValues.size() == 3 && 
                Integer.valueOf(inputValues.get(0)) >= PlateauSize.minXValue &&
                Integer.valueOf(inputValues.get(1)) >= PlateauSize.minYValue, 
                "Invalid coordinates given!");
        // @formatter:on
        Orientation orientation = Orientation.valueOf(inputValues.get(2).toUpperCase());
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
     * Splits the given user input into single instructions and converts each to
     * the designated type ({@link Instruction}).
     * 
     * @param instructionInput Given user input
     * @return List of {@link Instruction}s
     */
    @VisibleForTesting
    List<Instruction> extractInstructions(String instructionInput) {
        String[] splittedInput = instructionInput.toUpperCase().split(""); // splits into single characters as String
        List<Instruction> instructionList = Lists.newArrayList();
        try {
            for (String instruction : splittedInput) {
                if (!instruction.trim().isEmpty()) {
                    instructionList.add(Instruction.valueOf(instruction));
                }
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid instructions given: " + instructionInput);
        }
        return instructionList;
    }
}
