package com.study.insurancandbatch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contract_product_coverage_map")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractProductCoverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_product_coverage_seq")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_coverage_seq")
    private ProductCoverage productCoverage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_seq")
    private Contract contract;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDtime;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDtime;

    private int state;

    public ContractProductCoverage(Contract contract, ProductCoverage productCoverage, int state) {
        this.contract = contract;
        this.productCoverage = productCoverage;
        this.state = state;
    }
}
