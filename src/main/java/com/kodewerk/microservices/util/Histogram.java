package com.kodewerk.microservices.util;

import java.util.Arrays;

/**
 * very dumb minimal histogram. All kinds of bad breaking assumption in here....
 */
public class Histogram {

    private int[] bins;
    private int binSize;

    public Histogram(Integer[] data) {
        Arrays.sort(data);
        calculateBinSize(data);
        binData(data);
    }

    /**
     * Rule 1: nearest square root of data size
     * Rule 2: nearest round whole number
     */
    private void calculateBinSize(Integer[] dataPoints) {
        int numberOfBins = (int)(Math.floor(Math.sqrt((double)dataPoints.length))) + 1;
        double range = (double)(dataPoints[dataPoints.length-1]) / (double)numberOfBins;
        double rounded = Math.ceil(range * Math.pow(10, -Math.floor(Math.log10(range)))) / Math.pow(10, -Math.floor(Math.log10(range)));
        int digits = 0;
        while ( rounded >= 5.0d) {
            digits++;
            rounded /= 10;
        }
        binSize = (int)Math.pow(10.0d, (double) digits);
        numberOfBins = (dataPoints[dataPoints.length-1] / binSize) + 1;
        bins = new int[numberOfBins];
    }

    private void binData(Integer[] dataPoints) {
        Arrays.stream(dataPoints).map(dataPoint -> dataPoint / binSize).forEach(index -> bins[index]++);
    }

    public int getNumberOfBins() {
        return bins.length;
    }

    public int getBinCount(int bin) {
        return bins[bin];
    }

    public int getBinSize() {
        return binSize;
    }
}
