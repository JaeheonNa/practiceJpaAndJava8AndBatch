package com.study.insuranceandbatch;

import com.study.insuranceandbatch.entity.Coverage;
import com.study.insuranceandbatch.entity.Product;
import com.study.insuranceandbatch.entity.ProductCoverage;
import com.study.insuranceandbatch.repository.CoverageRepository;
import com.study.insuranceandbatch.repository.ProductCoverageRepository;
import com.study.insuranceandbatch.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = {"com.study.insuranceandbatch"})
@RequiredArgsConstructor
@EnableBatchProcessing
public class InsuranceAndBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(InsuranceAndBatchApplication.class, args);
    }
}
