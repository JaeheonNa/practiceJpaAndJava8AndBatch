package com.study.insuranceandbatch.repository;

import com.study.insuranceandbatch.entity.Contract;
import com.study.insuranceandbatch.entity.ContractProductCoverage;
import com.study.insuranceandbatch.repository.querydsl.customInterface.ContractProductCoverageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractProductCoverageRepository extends JpaRepository<ContractProductCoverage, Long>, ContractProductCoverageRepositoryCustom {
    List<ContractProductCoverage> findAllByContract(Contract contract); // Test용

}
