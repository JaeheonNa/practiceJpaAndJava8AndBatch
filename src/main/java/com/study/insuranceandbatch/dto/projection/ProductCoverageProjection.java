package com.study.insuranceandbatch.dto.projection;

import com.study.insuranceandbatch.entity.Coverage;
import com.study.insuranceandbatch.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductCoverageProjection {
    private Product product;
    private Coverage coverage;
    private int useYn;
}
