package com.study.insuranceandbatch.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.insuranceandbatch.advice.exception.NoSuchContractException;
import com.study.insuranceandbatch.common.CommonConstant;
import com.study.insuranceandbatch.dto.ContractDto;
import com.study.insuranceandbatch.dto.CoverageDto;
import com.study.insuranceandbatch.dto.ProductDto;
import com.study.insuranceandbatch.dto.projection.ContractDetailProjection;
import com.study.insuranceandbatch.dto.response.ContractDetailResponse;
import com.study.insuranceandbatch.entity.ContractProductCoverage;
import com.study.insuranceandbatch.repository.querydsl.customInterface.ContractProductCoverageRepositoryCustom;
import com.study.insuranceandbatch.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.study.insuranceandbatch.entity.QContract.contract;
import static com.study.insuranceandbatch.entity.QProductCoverage.productCoverage;
import static com.study.insuranceandbatch.entity.QContractProductCoverage.contractProductCoverage;
import static com.study.insuranceandbatch.entity.QCoverage.coverage1;
import static com.study.insuranceandbatch.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ContractProductCoverageRepositoryImpl implements ContractProductCoverageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ContractDetailResponse findByContractSeq(Long contractSeq) {
        List<ContractDetailProjection> contractDetailProjections = queryFactory.select(Projections.constructor(ContractDetailProjection.class,
                        contract,
                        product,
                        coverage1,
                        contractProductCoverage
                ))
                .from(contractProductCoverage)
                .join(contract).on(contractProductCoverage.contract.seq.eq(contract.seq))
                .join(productCoverage).on(contractProductCoverage.productCoverage.seq.eq(productCoverage.seq))
                .join(product).on(productCoverage.product.seq.eq(product.seq))
                .join(coverage1).on(productCoverage.coverage.seq.eq(coverage1.seq))
                .where(contractProductCoverage.contract.seq.eq(contractSeq))
                .fetch();

        if(contractDetailProjections.size() == 0) throw new NoSuchContractException();

        List<CoverageDto> coverageDtos = contractDetailProjections.stream()
                .map(cd -> CoverageDto.builder()
                        .seq(cd.getCoverage().getSeq())
                        .name(cd.getCoverage().getName())
                        .coverage(cd.getCoverage().getCoverage())
                        .base(cd.getCoverage().getBase())
                        .state(cd.getContractProductCoverage().getState())
                        .stateStr(CommonConstant.convertInsuranceCode(cd.getContractProductCoverage().getState()))
                        .createDtime(cd.getContractProductCoverage().getCreateDtime())
                        .updateDtime(cd.getContractProductCoverage().getUpdateDtime())
                        .build())
                .collect(Collectors.toList());

        ProductDto productDto = ProductDto.builder()
                .seq(contractDetailProjections.get(0).getProduct().getSeq())
                .name(contractDetailProjections.get(0).getProduct().getName())
                .coverages(coverageDtos)
                .build();

        ContractDto contractDto = ContractDto.builder()
                .seq(contractDetailProjections.get(0).getContract().getSeq())
                .period(contractDetailProjections.get(0).getContract().getPeriod())
                .totalCost(contractDetailProjections.get(0).getContract().getTotalCost())
                .state(contractDetailProjections.get(0).getContract().getState())
                .stateStr(CommonConstant.convertInsuranceCode(contractDetailProjections.get(0).getContract().getState()))
                .startDate(contractDetailProjections.get(0).getContract().getStartDate())
                .endDate(contractDetailProjections.get(0).getContract().getEndDate())
                .createDtime(contractDetailProjections.get(0).getContract().getCreateDtime())
                .updateDtime(contractDetailProjections.get(0).getContract().getUpdateDtime())
                .build();

        ContractDetailResponse contractDetail = ContractDetailResponse.builder()
                .product(productDto)
                .contract(contractDto)
                .build();

        return contractDetail;
    }

    @Override
    public List<ContractProductCoverage> findContractProductCoverageByContractSeq(Long contractSeq, List<Long> coverageSeqs) {
        List<ContractProductCoverage> contractProductCoverages = queryFactory.select(contractProductCoverage)
                .from(contractProductCoverage)
                .where(contractProductCoverage.contract.seq.eq(contractSeq)
                        .and(contractProductCoverage.productCoverage.coverage.seq.in(coverageSeqs)))
                .fetchJoin().fetch();
        return contractProductCoverages;
    }
}
