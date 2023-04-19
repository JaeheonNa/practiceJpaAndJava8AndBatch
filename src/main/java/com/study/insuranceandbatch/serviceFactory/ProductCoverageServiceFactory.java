package com.study.insuranceandbatch.serviceFactory;

import com.study.insuranceandbatch.service.ProductCoverageService;
import com.study.insuranceandbatch.serviceImpl.ProductCoverageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCoverageServiceFactory {
    private final ProductCoverageServiceImpl productCoverageServiceImpl;

    public ProductCoverageService getProductCoverageService(){
        return productCoverageServiceImpl;
    }

}
