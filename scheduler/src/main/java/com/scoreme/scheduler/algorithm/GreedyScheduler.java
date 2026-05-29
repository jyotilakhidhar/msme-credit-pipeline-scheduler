package com.scoreme.scheduler.algorithm;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import com.scoreme.scheduler.model.AssignmentResult;
import com.scoreme.scheduler.model.SchedulerInstance;
import com.scoreme.scheduler.model.Task;

/**
 * Priority-Aware Conflict-First Greedy Scheduler
 *
 * Algorithm logic (teacher-classroom analogy):
 * 1. Sabse important + tight-window wale task ko PEHLE schedule karo
 * 2. Har task ke liye uski SLA window mein ek-ek slot try karo
 * 3. Slot valid hai agar:
 *    F1 — koi conflicting task us slot mein nahi (ConflictChecker)
 *    F2 — slot ki capacity exceed nahi hogi (ResourceChecker)
 *    F3 — slot task ki SLA window mein hai (loop condition se automatically)
 * 4. Koi slot nahi mila → INFEASIBLE report karo
 */
public class GreedyScheduler {

    private final ConflictChecker conflictChecker = new ConflictChecker();
    private final ResourceChecker resourceChecker = new ResourceChecker();
    private final PenaltyCalculator penaltyCalculator = new PenaltyCalculator();

    public AssignmentResult schedule(SchedulerInstance instance) {

        long startTime = System.currentTimeMillis();

        List<Task> tasks = instance.getTasks();
        int n = tasks.size();

        // Step 1 — Index list banao aur sort karo
        // Sort order: high weight pehle, phir choti window pehle
        // Reason: important + constrained tasks ko pehle assign karna safer hai
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < n; i++) order.add(i);

        order.sort((i, j) -> {
            Task ti = tasks.get(i);
            Task tj = tasks.get(j);
            // High weight pehle (descending)
            int cmp = Double.compare(tj.getWeight(), ti.getWeight());
            if (cmp != 0) return cmp;
            // Same weight? Choti window pehle (ascending)
            return Integer.compare(ti.getWindowSize(), tj.getWindowSize());
        });

        // Step 2 — Greedy assignment
        Map<String, Integer> assignment = new LinkedHashMap<>();

        for (int idx : order) {
            Task task = tasks.get(idx);
            boolean assigned = false;

            // F3 — sirf SLA window ke andar slots try karo
            for (int slot = task.getLowerBound(); slot <= task.getUpperBound(); slot++) {

                // F1 — conflict check
                boolean noConflict = conflictChecker.isSafe(idx, slot, instance, assignment);
                if (!noConflict) continue;

                // F2 — resource check
                boolean resourceOk = resourceChecker.fits(idx, slot, instance, assignment);
                if (!resourceOk) continue;

                // Dono pass → assign karo!
                assignment.put(task.getId(), slot);
                assigned = true;
                break;
            }

            // Koi slot nahi mila — INFEASIBLE
            if (!assigned) {
                long runtime = System.currentTimeMillis() - startTime;
                return new AssignmentResult(
                    assignment, -1.0, runtime, false,
                    "Task " + task.getId() + " ke liye koi valid slot nahi mila " +
                    "(window=[" + task.getLowerBound() + "," + task.getUpperBound() + "]," +
                    " conflicts aur resource limits ke baad)"
                );
            }
        }

        // Step 3 — Penalty calculate karo
        double penalty = penaltyCalculator.calculate(instance, assignment);
        long runtime = System.currentTimeMillis() - startTime;

        return new AssignmentResult(assignment, penalty, runtime, true, null);
    }
}