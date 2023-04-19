package com.study.insurancandbatch.dto.response;

import com.study.insurancandbatch.dto.ContractDto;
import com.study.insurancandbatch.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class ContractDetailResponse {
    private ProductDto product;
    private ContractDto contract;
}
