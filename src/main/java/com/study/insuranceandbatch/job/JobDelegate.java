package com.study.insuranceandbatch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobDelegate {

	private final JobRepository jobRepository;

	@Bean
	public BaseJobBuilderFactory batchBaseJobBuilderFactory(){
		return new BaseJobBuilderFactory(jobRepository);
	}
}