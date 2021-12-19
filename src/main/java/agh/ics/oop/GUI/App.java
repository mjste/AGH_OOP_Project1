package agh.ics.oop.GUI;

import agh.ics.oop.WorldMap;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class App extends Application {
    private WorldMap boundedWorldMap;
    private WorldMap unBoundedWorldMap;
    private Scene scene1;
    private Scene scene2;
    private Stage stage;

    private int width;
    private int height;
    private int startEnergy;
    private int moveEnergy;
    private int delay;
    private double jungleRatio;
    private int startAnimalsNumber;
    private TextField widthTextField;
    private TextField heightTextField;
    private TextField startEnergyTextField;
    private TextField moveEnergyTextField;
    private TextField jungleRatioTextField;
    private TextField delayTextField;
    private TextField startAnimalsNumberTextField;
    private Label errorLabel;


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        makeScene1();

        stage.setScene(scene1);
        stage.setTitle("Set parameters");
        stage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    void makeScene1() {
        GridPane scene1Grid = new GridPane();
        scene1Grid.getChildren().clear();
        scene1Grid.getColumnConstraints().clear();
        scene1Grid.getRowConstraints().clear();
        scene1Grid.setGridLinesVisible(false);

        scene1Grid.getColumnConstraints().add(new ColumnConstraints(480));
        scene1Grid.getColumnConstraints().add(new ColumnConstraints(160));
        scene1Grid.getColumnConstraints().add(new ColumnConstraints(160));
        scene1Grid.getColumnConstraints().add(new ColumnConstraints(480));

        int row = 0;
        Label widthLabel = new Label("Width");
        widthTextField = new TextField("30");
        scene1Grid.add(widthLabel, 1, row);
        scene1Grid.add(widthTextField, 2, row);
        row += 1;


        Label heightLabel = new Label("Height");
        heightTextField = new TextField("30");
        scene1Grid.add(heightLabel, 1, row);
        scene1Grid.add(heightTextField, 2, row);
        row += 1;

        Label startEnergyLabel = new Label("Start energy");
        startEnergyTextField = new TextField("100");
        scene1Grid.add(startEnergyLabel, 1, row);
        scene1Grid.add(startEnergyTextField, 2, row);
        row += 1;

        Label moveEnergyLabel = new Label("Move energy");
        moveEnergyTextField = new TextField("1");
        scene1Grid.add(moveEnergyLabel, 1, row);
        scene1Grid.add(moveEnergyTextField, 2, row);
        row += 1;

        Label jungleRatioLabel = new Label("Jungle Ratio");
        jungleRatioTextField = new TextField("0.3");
        scene1Grid.add(jungleRatioLabel, 1, row);
        scene1Grid.add(jungleRatioTextField, 2, row);
        row += 1;

        Label delayLabel = new Label("Delay (ms)");
        delayTextField = new TextField("100");
        scene1Grid.add(delayLabel, 1, row);
        scene1Grid.add(delayTextField, 2, row);
        row += 1;

        Label startAnimalsNumberLabel = new Label("Number of animals on entry");
        startAnimalsNumberTextField = new TextField("10");
        scene1Grid.add(startAnimalsNumberLabel, 1, row);
        scene1Grid.add(startAnimalsNumberTextField, 2, row);
        row += 1;

        Button submitButton = new Button("Submit");
        scene1Grid.add(submitButton, 1, row, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        submitButton.setOnAction(event -> submitButtonAction());
        row += 1;

        errorLabel = new Label("");
        errorLabel.setTextFill(Paint.valueOf("red"));
        scene1Grid.add(errorLabel, 0, row, 4, 1);
        GridPane.setHalignment(errorLabel, HPos.CENTER);
        row += 1;

        for (int i = 0; i < row; i++)
            scene1Grid.getRowConstraints().add(new RowConstraints(35));


//        scene1Grid.setGridLinesVisible(true);

        scene1 = new Scene(scene1Grid, 1280, 720);
    }

    void makeScene2() {
        GridPane grid = new GridPane();
        scene2 = new Scene(grid, 1280, 720);

        grid.getColumnConstraints().add(new ColumnConstraints(900));
        grid.getColumnConstraints().add(new ColumnConstraints(380));
        grid.getRowConstraints().add(new RowConstraints(450));
        grid.getRowConstraints().add(new RowConstraints(270));
        grid.setGridLinesVisible(true);

        GridPane unboundedMapGrid = new GridPane();
        GridPane boundedMapGrid = new GridPane();
        grid.add(unboundedMapGrid, 0, 0);
        grid.add(boundedMapGrid, 1, 0);
    }

    public void submitButtonAction() {
        try {
            this.height = Integer.parseInt(this.heightTextField.getText());
            this.width = Integer.parseInt(this.widthTextField.getText());
            this.startEnergy = Integer.parseInt(this.startEnergyTextField.getText());
            this.moveEnergy = Integer.parseInt(this.moveEnergyTextField.getText());
            this.jungleRatio = Double.parseDouble(this.jungleRatioTextField.getText());
            this.delay = Integer.parseInt(this.delayTextField.getText());
            this.startAnimalsNumber = Integer.parseInt(this.startAnimalsNumberTextField.getText());
            errorLabel.setText("");

            if (height <= 0 ||
                    width <= 0 ||
                    startEnergy <= 0 ||
                    moveEnergy <= 0 ||
                    jungleRatio <= 0 ||
                    jungleRatio > 1 ||
                    delay <= 0 ||
                    startAnimalsNumber <= 0)
                throw new IllegalArgumentException();

        } catch (NumberFormatException e) {
            System.out.println("submitButtonAction: "+e.getMessage());
            errorLabel.setText("Your input is invalid. Please try again.");
        } catch (IllegalArgumentException e) {
            errorLabel.setText("Your argument is illegal. Consider positive values.");
        }

        makeScene2();
        stage.setScene(scene2);
    }
}
