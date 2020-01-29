package kodewerk.microservices.model;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import kodewerk.microservices.util.Combinations;
import kodewerk.microservices.util.Histogram;

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
        for (int i = 1; i <= callChainLength; i++) {
            deadTime += averagePauseTime;
            long pathCount = combinations.combinations(i);
            double pauseEventProbability = Math.pow( pauseProbability, (double)i);
            double noPauseProbability = Math.pow( running, (double)(callChainLength - i));
            int numberOfStalledByNGCs = (int)Math.round(noPauseProbability*pauseEventProbability * (double)pathCount * arrivalRate);
            for (int j = 0; j < numberOfStalledByNGCs; j++) {
                int p = pauseTime.nextInt(1, deadTime);
                pauseTimes.add(p);
            }
        }

        Histogram histogram = new Histogram(pauseTimes.toArray(new Integer[0]));
        for (int i = 0; i < histogram.getNumberOfBins(); i++) {
            String rangeLabel = (i * histogram.getBinSize()) + "-" + ((i+1)*histogram.getBinSize());
            data.add(new XYChart.Data(rangeLabel, histogram.getBinCount(i)));
        }
    }
}
