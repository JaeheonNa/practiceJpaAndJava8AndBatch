package com.study.insurancandbatch.job.BatchBizAuth;

import com.study.insurancandbatch.job.JobDelegate;
import com.study.insurancandbatch.tasklet.CheckExpiredInsuranceTasklet;
import com.study.insurancandbatch.tasklet.CheckExpiringInsuranceTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchBizAuth {
	private final JobDelegate jobDelegate;

	private final StepBuilderFactory stepBuilderFactory;

    private final CheckExpiredInsuranceTasklet checkExpiredInsuranceTasklet;

	private final CheckExpiringInsuranceTasklet checkExpiringInsuranceTasklet;

	@Bean
	public Job jobBatchCheckExpiredInsurance(){
		return this.jobDelegate.batchBaseJobBuilderFactory()
			.get("checkExpiredInsuranceStateJob-")	// jobBatchBizAuth
			.incrementer(new RunIdIncrementer())
			.start(checkExpiredInsuranceState())
			.build();
	}
	@Bean
	public Step checkExpiredInsuranceState(){
		return this.stepBuilderFactory.get("checkExpiredInsuranceState")
				.tasklet(checkExpiredInsuranceTasklet).build();
	}

	@Bean
	public Job jobBatchCheckExpiringInsurance(){
		return this.jobDelegate.batchBaseJobBuilderFactory()
				.get("checkExpiringInsuranceStateJob-")	// jobBatchBizAuth
				.incrementer(new RunIdIncrementer())
				.start(checkExpiringInsuranceState())
				.build();
	}
	@Bean
	public Step checkExpiringInsuranceState(){
		return this.stepBuilderFactory.get("checkExpiringInsuranceState")
				.tasklet(checkExpiringInsuranceTasklet).build();
	}
}