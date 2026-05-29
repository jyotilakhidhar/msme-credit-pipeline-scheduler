package com.scoreme.scheduler;

import com.scoreme.scheduler.algorithm.GreedyScheduler;
import com.scoreme.scheduler.model.AssignmentResult;
import com.scoreme.scheduler.model.SchedulerInstance;
import com.scoreme.scheduler.model.Task;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SchedulerTest {

    private final GreedyScheduler scheduler = new GreedyScheduler();

    /**
     * Test 1 — Single task → trivially slot 0 mein assign hona chahiye
     * Viva: "Simplest possible case — ek task, koi conflict nahi, assign hoga"
     */
    @Test
    void singleTask_shouldAssignToFirstValidSlot() {
        Task t0 = new Task("T0", new double[]{2, 4, 1, 0.5}, 0, 2, 5.0);
        List<double[]> caps = Arrays.asList(
            new double[]{8, 16, 4, 2},
            new double[]{8, 16, 4, 2},
            new double[]{8, 16, 4, 2}
        );
        SchedulerInstance inst = new SchedulerInstance(
            List.of(t0), List.of(), caps, 3
        );
        AssignmentResult result = scheduler.schedule(inst);
        assertTrue(result.isFeasible());
        assertEquals(0, result.getAssignment().get("T0")); // pehla valid slot
    }

    /**
     * Test 2 — All-conflict graph (chromatic number > K) → INFEASIBLE
     * T0-T1-T2 sab ek doosre se conflict karte hain, K=2 slots only
     * Viva: "3 tasks, 2 slots, sab conflict — pigeonhole se infeasible"
     */
    @Test
    void allConflictGraph_shouldReturnInfeasible() {
        Task t0 = new Task("T0", new double[]{1, 1, 1, 1}, 0, 1, 1.0);
        Task t1 = new Task("T1", new double[]{1, 1, 1, 1}, 0, 1, 1.0);
        Task t2 = new Task("T2", new double[]{1, 1, 1, 1}, 0, 1, 1.0);
        List<int[]> conflicts = Arrays.asList(
            new int[]{0, 1}, new int[]{1, 2}, new int[]{0, 2}
        );
        List<double[]> caps = Arrays.asList(
            new double[]{10, 10, 10, 10},
            new double[]{10, 10, 10, 10}
        );
        SchedulerInstance inst = new SchedulerInstance(
            List.of(t0, t1, t2), conflicts, caps, 2
        );
        AssignmentResult result = scheduler.schedule(inst);
        assertFalse(result.isFeasible());
        assertNotNull(result.getViolationReason());
    }

    /**
     * Test 3 — Zero capacity slot → task wahan assign nahi hoga
     * Slot 0 ki capacity zero hai — task slot 1 mein jaana chahiye
     * Viva: "Resource constraint F2 ka direct test"
     */
    @Test
    void zeroCapacitySlot_taskShouldSkipIt() {
        Task t0 = new Task("T0", new double[]{2, 4, 1, 0.5}, 0, 1, 3.0);
        List<double[]> caps = Arrays.asList(
            new double[]{0, 0, 0, 0},  // slot 0 — completely full/zero
            new double[]{8, 16, 4, 2}  // slot 1 — available
        );
        SchedulerInstance inst = new SchedulerInstance(
            List.of(t0), List.of(), caps, 2
        );
        AssignmentResult result = scheduler.schedule(inst);
        assertTrue(result.isFeasible());
        assertEquals(1, result.getAssignment().get("T0")); // slot 1 mein jaana chahiye
    }

    /**
     * Test 4 — Tight SLA window → har task ka sirf 1 valid slot
     * Viva: "F3 constraint ka test — SLA window strict hone par bhi algorithm kaam karta hai"
     */
    @Test
    void tightSlaWindows_shouldAssignCorrectly() {
        // T0 sirf slot 0 mein, T1 sirf slot 1 mein — koi conflict nahi
        Task t0 = new Task("T0", new double[]{1, 1, 1, 1}, 0, 0, 2.0);
        Task t1 = new Task("T1", new double[]{1, 1, 1, 1}, 1, 1, 2.0);
        List<double[]> caps = Arrays.asList(
            new double[]{8, 8, 8, 8},
            new double[]{8, 8, 8, 8}
        );
        SchedulerInstance inst = new SchedulerInstance(
            List.of(t0, t1), List.of(), caps, 2
        );
        AssignmentResult result = scheduler.schedule(inst);
        assertTrue(result.isFeasible());
        assertEquals(0, result.getAssignment().get("T0"));
        assertEquals(1, result.getAssignment().get("T1"));
    }
}