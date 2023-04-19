package com.study.insurancandbatch.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductRequest {
    @NotNull
    private String name;
    @NotNull
    private int minPeriod;
    @NotNull
    private int maxPeriod;
}
