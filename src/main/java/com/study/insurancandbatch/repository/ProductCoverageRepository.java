package com.study.insurancandbatch.repository;

import com.study.insurancandbatch.entity.ProductCoverage;
import com.study.insurancandbatch.repository.querydsl.customInterface.ProductCoverageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductCoverageRepository extends JpaRepository<ProductCoverage, Long>, ProductCoverageRepositoryCustom {

}
