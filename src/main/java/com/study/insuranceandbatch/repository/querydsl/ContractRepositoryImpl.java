package com.study.insuranceandbatch.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.insuranceandbatch.common.CommonConstant;
import com.study.insuranceandbatch.entity.Contract;
import com.study.insuranceandbatch.repository.querydsl.customInterface.ContractRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.study.insuranceandbatch.entity.QContract.contract;


@Repository
@RequiredArgsConstructor
public class ContractRepositoryImpl implements ContractRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Contract> findAllExpiredContract() {
        List<Contract> contracts = queryFactory.select(contract)
                .from(contract)
                .where(contract.endDate.before(LocalDate.now())
                        .and(contract.state.eq(CommonConstant.NORMAL_CONTRACT)))
                .fetch();
        return contracts;
    }

    @Override
    public List<Contract> findAllExpiringContract() {
        List<Contract> contracts = queryFactory.select(contract)
                .from(contract)
                .where(contract.endDate.eq(LocalDate.now().plusWeeks(1))
                        .and(contract.state.eq(CommonConstant.NORMAL_CONTRACT)))
                .fetchJoin().fetch();
        return contracts;
    }
}
