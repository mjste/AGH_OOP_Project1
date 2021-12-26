package agh.ics.oop;

import agh.ics.oop.IPositionChangeObserver;
import agh.ics.oop.Vector2D;

public class SimulationEngine implements Runnable {
    private WorldMap map;

    public SimulationEngine(WorldMap map) {
        this.map = map;
    }

    @Override
    public void run() {
        while (true) {
            map.removeDeadAnimals();
            map.moveAnimals();
            map.eatGrass();
            map.reproduce();
            map.placeGrass();
            // powiadom App o zmianach
        }
    }
}
