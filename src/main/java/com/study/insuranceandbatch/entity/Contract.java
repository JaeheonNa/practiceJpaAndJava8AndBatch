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
@Table(name = "contract")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_seq")
    private Long seq;

    private int period;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDtime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDtime;

    private double totalCost;

    private int state; // 0: 철회, 1:정상, 2:만료

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDtime;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDtime;

    @JsonIgnore
    @OneToMany(mappedBy = "contract")
    private List<ContractProductCoverage> contractProductCoverageList = new ArrayList<>();

    public Contract(int period, double totalCost, int state, LocalDateTime startDtime) {
        this.period = period;
        this.totalCost = totalCost;
        this.state = state;
        this.startDtime = startDtime;
        this.endDtime = startDtime.plusMonths(period);
    }
}
