package com.study.insuranceandbatch.repository.querydsl.customInterface;

import com.study.insuranceandbatch.dto.response.ContractDetailResponse;
import com.study.insuranceandbatch.entity.ContractProductCoverage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractProductCoverageRepositoryCustom {
    ContractDetailResponse findByContractSeq(Long contractSeq);
    List<ContractProductCoverage> findContractProductCoverageByContractSeq(Long contractSeq, List<Long> coverageSeqs);

}
