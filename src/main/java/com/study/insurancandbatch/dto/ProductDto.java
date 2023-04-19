package com.study.insurancandbatch.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductDto {
    private String name;
    private List<CoverageDto> coverages;
}
