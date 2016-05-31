package com.sample.jbatchmarathon;

import java.io.IOException;
import java.io.PrintWriter;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jBatchを起動するためのServletです。
 * 
 * @author akira_abe
 */
@WebServlet(urlPatterns = "/batch-start")
public class BatchStartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ジョブの起動
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        long executionId = jobOperator.start("my-batch-job", null);

        PrintWriter out = response.getWriter();
        out.println("jBatchMarathon start!!! executionId -> " + executionId);
    }
}