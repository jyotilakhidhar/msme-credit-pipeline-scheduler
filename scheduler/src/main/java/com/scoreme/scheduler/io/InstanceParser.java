package com.scoreme.scheduler.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scoreme.scheduler.model.SchedulerInstance;
import com.scoreme.scheduler.model.Task;

/**
 * JSON input file ko SchedulerInstance mein convert karta hai.
 * Python generator ne jo JSON banaya — woh yahan read hota hai.
 */
public class InstanceParser {

    private final ObjectMapper mapper = new ObjectMapper();

    public SchedulerInstance parse(String filePath) throws Exception {
        JsonNode root = mapper.readTree(new File(filePath));

        int k = root.get("k").asInt();

        // Tasks parse karo
        List<Task> tasks = new ArrayList<>();
        JsonNode tasksNode = root.get("tasks");
        for (JsonNode t : tasksNode) {
            String id = t.get("id").asText();
            int lb = t.get("lower_bound").asInt();
            int ub = t.get("upper_bound").asInt();
            double weight = t.get("weight").asDouble();

            JsonNode resNode = t.get("resources");
            double[] resources = new double[resNode.size()];
            for (int i = 0; i < resNode.size(); i++) {
                resources[i] = resNode.get(i).asDouble();
            }
            tasks.add(new Task(id, resources, lb, ub, weight));
        }

        // Conflicts parse karo — [[0,1],[1,3]...]
        List<int[]> conflicts = new ArrayList<>();
        JsonNode conflictsNode = root.get("conflicts");
        for (JsonNode c : conflictsNode) {
            conflicts.add(new int[]{c.get(0).asInt(), c.get(1).asInt()});
        }

        // Capacities parse karo — har slot ki [CPU,RAM,GPU,Network]
        List<double[]> capacities = new ArrayList<>();
        JsonNode capsNode = root.get("capacities");
        for (JsonNode cap : capsNode) {
            double[] capArr = new double[cap.size()];
            for (int i = 0; i < cap.size(); i++) {
                capArr[i] = cap.get(i).asDouble();
            }
            capacities.add(capArr);
        }

        return new SchedulerInstance(tasks, conflicts, capacities, k);
    }
}