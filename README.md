# Mento-Mentee Matching Site

## 프로젝트 소개
Mento-Mentee Matching Site는 한림대학교 정보과학대학 내 멘토와 멘티를 연결해주는 웹 애플리케이션입니다.
사용자들은 자신이 멘토인지 멘티인지를 선택하고, 질문을 작성하거나 답변을 제공할 수 있습니다. 
또한, 사용자 인증 시스템을 통해 로그인 및 회원가입을 관리합니다. 
이 프로젝트는 Spring Boot, Spring Security, Thymeleaf를 사용하여 개발되었습니다.

## 주요 기능

### 회원가입 및 로그인
- 사용자 등록, 로그인, 그리고 인증을 처리합니다. 
- Spring Security를 사용하여 보안성을 높였습니다.

### 질문 작성 및 조회
- 사용자는 자신의 질문을 작성하고, 작성된 질문은 목록에 표시됩니다. 
- 각 질문은 상세 페이지에서 답변을 받을 수 있습니다.

### 답변 작성
- 각 질문에 대해 답변을 작성하고, 답변을 확인할 수 있습니다.

### 질문 목록 및 페이지네이션
- 질문 목록을 페이지별로 나누어, 대량의 질문이 있을 때에도 사용자 경험을 고려한 조회 방식을 제공합니다.

## 기술 스택
- **Spring Boot**: 백엔드 프레임워크
- **Spring Security**: 인증 및 권한 관리
- **Thymeleaf**: HTML 템플릿 엔진
- **BCryptPasswordEncoder**: 비밀번호 암호화
- **JPA / Hibernate**: 데이터베이스 접근 및 ORM
- **MySQL**: 데이터베이스 관리 시스템

## 디렉토리 구조
src/
 ├── main/
 │   ├── java/
 │   │   ├── com/
 │   │   │   └── example/
 │   │   │       ├── controller/   # 컨트롤러 클래스
 │   │   │       ├── service/      # 서비스 클래스
 │   │   │       ├── model/        # 엔티티 클래스
 │   │   │       └── repository/   # 데이터베이스 인터페이스
 │   ├── resources/
 │   │   ├── templates/
 │   │   │   ├── layouts/          # 공통 레이아웃 (헤더, 푸터)
 │   │   │   ├── mainpage/         # 주요 페이지 템플릿 (회원가입, 로그인 등)
 │   │   ├── application.properties  # 설정 파일
 │   └── static/                   # CSS, JS 파일
 └── test/
     └── java/                      # 테스트 클래스

