package rover;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Joiner;

import constants.Orientation;

public class Rover {
    private int xValue;
    private int yValue;
    private Orientation orientation;

    public Rover() {
    }

    public void setDeployPosition(int initialXValue, int initialYValue, Orientation initialOrientation) {
        checkArgument(initialXValue >= 0, "The X-value must not be negative!");
        checkArgument(initialYValue >= 0, "The Y-value must not be negative!");
        checkNotNull(initialOrientation, "The vehicle's Orientation must not be null!");

        this.xValue = initialXValue;
        this.yValue = initialYValue;
        this.orientation = initialOrientation;
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

    public String getPositionAsOutput() {
        Joiner joiner = Joiner.on(" ");
        String result = joiner.join(xValue, yValue, orientation.getAbbreviation());
        System.out.println(result);

        return result;
    }

    public void moveVehicle() {
        switch (this.orientation) {
            case NORTH:
                this.yValue++;
                break;
            case EAST:
                this.xValue++;
                break;
            case SOUTH:
                this.yValue--;
                break;
            case WEST:
                this.xValue--;
                break;
            default:
                throw new IllegalStateException("Invalid orientation occured!");
        }
    }

    public void turnVehicleLeft() {
        switch (this.orientation) {
            case NORTH:
                this.orientation = Orientation.WEST;
                break;
            case EAST:
                this.orientation = Orientation.NORTH;
                break;
            case SOUTH:
                this.orientation = Orientation.EAST;
                break;
            case WEST:
                this.orientation = Orientation.SOUTH;
                break;
            default:
                throw new IllegalStateException("Invalid orientation occured!");
        }
    }

    public void turnVehicleRight() {
        switch (this.orientation) {
            case NORTH:
                this.orientation = Orientation.EAST;
                break;
            case EAST:
                this.orientation = Orientation.SOUTH;
                break;
            case SOUTH:
                this.orientation = Orientation.WEST;
                break;
            case WEST:
                this.orientation = Orientation.NORTH;
                break;
            default:
                throw new IllegalStateException("Invalid orientation occured!");
        }
    }
}
