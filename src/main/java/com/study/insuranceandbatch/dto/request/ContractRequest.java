package com.study.insuranceandbatch.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ContractRequest {
    @NotNull
    private Long productSeq;
    @NotEmpty
    private List<Long> coverageSeqs;
    @NotNull
    private int period;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDtime;


}
