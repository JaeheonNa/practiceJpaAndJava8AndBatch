package com.study.insurancandbatch.dto.projection;

import com.study.insurancandbatch.entity.Contract;
import com.study.insurancandbatch.entity.ContractProductCoverage;
import com.study.insurancandbatch.entity.Coverage;
import com.study.insurancandbatch.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContractDetailProjection {
    private Contract contract;
    private Product product;
    private Coverage coverage;
    private ContractProductCoverage contractProductCoverage;
}
