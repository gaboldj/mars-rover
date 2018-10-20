package testenv;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Before;

import constants.Orientation;
import constants.PlateauCoordinates;

public class TestHelper {
    
    @Before
    public void setupPlateau() {
       PlateauCoordinates.setMaxXValue(anyInt());
       PlateauCoordinates.setMaxYValue(anyInt());
    }

    private static int anyInt() {
        return new Random().nextInt(Integer.MAX_VALUE);
    }
    
    protected static int anyXCoordinate() {
        return new Random().nextInt(PlateauCoordinates.maxXValue);
    }
    
    protected static int anyYCoordinate() {
        return new Random().nextInt(PlateauCoordinates.maxYValue);
    }
    
    protected static Orientation anyOrientation() {
        List<Orientation> enumValuesAsList = Arrays.asList(Orientation.values());
        return enumValuesAsList.get(new Random().nextInt(enumValuesAsList.size()));
    }

}
