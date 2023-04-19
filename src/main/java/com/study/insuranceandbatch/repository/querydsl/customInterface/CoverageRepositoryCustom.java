package com.study.insuranceandbatch.repository.querydsl.customInterface;

import com.study.insuranceandbatch.entity.Coverage;
import com.study.insuranceandbatch.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoverageRepositoryCustom {
    List<Coverage> findAllCoveragesByProduct(Product product);
}
