package com.kodewerk.microservices.model;

/********************************************
 * Copyright (c) 2020 Kirk Pepperdine
 * All right reserved
 ********************************************/

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.kodewerk.microservices.util.Combinations;


public class MicroservicePauseDistributionChart extends Application {

    private ScatterChart<Number, Number> chart;
    private XYChart.Series<Number, Number> pauseEvents = new XYChart.Series<>();

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Pause Distribution Model");

        chart = buildScatterChart(
                "Pause Times",
                "Number of Pause Events",
                "P(no pause)");

        Label numberOfNodesLabel = new Label("Number of Nodes: ");
        Label gcOverHeadLabel = new Label("GC Overhead: ");
        TextField numberOfNodes = new TextField("5");
        TextField gcOverHead = new TextField("5");
        Button button = new Button("Compute");

        button.setOnAction((event) -> {
            ObservableList<XYChart.Data<Number, Number>> probabilityOfSeeingAPause = FXCollections.observableArrayList();
            pauseDistribution(probabilityOfSeeingAPause, Integer.valueOf(numberOfNodes.getText()), Integer.valueOf(gcOverHead.getText()));
            pauseEvents.getData().setAll(probabilityOfSeeingAPause);
        });

        HBox controls = new HBox(5, numberOfNodesLabel, numberOfNodes, gcOverHeadLabel, gcOverHead,button);
        VBox root = new VBox(5, controls, chart);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 900, 360);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    ScatterChart<Number, Number> buildScatterChart(String title, String xAxisLabel, String yAxisLabel) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(xAxisLabel);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yAxisLabel);
        ScatterChart<Number, Number> chart = new ScatterChart<>(xAxis, yAxis);
        chart.setTitle(title);
        pauseEvents.setName("No Pause");
        chart.getData().add(pauseEvents);
        return chart;
    }

    public void pauseDistribution(ObservableList<XYChart.Data<Number, Number>> data, int numberOfNodes, int probabilityOfBeingInAPause) {
        double pauseProbability = (double) probabilityOfBeingInAPause / 100.0d;
        double running = 1.00d - pauseProbability;
        int numberOfCalls = (numberOfNodes * 2) - 1;
        Combinations combinations = new Combinations(numberOfCalls);

        for (int i = 0; i <= numberOfCalls; i++) {
            long numberOfPaths = combinations.combinations(i);
            double pauseEventProbability = Math.pow( pauseProbability, (double)i);
            double noPauseProbability = Math.pow( running, (double)(numberOfCalls - i));
            data.add(new XYChart.Data(i, noPauseProbability*pauseEventProbability * (double)numberOfPaths));
        }
    }
}

/*
1 ways of seeing 0 pauses
39 ways of seeing 1 pauses
741 ways of seeing 2 pauses
9139 ways of seeing 3 pauses
82251 ways of seeing 4 pauses
575757 ways of seeing 5 pauses
3262623 ways of seeing 6 pauses
15380937 ways of seeing 7 pauses
61523748 ways of seeing 8 pauses
211915132 ways of seeing 9 pauses
635745396 ways of seeing 10 pauses
1676056044 ways of seeing 11 pauses
3910797436 ways of seeing 12 pauses
8122425444 ways of seeing 13 pauses
15084504396 ways of seeing 14 pauses
25140840660 ways of seeing 15 pauses
37711260990 ways of seeing 16 pauses
51021117810 ways of seeing 17 pauses
62359143990 ways of seeing 18 pauses
68923264410 ways of seeing 19 pauses
68923264410 ways of seeing 20 pauses
62359143990 ways of seeing 21 pauses
51021117810 ways of seeing 22 pauses
37711260990 ways of seeing 23 pauses
25140840660 ways of seeing 24 pauses
15084504396 ways of seeing 25 pauses
8122425444 ways of seeing 26 pauses
3910797436 ways of seeing 27 pauses
1676056044 ways of seeing 28 pauses
635745396 ways of seeing 29 pauses
211915132 ways of seeing 30 pauses
61523748 ways of seeing 31 pauses
15380937 ways of seeing 32 pauses
3262623 ways of seeing 33 pauses
575757 ways of seeing 34 pauses
82251 ways of seeing 35 pauses
9139 ways of seeing 36 pauses
741 ways of seeing 37 pauses
39 ways of seeing 38 pauses
1 ways of seeing 39 pauses
 */
