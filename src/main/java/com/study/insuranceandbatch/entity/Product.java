package com.study.insuranceandbatch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_seq")
    private Long seq;
    @Column(unique = true)
    private String name;

    private int minPeriod;
    private int maxPeriod;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDtime;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDtime;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductCoverage> productCoverages = new ArrayList<>();

    public Product(String name, int minPeriod, int maxPeriod) {
        this.name = name;
        this.minPeriod = minPeriod;
        this.maxPeriod = maxPeriod;
    }

}
