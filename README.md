# 네캔만원 🍺
<div align="center">

 ![https___processor miricanvas com_user_hq_image_2022_08_20_03_10_e6629b6bf6034027-b152b9a8_-ㅼ틪留뚯썝-諛쒗몴_1](https://user-images.githubusercontent.com/83055813/185785199-18f5f740-e9b2-43bc-9897-ad2190dc9ea7.jpg)

 <br>

<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white"/>
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=JavaScript&logoColor=white"/>   
<img src="https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=React&logoColor=white"/>

</div>

<br>

**네캔만원**은 편의점 맥주의 다양한 정보를 제공하고 이를 기반으로 한 커뮤니티를 형성하여 맥주 구매 결정에 도움을 주는 서비스입니다.


### **배포링크**
http://4can10000won.shop/


### **시연영상**
https://youtu.be/obeEv0ZyjpM

## 프로젝트 구성
### BE
Java: 17, MySQL: 8.0.30, Spring Boot: 2.7.2,
### FE
React: 18.2.0, npm: 8.18.0

<br/>

엔티티 구현 시 FK 조회할 경우 fetch = LAZY옵션을 사용해서 N+1문제를 방지함.
Eager옵션이 필요한 경우엔 fetch join을 사용해서 해결함.

[Feat: LikeBeer와Beer 조회 쿼리 성능 최적화](https://github.com/Central-Heckaton/4can10000won/pull/10)

### Commit 메시지 규칙
```text
feat : 새로운 기능에 대한 커밋
fix : build 빌드 관련 파일 수정에 대한 커밋
build : 빌드 관련 파일 수정에 대한 커밋
chore : 그 외 자잘한 수정에 대한 커밋(rlxk qusrud)
ci : CI 관련 설정 수정에 대한 커밋
docs : 문서 수정에 대한 커밋
style : 코드 스타일 혹은 포맷 등에 관한 커밋
refactor : 코드 리팩토링에 대한 커밋
test : 테스트 코드 수정에 대한 커밋
```
위 규칙을 준수하여 Commit 메시지 작성함

### **기획 배경**
- 코로나19 이후 집에서 혼자 술을 즐기는 '혼술'이 트렌드로 자리 잡았습니다.
- 국내 편의점은 5만개 이상 존재하고, 맥주는 편의점의 주력 판매 물품 중 하나입니다. 
- 맥주에 대한 정보를 종합하여 한눈에 제공하는 서비스가 없습니다. 
- 기존의 맥주 추천 서비스들은 맥주의 단순한 정보만 제공해주거나, 정보 제공보다 광고가 위주인 곳이 많습니다.

<br>

### **주요 기능**
- 편의점에서 판매하는 맥주의 다양한 정보(맥주 종류, 향, 맛, 설명 등)를 제공해주어 취향에 따라 맥주를 선택할 수 있습니다. 
- 맥주에 대해 많은 사람들과 의견을 나눌 수 있는 소통의 장을 마련하였습니다.

## 다이어그램
### 클래스 다이어그램
![](https://velog.velcdn.com/images/kyunghwan1207/post/d5adc818-c2b1-4806-a644-de95fb9bd4ad/image.png)
### ERD
![](https://velog.velcdn.com/images/kyunghwan1207/post/d384008e-e857-43c4-a8fb-e0418080c785/image.png)

## 세부기능
### 사용자 인증
1. 이메일 중복검사 기능으로 중복된 이메일 가입 방지
2. 네이버, 구글 소셜로그인/회원가입을 통해 신규가입자 유입 원활(Spring Security, 세션)
3. 로그인이 필요한 페이지 접근시 PrincipalDetail의 User정보를 확인해서 로그인 된 사용자가 아니라면 403 Forbidden에러를 보내어 로그인 페이지로 redirect<br/>
[Feat: 로그아웃기능 및 상태코드를 통한 페이지 접근제한](https://github.com/Central-Heckaton/4can10000won/pull/82)

### 관리자
1. "admin"으로 시작하는 이메일주소는 ROLE_ADMIN을 부여해서 신고된 댓글 목록 및 사용자를 관리할 수 있음
2. 신고된 댓글을 관리하는 페이지는 웹기반이기에 Pagable 객체를 사용한 Pagenation을 적용해서 Controller에서 신고된 댓글 목록 조회 시간 89% 개선

### 맥주
1. 맥주 데이터를 확보하기 위해 RateBeer사이트에서 웹크롤링을 통해 데이터 가져옴
2. 사용자가 맥주를 "좋아요" 체크할 수 있도록 해서 관심있는 맥주리스트 유지할 수 있도록 함
3. "좋아요" 기능을 위해 사용자-맥주 다대다 관계를 LikeBeer 중간테이블을 두어서 다대일, 일다대로 풀어냄
4. 맥주 이름검색 가능([Feat : 맥주이름 검색기능 구현](https://github.com/Central-Heckaton/4can10000won/pull/26)), 종류(라거, 에일, IPA, 스타우트) 검색 가능([Feat : Search 페이지 filter 기능 + 검색 기능 추가](https://github.com/Central-Heckaton/4can10000won/pull/40))

### 댓글
1. 댓글과 대댓글을 작성할 수 있게하기 위해서
parentId 필드로 null 인지/아닌지에 따라 부모/자식 댓글로 구분했음(parentId -> null: 댓글, 부모id: 대댓글)
2. 자신이 작성한 댓글은 수정, 삭제할 수 있으며
부적절한 댓글을 발견할 경우 "깃발" 버튼을 눌러 신고할 수 있음
3. 댓글, 대댓글 생성 시 fetch = LAZY 옵션으로 인해 발생한 LazyInitalizationException에러 해결함
[Chore: 댓글, 대댓글 생성 시 LazyInitializationException에러 해결](https://github.com/Central-Heckaton/4can10000won/pull/45)


### **기대 효과**
- 편의점 점주는 맥주의 후기 및 평점으로 맥주 소비량을 예측할 수 있습니다.
- 배달 플랫폼, 음식점과 협업하여 맥주와 어울리는 안주를 추천해줄 수 있습니다.
- 다양한 정보 제공으로 특정한 맥주의 구매를 유도할 수 있습니다.
- 다른 사람들의 후기를 참고하여 새로운 맥주의 접근성을 높여줄 수 있습니다.

<br>

### **추가 기능 (앞으로 추가될 기능입니다)**
- '주간 핫 비어 랭크'를 만들어서 한 주동안 가장 많은 좋아요를 받은 순서대로 맥주 랭킹 1위~3위까지 선정하여 보여줄 것 입니다.
- 프로필 이미지를 업데이트 가능하도록 할 것입니다.
- 스마트폰에 설치할 수 있도록 PWA 형태로 바꾸어 많은 사용자들의 참여를 이끌어낼 것입니다.
- 검색엔진 최적화로 검색했을 때 나올 수 있도록 할 것 입니다.
- ~~Admin 페이지를 만들어 FAQ를 받고, 댓글을 관리하고, 맥주를 등록할 수 있도록 할 것입니다.~~ (완료)

- 사용자가 댓글 신고할 경우 관리자 이메일로 알림오는 기능

<br>

### **Infra Diagram**
![](https://velog.velcdn.com/images/kyunghwan1207/post/6fd65d32-253b-4082-b002-86a7f3602843/image.png)

<br>

### **네캔만원 참여 & 기여자**

| [고경환](https://github.com/kyunghwan1207) | [서지오](https://github.com/seo-jio) | [원동현](https://github.com/Hellol77) | [정채빈](https://github.com/chaevivin) |
|:---------------------------------------:|:---------------------------------:|:----------------------------------:|:-----------------------------------:|
|                Back-end                 |             Back-end              |             Front-end              |              Front-end              |
