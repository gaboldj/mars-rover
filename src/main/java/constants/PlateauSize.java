package constants;

public class PlateauSize {

    public static final int minXValue = 0;
    public static final int minYValue = 0;
    public static int maxXValue;
    public static int maxYValue;

    public PlateauSize() {
    }

    public static void setMaxXValue(int input) {
       maxXValue = input;
    }
    
    public static void setMaxYValue(int input) {
        maxYValue = input;
     }
}
