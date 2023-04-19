package com.study.insurancandbatch.serviceImpl;

import com.study.insurancandbatch.advice.exception.NoSuchCoverageException;
import com.study.insurancandbatch.advice.exception.NoSuchProductException;
import com.study.insurancandbatch.dto.Result;
import com.study.insurancandbatch.dto.projection.ProductCoverageProjection;
import com.study.insurancandbatch.dto.request.CoverageRequest;
import com.study.insurancandbatch.dto.request.ProductCoverageMapRequest;
import com.study.insurancandbatch.dto.request.ProductRequest;
import com.study.insurancandbatch.dto.response.ProductCoveragesResponse;
import com.study.insurancandbatch.entity.Coverage;
import com.study.insurancandbatch.entity.Product;
import com.study.insurancandbatch.entity.ProductCoverage;
import com.study.insurancandbatch.repository.CoverageRepository;
import com.study.insurancandbatch.repository.ProductCoverageRepository;
import com.study.insurancandbatch.repository.ProductRepository;
import com.study.insurancandbatch.service.ProductCoverageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCoverageServiceImpl implements ProductCoverageService {
    private final ProductRepository productRepository;
    private final CoverageRepository coverageRepository;
    private final ProductCoverageRepository productCoverageRepository;
    @Override
    @Transactional
    public Result insertProduct(ProductRequest request) {
        Product product = new Product(request.getName(), request.getMinPeriod(), request.getMaxPeriod());
        productRepository.save(product);
        return new Result().success("상품이 정상 등록되었습니다.");
    }

    @Override
    @Transactional
    public Result insertCoverage(CoverageRequest request) {
        Coverage coverage = new Coverage(request.getName(), request.getCoverage(), request.getBase());
        coverageRepository.save(coverage);
        return new Result().success("담보가 정상 등록되었습니다.");
    }

    @Override
    @Transactional
    public Result insertProductCoverageMap(ProductCoverageMapRequest request) {
        Product product = productRepository.findById(request.getProductSeq()).orElseThrow(() -> new NoSuchProductException());
        List<Coverage> coverages = coverageRepository.findAllById(request.getCoverageSeqs());
        if(coverages.size() == 0 || request.getCoverageSeqs().size() != coverages.size())
            throw new NoSuchCoverageException();
        coverages.stream().forEach(c-> {
            ProductCoverage productCoverage = new ProductCoverage(product, c);
            productCoverageRepository.save(productCoverage);
        });
        return new Result().success("보험 상품과 담보가 정상 매핑되었습니다.");
    }

    @Override
    @Transactional
    public Result getAllProducts() {
        List<Product> products = productRepository.findAll();
        return new Result().success(products);
    }

    @Override
    @Transactional
    public Result getAllCoverage() {
        List<Coverage> coverages = coverageRepository.findAll();
        return new Result().success(coverages);
    }

    @Override
    @Transactional
    public Result getAllProductCoverages() {
        List<ProductCoverageProjection> allProductCoverages = productCoverageRepository.getAllProductCoverages();

        List<Product> products = allProductCoverages.stream().map(pc -> pc.getProduct()).distinct().collect(Collectors.toList());

        List<ProductCoveragesResponse> responseResult = products.stream().map(p -> {
            List<Coverage> coverageForProduct = allProductCoverages.stream().filter(pc -> pc.getProduct().equals(p)).map(pc -> pc.getCoverage()).collect(Collectors.toList());
            return ProductCoveragesResponse.builder()
                    .product(p)
                    .coverages(coverageForProduct).build();
        }).collect(Collectors.toList());

        return new Result().success(responseResult);
    }
}
