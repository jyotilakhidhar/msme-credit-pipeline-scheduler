package com.scoreme.scheduler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.scoreme.scheduler.algorithm.GreedyScheduler;
import com.scoreme.scheduler.io.InstanceParser;
import com.scoreme.scheduler.io.ResultWriter;
import com.scoreme.scheduler.model.AssignmentResult;
import com.scoreme.scheduler.model.SchedulerInstance;

/**
 * Entry point — JSON file read karo, algorithm chalo, result save karo.
 * Usage: mvn spring-boot:run -Dspring-boot.run.arguments="<input.json> <output.json>"
 */
@SpringBootApplication
public class SchedulerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        String inputFile  = args.length > 0 ? args[0] : "../instances/sample.json";
        String outputFile = args.length > 1 ? args[1] : "../results/result.json";

        System.out.println("Input:  " + inputFile);
        System.out.println("Output: " + outputFile);

        InstanceParser parser = new InstanceParser();
        SchedulerInstance instance = parser.parse(inputFile);
        System.out.println("Loaded: " + instance);

        GreedyScheduler scheduler = new GreedyScheduler();
        AssignmentResult result = scheduler.schedule(instance);
        System.out.println("Result: " + result);

        ResultWriter writer = new ResultWriter();
        writer.write(result, outputFile);
    }
}