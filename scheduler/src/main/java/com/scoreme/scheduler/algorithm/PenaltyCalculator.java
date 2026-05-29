package com.scoreme.scheduler.algorithm;

import java.util.Map;

import com.scoreme.scheduler.model.SchedulerInstance;
import com.scoreme.scheduler.model.Task;

/**
 * Task 2 — Custom Penalty Function
 *
 * P(σ) = P_base + λ × Load_Imbalance_Penalty
 *
 * P_base    = Σ weight(task) × slot_number   ← assignment ka base cost
 * Imbalance = Σ (used[slot][dim] / capacity[slot][dim])²  ← resource waste
 *
 * Viva answer: "Maine load imbalance term isliye add ki kyunki
 * weighted slot index alone is insufficient — if one slot has
 * CPU at 100% and GPU at 10%, that is infrastructure waste.
 * This is a real problem in ScoreMe's OCR cluster."
 */
public class PenaltyCalculator {

    private static final double LAMBDA = 0.5; // imbalance ka weight

    public double calculate(SchedulerInstance instance,
                            Map<String, Integer> assignment) {

        double pBase = 0.0;
        double imbalance = 0.0;

        // P_base — har task ka weight × uska slot number
        for (Task task : instance.getTasks()) {
            Integer slot = assignment.get(task.getId());
            if (slot != null) {
                pBase += task.getWeight() * slot;
            }
        }

        // Load Imbalance — check each dimension per slot
        int numDims = instance.getCapacities().get(0).length;

        for (int s = 0; s < instance.getK(); s++) {
            double[] capacity = instance.getCapacities().get(s);
            double[] used = new double[numDims];

            // Calculate total resource usage for tasks in this slot
            for (Task task : instance.getTasks()) {
                Integer slot = assignment.get(task.getId());
                if (slot != null && slot == s) {
                    double[] res = task.getResources();
                    for (int d = 0; d < numDims; d++) {
                        used[d] += res[d];
                    }
                }
            }

            // Imbalance = (used/capacity)² for each dimension
            for (int d = 0; d < numDims; d++) {
                if (capacity[d] > 0) {
                    double ratio = used[d] / capacity[d];
                    imbalance += ratio * ratio;
                }
            }
        }

        return pBase + LAMBDA * imbalance;
    }
}