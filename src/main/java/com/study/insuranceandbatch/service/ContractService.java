package com.study.insuranceandbatch.service;

import com.study.insuranceandbatch.dto.Result;
import com.study.insuranceandbatch.dto.request.ContractRequest;
import com.study.insuranceandbatch.dto.request.UpdateContractRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContractService {
    Result getCalculatedCost(int period, List<Long> coverageSeqs);
    double calculatedCost(int period, List<Long> coverageSeqs);
    Result insertContract(ContractRequest request);

    Result getContract(Long contractSeq);

    Result updateContract(UpdateContractRequest reqeust);

}
