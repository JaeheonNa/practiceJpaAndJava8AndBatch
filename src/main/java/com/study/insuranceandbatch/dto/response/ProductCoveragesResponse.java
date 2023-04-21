package com.study.insuranceandbatch.dto.response;

import com.study.insuranceandbatch.dto.CoverageDto;
import com.study.insuranceandbatch.entity.Coverage;
import com.study.insuranceandbatch.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductCoveragesResponse {
    private Product product;
    private List<CoverageDto> coverages;
}
