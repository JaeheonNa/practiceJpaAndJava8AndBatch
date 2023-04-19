package com.study.insuranceandbatch.repository;

import com.study.insuranceandbatch.entity.Coverage;
import com.study.insuranceandbatch.repository.querydsl.customInterface.CoverageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverageRepository extends JpaRepository<Coverage, Long>, CoverageRepositoryCustom {
}
