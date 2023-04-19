package com.study.insuranceandbatch.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.insuranceandbatch.entity.Coverage;
import com.study.insuranceandbatch.entity.Product;
import com.study.insuranceandbatch.repository.querydsl.customInterface.CoverageRepositoryCustom;
import com.study.insuranceandbatch.entity.QCoverage;
import com.study.insuranceandbatch.entity.QProductCoverage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CoverageRepositoryImpl implements CoverageRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Coverage> findAllCoveragesByProduct(Product product) {
        List<Coverage> coverages = queryFactory.select(
                QCoverage.coverage1
                ).from(QProductCoverage.productCoverage)
                .innerJoin(QCoverage.coverage1).on(QProductCoverage.productCoverage.coverage.eq(QCoverage.coverage1))
                .where(QProductCoverage.productCoverage.product.eq(product))
                .fetch();
        return coverages;
    }
}
