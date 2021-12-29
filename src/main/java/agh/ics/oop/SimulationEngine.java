package agh.ics.oop;

import agh.ics.oop.GUI.App;

import java.util.Arrays;
import java.util.Map;

public class SimulationEngine implements Runnable {
    private final WorldMap map;
    private final App app;
    private final MapType mapType;
    private final int delay;
    private boolean paused;
    private boolean stopped;

    public SimulationEngine(App app, WorldMap map, int delay) {
        this.map = map;
        this.app = app;
        if (map.isBounded()) this.mapType = MapType.BOUNDED;
        else this.mapType = MapType.UNBOUNDED;
        this.delay = delay;
        this.paused = false;
        this.stopped = false;
    }

    @Override
    public void run() {
        while (!stopped) {
            if (!paused) {
                epoch();
            } else {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
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

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public void stop() {
        stopped = true;
    }

    public boolean isPaused() {
        return paused;
    }
}
