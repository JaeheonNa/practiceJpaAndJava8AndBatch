package com.study.insuranceandbatch.repository;

import com.study.insuranceandbatch.entity.Contract;
import com.study.insuranceandbatch.repository.querydsl.customInterface.ContractRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long>, ContractRepositoryCustom {
}
