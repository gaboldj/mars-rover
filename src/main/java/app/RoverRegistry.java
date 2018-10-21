package app;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.Rover;

public class RoverRegistry {

    private static Map<Long, Rover> roverMap = new HashMap<Long, Rover>();

    public static void registerRover(long id, Rover rover) {
        roverMap.put(id, rover);
    }

    public static Collection<Rover> getDeployedRovers() {
        return roverMap.values();
    }
}
