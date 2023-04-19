package com.study.insuranceandbatch.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateContractRequest {
    @NotNull
    private Long contractSeq;
    private int contractState;
    private int period;
    private List<Long> addCoverageSeqs;
    private List<Long> cancelCoverageSeqs;

}
