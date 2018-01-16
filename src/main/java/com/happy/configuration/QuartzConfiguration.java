package com.happy.configuration;

import com.happy.schedule.MyScheduler;
import com.happy.schedule.QuartzJobLauncher;
import com.happy.schedule.SpringJobFactory;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class QuartzConfiguration {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobLocator jobLocator;

    @Resource
    private SpringJobFactory springJobFactory;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean jobfactory = new JobDetailFactoryBean();
        jobfactory.setJobClass(QuartzJobLauncher.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jobName", "job1");
        map.put("jobLauncher", jobLauncher);
        map.put("jobLocator", jobLocator);
        jobfactory.setJobDataAsMap(map);
        jobfactory.setGroup("group1");
        jobfactory.setName("job1");
        return jobfactory;
    }

    // Job is scheduled after every 2 minute
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean ctFactory = new CronTriggerFactoryBean();
        ctFactory.setJobDetail(jobDetailFactoryBean().getObject());
        ctFactory.setStartDelay(3000);
        ctFactory.setName("trigger1");
        ctFactory.setGroup("group1");
        ctFactory.setCronExpression("0/10 1 * * * ?");
        return ctFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //schedulerFactoryBean.setJobFactory(springJobFactory);
        schedulerFactoryBean.setTriggers(cronTriggerFactoryBean().getObject());
        return schedulerFactoryBean;
    }


}
