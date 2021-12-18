package agh.ics.oop;

import java.util.Random;

public class Animal{
    private final Vector2D position;
    private MapDirection mapDirection;
    private int energy;
    private int[] genes;
    private Random random;

    public Animal(Vector2D position, int energy) {
        this.position = position;
        this.energy = energy;
        this.random = new Random();
        this.mapDirection = MapDirection.values()[random.nextInt(8)];
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

    public void move() {
        int mapDirectionChange = random.nextInt(8);
        mapDirection = MapDirection.changeDirection(mapDirection, mapDirectionChange);
        if (mapDirectionChange == 0 || mapDirectionChange == 4) {
            Vector2D newPosition = MapDirection.toUnitVector(mapDirection);
        }
    }
}
