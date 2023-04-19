package com.study.insuranceandbatch.batch.quartz;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class BatchSchedulerConfig {
    @Autowired
    @Qualifier("BatchJobDetail")
    private JobDetail BatchJobDetail;

    @Autowired
    @Qualifier("BatchJobCronTrigger")
    private Trigger BatchJobCronTrigger;

    @Autowired
    @Qualifier("BatchJobDetail1")
    private JobDetail BatchJobDetail1;

    @Autowired
    @Qualifier("BatchJobCronTrigger1")
    private Trigger BatchJobCronTrigger1;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobDetails(BatchJobDetail);
        schedulerFactoryBean.setTriggers(BatchJobCronTrigger);
        return schedulerFactoryBean;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean1(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobDetails(BatchJobDetail1);
        schedulerFactoryBean.setTriggers(BatchJobCronTrigger1);
        return schedulerFactoryBean;
    }
}