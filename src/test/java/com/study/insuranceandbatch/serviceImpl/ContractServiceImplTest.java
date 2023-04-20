package com.study.insuranceandbatch.serviceImpl;

import com.study.insuranceandbatch.advice.exception.ExpiredContractException;
import com.study.insuranceandbatch.advice.exception.NoSuchContractException;
import com.study.insuranceandbatch.advice.exception.NoSuchProductOrCoverageException;
import com.study.insuranceandbatch.advice.exception.NotPossibleChangePeriodException;
import com.study.insuranceandbatch.common.CommonConstant;
import com.study.insuranceandbatch.dto.response.ContractDetailResponse;
import com.study.insuranceandbatch.entity.*;
import com.study.insuranceandbatch.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
class ContractServiceImplTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CoverageRepository coverageRepository;
    @Autowired
    private ProductCoverageRepository productCoverageRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private ContractProductCoverageRepository contractProductCoverageRepository;

    @PostConstruct
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
        ProductCoverage productCoverage_3 = new ProductCoverage(product_2, coverage_3);
        ProductCoverage productCoverage_4 = new ProductCoverage(product_2, coverage_4);
        productCoverageRepository.save(productCoverage_1);
        productCoverageRepository.save(productCoverage_2);
        productCoverageRepository.save(productCoverage_3);
        productCoverageRepository.save(productCoverage_4);
        productCoverageRepository.flush();
    }
    @Test
    @Transactional
    void getCalculatedCost() {
        List<Long> coverageSeqs = new ArrayList<>();
        coverageSeqs.add(4L);
        coverageSeqs.add(3L);
        int period = 8;

        List<Coverage> coverages = coverageRepository.findAllById(coverageSeqs);
        List<Double> costs = coverages.stream().map(c -> c.getCoverage() / c.getBase()).collect(Collectors.toList());
        Double totalCost = 0D;
        for (Double cost : costs) totalCost += cost * period;
        totalCost = Math.floor(totalCost * 100) / 100.0;

        Assertions.assertThat(totalCost).isEqualTo(471894.73);
//        648855.26 11
//        117973.68 2
//        471894.73 8
    }

    @Test
    @Transactional
    public void insertContractTest(){
        int period = 8;
        Long productSeq = 2L;
        List<Long> coverageSeqs = new ArrayList<>();
        coverageSeqs.add(3L);
        coverageSeqs.add(4L);

        LocalDate startDate = LocalDate.now();
        // 계약 생성
        double cost = calculatedCost(period, coverageSeqs);
        Contract contract = new Contract(period, cost, CommonConstant.NORMAL_CONTRACT, startDate);

        // 보험-담보 조회
        List<ProductCoverage> productCoverages = productCoverageRepository.findByProductSeqAndCoverageSeqs(productSeq, coverageSeqs);
        Assertions.assertThat(productCoverages.size()).isEqualTo(coverageSeqs.size());
        if(productCoverages.size() != coverageSeqs.size()) throw new NoSuchProductOrCoverageException();

        // 계약 생성
        contractRepository.save(contract);
        productCoverages.stream().forEach(pc ->{
            ContractProductCoverage contractProductCoverage = new ContractProductCoverage(contract, pc, CommonConstant.NORMAL_CONTRACT);
            contractProductCoverageRepository.save(contractProductCoverage);
        });
        contractRepository.flush();
        contractProductCoverageRepository.flush();

        // test
        List<Contract> contracts = contractRepository.findAll();
        List<ContractProductCoverage> contractProductCoverages = contractProductCoverageRepository.findAll();
        Assertions.assertThat(contracts.size()).isEqualTo(1);
        Assertions.assertThat(contractProductCoverages.size()).isEqualTo(2);
        Assertions.assertThat(contracts.get(0).getSeq()).isEqualTo(1);
        Assertions.assertThat(contracts.get(0).getState()).isEqualTo(1);
        Assertions.assertThat(contractProductCoverages.get(0).getProductCoverage().getProduct().getName()).isEqualTo("휴대폰 보험");
        Assertions.assertThat(contracts.get(0).getTotalCost()).isEqualTo(471894.73);

    }

    @Test
    @Transactional
    public void getContract() {
        int period = 8;
        Long productSeq = 2L;
        List<Long> coverageSeqs = new ArrayList<>();
        coverageSeqs.add(3L);
        coverageSeqs.add(4L);
        LocalDate startDate = LocalDate.now();


        // 계약 생성
        double cost = calculatedCost(period, coverageSeqs);
        Contract contract = new Contract(period, cost, CommonConstant.NORMAL_CONTRACT, startDate);

        // 보험-담보 조회
        List<ProductCoverage> productCoverages = productCoverageRepository.findByProductSeqAndCoverageSeqs(productSeq, coverageSeqs);
        Assertions.assertThat(productCoverages.size()).isEqualTo(coverageSeqs.size());
        if(productCoverages.size() != coverageSeqs.size()) throw new NoSuchProductOrCoverageException();

        // 계약 생성
        contractRepository.save(contract);
        productCoverages.stream().forEach(pc ->{
            ContractProductCoverage contractProductCoverage = new ContractProductCoverage(contract, pc, CommonConstant.NORMAL_CONTRACT);
            contractProductCoverageRepository.save(contractProductCoverage);
        });
        contractRepository.flush();
        contractProductCoverageRepository.flush();


        ContractDetailResponse contractDetail = contractProductCoverageRepository.findByContractSeq(1L);


        Assertions.assertThat(contractDetail.getContract().getTotalCost()).isEqualTo(471894.73);
        Assertions.assertThat(contractDetail.getProduct().getName()).isEqualTo("휴대폰 보험");
        Assertions.assertThat(contractDetail.getProduct().getCoverages().get(0).getName()).isEqualTo("부분 손실");
        Assertions.assertThat(contractDetail.getProduct().getCoverages().get(1).getName()).isEqualTo("전체 손실");


    }


    @Test
    @Transactional
    public void updateContract() {

        int period1 = 8;
        Long productSeq = 2L;
        List<Long> coverageSeqs = new ArrayList<>();
        coverageSeqs.add(3L);
        LocalDate startDate = LocalDate.now();


        // 계약 생성
        double cost = calculatedCost(period1, coverageSeqs);
        Contract contract1 = new Contract(period1, cost, CommonConstant.NORMAL_CONTRACT, startDate);

        // 보험-담보 조회
        List<ProductCoverage> productCoverages1 = productCoverageRepository.findByProductSeqAndCoverageSeqs(productSeq, coverageSeqs);
        Assertions.assertThat(productCoverages1.size()).isEqualTo(coverageSeqs.size());
        if(productCoverages1.size() != coverageSeqs.size()) throw new NoSuchProductOrCoverageException();

        // 계약 생성
        contractRepository.save(contract1);
        productCoverages1.stream().forEach(pc ->{
            ContractProductCoverage contractProductCoverage = new ContractProductCoverage(contract1, pc, CommonConstant.NORMAL_CONTRACT);
            contractProductCoverageRepository.save(contractProductCoverage);
        });
        contractRepository.flush();
        contractProductCoverageRepository.flush();

        Assertions.assertThat(contractProductCoverageRepository.findAllByContract(contractRepository.findById(1L).get()).size()).isEqualTo(1);

        ///////////////


        Long contractSeq = 1L;
        int contractState = CommonConstant.NORMAL_CONTRACT;
        int period = 1;
        List<Long> addCoverageSeqs = new ArrayList<>();
        addCoverageSeqs.add(4L);
        List<Long> cancelCoverageSeqs = new ArrayList<>();
        cancelCoverageSeqs.add(3L);

        // 계약 상태 변경
        Contract contract = contractRepository.findById(contractSeq).orElseThrow(NoSuchContractException::new);
        if(contract.getState() == CommonConstant.EXPIRED_CONTRACT) throw new ExpiredContractException();
        contract.setState(contractState);

        // 계약 기간 변경
        if(contract.getStartDate().plusMonths(period).isBefore(LocalDate.now())) throw new NotPossibleChangePeriodException();
        contract.setPeriod(period);
        contract.setEndDate(contract.getStartDate().plusMonths(period));

        contractRepository.save(contract);

        //담보 추가
        if(addCoverageSeqs.size() != 0){
            List<ContractProductCoverage> allByContract = contractProductCoverageRepository.findAllByContract(contract);
            Product product = allByContract.get(0).getProductCoverage().getProduct();
            List<Long> coveragesForProduct = productCoverageRepository.getAllCoveragesByProduct(product);
            addCoverageSeqs.retainAll(coveragesForProduct);
            if(addCoverageSeqs.size() == 0) throw new NoSuchProductOrCoverageException();

            Assertions.assertThat(addCoverageSeqs.get(0)).isEqualTo(4);

            List<ProductCoverage> productCoverages = productCoverageRepository.findAllProductCoveragesByCoverageSeqs(addCoverageSeqs);

            Assertions.assertThat(productCoverages.size()).isEqualTo(1);
            Assertions.assertThat(productCoverages.get(0).getCoverage().getSeq()).isEqualTo(4);

            productCoverages.stream().forEach(pc->{
                ContractProductCoverage contractProductCoverage = new ContractProductCoverage(contract, pc, CommonConstant.NORMAL_CONTRACT);
                contractProductCoverageRepository.save(contractProductCoverage);
            });


        }

        // 담보 철회
        if(cancelCoverageSeqs.size() != 0){
            List<ContractProductCoverage> contractProductCoverages = contractProductCoverageRepository.findContractProductCoverageByContractSeq(contractSeq, cancelCoverageSeqs);

            Assertions.assertThat(contractProductCoverages.size()).isEqualTo(1);
            Assertions.assertThat(contractProductCoverages.get(0).getProductCoverage().getCoverage().getSeq()).isEqualTo(3);

            contractProductCoverages.stream().forEach(cpc ->{
                cpc.setState(CommonConstant.CANCELED_CONTRACT);
                contractProductCoverageRepository.save(cpc);
            });
        }

        contractRepository.flush();
        productCoverageRepository.flush();
        contractProductCoverageRepository.flush();

        Contract findContract = contractRepository.findById(contractSeq).get();
        List<ContractProductCoverage> allByContract = contractProductCoverageRepository.findAllByContract(findContract);

        Assertions.assertThat(findContract.getState()).isEqualTo(CommonConstant.NORMAL_CONTRACT);
        Assertions.assertThat(findContract.getPeriod()).isEqualTo(1);


        allByContract.stream().forEach(cpc->{
            if(cpc.getState() == CommonConstant.NORMAL_CONTRACT) {
                Assertions.assertThat(cpc.getProductCoverage().getCoverage().getSeq()).isEqualTo(4);
            } else if(cpc.getState() == CommonConstant.CANCELED_CONTRACT){
                Assertions.assertThat(cpc.getProductCoverage().getCoverage().getSeq()).isEqualTo(3);
            }
        });

    }



    public double calculatedCost(int period, List<Long> coverageSeqs) {
        List<Coverage> coverages = coverageRepository.findAllById(coverageSeqs);
        List<Double> costs = coverages.stream().map(c -> c.getCoverage() / c.getBase()).collect(Collectors.toList());
        Double totalCost = 0D;
        for (Double cost : costs) totalCost += cost * period;
        totalCost = Math.floor(totalCost * 100) / 100.0;
        return totalCost;
    }
}