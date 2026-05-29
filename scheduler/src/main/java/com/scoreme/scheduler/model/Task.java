package com.scoreme.scheduler.model;

public class Task {

    private String id;

    private double[] resources;

   
    private int lowerBound;
    private int upperBound;

   
    private double weight;

    public Task() {}

    public Task(String id, double[] resources, int lowerBound, int upperBound, double weight) {
        this.id = id;
        this.resources = resources;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.weight = weight;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public double[] getResources() { return resources; }
    public void setResources(double[] resources) { this.resources = resources; }

    public int getLowerBound() { return lowerBound; }
    public void setLowerBound(int lowerBound) { this.lowerBound = lowerBound; }

    public int getUpperBound() { return upperBound; }
    public void setUpperBound(int upperBound) { this.upperBound = upperBound; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }


    public int getWindowSize() {
        return upperBound - lowerBound;
    }

    @Override
    public String toString() {
        return String.format("Task{id='%s', window=[%d,%d], weight=%.1f}",
                id, lowerBound, upperBound, weight);
    }
}