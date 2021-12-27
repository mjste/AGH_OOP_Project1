package agh.ics.oop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WorldMap implements IPositionChangeObserver{
    private List<Animal> animalList;
    private LinkedList<Animal>[][] animals;
    private Boolean[][] grassMap;
    private final Grass grass;
    private final Vector2D lowerLeft;
    private final Vector2D upperRight;
    private final Vector2D jungleLowerLeft;
    private final Vector2D jungleUpperRight;
    private final Random random;
    private final boolean bounded;
    private final int moveEnergy;
    private final int plantEnergy;
    private final int reproductionEnergy;


    public WorldMap(int width, int height, boolean bounded, int moveEnergy, int plantEnergy, int reproductionEnergy, double jungleRatio) {
        this.lowerLeft = new Vector2D(0,0);
        this.upperRight = new Vector2D(width-1, height-1);
        this.bounded = bounded;
        this.grass = new Grass();
        this.animalList = new LinkedList<>();
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.reproductionEnergy = reproductionEnergy;
        this.random = new Random();
        initAnimals(width, height);
        initGrass(width, height);

        double squaredJungleRatio = Math.sqrt(jungleRatio)/2;
        int dx = (int)(1-squaredJungleRatio)*(upperRight.x-lowerLeft.x);
        int dy = (int)(1-squaredJungleRatio)*(upperRight.y-lowerLeft.y);
        jungleLowerLeft = new Vector2D(lowerLeft.x+dx, lowerLeft.y+dy);
        jungleUpperRight = new Vector2D(upperRight.x+dx, upperRight.y-dy);


    }

    public WorldMap() {
        this.lowerLeft = new Vector2D(0, 0);
        this.upperRight = new Vector2D(29, 29);
        this.bounded = true;
        this.grass = new Grass();
        this.moveEnergy = 1;
        this.reproductionEnergy = 20;
        this.plantEnergy = 50;
        this.jungleLowerLeft = new Vector2D(10, 10);
        this.jungleUpperRight = new Vector2D(19, 19);
        this.random = new Random();
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
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grassMap[i][j] = false;
            }
        }
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

    public void makeInitialAnimals(int numberOfAnimals, int startEnergy) {
        for (int i = 0; i < numberOfAnimals; i++) {
            Vector2D position = new Vector2D(random.nextInt(upperRight.x- lowerLeft.x), random.nextInt(upperRight.y- lowerLeft.y));
            Animal animal = new Animal(this, position, startEnergy);
            placeAnimal(animal);
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

    public void removeDeadAnimals() {
        List<Animal> deadAnimals = new ArrayList<>();
        for (Animal animal : animalList) {
            if (animal.getEnergy() < moveEnergy) {
                deadAnimals.add(animal);
            }
        }
        for (Animal animal : deadAnimals) {
            animalList.remove(animal);
            Vector2D pos = animal.getPosition();
            int x = pos.x;
            int y = pos.y;
            animals[x][y].remove(animal);
        }
    }

    @Override
    public void changePosition(Animal animal, Vector2D oldPosition, Vector2D newPosition) {
        animals[oldPosition.x][oldPosition.y].remove(animal);
        animals[newPosition.x][newPosition.y].add(animal);
    }

    public void moveAnimals(){
        for (Animal animal : animalList) {
            animal.move();
            animal.changeEnergy(-moveEnergy);
        }

    }

    public void eatGrass() {
        for (int i = 0; i < upperRight.x; i++) {
            for (int j = 0; j < upperRight.y; j++) {
                if (grassMap[i][j] && animals[i][j].size() > 0) {
                    grassMap[i][j] = false;
                    int maxEnergy = 0;
                    for (Animal animal : animals[i][j]) {
                        if (animal.getEnergy() > maxEnergy) maxEnergy = animal.getEnergy();
                    }
                    List<Animal> strongestAnimals = new ArrayList();
                    for (Animal animal : animals[i][j]) {
                        if (animal.getEnergy() == maxEnergy) strongestAnimals.add(animal);
                    }
                    int numberOfStrongestAnimals = strongestAnimals.size();
                    for (Animal animal : strongestAnimals) {
                        animal.changeEnergy(plantEnergy / numberOfStrongestAnimals);
                    }
                }
            }
        }
    }

    public void placeGrass() {
        int count = 100;
        boolean placedInJungle = false;
        boolean placedOutside = false;
        while (count > 0 && !(placedInJungle && placedOutside)) {
            int x = random.nextInt(upperRight.x - lowerLeft.x) + lowerLeft.x;
            int y = random.nextInt(upperRight.y - lowerLeft.y) + lowerLeft.y;
            if (!grassMap[x][y] && animals[x][y].size() == 0) {
                Vector2D position = new Vector2D(x, y);
                if (position.precedes(jungleUpperRight) && position.follows(jungleUpperRight)) {
                    if (!placedInJungle) {
                        placedInJungle = true;
                        grassMap[x][y] = true;
                    }
                } else {
                    if (!placedOutside) {
                        placedOutside = true;
                        grassMap[x][y] = true;
                    }
                }
            }
            count--;
        }
        for (int x = lowerLeft.x; x <= upperRight.x && !placedOutside; x++) {
            for (int y = lowerLeft.y; y <= upperRight.y && !placedOutside; y++) {
                if (!grassMap[x][y] && animals[x][y].size() == 0) {
                    Vector2D position = new Vector2D(x, y);
                    if (!(position.precedes(jungleUpperRight) && position.follows(jungleLowerLeft))) {
                        placedOutside = true;
                        grassMap[x][y] = true;
                    }
                }
            }
        }
        for (int x = jungleLowerLeft.x; x <= jungleUpperRight.x && !placedInJungle; x++) {
            for (int y = jungleLowerLeft.y; y <= jungleUpperRight.y && !placedInJungle; y++) {
                if (!grassMap[x][y] && animals[x][y].size() == 0) {
                    placedInJungle = true;
                    grassMap[x][y] = true;
                }
            }
        }
    }

    public void reproduce() {
        for (int x = 0; x < upperRight.x-1; x++) {
            for (int y = 0; y < upperRight.y-1; y++) {
                if (animals[x][y].size() > 1) {
                    Animal strongestAnimal1 = new Animal(this, new Vector2D(-1, -1), 0);
                    Animal strongestAnimal2 = new Animal(this, new Vector2D(-1, -1), 0);
                    for (Animal animal : animals[x][y]) {
                        if (animal.getEnergy() > strongestAnimal1.getEnergy()) {
                            strongestAnimal2 = strongestAnimal1;
                            strongestAnimal1 = animal;
                        } else if (animal.getEnergy() > strongestAnimal2.getEnergy()) {
                            strongestAnimal2 = animal;
                        }
                    }

                    if (strongestAnimal2.getEnergy() >= reproductionEnergy) {
                        int dE1 = strongestAnimal1.getEnergy()/4;
                        int dE2 = strongestAnimal2.getEnergy()/4;
                        strongestAnimal1.changeEnergy(-dE1);
                        strongestAnimal2.changeEnergy(-dE2);

                        int[] genes = new int[32];
                        int[] genes1 = strongestAnimal1.getGenes();
                        int[] genes2 = strongestAnimal2.getGenes();

                        int limit = (dE1*32)/(dE1+dE2);
                        for (int i = 0; i < limit; i++) {
                            genes[i] = genes1[i];
                        }
                        for (int i = limit; i < 32; i++) {
                            genes[i] = genes2[i];
                        }
                        Animal child = new Animal(this, new Vector2D(x, y), dE1+dE2, genes);
                        placeAnimal(child);
                    }
                }
            }
        }
    }

    public IWorldMapElement objectAt(Vector2D position) {
        int x = position.x;
        int y = position.y;

        if (animals[x][y].size() > 0) {
            Animal strongestAnimal = animals[x][y].get(0);
            for (Animal animal : animals[x][y]) {
                if (animal.getEnergy() > strongestAnimal.getEnergy()) {
                    strongestAnimal = animal;
                }
            }
            return strongestAnimal;
        }
        if (grassMap[x][y]) {
            return grass;
        }
        return null;
    }

    public boolean inBoundaries(Vector2D position) {
        return position.follows(lowerLeft) && position.precedes(upperRight);
    }

    public Vector2D[] getBoundaries() {
        return new Vector2D[]{lowerLeft, upperRight};
    }
    public Vector2D normalizePosition(Vector2D position) {
        int x = position.x;
        int y = position.y;
        int width = upperRight.x- lowerLeft.x+1;
        int height = upperRight.y - lowerLeft.y+1;
        return new Vector2D((x+width)%width, (y+height)%height);
    }
}
