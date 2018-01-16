package com.happy.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class QuartzJobLauncher extends QuartzJobBean {
    private static final Logger log = LoggerFactory.getLogger(QuartzJobLauncher.class);

    private JobLauncher jobLauncher;
    private JobLocator jobLocator;
    private String jobName;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            Job job = jobLocator.getJob(jobName);
            JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
            log.info("{}_{} was completed successfully", job.getName(), jobExecution.getId());
        } catch (Exception e) {
            log.error("Encountered job execution exception!");
        }
    }
}
