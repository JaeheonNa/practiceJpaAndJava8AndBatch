package com.study.insuranceandbatch.serviceImpl;

import com.study.insuranceandbatch.advice.exception.*;
import com.study.insuranceandbatch.advice.exception.*;
import com.study.insuranceandbatch.common.CommonConstant;
import com.study.insuranceandbatch.dto.Result;
import com.study.insuranceandbatch.dto.request.ContractRequest;
import com.study.insuranceandbatch.dto.request.UpdateContractRequest;
import com.study.insuranceandbatch.dto.response.CalculatedCost;
import com.study.insuranceandbatch.dto.response.ContractDetailResponse;
import com.study.insuranceandbatch.entity.*;
import com.study.insuranceandbatch.repository.*;
import com.study.insuranceandbatch.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final CoverageRepository coverageRepository;
    private final ProductRepository productRepository;
    private final ProductCoverageRepository productCoverageRepository;
    private final ContractProductCoverageRepository contractProductCoverageRepository;

    @Override
    @Transactional
    public Result getCalculatedCost(int period, List<Long> coverageSeqs) {
        CalculatedCost cost = CalculatedCost.builder().totalCost(calculatedCost(period, coverageSeqs)).build();
        return new Result().success(cost);
    }

    @Override
    @Transactional
    public Result insertContract(ContractRequest request) {

        List<ProductCoverage> requestedProductCoverages = productCoverageRepository.findAllProductCoveragesByCoverageSeqs(request.getCoverageSeqs());
        List<ProductCoverage> deadProductCoverages = requestedProductCoverages.stream().filter(rpc -> rpc.getUseYn() == CommonConstant.DEAD).collect(Collectors.toList());
        if(deadProductCoverages.size() != 0) throw new NotAvailableProductCoverageException();
        // 계약 생성
        double cost = calculatedCost(request.getPeriod(), request.getCoverageSeqs());
        Contract contract = new Contract(request.getPeriod(), cost, CommonConstant.NORMAL_CONTRACT, request.getStartDate());

        // 보험-담보 조회
        List<ProductCoverage> productCoverages = productCoverageRepository.findByProductSeqAndCoverageSeqs(request.getProductSeq(), request.getCoverageSeqs());
        if(productCoverages.size() != request.getCoverageSeqs().size()) throw new NoSuchProductOrCoverageException();

        // 계약 저장
        contractRepository.save(contract);
        productCoverages.stream().forEach(pc ->{
            ContractProductCoverage contractProductCoverage = new ContractProductCoverage(contract, pc, CommonConstant.NORMAL_CONTRACT);
            contractProductCoverageRepository.save(contractProductCoverage);
        });

        return new Result().success("계약이 정상적으로 처리되었습니다.");
    }

    @Override
    @Transactional
    public Result getContract(Long contractSeq) {
        ContractDetailResponse contractDetail = contractProductCoverageRepository.findByContractSeq(contractSeq);
        return new Result().success(contractDetail);
    }

    @Override
    @Transactional
    public Result updateContract(UpdateContractRequest request) {

        //추가하려는 담보와 제거하려는 담보가 동시에 있는지 확인. 깊은 복사 사용.
        List<Long> addCoverageSeqs = new ArrayList<>(request.getAddCoverageSeqs());
        List<Long> cancelCoverageSeqs = new ArrayList<>(request.getCancelCoverageSeqs());
        addCoverageSeqs.retainAll(cancelCoverageSeqs);
        if(addCoverageSeqs.size() != 0) throw new NotAvailableSimultaneouslyAddAndCancelExeption();

        // 계약 상태 변경
        Contract contract = contractRepository.findById(request.getContractSeq()).orElseThrow(()->new NoSuchContractException());
        if(contract.getState() == CommonConstant.EXPIRED_CONTRACT) throw new ExpiredContractException();
        contract.setState(request.getContractState());

        // 계약 기간 변경
        if(contract.getStartDate().plusMonths(request.getPeriod()).isBefore(LocalDate.now())) throw new NotPossibleChangePeriodException();
        contract.setPeriod(request.getPeriod());
        contract.setEndDate(contract.getStartDate().plusMonths(request.getPeriod()));

        //담보 추가
        if(request.getAddCoverageSeqs().size() != 0){
            /*
            요청한 contractProductCoverage가
            1. productCoverage가 활성화 상태여야 하고,
            2. 해당 contract에 매핑된 productCoverage가 없거나 '철회'상태여야 함.
             */
            Product product = contract.getContractProductCoverageList().get(0).getProductCoverage().getProduct();
            List<ProductCoverage> requestProductCoverages = productCoverageRepository.findAliveByProductAndCoverageSeqs(product, request.getAddCoverageSeqs());
            if(request.getAddCoverageSeqs().size() != requestProductCoverages.size()) throw new NotAvailableProductCoverageException();
            List<ContractProductCoverage> requestContract = contractProductCoverageRepository.findAllByContract(contract);
            List<ProductCoverage> registeredProductCoverages = requestContract.stream().map(rc -> rc.getProductCoverage()).collect(Collectors.toList());

            // 없다면 생성.
            requestProductCoverages.stream()
                    .filter(rpc ->!registeredProductCoverages.contains(rpc))
                    .forEach(rpc -> {
                        ContractProductCoverage contractProductCoverage = new ContractProductCoverage(contract, rpc, CommonConstant.ALIVE);
                        contractProductCoverageRepository.save(contractProductCoverage);
                    });

            // 철회라면 정상으로.
            contractProductCoverageRepository.updateContractProductCoverage(contract, requestProductCoverages);
            contractProductCoverageRepository.flush();
        }

        // 담보 철회
        if(request.getCancelCoverageSeqs().size() != 0){
            List<ContractProductCoverage> contractProductCoverages = contractProductCoverageRepository.findContractProductCoverageByContractSeq(request.getContractSeq(), request.getCancelCoverageSeqs());
            // 철회로 상태 변경
            contractProductCoverages.stream().forEach(cpc ->{
                cpc.setState(CommonConstant.CANCELED_CONTRACT);
                contractProductCoverageRepository.save(cpc);
            });
            //담보 상태가 '모두' 철회라면 예외 처리.
            List<ContractProductCoverage> allByContract = contractProductCoverageRepository.findAllByContract(contract);
            long aliveContractCount = allByContract.stream().filter(ac -> ac.getState() == CommonConstant.ALIVE).count();
            if(aliveContractCount == 0) throw new ImpossibleCancelCoverageException();
        }

        contractRepository.save(contract);

        return new Result().success("계약 사항이 정상적으로 변경되었습니다.");
    }

    @Override
    public double calculatedCost(int period, List<Long> coverageSeqs) {
        List<Coverage> coverages = coverageRepository.findAllById(coverageSeqs);
        List<Double> costs = coverages.stream().map(c -> c.getCoverage() / c.getBase()).collect(Collectors.toList());
        Double totalCost = 0D;
        for (Double cost : costs) totalCost += cost * period;
        totalCost = Math.floor(totalCost * 100) / 100.0;
        return totalCost;
    }
}
