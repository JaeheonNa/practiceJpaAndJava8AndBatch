package com.study.insurancandbatch.tasklet;

import com.study.insurancandbatch.common.CommonConstant;
import com.study.insurancandbatch.entity.Contract;
import com.study.insurancandbatch.repository.ContractProductCoverageRepository;
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
public class CheckExpiredInsuranceTasklet implements Tasklet {
	private final ContractRepository contractRepository;
	private final ContractProductCoverageRepository contractProductCoverageRepository;

	@Override
	@Transactional
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
		log.info("############################## 만료된 계약 존재 확인 Start ##############################");
		List<Contract> expiredContracts = contractRepository.findAllExpiredContract();

		log.info("[" + expiredContracts.size() + "]건의 계약의 만료되었습니다.");

		expiredContracts.stream().forEach(xc->{
			xc.setState(CommonConstant.EXPIRED_CONTRACT);
			xc.getContractProductCoverageList().stream().forEach(xcpc -> {
				xcpc.setState(CommonConstant.EXPIRED_CONTRACT);
				contractProductCoverageRepository.save(xcpc);
			});
			contractRepository.save(xc);
		});

		log.info("[" + expiredContracts.size() + "]건의 계약을 만료 처리했습니다.");


		log.info("############################### 만료된 계약 존재 확인 End ###############################");
		return RepeatStatus.FINISHED;
	}
}