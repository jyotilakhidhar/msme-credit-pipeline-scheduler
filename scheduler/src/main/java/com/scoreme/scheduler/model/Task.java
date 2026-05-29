package com.scoreme.scheduler.model;

/**
 * Represents a single pipeline task in the MSME credit scoring system.
 * Example tasks: Bank Statement OCR, Bureau Pull, GST Verification, Fraud Score etc.
 *
 * Why this class exists:
 * Each task needs 4 things to be scheduled correctly —
 * how much resources it needs, when it MUST run (SLA window), and how important it is.
 */
public class Task {

    // Task ka unique naam — jaise "T0", "T1", "BureauPull" etc.
    private String id;

    // Resources ki zaroorat — [CPU_cores, RAM_GB, GPU_units, Network_Gbps]
    // Example: Bank OCR task ko zyada GPU chahiye → [8, 32, 4, 1.5]
    private double[] resources;

    // SLA Window — task must run within [lowerBound, upperBound]
    // lowerBound = earliest slot, upperBound = latest slot
    // Example: Bureau Pull → slots 1 se 4 ke beech hi run hona chahiye
    private int lowerBound;
    private int upperBound;

    // Priority weight — higher means more important
    // Tier-1 PSU bank ka task → high weight (8-10)
    // Tier-3 NBFC ka task → low weight (1-3)
    private double weight;

    // Constructors
    public Task() {}

    public Task(String id, double[] resources, int lowerBound, int upperBound, double weight) {
        this.id = id;
        this.resources = resources;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.weight = weight;
    }

    // Getters & Setters
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

    /**
     * SLA window size — smaller means fewer slot options available
     * Used in sorting — tasks with tight windows are assigned first
     */
    public int getWindowSize() {
        return upperBound - lowerBound;
    }

    @Override
    public String toString() {
        return String.format("Task{id='%s', window=[%d,%d], weight=%.1f}",
                id, lowerBound, upperBound, weight);
    }
}