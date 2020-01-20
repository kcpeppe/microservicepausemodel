package kodewerk.microservices.model;

/********************************************
 * Copyright (c) 2020 Kirk Pepperdine
 * All right reserved
 ********************************************/

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class MicroservicePauseDistributionModel {

    public MicroservicePauseDistributionModel() {}

    public void pauseDistribution(ObservableList<XYChart.Data<Number, Number>> data, int numberOfNodes, int probabilityOfBeingInAPause) {
        double pauseProbability = (double) probabilityOfBeingInAPause / 100.0d;
        double running = 1.00d - pauseProbability;
        int callChainLength = (numberOfNodes * 2) - 1;

        for (int i = 0; i <= callChainLength; i++) {
            int pathCount = combinations( callChainLength, i);
            double pauseEventProbability = Math.pow( pauseProbability, (double)i);
            double noPauseProbability = Math.pow( running, (double)(callChainLength - i));
            data.add(new XYChart.Data(i, noPauseProbability*pauseEventProbability * (double)pathCount));
            System.out.println(noPauseProbability*pauseEventProbability*(double)pathCount);
        }
    }

    int combinations(int n, int r) {
        if ( n == 0 || r == 0) return 1;
        int value = n;
        for ( int i = n - 1; i > (n-r); i--)
            value *= i;
        return value / factorial(r);
    }

    int factorial(int r) {
        if ( r < 2) return 1;
        return r * factorial(r-1);
    }
    //1000 arrivals per second with overhead of 5%.
}

