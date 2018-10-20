package constants;

public enum Command {
    
    MOVE("M"),
    TURN_LEFT("L"),
    TURN_RIGHT("R");

    private String command;
    
    Command(String command) {
        this.command = command;
    }
 
    public String getCommand() {
        return command;
    }

}
