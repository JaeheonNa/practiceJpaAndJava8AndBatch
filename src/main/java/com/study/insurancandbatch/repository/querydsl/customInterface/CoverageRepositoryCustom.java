package com.study.insurancandbatch.repository.querydsl.customInterface;

import com.study.insurancandbatch.entity.Coverage;
import com.study.insurancandbatch.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoverageRepositoryCustom {
    List<Coverage> findAllCoveragesByProduct(Product product);
}
