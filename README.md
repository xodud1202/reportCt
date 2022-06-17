# Project linux 빌드/설치 방법
### 1. 프로젝트 설치 workspace에 git clone
    sudo git clone https://github.com/xodud1202/reportCt.git

### 2. 프로젝트 빌드
    cd reportCt
    
    * gradlew 실행 권한 추가
    sudo chmod +x gradlew

    * gradlew 실행하여 빌드
    sudo ./gradlew build

### 3. 빌드된 jar 파일 실행
    cd build/libs
    java -jar musinsa-1.0-SNAPSHOT.jar
    
### 4. API 사용 방법
    1. 고객별 포인트 합계 조회
    GET Content-Type: application/json
    호출 : http://localhost:8080/sum/cust/point/info
    필수 파라미터 : custNo
    예제 : http://localhost:8080/sum/cust/point/info?custNo=1
    response : { data : {
                         TOT_SAVE_PNT : 적립금액합계,
                         TOT_LEFT_PNT : 잔여금액합계,
                         CUST_NO      : 고객번호,
                         TOT_USE_PNT  : 사용금액합계,
                         TOT_EXT_PNT  : 소멸금액합계
                        },
                 state: 결과(OK or FAIL),
                 msg  : 결과메세지(성공시없음)
    
    2. 고객별 적립/사용 내역 조회
    GET Content-Type: application/json
    호출 : http://localhost:8080/cust/point/hst/list
    필수 파라미터 : custNo, pageNo
    예제 : http://localhost:8080/cust/point/hst/list?custNo=1&pageNo=1
    response : {
                dataList: [
                           {
                            EXT_DT  : 소멸예정일자,
                            GBN     : 내역구분,
                            PNT_AMT : 포인트금액,
                            CUST_NO : 고객번호,
                            HST_DESC: 상세설명,
                            ORD_NO  : 사용주문번호
                           }, {.....}
                          ],
                state   : 결과(OK or FAIL),
                msg     : 결과 메세지(성공지 없음)
               }
               
    3. 고객별 포인트 적립
    POST Accept: application/json, Content-Type: application/json
    호출 : http://localhost:8080/save/point
    필수 파라미터 : custNo, givePntAmt
    파라미터 예제 : { "custNo" : "1", "givePntAmt" : "700" }
    response : { state: 결과(OK or FAIL), msg: 메세지 }
    
    4. 고객별 포인트 사용
    POST Accept: application/json, Content-Type: application/json
    호출 : http://localhost:8080/use/point
    필수 파라미터 : custNo, usePntAmt, ordNo
    파라미터 예제 : { "custNo" : "1", "usePntAmt" : "700", "ordNo" : "1" }
    response : { state: 결과(OK or FAIL), msg: 메세지 }
    
    5. 포인트 사용 취소 (실패시 data rollback용)
    POST Accept: application/json, Content-Type: application/json
    호출 : http://localhost:8080/cancel/use/point
    필수 파라미터 : ordNo
    파라미터 예제 : { "ordNo" : "1" }
    response : { state: 결과(OK or FAIL), msg: 메세지 }
