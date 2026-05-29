package com.scoreme.scheduler.algorithm;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.scoreme.scheduler.model.AssignmentResult;
import com.scoreme.scheduler.model.SchedulerInstance;
import com.scoreme.scheduler.model.Task;


public class GreedyScheduler {

    private final ConflictChecker conflictChecker = new ConflictChecker();
    private final ResourceChecker resourceChecker = new ResourceChecker();
    private final PenaltyCalculator penaltyCalculator = new PenaltyCalculator();

    public AssignmentResult schedule(SchedulerInstance instance) {

        long startTime = System.currentTimeMillis();

        List<Task> tasks = instance.getTasks();
        int n = tasks.size();

        
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < n; i++) order.add(i);

        order.sort((i, j) -> {
            Task ti = tasks.get(i);
            Task tj = tasks.get(j);
            int cmp = Double.compare(tj.getWeight(), ti.getWeight());
            if (cmp != 0) return cmp;
            return Integer.compare(ti.getWindowSize(), tj.getWindowSize());
        });

        Map<String, Integer> assignment = new LinkedHashMap<>();

        for (int idx : order) {
            Task task = tasks.get(idx);
            boolean assigned = false;

            for (int slot = task.getLowerBound(); slot <= task.getUpperBound(); slot++) {

                boolean noConflict = conflictChecker.isSafe(idx, slot, instance, assignment);
                if (!noConflict) continue;

                boolean resourceOk = resourceChecker.fits(idx, slot, instance, assignment);
                if (!resourceOk) continue;

                assignment.put(task.getId(), slot);
                assigned = true;
                break;
            }

            if (!assigned) {
                long runtime = System.currentTimeMillis() - startTime;
                return new AssignmentResult(
                    assignment, -1.0, runtime, false,
                    "Task " + task.getId() + " ke liye koi valid slot nahi mila " +
                    "(window=[" + task.getLowerBound() + "," + task.getUpperBound() + "]," +
                    " — all conflict and resource constraints checked)"
                );
            }
        }

        double penalty = penaltyCalculator.calculate(instance, assignment);
        long runtime = System.currentTimeMillis() - startTime;

        return new AssignmentResult(assignment, penalty, runtime, true, null);
    }
}