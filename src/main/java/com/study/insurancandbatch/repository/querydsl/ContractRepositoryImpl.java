package com.study.insurancandbatch.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.insurancandbatch.common.CommonConstant;
import com.study.insurancandbatch.entity.Contract;
import com.study.insurancandbatch.repository.querydsl.customInterface.ContractRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.study.insurancandbatch.entity.QContract.contract;


@Repository
@RequiredArgsConstructor
public class ContractRepositoryImpl implements ContractRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Contract> findAllExpiredContract() {
        List<Contract> contracts = queryFactory.select(contract)
                .from(contract)
                .where(contract.endDtime.before(LocalDateTime.now())
                        .and(contract.state.eq(CommonConstant.NORMAL_CONTRACT)))
                .fetch();
        return contracts;
    }

    @Override
    public List<Contract> findAllExpiringContract() {
        LocalDateTime atStartOfDay = LocalDate.now().atStartOfDay().plusDays(7);
        LocalDateTime atEndOfDay = LocalDate.now().atStartOfDay().plusDays(8);

        List<Contract> contracts = queryFactory.select(contract)
                .from(contract)
                .where(contract.endDtime.between(atStartOfDay, atEndOfDay)
                        .and(contract.state.eq(CommonConstant.NORMAL_CONTRACT)))
                .fetch();
        return contracts;
    }
}
