package com.study.insurancandbatch.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductCoverageMapRequest {
    @NotNull
    private Long productSeq;
    @NotEmpty
    private List<Long> coverageSeqs;
}
