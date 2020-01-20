package kodewerk.microservices.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

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
        ArrayList<Integer> pauseTimes = new ArrayList<>();
        for (int i = 1; i <= callChainLength; i++) {
            deadTime += averagePauseTime;
            int pathCount = combinations( callChainLength, i);
            double pauseEventProbability = Math.pow( pauseProbability, (double)i);
            double noPauseProbability = Math.pow( running, (double)(callChainLength - i));
            int numberOfStalledByNGCs = (int)Math.round(noPauseProbability*pauseEventProbability * (double)pathCount * arrivalRate);
            for (int j = 0; j < numberOfStalledByNGCs; j++) {
                int p = pauseTime.nextInt(1, deadTime);
                pauseTimes.add(p);
            }
        }
        /*
        series1.setName("Histogram");      
        series1.getData().add(new XYChart.Data("0-10", group[0]));
        series1.getData().add(new XYChart.Data("10-20", group[1]));
        series1.getData().add(new XYChart.Data("20-30", group[2]));
        series1.getData().add(new XYChart.Data("30-40", group[3]));
        series1.getData().add(new XYChart.Data("40-50", group[4]));
         */
        Histogram histogram = new Histogram(pauseTimes.toArray(new Integer[0]));
        for (int i = 0; i < histogram.getNumberOfBins(); i++) {
            String rangeLabel = (i * histogram.getBinSize()) + "-" + ((i+1)*histogram.getBinSize());
            data.add(new XYChart.Data(rangeLabel, histogram.getBinCount(i)));
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

    public static void main(String[] args) {
        TailLatencyModel model = new TailLatencyModel();
        int numberOfNodes = 5;
        int gcOverhead = 5;
        int averagePauseTime = 100; //ms
        int arrivalRate = 1000; //arrivals per second
        model.calculateTailLatencies( FXCollections.observableArrayList(), numberOfNodes, gcOverhead, averagePauseTime, arrivalRate);
    }
}
