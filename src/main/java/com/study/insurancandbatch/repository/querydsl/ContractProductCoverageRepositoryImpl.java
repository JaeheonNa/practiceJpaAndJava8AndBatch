package com.study.insurancandbatch.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.insurancandbatch.advice.exception.NoSuchContractException;
import com.study.insurancandbatch.common.CommonConstant;
import com.study.insurancandbatch.dto.ContractDto;
import com.study.insurancandbatch.dto.CoverageDto;
import com.study.insurancandbatch.dto.ProductDto;
import com.study.insurancandbatch.dto.projection.ContractDetailProjection;
import com.study.insurancandbatch.dto.response.ContractDetailResponse;
import com.study.insurancandbatch.entity.ContractProductCoverage;
import com.study.insurancandbatch.repository.querydsl.customInterface.ContractProductCoverageRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.study.insurancandbatch.entity.QContract.contract;
import static com.study.insurancandbatch.entity.QContractProductCoverage.contractProductCoverage;
import static com.study.insurancandbatch.entity.QCoverage.coverage1;
import static com.study.insurancandbatch.entity.QProduct.product;
import static com.study.insurancandbatch.entity.QProductCoverage.productCoverage;

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
        List<ContractProductCoverage> contractProductCoverages = queryFactory.select(contractProductCoverage)
                .from(contractProductCoverage)
                .where(contractProductCoverage.contract.seq.eq(contractSeq)
                        .and(contractProductCoverage.productCoverage.coverage.seq.in(coverageSeqs)))
                .fetch();
        return contractProductCoverages;
    }
}
