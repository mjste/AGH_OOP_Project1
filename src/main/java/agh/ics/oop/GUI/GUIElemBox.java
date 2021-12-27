package agh.ics.oop.GUI;

import agh.ics.oop.IWorldMapElement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;

public class GUIElemBox {
    private final Pane pane;

    public GUIElemBox(IWorldMapElement element, double width, double height) {
        pane = new Pane();
        pane.setStyle("-fx-background-color: #ffff00");
        pane.setPrefSize(width, height);
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
