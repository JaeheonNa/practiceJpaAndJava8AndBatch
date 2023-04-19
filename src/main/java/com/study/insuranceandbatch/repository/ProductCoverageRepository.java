package com.study.insuranceandbatch.repository;

import com.study.insuranceandbatch.entity.ProductCoverage;
import com.study.insuranceandbatch.repository.querydsl.customInterface.ProductCoverageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductCoverageRepository extends JpaRepository<ProductCoverage, Long>, ProductCoverageRepositoryCustom {

}
