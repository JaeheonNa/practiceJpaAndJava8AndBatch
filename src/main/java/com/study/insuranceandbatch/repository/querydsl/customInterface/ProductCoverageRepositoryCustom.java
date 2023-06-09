package com.study.insuranceandbatch.repository.querydsl.customInterface;

import com.study.insuranceandbatch.dto.projection.ProductCoverageProjection;
import com.study.insuranceandbatch.entity.Product;
import com.study.insuranceandbatch.entity.ProductCoverage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCoverageRepositoryCustom {
    List<ProductCoverageProjection> getAliveProductCoverages();
    List<ProductCoverage> findByProductSeqAndCoverageSeqs(Long productSeq, List<Long> coverageSeqs);
    List<Long> getAllCoveragesByProduct(Product product);

    List<ProductCoverage> findAllProductCoveragesByCoverageSeqs(List<Long> coverageSeqs);
    List<ProductCoverage> findAliveByProductAndCoverageSeqs(Product product, List<Long> coverageSeqs);


}
