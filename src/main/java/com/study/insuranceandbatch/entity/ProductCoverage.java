package com.study.insuranceandbatch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.insuranceandbatch.common.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="product_coverage_map", uniqueConstraints={
        @UniqueConstraint(
                name="product_coverage_map_constraints",
                columnNames={"product_seq", "coverage_seq"}
        )
    }
)
public class ProductCoverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_coverage_seq")
    private Long seq;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_seq")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="coverage_seq")
    private Coverage coverage;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDtime;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDtime;

    private int useYn;

    public ProductCoverage(Product product, Coverage coverage) {
        this.product = product;
        this.coverage = coverage;
        this.useYn = CommonConstant.ALIVE;
    }
}
