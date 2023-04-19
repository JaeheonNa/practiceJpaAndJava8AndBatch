package com.study.insuranceandbatch.controller;

import com.study.insuranceandbatch.dto.Result;
import com.study.insuranceandbatch.dto.request.ContractRequest;
import com.study.insuranceandbatch.dto.request.UpdateContractRequest;
import com.study.insuranceandbatch.service.ContractService;
import com.study.insuranceandbatch.serviceFactory.ContractServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("contract")
public class ContractController {

    private final ContractServiceFactory contractServiceFactory;

    @GetMapping("calculatedCost/{period}")
    public Result getCalculatedCost(@PathVariable("period") int period,
                                    @RequestParam("coverageSeqs") List<Long> coverageSeqs){
        ContractService contractService = contractServiceFactory.getContractService();
        return contractService.getCalculatedCost(period, coverageSeqs);
    }

    @PostMapping()
    public Result insertContract(@RequestBody @Valid ContractRequest request){
        ContractService contractService = contractServiceFactory.getContractService();
        return contractService.insertContract(request);
    }

    @GetMapping("{contractSeq}")
    public Result getContract(@PathVariable("contractSeq") Long contractSeq){
        ContractService contractService = contractServiceFactory.getContractService();
        return contractService.getContract(contractSeq);
    }

    @PutMapping()
    public Result updateContract(@RequestBody @Valid UpdateContractRequest request){
        ContractService contractService = contractServiceFactory.getContractService();
        return contractService.updateContract(request);
    }


}
