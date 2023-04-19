package com.study.insurancandbatch.dto.response;

import com.study.insurancandbatch.entity.Coverage;
import com.study.insurancandbatch.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductCoveragesResponse {
    private Product product;
    private List<Coverage> coverages;
}
