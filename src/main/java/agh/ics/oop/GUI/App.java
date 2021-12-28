package agh.ics.oop.GUI;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class App extends Application {
    private WorldMap boundedWorldMap;
    private WorldMap unboundedWorldMap;
    private SimulationEngine boundedEngine;
    private SimulationEngine unboundedEngine;
    private Thread boundedThread;
    private Thread unboundedThread;
    private Scene scene1;
    private Scene scene2;
    private Stage stage;
    private GridPane boundedGrid;
    private GridPane unboundedGrid;

    private int scene2Width;
    private int scene2Height;
    private int width;
    private int height;
    private int startEnergy;
    private int moveEnergy;
    private int plantEnergy;
    private int reproductionEnergy;
    private int delay;
    private int startAnimalsNumber;
    private double jungleRatio;
    private double elemBoxWidth;
    private double elemBoxHeight;
    private TextField widthTextField;
    private TextField heightTextField;
    private TextField startEnergyTextField;
    private TextField moveEnergyTextField;
    private TextField plantEnergyTextField;
    private TextField reproductionEnergyTextField;
    private TextField jungleRatioTextField;
    private TextField delayTextField;
    private TextField startAnimalsNumberTextField;
    private Label errorLabel;
    private XYChart.Series<Number, Number> lifespanSeries;


    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            // to kill any existing threads
            System.exit(0);
        });
        makeScene1();

        stage.setScene(scene1);
        stage.setTitle("Set parameters");
        stage.show();
    }

    void makeScene1() {
        GridPane scene1Grid = new GridPane();
        scene1Grid.getChildren().clear();
        scene1Grid.getColumnConstraints().clear();
        scene1Grid.getRowConstraints().clear();
        scene1Grid.setGridLinesVisible(false);

        scene1Grid.getColumnConstraints().add(new ColumnConstraints(450));
        scene1Grid.getColumnConstraints().add(new ColumnConstraints(190));
        scene1Grid.getColumnConstraints().add(new ColumnConstraints(190));
        scene1Grid.getColumnConstraints().add(new ColumnConstraints(450));

        int row = 0;
        Label widthLabel = new Label("Width");
        widthTextField = new TextField("15");
        scene1Grid.add(widthLabel, 1, row);
        scene1Grid.add(widthTextField, 2, row);
        row += 1;


        Label heightLabel = new Label("Height");
        heightTextField = new TextField("15");
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

        Label plantEnergyLabel = new Label("Plant Energy");
        plantEnergyTextField = new TextField("50");
        scene1Grid.add(plantEnergyLabel, 1, row);
        scene1Grid.add(plantEnergyTextField, 2, row);
        row += 1;

        Label reproductionEnergyLabel = new Label("Reproduction energy");
        reproductionEnergyTextField = new TextField("30");
        scene1Grid.add(reproductionEnergyLabel, 1, row);
        scene1Grid.add(reproductionEnergyTextField, 2, row);
        row += 1;


        Label jungleRatioLabel = new Label("Jungle Ratio [0,1]");
        jungleRatioTextField = new TextField("0.3");
        scene1Grid.add(jungleRatioLabel, 1, row);
        scene1Grid.add(jungleRatioTextField, 2, row);
        row += 1;

        Label delayLabel = new Label("Delay (ms) (0, inf)");
        delayTextField = new TextField("100");
        scene1Grid.add(delayLabel, 1, row);
        scene1Grid.add(delayTextField, 2, row);
        row += 1;

        Label startAnimalsNumberLabel = new Label("Entry animals [10, inf)");
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

        scene1 = new Scene(scene1Grid, 1280, 720);
    }

    void makeScene2() {
        GridPane grid = new GridPane();
        scene2Width = 1280;
        scene2Height = 720;
        scene2 = new Scene(grid, scene2Width, scene2Height);

        int mapGridWidth = 450;
        int mapGridHeight = 450;

        grid.getColumnConstraints().add(new ColumnConstraints(2 * mapGridWidth));
        grid.getColumnConstraints().add(new ColumnConstraints(scene2Width - 2 * mapGridWidth));
        grid.getRowConstraints().add(new RowConstraints(mapGridHeight));
        grid.getRowConstraints().add(new RowConstraints(scene2Height - mapGridHeight));
        grid.setGridLinesVisible(true);

        GridPane mapsGrid = new GridPane();
        grid.add(mapsGrid, 0, 0);
        mapsGrid.getColumnConstraints().add(new ColumnConstraints(450));
        mapsGrid.getColumnConstraints().add(new ColumnConstraints(450));
        mapsGrid.getRowConstraints().add(new RowConstraints(450));
        mapsGrid.setGridLinesVisible(true);

        unboundedGrid = new GridPane();
        boundedGrid = new GridPane();
        mapsGrid.add(unboundedGrid, 0, 0);
        mapsGrid.add(boundedGrid, 1, 0);

        elemBoxWidth = ((double) mapGridWidth) / ((double) width);
        elemBoxHeight = ((double) mapGridHeight) / ((double) height);
        setMapGridsConstraints();

        unboundedGrid.setGridLinesVisible(true);
        boundedGrid.setGridLinesVisible(true);

        initWorldMapsAndEngines();

        refreshGrid(MapType.UNBOUNDED);
        refreshGrid(MapType.BOUNDED);

        makeBottomButtons(grid, mapGridWidth, mapGridHeight);
        makeChart(grid);
    }

    void setMapGridsConstraints() {
        for (int i = 0; i < height; i++) {
            unboundedGrid.getRowConstraints().add(new RowConstraints(elemBoxHeight));
            boundedGrid.getRowConstraints().add(new RowConstraints(elemBoxHeight));
        }
        for (int i = 0; i < width; i++) {
            unboundedGrid.getColumnConstraints().add(new ColumnConstraints(elemBoxWidth));
            boundedGrid.getColumnConstraints().add(new ColumnConstraints(elemBoxWidth));
        }
    }

    void makeChart(GridPane grid) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Day number");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Values");

        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle("Statistics");

        lifespanSeries = new XYChart.Series();
        lifespanSeries.setName("Avg unbounded lifespan");

        lineChart.getData().add(lifespanSeries);

        grid.add(lineChart, 1, 0);

    }

    void initWorldMapsAndEngines() {
        unboundedWorldMap = new WorldMap(width, height, false, moveEnergy, plantEnergy, reproductionEnergy, jungleRatio);
        unboundedWorldMap.makeInitialAnimals(startAnimalsNumber, startEnergy);
        unboundedEngine = new SimulationEngine(this, unboundedWorldMap, delay);
        unboundedThread = new Thread(unboundedEngine);

        boundedWorldMap = new WorldMap(width, height, true, moveEnergy, plantEnergy, reproductionEnergy, jungleRatio);
        boundedWorldMap.makeInitialAnimals(startAnimalsNumber, startEnergy);
        boundedEngine = new SimulationEngine(this, boundedWorldMap, delay);
        boundedThread = new Thread(boundedEngine);
    }

    void refreshGrid(MapType type) {
        GridPane grid;
        WorldMap map;
        if (type == MapType.BOUNDED) {
            grid = boundedGrid;
            map = boundedWorldMap;
        } else {
            grid = unboundedGrid;
            map = unboundedWorldMap;
        }

        grid.getChildren().clear();
        grid.setGridLinesVisible(false);
        grid.setGridLinesVisible(true);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int x = i;
                int y = height - j - 1;
                IWorldMapElement elem = map.objectAt(new Vector2D(x, y));
                if (elem != null) {
                    GUIElemBox guiElemBox = new GUIElemBox(elem, elemBoxWidth, elemBoxHeight, moveEnergy);
                    grid.add(guiElemBox.getPane(), i, j);
                }
            }
        }
    }

    void makeBottomButtons(GridPane grid, int mapGridWidth, int mapGridHeight) {
        GridPane buttonsGrid = new GridPane();
        grid.add(buttonsGrid, 0, 1);
        for (int i = 0; i < 4; i++) {
            buttonsGrid.getColumnConstraints().add(new ColumnConstraints((double) 2 * mapGridWidth / 4));
        }
        for (int i = 0; i < 2; i++) {
            buttonsGrid.getRowConstraints().add(new RowConstraints((double) (scene2Height - mapGridHeight) / 2));
        }

        Button unboundedStartButton = new Button("Play");
        unboundedStartButton.setOnAction(event -> {
            if (unboundedThread.getState() == Thread.State.NEW) {
                unboundedThread.start();
            }
            unboundedEngine.resume();
        });
        buttonsGrid.add(unboundedStartButton, 0, 0);
        GridPane.setHalignment(unboundedStartButton, HPos.CENTER);
        GridPane.setValignment(unboundedStartButton, VPos.CENTER);


        Button unboundedPauseButton = new Button("Pause");
        unboundedPauseButton.setOnAction(event -> unboundedEngine.pause());
        buttonsGrid.add(unboundedPauseButton, 1, 0);
        GridPane.setHalignment(unboundedPauseButton, HPos.CENTER);
        GridPane.setValignment(unboundedPauseButton, VPos.CENTER);


        Button boundedStartButton = new Button("Play");
        boundedStartButton.setOnAction(event -> {
            if (boundedThread.getState() == Thread.State.NEW) {
                boundedThread.start();
            }
            boundedEngine.resume();
        });
        buttonsGrid.add(boundedStartButton, 2, 0);
        GridPane.setHalignment(boundedStartButton, HPos.CENTER);
        GridPane.setValignment(boundedStartButton, VPos.CENTER);


        Button boundedPauseButton = new Button("Pause");
        boundedPauseButton.setOnAction(event -> boundedEngine.pause());
        buttonsGrid.add(boundedPauseButton, 3, 0);
        GridPane.setHalignment(boundedPauseButton, HPos.CENTER);
        GridPane.setValignment(boundedPauseButton, VPos.CENTER);


        Button allStartButton = new Button("Play all");
        allStartButton.setOnAction(event -> {
            if (unboundedThread.getState() == Thread.State.NEW) {
                unboundedThread.start();
            }
            unboundedEngine.resume();

            if (boundedThread.getState() == Thread.State.NEW) {
                boundedThread.start();
            }
            boundedEngine.resume();
        });
        buttonsGrid.add(allStartButton, 1, 1);
        GridPane.setHalignment(allStartButton, HPos.CENTER);
        GridPane.setValignment(allStartButton, VPos.CENTER);


        Button allPauseButton = new Button("Pause all");
        allPauseButton.setOnAction(event -> {
            unboundedEngine.pause();
            boundedEngine.pause();
        });
        buttonsGrid.add(allPauseButton, 2, 1);
        GridPane.setHalignment(allPauseButton, HPos.CENTER);
        GridPane.setValignment(allPauseButton, VPos.CENTER);


        Button setParametersButton = new Button("Set Parameters");
        setParametersButton.setOnAction(event -> setParametersButtonAction());
        buttonsGrid.add(setParametersButton, 3, 1);
        GridPane.setHalignment(setParametersButton, HPos.CENTER);
        GridPane.setValignment(setParametersButton, VPos.CENTER);
    }

    public void submitButtonAction() {
        try {
            this.height = Integer.parseInt(this.heightTextField.getText());
            this.width = Integer.parseInt(this.widthTextField.getText());
            this.startEnergy = Integer.parseInt(this.startEnergyTextField.getText());
            this.moveEnergy = Integer.parseInt(this.moveEnergyTextField.getText());
            this.plantEnergy = Integer.parseInt(this.plantEnergyTextField.getText());
            this.reproductionEnergy = Integer.parseInt(this.reproductionEnergyTextField.getText());
            this.jungleRatio = Double.parseDouble(this.jungleRatioTextField.getText());
            this.delay = Integer.parseInt(this.delayTextField.getText());
            this.startAnimalsNumber = Integer.parseInt(this.startAnimalsNumberTextField.getText());
            errorLabel.setText("");

            if (height <= 0 ||
                    width <= 0 ||
                    startEnergy <= 0 ||
                    moveEnergy <= 0 ||
                    plantEnergy <= 0 ||
                    reproductionEnergy <= 0 ||
                    jungleRatio <= 0 ||
                    jungleRatio > 1 ||
                    delay <= 0 ||
                    startAnimalsNumber < 10 ||
                    startAnimalsNumber > width * height*0.9)
                throw new IllegalArgumentException();

            makeScene2();
            stage.setScene(scene2);

        } catch (NumberFormatException e) {
            System.out.println("submitButtonAction: " + e.getMessage());
            errorLabel.setText("Your input is invalid. Please try again.");
        } catch (IllegalArgumentException e) {
            errorLabel.setText("Your argument is illegal. Check for negative values or ranges.");
        }
    }

    public void setParametersButtonAction() {
        stage.setScene(scene1);
        unboundedEngine.stop();
        boundedEngine.stop();
    }

    public void update(MapType type) {Platform.runLater(() -> {
        refreshGrid(type);
        updateChart();
    });
    }

    void updateChart() {
        lifespanSeries.getData().add(new XYChart.Data<>(unboundedWorldMap.getDaysCount(), unboundedWorldMap.getAverageLifespan()));
    }


}
