package agh.ics.oop;

import agh.ics.oop.GUI.App;

public class SimulationEngine implements Runnable {
    private final WorldMap map;
    private final App app;

    public SimulationEngine(App app, WorldMap map) {
        this.map = map;
        this.app = app;
    }

    @Override
    public void run() {
        while (true) {
            map.removeDeadAnimals();
            map.moveAnimals();
            map.eatGrass();
            map.reproduce();
            map.placeGrass();
            // powiadom apkę o nowym renderze
        }
    }
}
