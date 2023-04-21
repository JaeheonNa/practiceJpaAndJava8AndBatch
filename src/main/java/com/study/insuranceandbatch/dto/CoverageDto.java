package com.study.insuranceandbatch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.insuranceandbatch.common.CommonConstant;
import com.study.insuranceandbatch.entity.Coverage;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CoverageDto {
    private Long seq;
    private String name;
    private double coverage;
    private double base;
    private int state;
    private String stateStr;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDtime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDtime;

    public static CoverageDto convert(Coverage coverage, int state){
        CoverageDto coverageDto = CoverageDto.builder()
                .seq(coverage.getSeq())
                .name(coverage.getName())
                .coverage(coverage.getCoverage())
                .base(coverage.getBase())
                .state(state)
                .stateStr(CommonConstant.productCoverageState(state))
                .createDtime(coverage.getCreateDtime())
                .updateDtime(coverage.getUpdateDtime())
                .build();
        return coverageDto;
    }
}
