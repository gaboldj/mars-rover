package testenv;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import constants.Orientation;

public class TestHelper {
    
    protected static int anyInt() {
        return new Random().nextInt(Integer.MAX_VALUE);
    }
    
    protected static Orientation randomOrientation() {
        List<Orientation> enumValuesAsList = Arrays.asList(Orientation.values());
        return enumValuesAsList.get(new Random().nextInt(enumValuesAsList.size()));
    }

}
