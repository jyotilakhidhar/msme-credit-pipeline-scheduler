package com.scoreme.scheduler.model;

import java.util.Map;

/**
 * Holds the final output of the scheduling algorithm.
 *
 * Why this class exists:
 * Assignment ne kya output diya — yeh ek clean object mein
 * Wrapping in one object allows easy JSON serialization.
 * Like an exam result card — pass/fail, score, remarks.
 *
 * ScoreMe requires exactly these 5 fields in the output.
 */
public class AssignmentResult {

    // task_id → slot_number mapping
    // Example: {"T0": 2, "T1": 0, "T3": 1}
    // Matlab T0 ko slot 2 mein assign kiya, T1 ko slot 0 mein
    private Map<String, Integer> assignment;

    // Total penalty score — jitna kam utna better
    // Hamara formula: P_base + load imbalance penalty
    private double penalty;

    // Algorithm kitne milliseconds mein complete hua
    private long runtimeMs;

    // Kya valid assignment mila? true = feasible, false = infeasible
    private boolean feasible;

    // Reason for infeasibility — null if feasible
    // Example: "Task T3 ke liye koi valid slot nahi mila — sab conflicts ya capacity full"
    // Feasible hone par yeh null rahega
    private String violationReason;

    // Constructors
    public AssignmentResult() {}

    public AssignmentResult(Map<String, Integer> assignment, double penalty,
                             long runtimeMs, boolean feasible, String violationReason) {
        this.assignment = assignment;
        this.penalty = penalty;
        this.runtimeMs = runtimeMs;
        this.feasible = feasible;
        this.violationReason = violationReason;
    }

    // Getters & Setters
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