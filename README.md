# 알고모여 백엔드 프로젝트

-중앙대학교 소프트웨어학과 3학년 2학기에 진행한 캡스톤디자인 프로젝트

-알고리즘 문제 풀이 스터디 진행을 도와주는 플랫폼 ‘알고모여’의 백엔드 프로젝트 입니다.

# URL

[https://algogather.com/](https://algogather.com/)

# 환경

- JDK 17

- Spring Boot 2.7.16

- Gradle 8.2.1

# 주요 API

- 회원가입, 로그인 등 유저 관리
- 스터디방 생성 및 스터디방 인원 관리
- 스터디방 과제 생성 및 과제 풀이 현황 확인
- 소스 코드 공유 및 피드백 작성 API
- 스터디방 구인글 작성 API
- 컴파일러 서버와 연동 및 컴파일 API

# 실행 방법

1. IntelliJ를 설치합니다.
2. 해당 프로젝트를 엽니다.
3. Settings > 빌드, 실행, 배포 > 빌드 도구 > Gradle에서 ‘빌드 및 실행’, ‘테스트 실행’에서 Gradle을 선택합니다.
4. ApiApplication 클래스의 main을 실행합니다.

# 빌드 방법

1. src/main/resources/application-prod.yml 을 생성하고 다음과 같이 작성합니다.

```jsx
spring:
  datasource:
    driver-class-name: # DB 드라이버
    url: # DB URL
    username: # DB USER_NAME
    password: # DB 패스워드

jpa:
    hibernate:
      ddl-auto: update
    generate-ddl: true
    defer-datasource-initialization: true

  sql:
    init:
      mode: always
server:
  servlet:
    session:
      timeout: 1800 # 1800초 (30분)
      cookie:
        max-age: 1800 # 1800초 (30분)
        http-only: true
        same-site: None
        secure: true
  mvc:
    hiddenmethod:
      filter:
        enabled: true

clientURL: # 클라이언트 서버 주소
compilerURL: # 컴파일러 서버 주소
```

2. src/main/resources/application.yml을 다음과 같이 변경합니다.

```jsx
spring:
  profiles:
    active:
      - prod
```

3. 다음 명령어를 사용하여 build합니다.

```jsx
gradle build --debug
```
