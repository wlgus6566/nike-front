1. 설치( node, npm, yarn )

    1. node, npm, yarn 설치
       node : 자바스크립트 기반 서버 사이트 엔진
       npm( node package manager) : 의존선 관리를 위해 사용 노드 패키지 관리자
       yarn : npm 보다 성능이 좋은 패키지 관리자

        ```shell
        #윈도우 설치(다운 설치)
        https://nodejs.org/ko/download/

        #mac 설치
        brew install node
        ===========================

        #설치 확인
        node -v
        npm -v

        #npm 최신 버전으로 업데이트
        sudo npm install -g npm
        sudo npm install -g yarn @vue/cli

        ```

    2. 크롬 vue.js devtools 설치

2) 빠르게 업무 시작하기

    1. **디렉토리 구조**

        ```
        .
        ├── dist               ->vue build 소스 파일정보
        ├── node_modules       ->모듈 라이브러리
        ├── public
        │   ├── begin-with-the-crazy-ideas.md
        │   └── on-simplicity-in-technology.md
        ├── src                ->개발자가 작업하는 폴더
        │   ├── api            ->api 데이터처리 작업  *******
        │   ├── assets         ->리소스 데이터 위치
        │   └── axios          ->axios 공통 인터셉터 설정( 공통작업 )
        │   └── components     ->컴포넌트 작업위치( 모두 같이 사용하는 콤포넌트 )
        │   └── router         ->vue router 설정( url 설정하는 곳 ) *******
        │   └── view           ->작업자들이 vue 파일 올리는 부분 *******
        │   └── App.vue				 ->vue 레이아웃 시작 페이지
        │   └── main.js        ->vue 시작 페이지
        ├── babel.config.js    ->바벨 설정
        ├── package.json       ->설치 모듈정보
        ├── README.md          ->기본 설명
        ├── project.iml        ->Intellij 설정정보
        ```

    2. **작업자가 작성해야하는 파일**( 기본 위 **\*\*** 작업 )

        ```
        api    > 폴더 하위 api 데이터 바인딩
        router > url 관련 페이지설정
        view   > 폴더 하위 vue 파일 api 연동 작업
        ```

    3. **Swagger 보는법**

    4. 기본 라이브러리 설명

        - [Vue.js](https://vuejs.org/)
            - 안정적인 Web Application 구현을 위한 기본 프레임워크 입니다.
        - [Babel](https://babeljs.io/)
            - 하위 브라우저를 위한 트랜스파일러( ECMAScript2015, Typescript 를 하위 버전의 스크립트로 변환 ) 입니다.
        - [Vuex](https://vuex.vuejs.org/kr/intro.html)
            - Vue.js 기반 프로젝트에서 상태 관리 패턴을 적용하기 위한 라이브러리 입니다.
        - [vue-router](https://router.vuejs.org/kr/)
            - Vue.js 기반 프로젝트에서 SPA 구현을 위해 적용하는 라우팅 라이브러리 입니다.
        - [axios](https://github.com/axios/axios)
            - Promise 기반의 Ajax 라이브러리입니다.
            - [axios 번역](https://yamoo9.github.io/axios/)
        - [lodash](https://lodash.com/)
            - debounce 를 이용한 사용자의 이중클릭을 통한 ajax 연속 호출 방지등을 처리 등 기타 데이터 조작을 쉽게 하기 위한 라이브러리 입니다.

3) 심화

    1. 애니메이션 ( transition )

4) 기타 리소스

    1. [element](http://element.eleme.io)
    2. [Onsen UI](https://onsen.io/)
    3. [Awesome vue](https://github.com/vuejs/awesome-vue)
    4. [vue Curated](https://curated.vuejs.org)

5) vue 초기 생성

    ```shell
    vue create nikefront

    manually 설정 ( bable, router, vuex, linter )
    Vue CLI v4.3.1
    ? Please pick a preset: Manually select features
    ? Check the features needed for your project: Babel, Router, Vuex, Linter
    ? Use history mode for router? (Requires proper server setup for index fallback
    in production) Yes
    ? Pick a linter / formatter config: Airbnb
    ? Pick additional lint features: Lint on save
    ? Where do you prefer placing config for Babel, ESLint, etc.? In dedicated confi
    g files
    ? Save this as a preset for future projects? No

    yarn add axios

    ```

6) 기본 라이브러리 설명
