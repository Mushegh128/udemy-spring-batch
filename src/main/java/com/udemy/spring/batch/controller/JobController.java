package com.udemy.spring.batch.controller;

import com.udemy.spring.batch.model.JobParamsRequest;
import com.udemy.spring.batch.service.JobService;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobOperator jobOperator;

    @GetMapping("/start/{jobName}")
    public String startJob(@PathVariable String jobName, @RequestParam List<JobParamsRequest> jobParamsRequestList) {
        jobService.startJob(jobName, jobParamsRequestList);
        return "Job started";
    }

    @GetMapping("/stop{executionId}")
    public String stopJob(@PathVariable long executionId) {
        try {
            jobOperator.stop(executionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Job stopped";
    }

}
