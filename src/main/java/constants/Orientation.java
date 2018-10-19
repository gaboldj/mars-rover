package constants;

public enum Orientation {
    
    NORTH("N"),
    EAST("E"),
    SOUTH("S"),
    WEST("W");

    private String orientation;
    
    Orientation(String orientation) {
        this.orientation = orientation;
    }
 
    public String getAbbreviation() {
        return orientation;
    }

}
