package com.scoreme.scheduler.io;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.scoreme.scheduler.model.AssignmentResult;

/**
 * AssignmentResult ko JSON file mein likhta hai.
 * Output results/ folder mein save hoga.
 */
public class ResultWriter {

    private final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public void write(AssignmentResult result, String filePath) throws Exception {
        mapper.writeValue(new File(filePath), result);
        System.out.println("Result saved: " + filePath);
    }
}