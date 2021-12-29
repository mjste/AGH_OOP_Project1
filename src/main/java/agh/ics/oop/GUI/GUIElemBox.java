package agh.ics.oop.GUI;

import agh.ics.oop.Animal;
import agh.ics.oop.IWorldMapElement;
import agh.ics.oop.MapType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;

public class GUIElemBox {
    private final Pane pane;
    private final IWorldMapElement iWorldMapElement;
    private final App app;
    private final MapType type;

    public GUIElemBox(App app, IWorldMapElement element, MapType type, double width, double height, int moveEnergy) {
        this.app = app;
        this.iWorldMapElement = element;
        this.type = type;
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

            pane.setOnMouseClicked(event -> {
                setAppMonitoredAnimal();
            });
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

    private void setAppMonitoredAnimal() {
        if (app.enginePaused(type))
            app.setMonitoredAnimal((Animal) iWorldMapElement);
    }
}
