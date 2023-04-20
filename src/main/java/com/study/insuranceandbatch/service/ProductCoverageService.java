package com.study.insuranceandbatch.service;

import com.study.insuranceandbatch.dto.Result;
import com.study.insuranceandbatch.dto.request.CoverageRequest;
import com.study.insuranceandbatch.dto.request.ProductCoverageMapRequest;
import com.study.insuranceandbatch.dto.request.ProductRequest;
import org.springframework.stereotype.Service;

@Service
public interface ProductCoverageService {
    Result init();
    Result insertProduct(ProductRequest request);
    Result insertCoverage(CoverageRequest request);
    Result insertProductCoverageMap(ProductCoverageMapRequest request);
    Result getAllProducts();

    Result getAllCoverage();

    Result getAllProductCoverages();

    Result deleteProductCoverageMap(ProductCoverageMapRequest request);
}
