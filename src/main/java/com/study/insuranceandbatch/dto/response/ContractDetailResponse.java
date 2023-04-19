package com.study.insuranceandbatch.dto.response;

import com.study.insuranceandbatch.dto.ContractDto;
import com.study.insuranceandbatch.dto.ProductDto;
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
