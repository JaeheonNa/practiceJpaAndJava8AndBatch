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

    private final ProductRepository productRepository;
    private final CoverageRepository coverageRepository;
    private final ProductCoverageRepository productCoverageRepository;

    @PostConstruct
    @Transactional
    public void init(){
        Product product_1 = new Product("여행자 보험", 1, 3);
        Product product_2 = new Product("휴대폰 보험", 1, 12);
        productRepository.save(product_1);
        productRepository.save(product_2);
        Coverage coverage_1 = new Coverage("상해 치료비", 1000000L, 100L);
        Coverage coverage_2 = new Coverage("항공기 지연 도착 시 보상금", 500000L, 100L);
        Coverage coverage_3 = new Coverage("부분 손실", 750000L, 38L);
        Coverage coverage_4 = new Coverage("전체 손실", 1570000, 40L);
        coverageRepository.save(coverage_1);
        coverageRepository.save(coverage_2);
        coverageRepository.save(coverage_3);
        coverageRepository.save(coverage_4);
        ProductCoverage productCoverage_1 = new ProductCoverage(product_1, coverage_1);
        ProductCoverage productCoverage_2 = new ProductCoverage(product_1, coverage_2);
        ProductCoverage productCoverage_3 = new ProductCoverage(product_2, coverage_3);
        ProductCoverage productCoverage_4 = new ProductCoverage(product_2, coverage_4);
        productCoverageRepository.save(productCoverage_1);
        productCoverageRepository.save(productCoverage_2);
        productCoverageRepository.save(productCoverage_3);
        productCoverageRepository.save(productCoverage_4);
    }
}
