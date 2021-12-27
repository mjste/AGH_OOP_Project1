package agh.ics.oop;

import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public interface IWorldMapElement {
    Image getImage() throws FileNotFoundException;
}
