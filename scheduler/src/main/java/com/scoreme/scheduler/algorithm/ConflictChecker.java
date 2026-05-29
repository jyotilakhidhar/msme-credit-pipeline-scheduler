package com.scoreme.scheduler.algorithm;

import java.util.Map;

import com.scoreme.scheduler.model.SchedulerInstance;

/**
 * Checks F1 constraint — no two conflicting tasks in the same slot.
 * Detects GPU or Kafka partition clashes between tasks.
 */
public class ConflictChecker {

    /**
     * Is it safe to assign taskIndex to the given slot?
     * Returns false if any conflicting task is already in this slot.
     */
    public boolean isSafe(int taskIndex, int slot,
                           SchedulerInstance instance,
                           Map<String, Integer> currentAssignment) {

        String taskId = instance.getTasks().get(taskIndex).getId();

        for (int[] conflict : instance.getConflicts()) {
            int a = conflict[0];
            int b = conflict[1];

            // Check if taskIndex is part of this conflict pair
            int neighborIndex = -1;
            if (a == taskIndex) neighborIndex = b;
            else if (b == taskIndex) neighborIndex = a;

            if (neighborIndex == -1) continue; // yeh conflict humse related nahi

            // Has the neighbor task been assigned yet?
            String neighborId = instance.getTasks().get(neighborIndex).getId();
            Integer neighborSlot = currentAssignment.get(neighborId);

            if (neighborSlot != null && neighborSlot == slot) {
                return false; // Conflict detected in same slot
            }
        }
        return true; // No conflict found
    }
}