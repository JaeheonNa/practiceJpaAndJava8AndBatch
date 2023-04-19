package com.study.insuranceandbatch.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalculatedCost {
    Double totalCost;
}
