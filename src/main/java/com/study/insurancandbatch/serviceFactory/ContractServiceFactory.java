package com.study.insurancandbatch.serviceFactory;

import com.study.insurancandbatch.service.ContractService;
import com.study.insurancandbatch.serviceImpl.ContractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractServiceFactory {
    private final ContractServiceImpl contractServiceImpl;

    public ContractService getContractService(){
        return contractServiceImpl;
    }
}
