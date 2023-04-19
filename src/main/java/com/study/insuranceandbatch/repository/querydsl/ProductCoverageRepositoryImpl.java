package com.study.insuranceandbatch.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.insuranceandbatch.dto.projection.ProductCoverageProjection;
import com.study.insuranceandbatch.entity.Product;
import com.study.insuranceandbatch.entity.ProductCoverage;
import com.study.insuranceandbatch.repository.querydsl.customInterface.ProductCoverageRepositoryCustom;
import com.study.insuranceandbatch.entity.QProductCoverage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class ProductCoverageRepositoryImpl implements ProductCoverageRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductCoverageProjection> getAllProductCoverages() {
        List<ProductCoverageProjection> productCoverageProjections
                = queryFactory.select(Projections.constructor(ProductCoverageProjection.class,
                        QProductCoverage.productCoverage.product,
                        QProductCoverage.productCoverage.coverage
                )).from(QProductCoverage.productCoverage)
                .orderBy(QProductCoverage.productCoverage.product.seq.asc())
                .fetch();
        return productCoverageProjections;
    }

    @Override
    public List<ProductCoverage> findByProductSeqAndCoverageSeq(Long productSeq, List<Long> coverageSeqs) {
        List<ProductCoverage> productCoverages = queryFactory.selectFrom(QProductCoverage.productCoverage)
                .where(QProductCoverage.productCoverage.product.seq.eq(productSeq)
                        .and(QProductCoverage.productCoverage.coverage.seq.in(coverageSeqs)))
                .fetch();
        return productCoverages;
    }

    @Override
    public List<Long> getAllCoveragesByProduct(Product product) {
        List<Long> coverages = queryFactory.select(QProductCoverage.productCoverage.coverage.seq)
                .from(QProductCoverage.productCoverage)
                .where(QProductCoverage.productCoverage.product.eq(product))
                .fetch();
        return coverages;
    }

    @Override
    public List<ProductCoverage> findAllProductCoveragesByCoverageSeqs(List<Long> coverageSeqs) {
        List<ProductCoverage> productCoverages = queryFactory.selectFrom(QProductCoverage.productCoverage)
                .where(QProductCoverage.productCoverage.coverage.seq.in(coverageSeqs))
                .fetch();
        return productCoverages;
    }

}
