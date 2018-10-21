package testenv;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Before;

import constants.Instruction;
import constants.Orientation;
import constants.PlateauSize;

public class TestHelper {
    
    @Before
    public void setupPlateau() {
       PlateauSize.setMaxXValue(anyInt());
       PlateauSize.setMaxYValue(anyInt());
    }

    private static int anyInt() {
        return new Random().nextInt(Integer.MAX_VALUE);
    }
    
    protected static int anyXCoordinate() {
        return new Random().nextInt(PlateauSize.maxXValue);
    }
    
    protected static int anyYCoordinate() {
        return new Random().nextInt(PlateauSize.maxYValue);
    }
    
    protected static Orientation anyOrientation() {
        List<Orientation> enumValuesAsList = Arrays.asList(Orientation.values());
        return enumValuesAsList.get(new Random().nextInt(enumValuesAsList.size()));
    }
    
    protected static Instruction anyInstruction() {
        List<Instruction> enumValuesAsList = Arrays.asList(Instruction.values());
        return enumValuesAsList.get(new Random().nextInt(enumValuesAsList.size()));
    }

}
