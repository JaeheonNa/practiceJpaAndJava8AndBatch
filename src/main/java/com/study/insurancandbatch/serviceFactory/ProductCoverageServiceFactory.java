package com.study.insurancandbatch.serviceFactory;

import com.study.insurancandbatch.service.ProductCoverageService;
import com.study.insurancandbatch.serviceImpl.ProductCoverageServiceImpl;
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
