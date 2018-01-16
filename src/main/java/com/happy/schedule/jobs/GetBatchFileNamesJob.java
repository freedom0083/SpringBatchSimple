package com.happy.schedule.jobs;

import com.happy.database.entity.Batch;
import com.happy.database.mapper.BatchMapper;
import com.happy.jobLauncher.BatchResponseJobLauncher;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.List;

public class GetBatchFileNamesJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(GetBatchFileNamesJob.class);

    @Autowired
    public BatchMapper batchMapper;
    @Autowired
    private BatchResponseJobLauncher launcher;
    @Autowired
    private JobRepository jobRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<Batch> batchFileList =  batchMapper.getBatchFileNames();

        if (batchFileList != null && batchFileList.size() != 0 ) {
            for (Batch batch : batchFileList) {
                logger.info("File name is {}", batch.getFileName());

                try {
                    launcher.launchXmlFileToDatabaseJob();
                } catch (JobParametersInvalidException e) {
                    e.printStackTrace();
                } catch (JobExecutionAlreadyRunningException e) {
                    e.printStackTrace();
                } catch (JobRestartException e) {
                    e.printStackTrace();
                } catch (JobInstanceAlreadyCompleteException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
