package agh.ics.oop.GUI;

import agh.ics.oop.Animal;
import agh.ics.oop.IWorldMapElement;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;

public class GUIElemBox {
    private final Pane pane;

    public GUIElemBox(IWorldMapElement element, double width, double height, int moveEnergy) {
        pane = new Pane();
        pane.setPrefSize(width, height);

        if (element instanceof Animal) {
            Animal animal = (Animal) element;
            int colorIntensity = animal.getEnergy()*256/moveEnergy/100;
            colorIntensity = Math.min(255, colorIntensity);
            String color = Integer.toString(colorIntensity, 16);
            String style = "-fx-background-color: #ff"+color+color;
            pane.setStyle(style);
            height = 0.9*height;
        }

        try {
            ImageView imageView = new ImageView(element.getImage());
            imageView.setFitHeight(height);
            imageView.setFitWidth(width);
            pane.getChildren().add(imageView);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }


    }

    public Pane getPane() {
        return pane;
    }
}
