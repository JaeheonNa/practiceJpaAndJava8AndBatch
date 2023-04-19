package com.study.insurancandbatch.job;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;

public class BaseJobBuilderFactory extends JobBuilderFactory {
	public BaseJobBuilderFactory(JobRepository jobRepository) {
		super(jobRepository);
	}
}