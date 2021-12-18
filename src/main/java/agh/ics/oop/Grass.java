package agh.ics.oop;

public class Grass{
    private final Vector2D position;

    public Grass(Vector2D position) {
        this.position = position;
    }

    public Vector2D getPosition() {
        return position;
    }

    public String getImagePath() {
        return "src/main/resources/agh.ics.oop.Grass.png";
    }

    @Override
    public String toString() {
        return "*";
    }
}
