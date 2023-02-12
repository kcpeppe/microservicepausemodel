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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MicroserviceTailLatencyDistributionChart extends Application {

    private TailLatencyModel model;
    private BarChart<String, Number> chart;
    private XYChart.Series<String,Number> pauseDurations = new XYChart.Series<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pause Latency Contribution");
        model = new TailLatencyModel();

        chart = buildBarChart(
                "Pause Latency (ms)",
                "Pause durations",
                "Number of Events");

        Label numberOfNodesLabel = new Label("# of Nodes:                 ");
        Label gcOverHeadLabel = new Label("GC Overhead:             ");
        Label averagePauseTimeLabel = new Label("Average Pause Time: ");
        Label arrivalRateLabel = new Label("Arrival Rate:                ");
        TextField numberOfNodes = new TextField("5");
        TextField gcOverHead = new TextField("5");
        TextField averagePauseTime = new TextField("100"); // milliseconds
        TextField arrivalRate = new TextField("1000"); // per second
        Button button = new Button("Compute");

        button.setOnAction((event) -> {
            ObservableList<XYChart.Data<String, Number>> latencyDistribution = FXCollections.observableArrayList();
            model.calculateTailLatencies(latencyDistribution, Integer.valueOf(numberOfNodes.getText()), Integer.valueOf(gcOverHead.getText()), Integer.valueOf(averagePauseTime.getText()), Integer.valueOf(arrivalRate.getText()));
            pauseDurations.getData().setAll(latencyDistribution);
        });

        HBox numberOfnodesControls = new HBox(10, numberOfNodesLabel, numberOfNodes);
        HBox gcOverHeadControls = new HBox(10, gcOverHeadLabel, gcOverHead);
        HBox averagePauseTimeControls = new HBox(10, averagePauseTimeLabel, averagePauseTime);
        HBox arrivalRateControls = new HBox(10, arrivalRateLabel, arrivalRate);
        VBox controls = new VBox(10, numberOfnodesControls, gcOverHeadControls, averagePauseTimeControls, arrivalRateControls, button);
        controls.setAlignment(Pos.BASELINE_LEFT);
        HBox root = new HBox(5, controls, chart);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1200, 360);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    BarChart<String,Number> buildBarChart(String title, String xAxisLabel, String yAxisLabel) {

        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Range");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Counts");

        final BarChart<String,Number> chart = new BarChart<>(xAxis,yAxis);
        chart.setCategoryGap(0);
        chart.setBarGap(0);

        chart.setTitle(title);
        pauseDurations.setName("Pause Durations");
        chart.getData().add(pauseDurations);
        return chart;
    }
}
