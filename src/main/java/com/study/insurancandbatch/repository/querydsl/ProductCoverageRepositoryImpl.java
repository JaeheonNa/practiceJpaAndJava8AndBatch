package com.study.insurancandbatch.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.insurancandbatch.dto.projection.ProductCoverageProjection;
import com.study.insurancandbatch.entity.Product;
import com.study.insurancandbatch.entity.ProductCoverage;
import com.study.insurancandbatch.repository.querydsl.customInterface.ProductCoverageRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.study.insurancandbatch.entity.QProductCoverage.productCoverage;


@Repository
@RequiredArgsConstructor
public class ProductCoverageRepositoryImpl implements ProductCoverageRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductCoverageProjection> getAllProductCoverages() {
        List<ProductCoverageProjection> productCoverageProjections
                = queryFactory.select(Projections.constructor(ProductCoverageProjection.class,
                        productCoverage.product,
                        productCoverage.coverage
                )).from(productCoverage)
                .orderBy(productCoverage.product.seq.asc())
                .fetch();
        return productCoverageProjections;
    }

    @Override
    public List<ProductCoverage> findByProductSeqAndCoverageSeq(Long productSeq, List<Long> coverageSeqs) {
        List<ProductCoverage> productCoverages = queryFactory.selectFrom(productCoverage)
                .where(productCoverage.product.seq.eq(productSeq)
                        .and(productCoverage.coverage.seq.in(coverageSeqs)))
                .fetch();
        return productCoverages;
    }

    @Override
    public List<Long> getAllCoveragesByProduct(Product product) {
        List<Long> coverages = queryFactory.select(productCoverage.coverage.seq)
                .from(productCoverage)
                .where(productCoverage.product.eq(product))
                .fetch();
        return coverages;
    }

    @Override
    public List<ProductCoverage> findAllProductCoveragesByCoverageSeqs(List<Long> coverageSeqs) {
        List<ProductCoverage> productCoverages = queryFactory.selectFrom(productCoverage)
                .where(productCoverage.coverage.seq.in(coverageSeqs))
                .fetch();
        return productCoverages;
    }

}
