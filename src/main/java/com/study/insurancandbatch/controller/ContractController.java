package com.study.insurancandbatch.controller;

import com.study.insurancandbatch.dto.Result;
import com.study.insurancandbatch.dto.request.ContractRequest;
import com.study.insurancandbatch.dto.request.UpdateContractRequest;
import com.study.insurancandbatch.service.ContractService;
import com.study.insurancandbatch.serviceFactory.ContractServiceFactory;
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
