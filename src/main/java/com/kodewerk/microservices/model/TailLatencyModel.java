package com.kodewerk.microservices.model;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import com.kodewerk.microservices.util.Combinations;
import com.kodewerk.microservices.util.Histogram;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TailLatencyModel {

    public TailLatencyModel() {}

    public void calculateTailLatencies(ObservableList<XYChart.Data<String, Number>> data, int numberOfNodes, int gcOverhead, int averagePauseTime, int arrivalRate) {
        // arrival rate per second, pause time milliseconds
        ThreadLocalRandom pauseTime = ThreadLocalRandom.current();
        double pauseProbability = (double) gcOverhead / 100.0d;
        double running = 1.0d - pauseProbability;
        int deadTime = 0;
        int callChainLength = (numberOfNodes * 2) - 1;
        Combinations combinations = new Combinations(callChainLength);
        ArrayList<Integer> pauseTimes = new ArrayList<>();
        int k = 0;
        for (int i = 0; i <= callChainLength; i++) {
            long pathCount = combinations.combinations(i);
            double pauseEventProbability = Math.pow( pauseProbability, (double)i);
            double noPauseProbability = Math.pow( running, (double)(callChainLength - i));
            int numberOfStalledByNGCs = (int)Math.round(noPauseProbability*pauseEventProbability * (double)pathCount * arrivalRate);
            k += numberOfStalledByNGCs;
            for (int j = 0; j < numberOfStalledByNGCs; j++) {
                int p = 0;
                if ( deadTime != 0)
                    p = pauseTime.nextInt(1, deadTime);
                pauseTimes.add(p);
            }
            deadTime += averagePauseTime;
        }

        Histogram histogram = new Histogram(pauseTimes.toArray(new Integer[0]));
        for (int i = 0; i < histogram.getNumberOfBins(); i++) {
            String rangeLabel = (i * histogram.getBinSize()) + "-" + ((i+1)*histogram.getBinSize());
            data.add(new XYChart.Data(rangeLabel, histogram.getBinCount(i)));
        }
    }
}
