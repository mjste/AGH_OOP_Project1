package agh.ics.oop;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Grass{
    public Image image;

    public Grass() {
        try {
            image = new Image(new FileInputStream(getImagePath()));
        } catch (FileNotFoundException fe) {
            System.out.println(fe.getMessage() + " file not found");
        }
    }

    public String getImagePath() {
        return "src/main/resources/agh.ics.oop.Grass.png";
    }

    @Override
    public String toString() {
        return "*";
    }
}
