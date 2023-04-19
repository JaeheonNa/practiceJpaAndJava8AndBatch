package com.study.insurancandbatch.repository;

import com.study.insurancandbatch.entity.Contract;
import com.study.insurancandbatch.entity.ContractProductCoverage;
import com.study.insurancandbatch.repository.querydsl.customInterface.ContractProductCoverageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractProductCoverageRepository extends JpaRepository<ContractProductCoverage, Long>, ContractProductCoverageRepositoryCustom {
    List<ContractProductCoverage> findAllByContract(Contract contract); // Test용

}
