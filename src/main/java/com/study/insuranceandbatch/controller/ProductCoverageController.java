package com.study.insuranceandbatch.controller;

import com.study.insuranceandbatch.advice.exception.AlreadySoldInsuranceException;
import com.study.insuranceandbatch.advice.exception.NoSuchCoverageException;
import com.study.insuranceandbatch.dto.Result;
import com.study.insuranceandbatch.dto.request.CoverageRequest;
import com.study.insuranceandbatch.dto.request.ProductCoverageMapRequest;
import com.study.insuranceandbatch.dto.request.ProductRequest;
import com.study.insuranceandbatch.entity.Coverage;
import com.study.insuranceandbatch.service.ProductCoverageService;
import com.study.insuranceandbatch.serviceFactory.ProductCoverageServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("productCoverage")
public class ProductCoverageController {

    private final ProductCoverageServiceFactory productCoverageServiceFactory;

    @PostMapping("init")
    public Result init(){
        ProductCoverageService productCoverageService = productCoverageServiceFactory.getProductCoverageService();
        return productCoverageService.init();
    }

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

    @DeleteMapping("map")
    public Result deleteProductCoverageMap(@RequestBody @Valid ProductCoverageMapRequest request){
        ProductCoverageService productCoverageService = productCoverageServiceFactory.getProductCoverageService();
        Result result;
        try {
            result = productCoverageService.deleteProductCoverageMap(request);
        }catch (JpaSystemException e){
            throw new AlreadySoldInsuranceException();
        }
        return result;
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
