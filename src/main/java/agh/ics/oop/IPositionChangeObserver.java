package agh.ics.oop;

import agh.ics.oop.Vector2D;

public interface IPositionChangeObserver {
    public void changePosition(Animal animal,Vector2D oldPosition,  Vector2D newPosition);
}
