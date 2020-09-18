# 이 프로젝트는

Nike D&P Platform 구축 프로젝트입니다.


## 개발가이드

### * [서버, backend](./mdFile/backend.md)
### * [Frontend vue.js](./frontend/nike_vuejs_%EA%B0%9C%EB%B0%9C%EA%B0%80%EC%9D%B4%EB%93%9C.md)

### Node.js와 NPM 설치하기
npm의 경우는 Node Package Manager이기 때문에 node.js를 설치하면 같이 설치됩니다.

     $ node -v

설치 완료 후 하단의 명령 프롬포트를 실행하여 version 확인 한다  

**npm 버전 확인**

     $ npm -v
     
**node 버전 확인**
     
      $ node -v
          
 Nike D&P Platform 구축 프로젝트에서 사용된 버전은 하단과 같다.  
 
 **npm version - 6.11.3**  
 **Node version - v10.17.0**  
 
### 서버 세팅
package.json에 명시된 라이브러리등 설치

    $ npm install

### 서버 실행

    $ npm run serve

**PC localhost:8081**  
**MO localhost:8082**


### 디렉토리 구조(폴더구조)

    ~~├─ node_modules             # npm으로 설치되는 서드파트 라이브러리들이 모여 있는 디렉토리
    ├─ public                  
    ├─ package.json             # 빌드 스크립트 
    ├─ babel.config.js          # 트랜스파일러
    ├─ postcss.config.js        # css 자동화
    ├─ vue.config.js
    ├─ .gitignore               # git 예외처리 
    └─ src                      # 실제 코딩이 이루어지는 디렉토리
        ├─ api                  # api js 디렉토리
        ├─ components           # 컴포넌트 디렉토리
        ├─ pub
        ├─ router               # 라우터 디렉토리
        ├─ store                # 상태관리 디렉토리
        ├─ utils                # 필터 등의 유틸리티 함수 디렉토리
        ├─ App.vue
        ├─ main.js              #  Vue 인스턴스를 생성, 가장 먼저 실행되는 javascript 파일
        ├─ assets               # 리소스 리렉토리
        │   ├─ css              # css
        │   ├─ fonts            # 폰트 파일
        │   ├─ lottie           # 로띠
        │   └─ images           # 이미지
        │       └─ svg          # svg
        └─ views                # 라우터 페이지 디렉토리
            ├─ layouts          # 레이아웃 디렉토리
            └─ pages            # 페이지 디렉토리~~