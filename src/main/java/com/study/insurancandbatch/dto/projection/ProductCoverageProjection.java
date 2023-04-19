package com.study.insurancandbatch.dto.projection;

import com.study.insurancandbatch.entity.Coverage;
import com.study.insurancandbatch.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductCoverageProjection {
    private Product product;
    private Coverage coverage;
}
