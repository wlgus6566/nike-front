# backeend 공통처리에 대한 

## 응답값, 실패 메세지 반환(custom Exception)
 
### 응답비교

| 상태 | httpStatus | success(boolean) | code(string) |message(string) |
|:--------:|:--------:|:--------:|:--------:|:--------:|
|**성공** | 200 | true | SUC | 성공 |
|**실패(메세지반환)** | 200 | false | 서비스 별 정의 | 서비스 별 정의 |
|**에러** | 500 | false |ERR | 에러발생 |

### 응답예제
* 성공
    ~~~ json
    {
        "success": true,
        "code": "SUC",
        "msg": "성공하였습니다.",
        "data": {
        ...
        }
    }
    ~~~
    
* 실패(메세지반환)
    ~~~ json
    httpStatus : 200
  
    {
        "success": false,
        "code": "MANE01",
        "msg": "해당 회원의 상세정보가 존재하지 않습니다."
    }
    ~~~
* 오류
    ~~~ json
    httpStatus : 500
    
    {
      "success": false,
      "code": "E01",
      "msg": "에러발생"
    }
    ~~~
    
### 사용방법

1. 메세지를 반환해야 하는 부분에 custom exception인 CodeMessageHandelException 사용

    > 사용 : new CodeMessageHandleException(ErrorEnumCode에 정의한 코드, 메세지)
    ~~~ java
    /**
         * 상세조회
         *
         * @param managerSeq the manager seq
         * @return the optional
         */
        public SingleResult<Manager> findById(Long managerSeq) {
            return responseService.getSingleResult(
                    managerRepository.findById(managerSeq)
                            .orElseThrow(() -> new CodeMessageHandleException(ErrorEnumCode.loginError.LOGE04.toString(), ErrorEnumCode.loginError.LOGE04.getMessage())));
        }
    ~~~

1. 해당 swagger에 exception에 대한 코드, 메시지 기술
