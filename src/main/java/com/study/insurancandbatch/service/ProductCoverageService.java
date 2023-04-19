package com.study.insurancandbatch.service;

import com.study.insurancandbatch.dto.Result;
import com.study.insurancandbatch.dto.request.CoverageRequest;
import com.study.insurancandbatch.dto.request.ProductCoverageMapRequest;
import com.study.insurancandbatch.dto.request.ProductRequest;
import org.springframework.stereotype.Service;

@Service
public interface ProductCoverageService {

    Result insertProduct(ProductRequest request);
    Result insertCoverage(CoverageRequest request);
    Result insertProductCoverageMap(ProductCoverageMapRequest request);
    Result getAllProducts();

    Result getAllCoverage();

    Result getAllProductCoverages();
}
