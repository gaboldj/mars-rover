package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.google.common.base.Splitter;

import constants.Orientation;
import constants.PlateauCoordinates;
import testenv.TestHelper;

public class RoverTest extends TestHelper {

    private Rover underTest = new Rover();

    //
    // SET DEPLOY POSITION
    //
    @Test
    public void test_setDeployPosition_with_correct_values() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = anyOrientation();

        // When:
        underTest.setDeployPosition(xValue, yValue, orientation);

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(orientation);
    }

    @Test
    public void test_setDeployPosition_with_invalid_XValue() {
        // Given:
        int xValue = -1 * anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = anyOrientation();

        // When:
        try {
            underTest.setDeployPosition(xValue, yValue, orientation);
        } catch (Throwable t) {

            // Then:
            assertThat(t).isInstanceOf(IllegalArgumentException.class);
            assertThat(t.getMessage()).contains("X-value must not be negative");
            return;
        }

        fail("Exception expected!");
    }

    @Test
    public void test_setDeployPosition_with_invalid_YValue() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = -1 * anyXCoordinate();
        Orientation orientation = anyOrientation();

        // When:
        try {
            underTest.setDeployPosition(xValue, yValue, orientation);
        } catch (Throwable t) {

            // Then:
            assertThat(t).isInstanceOf(IllegalArgumentException.class);
            assertThat(t.getMessage()).contains("Y-value must not be negative");
            return;
        }

        fail("Exception expected!");
    }

    @Test
    public void test_setDeployPosition_with_invalid_Orientation() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = null;

        // When:
        try {
            underTest.setDeployPosition(xValue, yValue, orientation);
        } catch (NullPointerException npe) {

            // Then:
            assertThat(npe.getMessage()).contains("orientation must not be null");
            return;
        }

        fail("Exception expected!");
    }

    //
    // GET POSITION OUTPUT
    //
    @Test
    public void test_getPositionAsOutput_with_correctValues() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = anyOrientation();
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        String positionOutput = underTest.getPositionAsOutput();

        // Then:
        List<String> resultList = Splitter.on(' ').splitToList(positionOutput);
        assertThat(resultList).hasSize(3);
        assertThat(resultList.get(0)).isEqualTo(String.valueOf(xValue));
        assertThat(resultList.get(1)).isEqualTo(String.valueOf(yValue));
        assertThat(resultList.get(2)).isEqualTo(orientation.toString());
    }

    //
    // MOVE VEHICLE
    //
    @Test
    public void test_moveVehicle_heading_north()
        throws PlateauExceededException {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = Orientation.N;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.moveVehicle();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue + 1);
        assertThat(underTest.getOrientation()).isEqualTo(orientation);
    }

    @Test
    public void test_moveVehicle_heading_east()
        throws PlateauExceededException {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = Orientation.E;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.moveVehicle();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue + 1);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(orientation);
    }

    @Test
    public void test_moveVehicle_heading_south()
        throws PlateauExceededException {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = Orientation.S;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.moveVehicle();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue - 1);
        assertThat(underTest.getOrientation()).isEqualTo(orientation);
    }

    @Test
    public void test_moveVehicle_heading_west()
        throws PlateauExceededException {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = Orientation.W;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.moveVehicle();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue - 1);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(orientation);
    }

    @Test
    public void test_moveVehicle_exceeds_Y_max_value()
        throws PlateauExceededException {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = PlateauCoordinates.maxYValue;
        Orientation orientation = Orientation.N;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        try {
            underTest.moveVehicle();
        } catch (PlateauExceededException ex) {
            // Then:
            assertThat(ex.getMessage()).contains("Y-value " + (PlateauCoordinates.maxYValue + 1));
            return;
        }

        fail("Exception expected!");
    }

    @Test
    public void test_moveVehicle_exceeds_Y_min_value()
        throws PlateauExceededException {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = PlateauCoordinates.minYValue;
        Orientation orientation = Orientation.S;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        try {
            underTest.moveVehicle();
        } catch (PlateauExceededException ex) {
            // Then:
            assertThat(ex.getMessage()).contains("Y-value " + (PlateauCoordinates.minYValue - 1));
            return;
        }

        fail("Exception expected!");
    }

    @Test
    public void test_moveVehicle_exceeds_X_max_value()
        throws PlateauExceededException {
        // Given:
        int xValue = PlateauCoordinates.maxXValue;
        int yValue = anyYCoordinate();
        Orientation orientation = Orientation.E;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        try {
            underTest.moveVehicle();
        } catch (PlateauExceededException ex) {
            // Then:
            assertThat(ex.getMessage()).contains("X-value " + (PlateauCoordinates.maxXValue + 1));
            return;
        }

        fail("Exception expected!");
    }

    @Test
    public void test_moveVehicle_exceeds_X_min_value()
        throws PlateauExceededException {
        // Given:
        int xValue = PlateauCoordinates.minXValue;
        int yValue = anyYCoordinate();
        Orientation orientation = Orientation.W;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        try {
            underTest.moveVehicle();
        } catch (PlateauExceededException ex) {
            // Then:
            assertThat(ex.getMessage()).contains("X-value " + (PlateauCoordinates.minXValue - 1));
            return;
        }

        fail("Exception expected!");
    }

    //
    // TURN VEHICLE LEFT
    //
    @Test
    public void test_turnVehicleLeft_from_heading_north() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = Orientation.N;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.turnVehicleLeft();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(Orientation.W);
    }

    @Test
    public void test_turnVehicleLeft_from_heading_east() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = Orientation.E;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.turnVehicleLeft();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(Orientation.N);
    }

    @Test
    public void test_turnVehicleLeft_from_heading_south() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = Orientation.S;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.turnVehicleLeft();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(Orientation.E);
    }

    @Test
    public void test_turnVehicleLeft_from_heading_west() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = Orientation.W;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.turnVehicleLeft();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(Orientation.S);
    }

    //
    // TURN VEHICLE RIGHT
    //
    @Test
    public void test_turnVehicleRight_from_heading_north() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = Orientation.N;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.turnVehicleRight();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(Orientation.E);
    }

    @Test
    public void test_turnVehicleRight_from_heading_east() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = Orientation.E;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.turnVehicleRight();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(Orientation.S);
    }

    @Test
    public void test_turnVehicleRight_from_heading_south() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = Orientation.S;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.turnVehicleRight();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(Orientation.W);
    }

    @Test
    public void test_turnVehicleRight_from_heading_west() {
        // Given:
        int xValue = anyXCoordinate();
        int yValue = anyYCoordinate();
        Orientation orientation = Orientation.W;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.turnVehicleRight();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(Orientation.N);
    }

}
