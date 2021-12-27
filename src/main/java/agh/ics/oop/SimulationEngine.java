package agh.ics.oop;

import agh.ics.oop.GUI.App;

public class SimulationEngine implements Runnable {
    private final WorldMap map;
    private final App app;
    private final MapType mapType;
    private final int delay;

    public SimulationEngine(App app, WorldMap map, int delay) {
        this.map = map;
        this.app = app;
        if (map.isBounded()) this.mapType = MapType.BOUNDED;
        else this.mapType = MapType.UNBOUNDED;
        this.delay = delay;
    }

    @Override
    public synchronized void run() {
        while (true) {
            epoch();
        }
    }

    private synchronized void epoch() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map.removeDeadAnimals();
        map.moveAnimals();
        map.eatGrass();
        map.reproduce();
        map.placeGrass();
        app.update(mapType);
    }
}
