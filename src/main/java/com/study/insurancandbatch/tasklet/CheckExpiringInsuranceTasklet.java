package com.study.insurancandbatch.tasklet;

import com.study.insurancandbatch.entity.Contract;
import com.study.insurancandbatch.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class CheckExpiringInsuranceTasklet implements Tasklet {
	private final ContractRepository contractRepository;

	@Override
	@Transactional
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
		log.info("############################## 일주일 후 만료될 계약 존재 확인 Start ##############################");

		List<Contract> expiringContracts = contractRepository.findAllExpiringContract();

		log.info("총 [" +expiringContracts.size()+ "] 건의 계약이 일주일 후 만료됩니다.");
		expiringContracts.stream().forEach(xc->{
			log.info("일련 번호 ["+ xc.getSeq()+"] 번의 보험 계약이 일주일 후 만료됩니다.");
		});
		log.info("############################### 일주일 후 만료될 계약 존재 확인 End ###############################");


		log.info(contractRepository.count()+"건의 계약이 확인됐습니다.");
		return RepeatStatus.FINISHED;
	}
}