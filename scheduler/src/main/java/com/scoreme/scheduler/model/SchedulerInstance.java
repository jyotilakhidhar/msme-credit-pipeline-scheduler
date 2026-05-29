package com.scoreme.scheduler.model;

import java.util.List;

public class SchedulerInstance {

    private List<Task> tasks;

    private List<int[]> conflicts;

    private List<double[]> capacities;

    private int k;

    public SchedulerInstance() {}

    public SchedulerInstance(List<Task> tasks, List<int[]> conflicts,
                              List<double[]> capacities, int k) {
        this.tasks = tasks;
        this.conflicts = conflicts;
        this.capacities = capacities;
        this.k = k;
    }

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