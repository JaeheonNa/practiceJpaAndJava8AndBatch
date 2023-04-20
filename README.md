# 20230417-njh
나재헌님(BE)


# Insurance
## 목차
0. [개요](#0-개요)  
1. [보험 및 담보 초기 데이터 생성 API](#1-보험-및-담보-초기-데이터-삽입-API)  
2. [보험 생성 API](#2-보험-생성-API)  
3. [담보 생성 API](#3-담보-생성-API)  
4. [보험 담보 매핑 API](#4-보험-담보-매핑-API)  
5. [보험 담보 매핑 해제 API](#5-보험-담보-해제-API)  
6. [보험 조회 API](#6-보험-조회-API)  
7. [담보 조회 API](#7-담보-조회-API)  
8. [보험 담보 매핑 조회 API](#8보험-담보-매핑-조회-API)  
9. [예상 보험료 조회 API](#9예상-보험료-조회-API)  
10. [보험 계약 API](#10보험-계약-API)  
11. [보험 계약 조회 API](#11보험-계약-조회-API)  
12. [보험 계약 수정 API](#12보험-계약-수정-API)  
13. [과제 요건에 대한 고민 및 접근 해결 방식](#13-과제-요건에-대한-고민-및-접근-해결-방식)  

## 0. 개요
    보험 및 담보로 구성된 상품을 등록하고 조회하고, 보험 계약을 체결 및 수정하는 API입니다.
    어플리케이션에는 보험 계약 만료에 따른 보험 상태 업데이트 및 보험 계약 별 만료 1주일 전 로그를 남기는 Batch 프로그램이 포함돼 있습니다.
    
    테스트는 소스코드를 다운 받아 IDE에서 실행시키거나, 아래 URL에서 jar파일 실행하시면 가능합니다.
    API 명세에 따라 실행하시면 됩니다. 
    DB는 h2 인메모리 버전으로 설정돼 있습니다. (제시된 상품은 1.[보험 및 담보 초기 데이터 생성 API]를 실행하셔야 DB에 저장됩니다.)
    Postman 사용을 권장드립니다. 
    
    
 ```
     jar 파일 다운로드 URL : https://drive.google.com/drive/folders/1XFE5r7px20syHKRGtzcFdFV19lG8C5X1?usp=sharing
     jar 파일 실행 명령    : java -jar ./insuranceAndBatch-0.0.1-SNAPSHOT.jar
 ```

## 1. 보험 및 담보 초기 데이터 생성 API   
### 1.1. 기본 정보
|URL|HTTP 메서드|   
|:--|:--------|   
|https://localhost:8080/productCoverage/init|POST   
### 1.2. 요청 데이터
|NAME|TYPE|DESCRIPTION|REQUIRED|   
|:---|:---|:----------|:-------|   

### 1.3. 응답 데이터 
|NAME|TYPE|DESCRIPTION|   
|:---|:---|:----------|    
|code|number|결과 코드|
|message|string|결과 메시지|
|data|string|요청 결과|

### 1.4. Sample
#### 1.4.1. 요청값
```
(POST) https://localhost:8080/productCoverage/init
-body-
{

}
```
#### 1.4.2. 응답값
```
{
    "code": 0,
    "message": "Success",
    "data": "기본 보험 및 기본 담보가 정상적으로 등록됐습니다."
}
```
  
## 2. 보험 생성 API  
### 2.1. 기본 정보
|URL|HTTP 메서드|   
|:--|:--------|   
|http://localhost:8080/productCoverage/product|POST|   
### 2.2. 요청 데이터
|NAME|TYPE|DESCRIPTION|REQUIRED|  
|:---|:---|:----------|:-------|   
|name|string|보험명|true|
|minPeriod|number|최소 계약 기간(단위: 월)|true|
|maxPeriod|number|최대 계약 기간(단위: 월)|true|

### 2.3. 응답 데이터
|NAME|TYPE|DESCRIPTION|   
|:---|:---|:----------|   
|code|number|결과 코드|
|message|string|결과 메시지|
|data|string|요청 결과|

### 2.4. Sample
#### 2.4.1. 요청
```
(POST) https://localhost:8080/productCoverage/init
-body-
{
    "name": "자전거 보험",
    "minPeriod": 1,
    "maxPeriod": 3
}
```
#### 2.4.2. 응답
```
{
    "code": 0,
    "message": "Success",
    "data": "상품이 정상 등록되었습니다."
}
```
 
## 3. 담보 생성 API  
### 3.1. 기본 정보
|URL|HTTP 메서드|   
|:--|:--------|   
|http://localhost:8080/productCoverage/coverage|POST|   
### 3.2. 요청 데이터
|NAME|TYPE|DESCRIPTION|REQUIRED|  
|:---|:---|:----------|:-------|   
|name|string|담보명|true|
|coverage|number|가입 금액|true|
|base|number|기준 금액|true|

### 3.3. 응답 데이터
|NAME|TYPE|DESCRIPTION|   
|:---|:---|:----------|   
|code|number|결과 코드|
|message|string|결과 메시지|
|data|string|요청 결과|

### 3.4. Sample
#### 3.4.1. 요청
```
(POST) https://localhost:8080/productCoverage/coverage
-body-
{
    "name": "대물 피해",
    "coverage": 500000,
    "base": 70
}
```
#### 3.4.2. 응답
```
{
    "code": 0,
    "message": "Success",
    "data": "담보가 정상 등록되었습니다."
}
```

## 4. 보험 담보 매핑 API  
### 4.1. 기본 정보
|URL|HTTP 메서드|   
|:--|:--------|   
|http://localhost:8080/productCoverage/map|POST|   
### 4.2. 요청 데이터
|NAME|TYPE|DESCRIPTION|REQUIRED|  
|:---|:---|:----------|:-------|   
|productSeq|string|기등록 보험 일련 번호|true|
|CoverageSeq|List<number>|기등록 담보 일련 번호 리스트|true|

### 4.3. 응답 데이터
|NAME|TYPE|DESCRIPTION|   
|:---|:---|:----------|   
|code|number|결과 코드|
|message|string|결과 메시지|
|data|string|요청 결과|

### 4.4. Sample
#### 4.4.1. 요청
```
(POST) https://localhost:8080/productCoverage/coverage
-body-
{
    "productSeq" : 3,
    "coverageSeqs":[5]
}
```
#### 4.4.2. 응답
```
{
    "code": 0,
    "message": "Success",
    "data": "보험 상품과 담보가 정상 매핑되었습니다."
}
```
    
 ## 5. 보험 담보 매핑 해제 API  
### 5.1. 기본 정보
|URL|HTTP 메서드|   
|:--|:--------|   
|http://localhost:8080/productCoverage/map|DELETE|   
### 5.2. 요청 데이터
|NAME|TYPE|DESCRIPTION|REQUIRED|  
|:---|:---|:----------|:-------|   
|productSeq|string|기등록 보험 일련 번호|true|
|CoverageSeq|List<number>|기등록 담보 일련 번호 리스트|true|

### 5.3. 응답 데이터
|NAME|TYPE|DESCRIPTION|   
|:---|:---|:----------|   
|code|number|결과 코드|
|message|string|결과 메시지|
|data|string|요청 결과|

### 5.4. Sample
#### 5.4.1. 요청
```
(DELETE) https://localhost:8080/productCoverage/coverage
-body-
{
    "productSeq" : 1,
    "coverageSeqs":[2]
}
```
#### 5.4.2. 응답
```
{
    "code": 0,
    "message": "Success",
    "data": "보험 상품에서 담보가 정상적 제거되었습니다."
}
```
  
## 6. 보험 조회 API 
### 6.1. 기본 정보
|URL|HTTP 메서드|   
|:--|:--------|   
|http://localhost:8080/productCoverage/product/all|GET|   
### 6.2. 요청 데이터
|NAME|TYPE|DESCRIPTION|REQUIRED|  
|:---|:---|:----------|:-------|   

### 6.3. 응답 데이터
|NAME|TYPE|DESCRIPTION|   
|:---|:---|:----------|   
|code|number|결과 코드|
|message|string|결과 메시지|
|data|**JsonArray**|요청 결과|
|seq|number|보험 일련 번호|
|name|string|보험명|
|minPeriod|number|최소 계약 기간(단위: 월)|
|maxPeriod|number|최대 계약 기간(단위: 월)|
|createDtime|dateTimeString|등록일시|
|updateDtime|dateTimeString|수정일시|

### 6.4. Sample
#### 6.4.1. 요청
```
(GET) http://localhost:8080/productCoverage/product/all
```
#### 6.4.2. 응답
```
{
    "code": 0,
    "message": "Success",
    "data": [
        {
            "seq": 1,
            "name": "여행자 보험",
            "minPeriod": 1,
            "maxPeriod": 3,
            "createDtime": "2023-04-20 12:50:37",
            "updateDtime": "2023-04-20 12:50:37"
        },
        {
            "seq": 2,
            "name": "휴대폰 보험",
            "minPeriod": 1,
            "maxPeriod": 12,
            "createDtime": "2023-04-20 12:50:37",
            "updateDtime": "2023-04-20 12:50:37"
        },
        {
            "seq": 3,
            "name": "자전거 보험",
            "minPeriod": 1,
            "maxPeriod": 3,
            "createDtime": "2023-04-20 12:50:53",
            "updateDtime": "2023-04-20 12:50:53"
        }
    ]
}
```

## 7. 담보 조회 API 
### 7.1. 기본 정보
|URL|HTTP 메서드|   
|:--|:--------|   
|http://localhost:8080/productCoverage/coverage/all|GET|   
### 7.2. 요청 데이터
|NAME|TYPE|DESCRIPTION|REQUIRED|  
|:---|:---|:----------|:-------|   

### 7.3. 응답 데이터
|NAME|TYPE|DESCRIPTION|   
|:---|:---|:----------|   
|code|number|결과 코드|
|message|string|결과 메시지|
|data|**JsonArray**|요청 결과|
|seq|number|담보 일련 번호|
|name|string|명|
|coverage|number|가입 금액|
|base|number|기준 금액|
|createDtime|dateTimeString|등록일시|
|updateDtime|dateTimeString|수정일시|

### 7.4. Sample
#### 7.4.1. 요청
```
(GET) http://localhost:8080/productCoverage/coverage/all
```
#### 7.4.2. 응답
```
{
    "code": 0,
    "message": "Success",
    "data": [
        {
            "seq": 1,
            "name": "상해 치료비",
            "coverage": 1000000.0,
            "base": 100.0,
            "createDtime": "2023-04-20 12:58:10",
            "updateDtime": "2023-04-20 12:58:10"
        },
        {
            "seq": 2,
            "name": "항공기 지연 도착 시 보상금",
            "coverage": 500000.0,
            "base": 100.0,
            "createDtime": "2023-04-20 12:58:10",
            "updateDtime": "2023-04-20 12:58:10"
        },
        {
            "seq": 3,
            "name": "부분 손실",
            "coverage": 750000.0,
            "base": 38.0,
            "createDtime": "2023-04-20 12:58:10",
            "updateDtime": "2023-04-20 12:58:10"
        },
        {
            "seq": 4,
            "name": "전체 손실",
            "coverage": 1570000.0,
            "base": 40.0,
            "createDtime": "2023-04-20 12:58:10",
            "updateDtime": "2023-04-20 12:58:10"
        },
        {
            "seq": 5,
            "name": "대물 피해",
            "coverage": 500000.0,
            "base": 70.0,
            "createDtime": "2023-04-20 12:58:25",
            "updateDtime": "2023-04-20 12:58:25"
        }
    ]
}
```

## 8. 보험 담보 매핑 조회 API 
### 8.1. 기본 정보
|URL|HTTP 메서드|   
|:--|:--------|   
|http://localhost:8080/productCoverage/all|GET|   
### 8.2. 요청 데이터
|NAME|TYPE|DESCRIPTION|REQUIRED|  
|:---|:---|:----------|:-------|   

### 8.3. 응답 데이터
|NAME|TYPE|DESCRIPTION|   
|:---|:---|:----------|   
|code|number|결과 코드|
|message|string|결과 메시지|
|data|**JsonArray**|요청 결과|
|product|*map*|보험 정보|
|seq|number|보험 일련 번호|
|name|string|보험명|
|minPeriod|number|최소 계약 기간(단위: 월)|
|maxPeriod|number|최대 계약 기간(단위: 월)|
|createDtime|dateTimeString|등록일시|
|updateDtime|dateTimeString|수정일시|
|coverages|List<map>|보험 별 담보 리스트|
|seq|number|담보 일련 번호|
|name|string|보험명|
|coverage|number|가입 금액|
|base|number|기준 금액|
|createDtime|dateTimeString|등록일시|
|updateDtime|dateTimeString|수정일시|

### 8.4. Sample
#### 8.4.1. 요청
```
(GET) http://localhost:8080/productCoverage/coverage/all
```
#### 8.4.2. 응답
```
{
    "code": 0,
    "message": "Success",
    "data": [
        {
            "product": {
                "seq": 1,
                "name": "여행자 보험",
                "minPeriod": 1,
                "maxPeriod": 3,
                "createDtime": "2023-04-20 12:58:10",
                "updateDtime": "2023-04-20 12:58:10"
            },
            "coverages": [
                {
                    "seq": 1,
                    "name": "상해 치료비",
                    "coverage": 1000000.0,
                    "base": 100.0,
                    "createDtime": "2023-04-20 12:58:10",
                    "updateDtime": "2023-04-20 12:58:10"
                },
                {
                    "seq": 2,
                    "name": "항공기 지연 도착 시 보상금",
                    "coverage": 500000.0,
                    "base": 100.0,
                    "createDtime": "2023-04-20 12:58:10",
                    "updateDtime": "2023-04-20 12:58:10"
                }
            ]
        },
        {
            "product": {
                "seq": 2,
                "name": "휴대폰 보험",
                "minPeriod": 1,
                "maxPeriod": 12,
                "createDtime": "2023-04-20 12:58:10",
                "updateDtime": "2023-04-20 12:58:10"
            },
            "coverages": [
                {
                    "seq": 3,
                    "name": "부분 손실",
                    "coverage": 750000.0,
                    "base": 38.0,
                    "createDtime": "2023-04-20 12:58:10",
                    "updateDtime": "2023-04-20 12:58:10"
                },
                {
                    "seq": 4,
                    "name": "전체 손실",
                    "coverage": 1570000.0,
                    "base": 40.0,
                    "createDtime": "2023-04-20 12:58:10",
                    "updateDtime": "2023-04-20 12:58:10"
                }
            ]
        },
        {
            "product": {
                "seq": 3,
                "name": "자전거 보험",
                "minPeriod": 1,
                "maxPeriod": 3,
                "createDtime": "2023-04-20 12:58:23",
                "updateDtime": "2023-04-20 12:58:23"
            },
            "coverages": [
                {
                    "seq": 5,
                    "name": "대물 피해",
                    "coverage": 500000.0,
                    "base": 70.0,
                    "createDtime": "2023-04-20 12:58:25",
                    "updateDtime": "2023-04-20 12:58:25"
                }
            ]
        }
    ]
}
```

## 9. 예상 보험료 조회 API  
### 9.1. 기본 정보
|URL|HTTP 메서드|   
|:--|:--------|   
|http://localhost:8080/contract/calculatedCost/{period}|GET|  

### 9.2. 요청 데이터
|NAME|TYPE|DESCRIPTION|REQUIRED|  
|:---|:---|:----------|:-------|   
|period|number|가입 기간|true|
|coverageSeqs|List<number>|담보 일련 번호|true|

### 9.3. 응답 데이터
|NAME|TYPE|DESCRIPTION|   
|:---|:---|:----------|   
|code|number|결과 코드|
|message|string|결과 메시지|
|data|map|요청 결과|
|totalCost|number|총 보험료|

### 9.4. Sample
#### 9.4.1. 요청
```
(GET) http://localhost:8080/contract/calculatedCost/8?coverageSeqs=3,4
```
#### 9.4.2. 응답
```
{
    "code": 0,
    "message": "Success",
    "data": {
        "totalCost": 471894.73
    }
}
```

## 10. 보험 계약 API  
### 10.1. 기본 정보
|URL|HTTP 메서드|   
|:--|:--------|   
|http://localhost:8080/contract|POST|  

### 10.2. 요청 데이터
|NAME|TYPE|DESCRIPTION|REQUIRED|  
|:---|:---|:----------|:-------|   
|productSeq|number|보험 일련 번호|true|
|coverageSes|List<number>|담보 일련 번호|true|
|period|number|가입 기간|true|
|startDate|dateString|보험 시작일|true|

### 10.3. 응답 데이터
|NAME|TYPE|DESCRIPTION|   
|:---|:---|:----------|   
|code|number|결과 코드|
|message|string|결과 메시지|
|data|string|요청 결과|

### 10.4. Sample
#### 10.4.1. 요청
```
(POST) http://localhost:8080/contract
-body-
{
    "productSeq" : 2,
    "coverageSeqs" : [3, 4],
    "period" : 8,
    "startDate" : "2023-04-22"
}
```
#### 10.4.2. 응답
```
{
    "code": 0,
    "message": "Success",
    "data": "계약이 정상적으로 처리되었습니다."
}
```

## 11. 보험 계약 조회 API  
### 11.1. 기본 정보
|URL|HTTP 메서드|   
|:--|:--------|   
|http://localhost:8080/contract/{productSeq}|GET|  

### 11.2. 요청 데이터
|NAME|TYPE|DESCRIPTION|REQUIRED|  
|:---|:---|:----------|:-------|   
|productSeq|number|보험 일련 번호|true|

### 11.3. 응답 데이터
|NAME|TYPE|DESCRIPTION|   
|:---|:---|:----------|   
|code|number|결과 코드|
|message|string|결과 메시지|
|data|**map**|요청 결과|
|product|*map*|보험 정보|
|name|string|보험명|
|coverages|List<map>|담보 리스트|
|name|string|담보명|
|coverage|number|가입 금액|
|base|number|기준 금액|
|state|number|담보 상태(0:만료, 1:정상, 2:철회)|
|stateStr|string|담보 상태|
|createDtime|dateTimeString|담보 등록 일시|
|updateDtime|dateTimeString|담보 수정 일시|
|contract|*map*|계약 정보|
|period|number|계약 기간|
|startDate|dateString|보험 시작일|
|endDate|dateString|보험 종료일|
|totalCost|number|총 보험료|
|state|number|계약 상태(0:만료, 1:정상, 2:철회)|
|stateStr|string|계약 상태|
|createDtime|dateTimeString|담보 등록 일시|
|updateDtime|dateTimeString|담보 수정 일시|

### 11.4. Sample
#### 11.4.1. 요청
```
(GET) http://localhost:8080/contract/1
```
#### 11.4.2. 응답
```
{
    "code": 0,
    "message": "Success",
    "data": {
        "product": {
            "name": "휴대폰 보험",
            "coverages": [
                {
                    "name": "부분 손실",
                    "coverage": 750000.0,
                    "base": 38.0,
                    "state": 1,
                    "stateStr": "정상",
                    "createDtime": "2023-04-20 09:31:39",
                    "updateDtime": "2023-04-20 09:31:39"
                },
                {
                    "name": "전체 손실",
                    "coverage": 1570000.0,
                    "base": 40.0,
                    "state": 1,
                    "stateStr": "정상",
                    "createDtime": "2023-04-20 09:31:39",
                    "updateDtime": "2023-04-20 09:31:39"
                }
            ]
        },
        "contract": {
            "period": 8,
            "startDate": "2023-04-22",
            "endDtate": "2023-12-22",
            "totalCost": 471894.73,
            "state": 1,
            "stateStr": "정상",
            "createDtime": "2023-04-20 09:31:39",
            "updateDtime": "2023-04-20 09:31:39"
        }
    }
}
```

## 12. 보험 계약 API  
### 12.1. 기본 정보
|URL|HTTP 메서드|   
|:--|:--------|   
|http://localhost:8080/contract|PUT|  

### 12.2. 요청 데이터
|NAME|TYPE|DESCRIPTION|REQUIRED|  
|:---|:---|:----------|:-------|   
|contractSeq|number|계약 일련 번호|true|
|contractState|number|변경할 계약 상태(1:정상, 2:철회)|false|
|period|number|변경할 가입 기간|false|
|addCoverageSeqs|List<number>|추가할 담보 일련 번호|false|
|cancelCoverageSeqs|List<number>|철회할 담보 일련 번호|false|

### 12.3. 응답 데이터
|NAME|TYPE|DESCRIPTION|   
|:---|:---|:----------|   
|code|number|결과 코드|
|message|string|결과 메시지|
|data|String|요청 결과|

### 12.4. Sample
#### 12.4.1. 요청
```
(PUT) http://localhost:8080/contract
-body-
{
    "contractSeq": 1,
    "contractState": 1,
    "period": 1,
    "addCoverageSeqs" :[],
    "cancelCoverageSeqs": []
}
```
#### 12.4.2. 응답
```
{
    "code": 0,
    "message": "Success",
    "data": "계약 사항이 정상적으로 변경되었습니다."
}
```
    
 ## 13. 과제 요건에 대한 고민 및 접근/해결 방식     
```
    1. 계약과 상품은 일대일 관계로 요건이 정해졌지만, 추후 일대다 관계로, 즉 하나의 계약에 여러 상품을 체결할 수 있는 방향으로 요건이 바뀔 가능성을 고려하여 다대다 관계로 매핑했습니다.
    2. 보험과 담보를 하나의 상품으로 설계하느냐, 보험과 담보를 각각 따로 두고 매핑하여 상품을 만들어내느냐를 고민했습니다.
        결론적으로 보험과 담보가 서로 다대다 관계일 가능성을 고려하여 매핑 테이블을 설계했습니다.
        보험을 Insurance, 담보를 Coverage, 상품을 Product로 명명할까도 고민했지만,
        담보가 없는 보험은 보험이 아니기 때문에 큰 틀의 보험을 Product로 정하고, 담보(Coverage)와의 매핑관계를 고려해 상품을 ProductCoverage로 명명하였습니다.
    3. 보험, 담보, 상품의 생성 구현이 필요하다면 당연히 조회 기능도 필요할 것 같아 각각의 조회 기능도 구현했습니다. 
    4. 보험과 담보의 매핑을 해제하는 기능을 구현하면서, 이미 판매된 상품은 보험-담보 매핑관계를 삭제하지 않도록 하는 것이 맞겠다고 판단했습니다. 
        따라서 매핑 정보는 '사용 여부'로 관리하도록 설계했습니다.
    5. 계약 기간이 지난 계약에 대해 계약 상태를 자동 변경할 목적으로 배치 기능을 추가했습니다. 또한 계약 만료 일주일 전인 계약에 대해서도 로그를 남기도록 배치 기능에 추가했습니다.
    6. 계약 사항 변경 시 보험료가 변동하는 게 당연할 것 같은데, 요건에 '계약 생성 시점에 계산한다'고 돼 있어서, 이게 맞는 건지 이메일 문의를 드렸지만, 답변을 받지 못해 우선 요건대로 구현했습니다.
    7. @PostConstruct에서 자동적으로 DB에 보험-담보-상품을 insert하도록 구현했었는데, 배치 프로그램을 삽입하면서 @PostConstruct에서 트랜젝션 에러가 발생했습니다.
        Spring 생명주기와 관련이 있어보이는데, 아직 정확한 원인을 파악하지 못해 부득이 '1.[보험 및 담보 초기 데이터 생성 API]'를 구현하게 됐습니다. 
        관련해서, 테스트 코드에서도 배치 프로그램 삽입 이후 JPA 트랜젝션 에러가 발생해 원인을 파악 중입니다만, 아직 뚜렷한 원인은 찾지 못했습니다.
        배치 프로그램 삽입 이후 테스트는 디버깅과 포스트맨으로 진행했습니다.
```
    
# 감사합니다.
