package com.scoreme.scheduler.algorithm;

import java.util.Map;

import com.scoreme.scheduler.model.SchedulerInstance;

public class ConflictChecker {

  
    public boolean isSafe(int taskIndex, int slot,
                           SchedulerInstance instance,
                           Map<String, Integer> currentAssignment) {

        String taskId = instance.getTasks().get(taskIndex).getId();

        for (int[] conflict : instance.getConflicts()) {
            int a = conflict[0];
            int b = conflict[1];

            int neighborIndex = -1;
            if (a == taskIndex) neighborIndex = b;
            else if (b == taskIndex) neighborIndex = a;

            if (neighborIndex == -1) continue;

            String neighborId = instance.getTasks().get(neighborIndex).getId();
            Integer neighborSlot = currentAssignment.get(neighborId);

            if (neighborSlot != null && neighborSlot == slot) {
                return false; 
            }
        }
        return true; 
    }
}