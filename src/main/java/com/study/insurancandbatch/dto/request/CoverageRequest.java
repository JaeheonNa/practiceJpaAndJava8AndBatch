package com.study.insurancandbatch.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CoverageRequest {
    @NotNull
    private String name;
    @NotNull
    private double coverage;
    @NotNull
    private double base;
}
