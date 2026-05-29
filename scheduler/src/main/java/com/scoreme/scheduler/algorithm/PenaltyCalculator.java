package com.scoreme.scheduler.algorithm;

import java.util.Map;

import com.scoreme.scheduler.model.SchedulerInstance;
import com.scoreme.scheduler.model.Task;


public class PenaltyCalculator {

    private static final double LAMBDA = 0.5; // imbalance ka weight

    public double calculate(SchedulerInstance instance,
                            Map<String, Integer> assignment) {

        double pBase = 0.0;
        double imbalance = 0.0;

        for (Task task : instance.getTasks()) {
            Integer slot = assignment.get(task.getId());
            if (slot != null) {
                pBase += task.getWeight() * slot;
            }
        }

        int numDims = instance.getCapacities().get(0).length;

        for (int s = 0; s < instance.getK(); s++) {
            double[] capacity = instance.getCapacities().get(s);
            double[] used = new double[numDims];

            for (Task task : instance.getTasks()) {
                Integer slot = assignment.get(task.getId());
                if (slot != null && slot == s) {
                    double[] res = task.getResources();
                    for (int d = 0; d < numDims; d++) {
                        used[d] += res[d];
                    }
                }
            }

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