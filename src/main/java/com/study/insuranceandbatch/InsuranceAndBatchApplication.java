package com.study.insuranceandbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.study.insuranceandbatch"})
@RequiredArgsConstructor
@EnableBatchProcessing
public class InsuranceAndBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(InsuranceAndBatchApplication.class, args);
    }
}
