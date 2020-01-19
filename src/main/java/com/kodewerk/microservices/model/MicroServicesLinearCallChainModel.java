package kodewerk.microservices.model;

/********************************************
 * Copyright (c) 2020 Kirk Pepperdine
 * All right reserved
 ********************************************/

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class MicroServicesLinearCallChainModel {

    public MicroServicesLinearCallChainModel() {}

    // number of pauses == 1
    public void probabilityOfHittingAPause(ObservableList<XYChart.Data<Number, Number>> data, int numberOfNodes, int probabilityOfBeingInAPause) {
        double pauseProbability = (double) probabilityOfBeingInAPause / 100.0d;
        double running = 1.00d - pauseProbability;
        for (int i = 1; i < numberOfNodes; i++) {
            int callChainLength = (i * 2) - 1;
            double noPauseProbability = Math.pow( running, (double)callChainLength);// * pauseProbability * callChainLength;
            System.out.println(i + ", " + noPauseProbability);
            data.add(new XYChart.Data(i, noPauseProbability));
        }
    }
}
