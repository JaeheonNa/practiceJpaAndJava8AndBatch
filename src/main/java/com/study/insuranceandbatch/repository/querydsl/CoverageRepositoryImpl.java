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

import static com.study.insuranceandbatch.entity.QCoverage.coverage1;
import static com.study.insuranceandbatch.entity.QProductCoverage.productCoverage;


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
