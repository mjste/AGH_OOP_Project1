package agh.ics.oop;

import agh.ics.oop.Animal;
import agh.ics.oop.Vector2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WorldMap {
    private List<Animal> animalList;
    private LinkedList<Animal>[][] animals;
    private Boolean[][] grassMap;
    private final Vector2D lowerLeft;
    private final Vector2D upperRight;
    private final boolean bounded;

    public WorldMap(int width, int height, boolean bounded) {
        this.lowerLeft = new Vector2D(0,0);
        this.upperRight = new Vector2D(width-1, height-1);
        this.bounded = bounded;
        initAnimals(width, height);
        initGrass(width, height);
        animalList = new ArrayList<>();
    }

    public void initAnimals(int width, int height) {
        animals = new LinkedList[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                animals[i][j] = new LinkedList<>();
            }
        }
    }
    public void initGrass(int width, int height) {
        grassMap = new Boolean[width][height];
    }


    public boolean isBounded() {
        return bounded;
    }

    public boolean canMoveTo(Vector2D position) {
        if (bounded) {
            return position.precedes(upperRight) && position.follows(lowerLeft);
        } else {
              return true;
        }
    }

    public void placeAnimal(Animal animal) {
        Vector2D animalPosition = animal.getPosition();
        animals[animalPosition.x][animalPosition.y].add(animal);
        animalList.add(animal);
    }

    public boolean isOccupied(Vector2D position) {
        return animalsAt(position).size() != 0 || grassAt(position);
    }

    public List<Animal> animalsAt(Vector2D position) {
        return animals[position.x][position.y];
    }
    
    public boolean grassAt(Vector2D position) {
        return grassMap[position.x][position.y];
    }
}
