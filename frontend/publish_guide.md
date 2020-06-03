
# CSS

#### Class Naming 규칙
| 잘못된 예| 올바른 예 |
|---|:---:|
| Tab | txt |
| sub-Title | sub-title | 

1. 시작의 이름은 영문 소문자를 사용하며 두 번째 단어부터 하이픈(-)으로 연결하며 대문자는 사용하지 않는다.
1. 영문 소문자, 숫자, 하이픈(-), 언더스코프(_)만 사용할 수 있다.
1. 하이픈(-)은 2개 이상의 단어를 조합할 때만 사용한다.
1. 하이픈(-)을 이용하여 3단계를 초과하여 사용하는 방법은 지양한다.

####CSS 코드 작성 규칙
- 문서의 첫 줄에 인코딩을 선언하며 인코딩은 HTML과 동일한 인코딩을 지정한다. 작업 정보를 아래와 같은 방법으로 작성한다.
````css
@charset "utf-8";
body , html{margin:0; padding:0;}
````

####모든 속성은 영문 소문자로만 작성한다
````css
/* 잘못된 예 */
.contents{color:#FFF;}
/* 올바른 예 */
.contents{color:#fff;}
````

####세미콜론(;)
- 마지막 선언된 속성에도 세미콜론(;)을 사용한다.
````css
/* 잘못된 예 */
.contents{color:#FFF; padding-left:0}
/* 올바른 예 */
.contents{color:#fff;  padding-left:0;}
````

####들여쓰기/줄 바꿈
- CSS 코드 작성시 들여쓰기는 허용하지 않는다.
- 공통으로 사용되는 속성의 선택자간의 줄 바꿈은 허용하며 이 외의 줄바꿈은 허용하지 않는다.
````css
/* 잘못된 예 */
.contents{
    position:relative;
    display:block; width:10px;
    height:10px;
}

/* 올바른 예 */
.contents{position:relative; display:block; width:10px; height:10px; margin:0; padding:0; font-size:12px; color:red;}
.box,
.box2{float:left; display:block;}
````

####속성의 선언 순서
- content, z-index, position, top, right, bottom, left, display, flex, visibility, float, clear, overflow, box-sizing, width, height, margin, padding, border 의 순서를 지킨다
````css
/* 잘못된 예 */
.contents{color:red; margin:0; padding:0; display:block; font-size:12px; position:relative; width:10px; height:10px;}

/* 올바른 예 */
.contents{position:relative; display:block; width:10px; height:10px; margin:0; padding:0; font-size:12px; color:red;}
````

####주석 / 빈줄
- CSS 주석 처리는 시작 주석을 작성하며 종료 주석은 작성하지 않는다. 종료코드 이후에 오는 요소에 한 칸 띄어 쓰기를 허용한다.
- 코드를 구분하기 위하여 코드 그룹 간 1줄의 빈 줄을 허용한다. 빈 줄의 1줄을 초과하지 않는다.
````css
/* title */
.title{color:red; margin:0; padding:0; display:block; font-size:12px; position:relative; width:10px; height:10px;}
.sub-title{color:red; margin:0; padding:0; display:block; font-size:12px; position:relative; width:10px; height:10px;}

/* button*/
.btn-red{position:relative; display:block; width:10px; height:10px; margin:0; padding:0; font-size:12px; color:red;}
.btn-blue{position:relative; display:block; width:10px; height:10px; margin:0; padding:0; font-size:12px; color:blue;}
````

####css 재정의
- margin, border, padding, font-size, text-align의 재정의 만 필요할 경우 하단의 클래스를 추가 하여 사용한다.
- 하단의 클래스로 재정의가 불가능할 경우 하단의 클래스 규칙에 맞춰 클래스를 추가 하여 사용한다.
- margin, padding의 경우 5px단위로만 정의 하여 사용한다.
````css
/* margin */
.mt0 {margin-top:0 !important;}
.mt5 {margin-top:5px !important;}
...

/* border */
.bt0 {border-top:none !important;}

/* padding */
.pd0 {padding:0 !important;}
.pb0 {padding-bottom:0 !important;}

/* hr */
.hr {margin:0; border:none; border-top:1px solid #f2f2f2;}

/* align */
.agl {text-align:left !important;}
.agc {text-align:center !important;}
.agr {text-align:right !important;}

/* color */
.fc-black {color:#101010 !important;}
.fc-blue {color:#7aa3d6 !important;}
.fc-green {color:#8fc741 !important;}
.fc-gray {color:#999 !important;}

/* font size */
.fs12 {font-size:12px !important;}
.fs14 {font-size:14px !important;}
.fs15 {font-size:15px !important;}

/* bg */
.bg-blue{padding-bottom:150px; background:#f4f7fd;}
````

####웹폰트 정의 방법
````css
@font-face {
	font-family: 'Noto Sans KR';
	font-style: normal;
	font-weight: 400;
	src: url('NotoSansKR-Regular.eot'); /* IE9 Compat Modes */
	src: local('Noto Sans KR Bold'), local('NotoSansKR-Bold'),
	url('NotoSansKR-Regular.eot?#iefix') format('embedded-opentype'), /* IE6-IE8 */
	url('NotoSansKR-Regular.woff2') format('woff2'), /* Super Modern Browsers */
	url('NotoSansKR-Regular.woff') format('woff'), /* Modern Browsers */
	url('NotoSansKR-Regular.ttf') format('truetype'), /* Safari, Android, iOS */
	url('NotoSansKR-Regular.svg#NotoSansKR') format('svg'); /* Legacy iOS */
}
````

