package com.study.insuranceandbatch.common;

public class CommonConstant {
    // 결과 정상 코드
    public static final int RESULT_SUCCESS = 0;

    // 결과 실패 코드 (default)
    public static final int RESULT_FAIL = 400;

    // 결과 정상 코드
    public static final String RESULT_SUCCESS_MESSAGE = "Success";

    // 결과 실패 코드 (default)
    public static final String RESULT_FAIL_MESSAGE = "fail";

    // 보험 계약 상태 코드
    public static int EXPIRED_CONTRACT = 0;
    public static int NORMAL_CONTRACT = 1;
    public static int CANCELED_CONTRACT = 2;

    public static int DEAD = 0;
    public static int ALIVE = 1;


    public static String convertInsuranceCode(int insuranceCode){
        String state = "";
        switch(insuranceCode){
            case 0: state ="만료";
                    break;
            case 1: state ="정상";
                    break;
            case 2: state ="철회";
        }
        return state;
    }

    public static String productCoverageState(int productCoverageState){
        String state = "";
        switch(productCoverageState){
            case 1: state ="정상";
                break;
            case 2: state ="철회";
        }
        return state;
    }

}
