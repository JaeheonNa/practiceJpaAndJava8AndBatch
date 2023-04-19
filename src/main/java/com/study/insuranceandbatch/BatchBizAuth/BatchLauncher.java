package com.study.insuranceandbatch.BatchBizAuth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Configurable
@Slf4j
@RequiredArgsConstructor
public class BatchLauncher {
    private final JobLauncher jobLauncher;

    @Autowired
    @Qualifier("jobBatchCheckExpiredInsurance")
    private Job jobBatchCheckExpiredInsurance;


    @Autowired
    @Qualifier("jobBatchCheckExpiringInsurance")
    private Job jobBatchCheckExpiringInsurance;

    public void checkExpiredInsurance() {
        try {
        	JobParameters jobParameters = new JobParametersBuilder()
                    .addString("jobBatchExpiredInsuranceCheck", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();
        	jobLauncher.run(jobBatchCheckExpiredInsurance, jobParameters);
        }catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }

    public void checkExpiringInsurance() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("jobBatchExpiringInsuranceCheck", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();
            jobLauncher.run(jobBatchCheckExpiringInsurance, jobParameters);
        }catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }
}