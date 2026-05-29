package com.scoreme.scheduler.algorithm;

import java.util.Map;

import com.scoreme.scheduler.model.SchedulerInstance;

/**
 * Checks F1 constraint — no two conflicting tasks in the same slot.
 * GPU clash ya Kafka partition clash detect karta hai.
 */
public class ConflictChecker {

    /**
     * Kya task taskIndex ko slot 's' mein assign karna safe hai?
     * Agar koi bhi conflicting task already slot 's' mein hai → false
     */
    public boolean isSafe(int taskIndex, int slot,
                           SchedulerInstance instance,
                           Map<String, Integer> currentAssignment) {

        String taskId = instance.getTasks().get(taskIndex).getId();

        for (int[] conflict : instance.getConflicts()) {
            int a = conflict[0];
            int b = conflict[1];

            // Pata karo — is conflict pair mein taskIndex involved hai?
            int neighborIndex = -1;
            if (a == taskIndex) neighborIndex = b;
            else if (b == taskIndex) neighborIndex = a;

            if (neighborIndex == -1) continue; // yeh conflict humse related nahi

            // Neighbor already assign hua hai?
            String neighborId = instance.getTasks().get(neighborIndex).getId();
            Integer neighborSlot = currentAssignment.get(neighborId);

            if (neighborSlot != null && neighborSlot == slot) {
                return false; // CLASH! same slot mein conflict hai
            }
        }
        return true; // safe hai
    }
}