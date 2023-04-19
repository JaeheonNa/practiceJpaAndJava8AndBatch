package com.study.insurancandbatch.repository.querydsl.customInterface;

import com.study.insurancandbatch.dto.response.ContractDetailResponse;
import com.study.insurancandbatch.entity.ContractProductCoverage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractProductCoverageRepositoryCustom {
    ContractDetailResponse findByContractSeq(Long contractSeq);
    List<ContractProductCoverage> findContractProductCoverageByContractSeq(Long contractSeq, List<Long> coverageSeqs);

}
