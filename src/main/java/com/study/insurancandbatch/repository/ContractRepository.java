package com.study.insurancandbatch.repository;

import com.study.insurancandbatch.entity.Contract;
import com.study.insurancandbatch.repository.querydsl.customInterface.ContractRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long>, ContractRepositoryCustom {
}
