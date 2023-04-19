package com.study.insurancandbatch.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.insurancandbatch.entity.Coverage;
import com.study.insurancandbatch.entity.Product;
import com.study.insurancandbatch.repository.querydsl.customInterface.CoverageRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.study.insurancandbatch.entity.QCoverage.coverage1;
import static com.study.insurancandbatch.entity.QProductCoverage.productCoverage;


@Repository
@RequiredArgsConstructor
public class CoverageRepositoryImpl implements CoverageRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Coverage> findAllCoveragesByProduct(Product product) {
        List<Coverage> coverages = queryFactory.select(
                coverage1
                ).from(productCoverage)
                .innerJoin(coverage1).on(productCoverage.coverage.eq(coverage1))
                .where(productCoverage.product.eq(product))
                .fetch();
        return coverages;
    }
}
