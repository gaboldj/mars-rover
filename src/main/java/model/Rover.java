package model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;

import app.RoverRegistry;
import constants.Instruction;
import constants.Orientation;
import constants.PlateauSize;
import model.exception.PlateauExceededException;
import model.exception.PositionBlockedException;

public class Rover {
    private int xValue;
    private int yValue;
    private Orientation orientation;

    public Rover() {
    }

    public int getxValue() {
        return xValue;
    }

    public int getyValue() {
        return yValue;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setDeployPosition(int initialXValue, int initialYValue, Orientation initialOrientation) {
        checkArgument(initialXValue >= 0, "The X-value must not be negative!");
        checkArgument(initialYValue >= 0, "The Y-value must not be negative!");
        checkNotNull(initialOrientation, "The vehicle's orientation must not be null!");

        this.xValue = initialXValue;
        this.yValue = initialYValue;
        this.orientation = initialOrientation;
    }

    /**
     * Lets the rover execute each instruction of the given list. Finally the
     * rover prints its actual position and orientation.
     * 
     * @param roverInstructions The user's list of instructions
     */
    public void executeInstructions(List<Instruction> roverInstructions) {
        for (Instruction instruction : roverInstructions) {
            try {
                this.executeInstruction(instruction);
            } catch (Exception ex) {
                System.err.println("Error occured while executing instruction " + instruction
                        + "\nCurrent Position: " + getPositionAsOutput() + "\n" + ex);
                return;
            }
        }
        System.out.println(getPositionAsOutput());
    }

    /**
     * Lets the rover execute a single instruction.
     * 
     * @param instruction Single instruction that rover should execute
     * @throws PlateauExceededException Exception will thrown if the given
     *             instruction can't be executed. E.g. if the new coordinates
     *             would exceed the plateau.
     * @throws PositionBlockedException
     */
    private void executeInstruction(Instruction instruction)
        throws PlateauExceededException, PositionBlockedException {
        switch (instruction) {
            case M:
                moveVehicle();
                break;
            case L:
                turnVehicleLeft();
                break;
            case R:
                turnVehicleRight();
                break;
            default:
                throw new IllegalStateException("Invalid instruction occured!");
        }
    }

    /**
     * Moves the rover forward. The direction depends on the actual orientation
     * of the rover.
     * 
     * @throws PlateauExceededException Exception will thrown if the move can't
     *             be executed because the new coordinates would exceed the
     *             plateau.
     * @throws PositionBlockedException Exception will thrown if the move can't
     *             be executed because another rover is blocking the proposed
     *             position.
     */
    @VisibleForTesting
    void moveVehicle()
        throws PlateauExceededException, PositionBlockedException {
        switch (orientation) {
            case N:
                if (this.yValue >= PlateauSize.maxYValue) {
                    throw new PlateauExceededException(
                            String.format("Y-value %s exceeds the plateau.", this.yValue + 1));
                }
                checkIfPositionIsBlocked(this.xValue, this.yValue + 1);
                this.yValue++;
                break;
            case E:
                if (this.xValue >= PlateauSize.maxXValue) {
                    throw new PlateauExceededException(
                            String.format("X-value %s exceeds the plateau.", this.xValue + 1));
                }
                checkIfPositionIsBlocked(this.xValue + 1, this.yValue);
                this.xValue++;
                break;
            case S:
                if (this.yValue <= PlateauSize.minYValue) {
                    throw new PlateauExceededException(
                            String.format("Y-value %s exceeds the plateau.", this.yValue - 1));
                }
                checkIfPositionIsBlocked(this.xValue, this.yValue - 1);
                this.yValue--;
                break;
            case W:
                if (this.xValue <= PlateauSize.minXValue) {
                    throw new PlateauExceededException(
                            String.format("X-value %s exceeds the plateau.", this.xValue - 1));
                }
                checkIfPositionIsBlocked(this.xValue - 1, this.yValue);
                this.xValue--;
                break;
            default:
                throw new IllegalStateException("Invalid orientation occured!");
        }
    }

    /**
     * Checks if the proposed positon is already blocked by any registered
     * rover. If so, a {@link PositionBlockedException} will be thrown.
     * 
     * @param newXValue The new X-coordinate the rover wants to step on.
     * @param newYValue The new Y-coordinate the rover wants to step on.
     * @throws PositionBlockedException
     */
    @VisibleForTesting
    void checkIfPositionIsBlocked(int newXValue, int newYValue)
        throws PositionBlockedException {
        Collection<Rover> roverCol = RoverRegistry.getDeployedRovers();
        for (Rover rover : roverCol) {
            if (rover.xValue == newXValue && rover.yValue == newYValue) {
                throw new PositionBlockedException(String
                        .format("The positon (%s %s) is blocked by another rover.", newXValue, newYValue));
            }
        }
    }

    /**
     * Turns the rover to the left. This changes orientation of the rover but
     * not its position (coordinates). of the rover.
     */
    @VisibleForTesting
    void turnVehicleLeft() {
        switch (this.orientation) {
            case N:
                this.orientation = Orientation.W;
                break;
            case E:
                this.orientation = Orientation.N;
                break;
            case S:
                this.orientation = Orientation.E;
                break;
            case W:
                this.orientation = Orientation.S;
                break;
            default:
                throw new IllegalStateException("Invalid orientation occured!");
        }
    }

    /**
     * Turns the rover to the right. This changes orientation of the rover but
     * not its position (coordinates). of the rover.
     */
    @VisibleForTesting
    void turnVehicleRight() {
        switch (this.orientation) {
            case N:
                this.orientation = Orientation.E;
                break;
            case E:
                this.orientation = Orientation.S;
                break;
            case S:
                this.orientation = Orientation.W;
                break;
            case W:
                this.orientation = Orientation.N;
                break;
            default:
                throw new IllegalStateException("Invalid orientation occured!");
        }
    }

    /**
     * Returns the actual position and orientation of the rover.
     * 
     * @return The rover's actual position and orientation
     */
    @VisibleForTesting
    String getPositionAsOutput() {
        Joiner joiner = Joiner.on(" ");
        return joiner.join(xValue, yValue, orientation);
    }
}
