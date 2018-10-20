package rover;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.google.common.base.Splitter;

import constants.Orientation;
import testenv.TestHelper;

public class RoverTest extends TestHelper {

    private Rover underTest = new Rover();

    @Test
    public void test_setDeployPosition_with_correct_values() {
        // Given:
        int xValue = anyInt();
        int yValue = anyInt();
        Orientation orientation = randomOrientation();

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
        int xValue = -1 * anyInt();
        int yValue = anyInt();
        Orientation orientation = randomOrientation();

        // When:
        try {
            underTest.setDeployPosition(xValue, yValue, orientation);
        } catch (Throwable t) {

            // Then:
            assertThat(t).isInstanceOf(IllegalArgumentException.class);
            assertThat(t.getLocalizedMessage()).contains("X-value must not be negative");
            return;
        }

        fail("Exception expected!");
    }

    @Test
    public void test_setDeployPosition_with_invalid_YValue() {
        // Given:
        int xValue = anyInt();
        int yValue = -1 * anyInt();
        Orientation orientation = randomOrientation();

        // When:
        try {
            underTest.setDeployPosition(xValue, yValue, orientation);
        } catch (Throwable t) {

            // Then:
            assertThat(t).isInstanceOf(IllegalArgumentException.class);
            assertThat(t.getLocalizedMessage()).contains("Y-value must not be negative");
            return;
        }

        fail("Exception expected!");
    }

    @Test
    public void test_setDeployPosition_with_invalid_Orientation() {
        // Given:
        int xValue = anyInt();
        int yValue = anyInt();
        Orientation orientation = null;

        // When:
        try {
            underTest.setDeployPosition(xValue, yValue, orientation);
        } catch (Throwable t) {

            // Then:
            assertThat(t).isInstanceOf(NullPointerException.class);
            assertThat(t.getLocalizedMessage()).contains("Orientation must not be null");
            return;
        }

        fail("Exception expected!");
    }

    @Test
    public void test_getPositionAsOutput_with_correctValues() {
        // Given:
        int xValue = anyInt();
        int yValue = anyInt();
        Orientation orientation = randomOrientation();
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        String positionOutput = underTest.getPositionAsOutput();

        // Then:
        List<String> resultList = Splitter.on(' ').splitToList(positionOutput);
        assertThat(resultList).hasSize(3);
        assertThat(resultList.get(0)).isEqualTo(String.valueOf(xValue));
        assertThat(resultList.get(1)).isEqualTo(String.valueOf(yValue));
        assertThat(resultList.get(2)).isEqualTo(orientation.getAbbreviation());
    }

    @Test
    public void test_moveVehicle_heading_north() {
        // Given:
        int xValue = anyInt();
        int yValue = anyInt();
        Orientation orientation = Orientation.NORTH;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.moveVehicle();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue + 1);
        assertThat(underTest.getOrientation()).isEqualTo(orientation);
    }

    @Test
    public void test_moveVehicle_heading_east() {
        // Given:
        int xValue = anyInt();
        int yValue = anyInt();
        Orientation orientation = Orientation.EAST;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.moveVehicle();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue + 1);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(orientation);
    }

    @Test
    public void test_moveVehicle_heading_south() {
        // Given:
        int xValue = anyInt();
        int yValue = anyInt();
        Orientation orientation = Orientation.SOUTH;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.moveVehicle();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue - 1);
        assertThat(underTest.getOrientation()).isEqualTo(orientation);
    }

    @Test
    public void test_moveVehicle_heading_west() {
        // Given:
        int xValue = anyInt();
        int yValue = anyInt();
        Orientation orientation = Orientation.WEST;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.moveVehicle();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue - 1);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(orientation);
    }

    @Test
    public void test_turnVehicleLeft_from_heading_north() {
        // Given:
        int xValue = anyInt();
        int yValue = anyInt();
        Orientation orientation = Orientation.NORTH;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.turnVehicleLeft();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(Orientation.WEST);
    }

    @Test
    public void test_turnVehicleLeft_from_heading_east() {
        // Given:
        int xValue = anyInt();
        int yValue = anyInt();
        Orientation orientation = Orientation.EAST;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.turnVehicleLeft();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(Orientation.NORTH);
    }

    @Test
    public void test_turnVehicleLeft_from_heading_south() {
        // Given:
        int xValue = anyInt();
        int yValue = anyInt();
        Orientation orientation = Orientation.SOUTH;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.turnVehicleLeft();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(Orientation.EAST);
    }

    @Test
    public void test_turnVehicleLeft_from_heading_west() {
        // Given:
        int xValue = anyInt();
        int yValue = anyInt();
        Orientation orientation = Orientation.WEST;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.turnVehicleLeft();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(Orientation.SOUTH);
    }

    @Test
    public void test_turnVehicleRight_from_heading_north() {
        // Given:
        int xValue = anyInt();
        int yValue = anyInt();
        Orientation orientation = Orientation.NORTH;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.turnVehicleRight();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(Orientation.EAST);
    }

    @Test
    public void test_turnVehicleRight_from_heading_east() {
        // Given:
        int xValue = anyInt();
        int yValue = anyInt();
        Orientation orientation = Orientation.EAST;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.turnVehicleRight();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(Orientation.SOUTH);
    }

    @Test
    public void test_turnVehicleRight_from_heading_south() {
        // Given:
        int xValue = anyInt();
        int yValue = anyInt();
        Orientation orientation = Orientation.SOUTH;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.turnVehicleRight();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(Orientation.WEST);
    }

    @Test
    public void test_turnVehicleRight_from_heading_west() {
        // Given:
        int xValue = anyInt();
        int yValue = anyInt();
        Orientation orientation = Orientation.WEST;
        underTest.setDeployPosition(xValue, yValue, orientation);

        // When:
        underTest.turnVehicleRight();

        // Then:
        assertThat(underTest.getxValue()).isEqualTo(xValue);
        assertThat(underTest.getyValue()).isEqualTo(yValue);
        assertThat(underTest.getOrientation()).isEqualTo(Orientation.NORTH);
    }

}
