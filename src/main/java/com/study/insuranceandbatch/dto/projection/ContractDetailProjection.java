package com.study.insuranceandbatch.dto.projection;

import com.study.insuranceandbatch.entity.Contract;
import com.study.insuranceandbatch.entity.ContractProductCoverage;
import com.study.insuranceandbatch.entity.Coverage;
import com.study.insuranceandbatch.entity.Product;
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
