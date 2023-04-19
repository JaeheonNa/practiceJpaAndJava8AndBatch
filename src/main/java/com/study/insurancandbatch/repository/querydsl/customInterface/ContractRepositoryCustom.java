package com.study.insurancandbatch.repository.querydsl.customInterface;

import com.study.insurancandbatch.entity.Contract;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepositoryCustom {
    List<Contract> findAllExpiredContract();
    List<Contract> findAllExpiringContract();


}
