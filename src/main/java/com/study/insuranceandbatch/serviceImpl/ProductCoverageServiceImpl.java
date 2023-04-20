package com.study.insuranceandbatch.serviceImpl;

import com.study.insuranceandbatch.advice.exception.NoSuchCoverageException;
import com.study.insuranceandbatch.advice.exception.NoSuchProductException;
import com.study.insuranceandbatch.dto.Result;
import com.study.insuranceandbatch.dto.projection.ProductCoverageProjection;
import com.study.insuranceandbatch.dto.request.CoverageRequest;
import com.study.insuranceandbatch.dto.request.ProductCoverageMapRequest;
import com.study.insuranceandbatch.dto.request.ProductRequest;
import com.study.insuranceandbatch.dto.response.ProductCoveragesResponse;
import com.study.insuranceandbatch.entity.Coverage;
import com.study.insuranceandbatch.entity.Product;
import com.study.insuranceandbatch.entity.ProductCoverage;
import com.study.insuranceandbatch.repository.CoverageRepository;
import com.study.insuranceandbatch.repository.ProductCoverageRepository;
import com.study.insuranceandbatch.repository.ProductRepository;
import com.study.insuranceandbatch.service.ProductCoverageService;
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
        if(products.size() == 0) return new Result().success("등록된 보험이 없습니다.");
        return new Result().success(products);
    }

    @Override
    @Transactional
    public Result getAllCoverage() {
        List<Coverage> coverages = coverageRepository.findAll();
        if(coverages.size() == 0) return new Result().success("등록된 담보가 없습니다.");
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

        if(responseResult.size()==0) new Result().success("매핑된 보험-담보 상품이 없습니다.");
        return new Result().success(responseResult);
    }

    @Override
    @Transactional
    public Result init(){
        Product product_1 = new Product("여행자 보험", 1, 3);
        Product product_2 = new Product("휴대폰 보험", 1, 12);
        productRepository.save(product_1);
        productRepository.save(product_2);
        Coverage coverage_1 = new Coverage("상해 치료비", 1000000L, 100L);
        Coverage coverage_2 = new Coverage("항공기 지연 도착 시 보상금", 500000L, 100L);
        Coverage coverage_3 = new Coverage("부분 손실", 750000L, 38L);
        Coverage coverage_4 = new Coverage("전체 손실", 1570000, 40L);
        coverageRepository.save(coverage_1);
        coverageRepository.save(coverage_2);
        coverageRepository.save(coverage_3);
        coverageRepository.save(coverage_4);
        ProductCoverage productCoverage_1 = new ProductCoverage(product_1, coverage_1);
        ProductCoverage productCoverage_2 = new ProductCoverage(product_1, coverage_2);
        ProductCoverage productCoverage_3 = new ProductCoverage(product_2, coverage_3);
        ProductCoverage productCoverage_4 = new ProductCoverage(product_2, coverage_4);
        productCoverageRepository.save(productCoverage_1);
        productCoverageRepository.save(productCoverage_2);
        productCoverageRepository.save(productCoverage_3);
        productCoverageRepository.save(productCoverage_4);
        return new Result().success("기본 보험 및 기본 담보가 정상적으로 등록됐습니다.");
    }

}
