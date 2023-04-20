package com.study.insuranceandbatch.advice;

import com.study.insuranceandbatch.dto.Result;
import com.study.insuranceandbatch.advice.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    protected Result defaultException(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return new Result().fail("알 수 없는 에러가 발생했습니다.", 400);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Result constraintViolationException(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return new Result().fail("DB 제약 조건에 위배된 명령입니다.", 400);
    }

    @ExceptionHandler(NoSuchProductException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Result noSuchProductException(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return new Result().fail("해당 보험이 존재하지 않습니다.", 400);
    }

    @ExceptionHandler(NoSuchCoverageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Result noSuchCoverageException(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return new Result().fail("해당 담보가 존재하지 않습니다.", 400);
    }

    @ExceptionHandler(NoSuchContractException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Result noSuchContractException(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return new Result().fail("해당 보험 계약이 존재하지 않습니다.", 400);
    }

    @ExceptionHandler(NoSuchProductOrCoverageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Result noSuchProductOrCoverageException(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return new Result().fail("가입하려는 보험 상품과 담보가 일치하지 않습니다.", 400);
    }

    @ExceptionHandler(ExpiredContractException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Result expiredContractException(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return new Result().fail("만료된 계약은 변경이 불가능합니다.", 400);
    }

    @ExceptionHandler(NotPossibleChangePeriodException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Result notPossibleChangePeriodException(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return new Result().fail("변경하려는 기간의 만료일이 현재보다 과거입니다.", 400);
    }

    @ExceptionHandler(ImpossibleCancelCoverageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Result impossibleCancelCoverageException(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return new Result().fail("담보를 모두 철회할 수 없습니다.", 400);
    }

    @ExceptionHandler(AlreadyMappedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Result alreadyMappedException(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return new Result().fail("이미 매핑된 정보는 추가 매핑할 수 없습니다.", 400);
    }

    @ExceptionHandler(AlreadySoldInsuranceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Result alreadySoldInsuranceException(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return new Result().fail("이미 판매된 상품은 제거할 수 없습니다.", 400);
    }


}
