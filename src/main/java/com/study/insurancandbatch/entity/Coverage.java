package com.study.insurancandbatch.entity;

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
@Table(name = "coverage")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Coverage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coverage_seq")
    private Long seq;

    @Column(unique = true)
    private String name;

    private double coverage;
    private double base;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDtime;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDtime;

    @JsonIgnore
    @OneToMany(mappedBy = "coverage")
    private List<ProductCoverage> productCoverages = new ArrayList<>();

    public Coverage(String name, double coverage, double base) {
        this.name = name;
        this.coverage = coverage;
        this.base = base;
    }


}
