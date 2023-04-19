package com.study.insurancandbatch.repository;

import com.study.insurancandbatch.entity.Coverage;
import com.study.insurancandbatch.repository.querydsl.customInterface.CoverageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverageRepository extends JpaRepository<Coverage, Long>, CoverageRepositoryCustom {
}
