package com.study.insuranceandbatch.repository.querydsl.customInterface;

import com.study.insuranceandbatch.dto.response.ContractDetailResponse;
import com.study.insuranceandbatch.entity.Contract;
import com.study.insuranceandbatch.entity.ContractProductCoverage;
import com.study.insuranceandbatch.entity.ProductCoverage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractProductCoverageRepositoryCustom {
    ContractDetailResponse findByContractSeq(Long contractSeq);
    List<ContractProductCoverage> findContractProductCoverageByContractSeq(Long contractSeq, List<Long> coverageSeqs);
    void updateContractProductCoverage(Contract contract, List<ProductCoverage> requestProductCoverages);

}
