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

@Repository
@RequiredArgsConstructor
public class ContractProductCoverageRepositoryImpl implements ContractProductCoverageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ContractDetailResponse findByContractSeq(Long contractSeq) {
        List<ContractDetailProjection> contractDetailProjections = queryFactory.select(Projections.constructor(ContractDetailProjection.class,
                        QContract.contract,
                        QProduct.product,
                        QCoverage.coverage1,
                        QContractProductCoverage.contractProductCoverage
                ))
                .from(QContractProductCoverage.contractProductCoverage)
                .join(QContract.contract).on(QContractProductCoverage.contractProductCoverage.contract.seq.eq(QContract.contract.seq))
                .join(QProductCoverage.productCoverage).on(QContractProductCoverage.contractProductCoverage.productCoverage.seq.eq(QProductCoverage.productCoverage.seq))
                .join(QProduct.product).on(QProductCoverage.productCoverage.product.seq.eq(QProduct.product.seq))
                .join(QCoverage.coverage1).on(QProductCoverage.productCoverage.coverage.seq.eq(QCoverage.coverage1.seq))
                .where(QContractProductCoverage.contractProductCoverage.contract.seq.eq(contractSeq))
                .fetch();

        if(contractDetailProjections.size() == 0) throw new NoSuchContractException();

        List<CoverageDto> coverageDtos = contractDetailProjections.stream()
                .map(cd -> CoverageDto.builder()
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
                .name(contractDetailProjections.get(0).getProduct().getName())
                .coverages(coverageDtos)
                .build();

        ContractDto contractDto = ContractDto.builder()
                .period(contractDetailProjections.get(0).getContract().getPeriod())
                .totalCost(contractDetailProjections.get(0).getContract().getTotalCost())
                .state(contractDetailProjections.get(0).getContract().getState())
                .stateStr(CommonConstant.convertInsuranceCode(contractDetailProjections.get(0).getContract().getState()))
                .startDtime(contractDetailProjections.get(0).getContract().getStartDtime())
                .endDtime(contractDetailProjections.get(0).getContract().getEndDtime())
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
        List<ContractProductCoverage> contractProductCoverages = queryFactory.select(QContractProductCoverage.contractProductCoverage)
                .from(QContractProductCoverage.contractProductCoverage)
                .where(QContractProductCoverage.contractProductCoverage.contract.seq.eq(contractSeq)
                        .and(QContractProductCoverage.contractProductCoverage.productCoverage.coverage.seq.in(coverageSeqs)))
                .fetch();
        return contractProductCoverages;
    }
}
