package com.study.insuranceandbatch.serviceImpl;

import com.study.insuranceandbatch.advice.exception.AlreadyMappedException;
import com.study.insuranceandbatch.advice.exception.NoSuchCoverageException;
import com.study.insuranceandbatch.advice.exception.NoSuchProductException;
import com.study.insuranceandbatch.common.CommonConstant;
import com.study.insuranceandbatch.dto.Result;
import com.study.insuranceandbatch.dto.projection.ProductCoverageProjection;
import com.study.insuranceandbatch.dto.response.ProductCoveragesResponse;
import com.study.insuranceandbatch.entity.Coverage;
import com.study.insuranceandbatch.entity.Product;
import com.study.insuranceandbatch.entity.ProductCoverage;
import com.study.insuranceandbatch.repository.CoverageRepository;
import com.study.insuranceandbatch.repository.ProductCoverageRepository;
import com.study.insuranceandbatch.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@SpringBootTest
class ProductCoverageServiceImplTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CoverageRepository coverageRepository;

    @Autowired
    private ProductCoverageRepository productCoverageRepository;

//    @PostConstruct
    public void init(){
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
        ProductCoverage productCoverage_3 = new ProductCoverage(product_1, coverage_3);
        ProductCoverage productCoverage_4 = new ProductCoverage(product_1, coverage_4);
        productCoverageRepository.save(productCoverage_1);
        productCoverageRepository.save(productCoverage_2);
        productCoverageRepository.save(productCoverage_3);
        productCoverageRepository.save(productCoverage_4);
        productCoverageRepository.flush();
    }

    @Test
    @Transactional
    public void insertProduct() {
        Product product_1 = new Product("여행자 보험", 1, 3);
        Product product_2 = new Product("휴대폰 보험", 1, 12);
        productRepository.save(product_1);
        productRepository.save(product_2);
        productRepository.flush();
        List<Product> products = productRepository.findAll();
        Assertions.assertThat(products.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void insertCoverage() {
        Coverage coverage_1 = new Coverage("상해 치료비", 1000000L, 100L);
        Coverage coverage_2 = new Coverage("항공기 지연 도착 시 보상금", 500000L, 100L);
        Coverage coverage_3 = new Coverage("부분 손실", 750000L, 38L);
        Coverage coverage_4 = new Coverage("전체 손실", 1570000, 40L);
        coverageRepository.save(coverage_1);
        coverageRepository.save(coverage_2);
        coverageRepository.save(coverage_3);
        coverageRepository.save(coverage_4);
        coverageRepository.flush();
        List<Coverage> coverages = coverageRepository.findAll();
        Assertions.assertThat(coverages.size()).isEqualTo(4);
    }

    @Test
    @Transactional
    public void insertProductCoverageMap() {
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
        ProductCoverage productCoverage_3 = new ProductCoverage(product_1, coverage_3);
        ProductCoverage productCoverage_4 = new ProductCoverage(product_1, coverage_4);
        productCoverageRepository.save(productCoverage_1);
        productCoverageRepository.save(productCoverage_2);
        productCoverageRepository.save(productCoverage_3);
        productCoverageRepository.save(productCoverage_4);
        productCoverageRepository.flush();

        List<ProductCoverage> productCoverages = productCoverageRepository.findAll();
        Assertions.assertThat(productCoverages.size()).isEqualTo(4);
        ProductCoverage findProductCoverage_3 = productCoverageRepository.findById(3L).get();
        Assertions.assertThat(findProductCoverage_3.getCoverage().getName()).isEqualTo("부분 손실");
    }

    @Test
    @Transactional
    public void uniqueTest() {
        Product product_1 = new Product("여행자 보험", 1, 3);
        Product product_2 = new Product("여행자 보험", 1, 12);
        productRepository.save(product_1);
        productRepository.save(product_2);
        productRepository.flush();
    }

    @Test
    @Transactional
    public void getAllInsurances1(){ // 0.3초
        List<Product> products = productRepository.findAll();
        List<ProductCoveragesResponse> productCoverages = products.stream().map(p -> {
            List<Coverage> coverages = coverageRepository.findAllCoveragesByProduct(p);
            ProductCoveragesResponse productCoveragesResponse = ProductCoveragesResponse.builder()
                    .product(p)
                    .coverages(coverages)
                    .build();
            return productCoveragesResponse;
        }).collect(Collectors.toList());
        Assertions.assertThat(productCoverages.get(0).getProduct().getName()).isEqualTo("여행자 보험");
    }

    @Test
    @Transactional
    public void getAllInsurances2(){ // 0.1초
        List<ProductCoverageProjection> allProductCoverages = productCoverageRepository.getAliveProductCoverages();
        Set<Product> productSet = allProductCoverages.stream().map(pc -> pc.getProduct()).collect(Collectors.toSet());
        List<ProductCoveragesResponse> responseResult = productSet.stream().map(p -> {
            List<Coverage> coverageForProduct = allProductCoverages.stream().filter(pc -> pc.getProduct().equals(p)).map(pc -> pc.getCoverage()).collect(Collectors.toList());
            return ProductCoveragesResponse.builder()
                    .product(p)
                    .coverages(coverageForProduct).build();
        }).collect(Collectors.toList());
        Assertions.assertThat(responseResult.get(0).getProduct().getName()).isEqualTo("여행자 보험");
    }

    @Test
    @Transactional
    public void insertProductCoverageMapNew(){

        init();

        //이미 있는 맵정보를 중복 적용하려 할 때의 테스트
        Long productSeq = 1L;
        List<Long> coverageSeqs = new ArrayList<>();
        coverageSeqs.add(2L);
        /////////////////////////////////////

        // 보험 상품 존재 확인
        Product product = productRepository.findById(productSeq).orElseThrow(() -> new NoSuchProductException());

        Assertions.assertThat(product.getSeq()).isEqualTo(1);

        // 담보 존재 확인
        List<Coverage> coverages = coverageRepository.findAllById(coverageSeqs);
        if(coverages.size() == 0 || coverageSeqs.size() != coverages.size())
            throw new NoSuchCoverageException();

        Assertions.assertThat(coverages.size()).isEqualTo(1);

        // 보험 상품-담보 매핑 존재 확인
        List<ProductCoverage> productCoverageList = productCoverageRepository.findByProductSeqAndCoverageSeqs(product.getSeq(), coverageSeqs);

        Assertions.assertThat(productCoverageList.size()).isEqualTo(1);

        // 요청한 매핑이 존재하는 매핑 중 이미 '사용' 중이라면 '이미 사용 중' Exception.
        List<ProductCoverage> aliveProductCoverages = productCoverageList.stream().filter(pc -> pc.getUseYn() == CommonConstant.ALIVE).collect(Collectors.toList());
        if(aliveProductCoverages.size() != 0) throw new AlreadyMappedException();

        Assertions.assertThat(aliveProductCoverages.size()).isEqualTo(0);

        // 존재하지만 미사용 중인 상품은 사용 여부를 '사용'으로 변경
        List<ProductCoverage> deadProductCoverages = productCoverageList.stream().filter(pc -> pc.getUseYn() == CommonConstant.DEAD).collect(Collectors.toList());
        deadProductCoverages.stream().forEach(dpc ->{
            dpc.setUseYn(CommonConstant.ALIVE);
            productCoverageRepository.save(dpc);
        });

        // 이미 매핑된 담보들 요청된 매핑 담보들을 비교, 요청된 매핑 중 매핑이 안 된 녀석만 골라내기.
        Set<Coverage> existMappedCoverages = productCoverageList.stream().map(pc -> pc.getCoverage()).collect(Collectors.toSet());
        Set<Coverage> requestedCoverages = coverages.stream().collect(Collectors.toSet());
        requestedCoverages.retainAll(existMappedCoverages);



        // 보험 상품-담보 매핑
        requestedCoverages.stream().forEach(c-> {
            ProductCoverage productCoverage = new ProductCoverage(product, c);
            productCoverageRepository.save(productCoverage);
        });

    }

}