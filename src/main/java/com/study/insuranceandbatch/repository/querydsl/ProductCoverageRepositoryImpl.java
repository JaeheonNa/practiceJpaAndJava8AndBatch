package com.study.insuranceandbatch.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.insuranceandbatch.common.CommonConstant;
import com.study.insuranceandbatch.dto.projection.ProductCoverageProjection;
import com.study.insuranceandbatch.entity.Product;
import com.study.insuranceandbatch.entity.ProductCoverage;
import com.study.insuranceandbatch.repository.querydsl.customInterface.ProductCoverageRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.study.insuranceandbatch.entity.QProductCoverage.productCoverage;


@Repository
@RequiredArgsConstructor
public class ProductCoverageRepositoryImpl implements ProductCoverageRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductCoverageProjection> getAliveProductCoverages() {
        List<ProductCoverageProjection> productCoverageProjections
                = queryFactory.select(Projections.constructor(ProductCoverageProjection.class,
                        productCoverage.product,
                        productCoverage.coverage,
                        productCoverage.useYn
                )).from(productCoverage)
                .orderBy(productCoverage.product.seq.asc())
                .fetch();
        return productCoverageProjections;
    }

    @Override
    public List<ProductCoverage> findByProductSeqAndCoverageSeqs(Long productSeq, List<Long> coverageSeqs) {
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
                .where(productCoverage.product.eq(product)
                        .and(productCoverage.useYn.eq(CommonConstant.ALIVE)))
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

    @Override
    public List<ProductCoverage> findAliveByProductAndCoverageSeqs(Product product, List<Long> coverageSeqs){
        List<ProductCoverage> productCoverages = queryFactory.select(productCoverage)
                .from(productCoverage)
                .where(productCoverage.product.eq(product)
                        .and(productCoverage.coverage.seq.in(coverageSeqs))
                        .and(productCoverage.useYn.eq(CommonConstant.ALIVE)))
                .fetch();
        return productCoverages;
    }


}
