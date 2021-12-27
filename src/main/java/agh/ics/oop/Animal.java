package agh.ics.oop;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class Animal implements IWorldMapElement{
    private Vector2D position;
    private MapDirection mapDirection;
    private final WorldMap map;
    private int energy;
    private final int[] genes;
    private final Random random;

    public Animal(WorldMap map, Vector2D position, int energy) {
        this.position = position;
        this.energy = energy;
        this.random = new Random();
        this.mapDirection = MapDirection.values()[random.nextInt(8)];
        this.map = map;
        this.genes = new int[32];
        for (int i = 0; i < 32; i++) {
            genes[i] = random.nextInt(8);
        }
    }
    public Animal(WorldMap map, Vector2D position, int energy, int[] genes) {
        this.position = position;
        this.energy = energy;
        this.random = new Random();
        this.mapDirection = MapDirection.values()[random.nextInt(8)];
        this.map = map;
        this.genes = genes;
    }

    public Vector2D getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return mapDirection.toString();
    }

    public String getImagePath() {
        return "src/main/resources/"+mapDirection.toString()+".png";
    }

    @Override
    public Image getImage() throws FileNotFoundException{
        return new Image(new FileInputStream(getImagePath()));
    }

    public void move() {
        Vector2D oldPosition = this.getPosition();
        int mapDirectionChange = random.nextInt(8);
        mapDirection = MapDirection.changeDirection(mapDirection, mapDirectionChange);
        if (mapDirectionChange == 0 || mapDirectionChange == 4) {
            Vector2D dPosition = MapDirection.toUnitVector(mapDirection);
            Vector2D newPosition = oldPosition.add(dPosition);
            if (map.canMoveTo(newPosition)) {
                if (!map.inBoundaries(newPosition)) {
                    newPosition = map.normalizePosition(newPosition);
                }
                this.position = newPosition;
                map.changePosition(this, oldPosition, newPosition);
            }
        }
    }

    public int getEnergy() {
        return this.energy;
    }

    public void changeEnergy(int value) {
        this.energy += value;
    }

    public int[] getGenes() {
        return genes;
    }
}
