package com.study.insuranceandbatch.batch.quartz;

import com.study.insuranceandbatch.batch.BatchBizAuth.BatchLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

@Configuration
public class BatchQuartzConfig {

    @Bean(name="BatchJobDetail")
    public MethodInvokingJobDetailFactoryBean BatchJobFactoryBean(BatchLauncher batchLauncher){
        return detailFactoryBean(batchLauncher);
    }

    @Bean(name="BatchJobCronTrigger")
    public CronTriggerFactoryBean BatchJobCronTriggerFactoryBean(@Qualifier("BatchJobDetail") MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean){
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(methodInvokingJobDetailFactoryBean.getObject());
        cronTriggerFactoryBean.setCronExpression("0 0/10 * * * ? "); // 10분마다 실시.
        return cronTriggerFactoryBean;
    }

    private <T>MethodInvokingJobDetailFactoryBean detailFactoryBean(T launcher) {
        MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        methodInvokingJobDetailFactoryBean.setTargetObject(launcher);
        methodInvokingJobDetailFactoryBean.setTargetMethod("checkExpiredInsurance");
        methodInvokingJobDetailFactoryBean.setConcurrent(false);
        return methodInvokingJobDetailFactoryBean;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Bean(name="BatchJobDetail1")
    public MethodInvokingJobDetailFactoryBean BatchJobFactoryBean1(BatchLauncher batchLauncher){
        return detailFactoryBean1(batchLauncher);
    }

    @Bean(name="BatchJobCronTrigger1")
    public CronTriggerFactoryBean BatchJobCronTriggerFactoryBean1(@Qualifier("BatchJobDetail1") MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean){
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(methodInvokingJobDetailFactoryBean.getObject());
        cronTriggerFactoryBean.setCronExpression("0 0 12 * * ? "); // 매일 정오에 실시.
        return cronTriggerFactoryBean;
    }

    private <T>MethodInvokingJobDetailFactoryBean detailFactoryBean1(T launcher) {
        MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        methodInvokingJobDetailFactoryBean.setTargetObject(launcher);
        methodInvokingJobDetailFactoryBean.setTargetMethod("checkExpiringInsurance");
        methodInvokingJobDetailFactoryBean.setConcurrent(false);
        return methodInvokingJobDetailFactoryBean;
    }


}