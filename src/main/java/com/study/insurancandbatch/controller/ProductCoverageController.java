package com.study.insurancandbatch.controller;

import com.study.insurancandbatch.dto.Result;
import com.study.insurancandbatch.dto.request.CoverageRequest;
import com.study.insurancandbatch.dto.request.ProductCoverageMapRequest;
import com.study.insurancandbatch.dto.request.ProductRequest;
import com.study.insurancandbatch.service.ProductCoverageService;
import com.study.insurancandbatch.serviceFactory.ProductCoverageServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("productCoverage")
public class ProductCoverageController {

    private final ProductCoverageServiceFactory productCoverageServiceFactory;

    @PostMapping("product")
    public Result insertProduct(@RequestBody @Valid ProductRequest request){
        ProductCoverageService productCoverageService = productCoverageServiceFactory.getProductCoverageService();
        return productCoverageService.insertProduct(request);
    }

    @PostMapping("coverage")
    public Result insertCoverage(@RequestBody @Valid CoverageRequest request){
        ProductCoverageService productCoverageService = productCoverageServiceFactory.getProductCoverageService();
        return productCoverageService.insertCoverage(request);
    }

    @PostMapping("map")
    public Result insertProductCoverageMap(@RequestBody @Valid ProductCoverageMapRequest request){
        ProductCoverageService productCoverageService = productCoverageServiceFactory.getProductCoverageService();
        return productCoverageService.insertProductCoverageMap(request);
    }

    @GetMapping("product/all")
    public Result getAllProducts(){
        ProductCoverageService productCoverageService = productCoverageServiceFactory.getProductCoverageService();
        return productCoverageService.getAllProducts();
    }

    @GetMapping("coverage/all")
    public Result getAllCoverage(){
        ProductCoverageService productCoverageService = productCoverageServiceFactory.getProductCoverageService();
        return productCoverageService.getAllCoverage();
    }

    @GetMapping("all")
    public Result getAllProductCoverageMap(){
        ProductCoverageService productCoverageService = productCoverageServiceFactory.getProductCoverageService();
        return productCoverageService.getAllProductCoverages();
    }


}
