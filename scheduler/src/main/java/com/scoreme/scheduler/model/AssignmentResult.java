package com.scoreme.scheduler.model;

import java.util.Map;


public class AssignmentResult {

    private Map<String, Integer> assignment;

 
    private double penalty;

    private long runtimeMs;

    private boolean feasible;

    private String violationReason;

    public AssignmentResult() {}

    public AssignmentResult(Map<String, Integer> assignment, double penalty,
                             long runtimeMs, boolean feasible, String violationReason) {
        this.assignment = assignment;
        this.penalty = penalty;
        this.runtimeMs = runtimeMs;
        this.feasible = feasible;
        this.violationReason = violationReason;
    }

    public Map<String, Integer> getAssignment() { return assignment; }
    public void setAssignment(Map<String, Integer> assignment) { this.assignment = assignment; }

    public double getPenalty() { return penalty; }
    public void setPenalty(double penalty) { this.penalty = penalty; }

    public long getRuntimeMs() { return runtimeMs; }
    public void setRuntimeMs(long runtimeMs) { this.runtimeMs = runtimeMs; }

    public boolean isFeasible() { return feasible; }
    public void setFeasible(boolean feasible) { this.feasible = feasible; }

    public String getViolationReason() { return violationReason; }
    public void setViolationReason(String violationReason) { this.violationReason = violationReason; }

    @Override
    public String toString() {
        return String.format("AssignmentResult{feasible=%b, penalty=%.2f, runtimeMs=%d}",
                feasible, penalty, runtimeMs);
    }
}