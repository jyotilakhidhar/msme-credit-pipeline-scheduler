package com.scoreme.scheduler.algorithm;

import java.util.Map;

import com.scoreme.scheduler.model.SchedulerInstance;
import com.scoreme.scheduler.model.Task;

public class ResourceChecker {

    public boolean fits(int taskIndex, int slot,
                        SchedulerInstance instance,
                        Map<String, Integer> currentAssignment) {

        Task newTask = instance.getTasks().get(taskIndex);
        double[] capacity = instance.getCapacities().get(slot);
        double[] required = newTask.getResources();

        double[] used = new double[capacity.length];

        for (Map.Entry<String, Integer> entry : currentAssignment.entrySet()) {
            if (entry.getValue() == slot) {
                String assignedId = entry.getKey();
                Task assignedTask = instance.getTasks().stream()
                        .filter(t -> t.getId().equals(assignedId))
                        .findFirst().orElse(null);

                if (assignedTask != null) {
                    double[] res = assignedTask.getResources();
                    for (int d = 0; d < used.length; d++) {
                        used[d] += res[d];
                    }
                }
            }
        }

        for (int d = 0; d < capacity.length; d++) {
            if (used[d] + required[d] > capacity[d]) {
                return false; 
            }
        }
        return true;
    }
}