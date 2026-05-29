package com.scoreme.scheduler.model;

import java.util.List;

/**
 * Holds the complete scheduling problem — all inputs in one place.
 *
 * Why this class exists:
 * Passing one object to the algorithm is cleaner than passing
 * 6-7 alag parameters pass karna. Jaise ek exam paper mein
 * all parameters separately.
 */
public class SchedulerInstance {

    // Saare tasks ki list — T0, T1, T2...
    private List<Task> tasks;

    // Conflict pairs — [i, j] matlab task i aur task j
    // ek hi slot mein NAHI chal sakte (GPU clash ya Kafka partition clash)
    // Example: [[0,1], [1,3]] → T0-T1 conflict, T1-T3 conflict
    private List<int[]> conflicts;

    // Har slot ki capacity — [CPU, RAM, GPU, Network]
    // capacities.get(0) → slot 0 ki capacity
    // capacities.get(1) → slot 1 ki capacity
    private List<double[]> capacities;

    // Total number of available slots
    private int k;

    // Constructors
    public SchedulerInstance() {}

    public SchedulerInstance(List<Task> tasks, List<int[]> conflicts,
                              List<double[]> capacities, int k) {
        this.tasks = tasks;
        this.conflicts = conflicts;
        this.capacities = capacities;
        this.k = k;
    }

    // Getters & Setters
    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }

    public List<int[]> getConflicts() { return conflicts; }
    public void setConflicts(List<int[]> conflicts) { this.conflicts = conflicts; }

    public List<double[]> getCapacities() { return capacities; }
    public void setCapacities(List<double[]> capacities) { this.capacities = capacities; }

    public int getK() { return k; }
    public void setK(int k) { this.k = k; }

    @Override
    public String toString() {
        return String.format("SchedulerInstance{tasks=%d, slots=%d, conflicts=%d}",
                tasks.size(), k, conflicts.size());
    }
}