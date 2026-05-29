package com.scoreme.scheduler.algorithm;

import java.util.Map;

import com.scoreme.scheduler.model.SchedulerInstance;
import com.scoreme.scheduler.model.Task;
/**
 * Checks F2 constraint — slot ki capacity exceed na ho.
 * Jaise classroom mein seats full hain toh aur students nahi aa sakte.
 */
public class ResourceChecker {

    /**
     * Kya task taskIndex slot 's' mein fit hoga — resources ke hisaab se?
     */
    public boolean fits(int taskIndex, int slot,
                        SchedulerInstance instance,
                        Map<String, Integer> currentAssignment) {

        Task newTask = instance.getTasks().get(taskIndex);
        double[] capacity = instance.getCapacities().get(slot);
        double[] required = newTask.getResources();

        // Pehle calculate karo — abhi is slot mein kitne resources use ho rahe hain
        double[] used = new double[capacity.length];

        for (Map.Entry<String, Integer> entry : currentAssignment.entrySet()) {
            if (entry.getValue() == slot) {
                // Yeh task already is slot mein hai
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

        // Ab check karo — naya task add karne ke baad capacity exceed hogi?
        for (int d = 0; d < capacity.length; d++) {
            if (used[d] + required[d] > capacity[d]) {
                return false; // capacity full!
            }
        }
        return true;
    }
}