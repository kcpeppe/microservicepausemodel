package kodewerk.microservices.model;

/********************************************
 * Copyright (c) 2020 Kirk Pepperdine
 * All right reserved
 ********************************************/

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class MicroServicesLinearCallChainModel {

    public MicroServicesLinearCallChainModel() {}

    public void probabilityOfHittingAPause(ObservableList<XYChart.Data<Number, Number>> data, int numberOfNodes, int probabilityOfBeingInAPause) {
        double overhead = 1.00d - ((double) probabilityOfBeingInAPause / 100.0d);
        for (int i = 1; i < numberOfNodes; i++) {
            int callChainLength = (i * 2) - 1;
            double prob = 1.00d - Math.pow( overhead, (double)callChainLength);
            data.add(new XYChart.Data(i, prob));
        }
    }
}
