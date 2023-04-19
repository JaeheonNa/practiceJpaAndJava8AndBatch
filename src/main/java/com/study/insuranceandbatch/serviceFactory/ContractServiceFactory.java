package com.study.insuranceandbatch.serviceFactory;

import com.study.insuranceandbatch.service.ContractService;
import com.study.insuranceandbatch.serviceImpl.ContractServiceImpl;
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
