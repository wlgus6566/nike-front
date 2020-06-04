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
1. ErrorEnumCode.class에 서비스 별로 에러코드, 메세지에 대한 enum값 정의

    > 에러 코드 생성 규칙 : 서비스명앞3자리 + E + 숫자
    ~~~ java
        public enum loginError {
    
            LOGE01("가입여부를 확인해주세요.\n등록된 정보가 없습니다."),
            LOGE02("이메일을 정확히 입력해주세요."),
            LOGE03("비밀번호를 입력해주세요."),
            LOGE04("회원을 조회할 수 없습니다."),
            LOGE05("이미 사용중인 이메일 입니다.");
    
            loginError(String value) {
                this.value = value;
            }
            private final String value;
            public String getMessage() {
                return value;
            }
    
        }
    ~~~

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
