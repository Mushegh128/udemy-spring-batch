package com.udemy.spring.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstStepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("before step" + stepExecution.getStepName());
        System.out.println("before exec context" + stepExecution.getJobExecution().getExecutionContext());
        System.out.println("before step exec context" + stepExecution.getExecutionContext());

        stepExecution.getExecutionContext().put("Gexamik2", "Gexamik2 value");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("after step" + stepExecution.getStepName());
        System.out.println("after exec context" + stepExecution.getJobExecution().getExecutionContext());
        System.out.println("after step exec context" + stepExecution.getExecutionContext());
        return null;
    }
}
