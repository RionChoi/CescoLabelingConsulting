# CescoLabelingConsulting — 표시컨설팅 & HACCP 시스템

> CESCO의 **식품 표시컨설팅 및 HACCP 인증 지원** 웹 애플리케이션입니다.  
> 식품 표시 컨설팅(FS)과 HACCP 위해요소중점관리기준(HC) 두 서비스를 하나의 시스템에서 제공합니다.
> ⚠️ 이 프로젝트는 CESCO 내부용 표시컨설팅 & HACCP 지원 시스템입니다.

---

## 📌 프로젝트 개요

| 항목         | 내용                            |
| ------------ | ------------------------------- |
| 그룹 ID      | `com.cesco`                     |
| 버전         | `0.0.1-SNAPSHOT`                |
| Java         | 11                              |
| 프레임워크   | Spring Boot `2.7.3`             |
| 템플릿 엔진  | Thymeleaf + Vue.js 2.x          |
| 데이터베이스 | MS-SQL Server 19                |
| ORM          | MyBatis `2.2.0`                 |
| 빌드         | Gradle (WAR 패키징 → `app.war`) |
| 서버         | Resin 4.0 / CentOS              |
| 배포         | Jenkins CI/CD                   |

---

## 📁 프로젝트 구조

```
CescoLabelingConsulting/
├── build.gradle                # Gradle 의존성 및 빌드 설정
├── settings.gradle             # 프로젝트 이름 설정
├── gradlew / gradlew.bat       # Gradle Wrapper
├── ci/                         # Jenkins 빌드 자동화 설정
└── src/
    └── main/
        ├── java/
        │   └── com/cesco/
        │       ├── CescoApplication.java        # Spring Boot 진입점
        │       ├── co/                          # 공통 서비스 (표시 & HACCP 공유)
        │       │   ├── acctMgt/                 # 계정 관리
        │       │   ├── codeMgt/                 # 공통 코드 관리
        │       │   ├── cusMgt/                  # 고객사 관리
        │       │   └── notice/                  # 공지사항
        │       ├── fs/                          # 표시 컨설팅 서비스
        │       │   ├── calculate/               # 요금 계산
        │       │   ├── consultMgt/              # 컨설팅 관리
        │       │   ├── consulting/              # 컨설팅 진행
        │       │   ├── dto/                     # DTO
        │       │   ├── main/                    # 대시보드 / 메인
        │       │   └── serviceStatement/        # 용역확인서
        │       ├── hc/                          # HACCP 서비스
        │       │   ├── calculate_hc/            # HACCP 요금 계산
        │       │   ├── consultMgt_hc/           # HACCP 컨설팅 관리
        │       │   ├── consulting_hc/           # HACCP 컨설팅 진행
        │       │   └── main_hc/                 # HACCP 대시보드
        │       └── sys/                         # 프레임워크 공통
        │           ├── comm/                    # 공통 유틸
        │           ├── common/                  # 공통 컴포넌트
        │           ├── communityhandlers/       # WebSocket 핸들러
        │           └── config/                  # Spring 설정 (Security, MyBatis 등)
        └── resources/
            ├── application.yaml                 # 기본 설정
            ├── application-develop.yaml         # 개발 환경
            ├── application-test.yaml            # 테스트 환경
            ├── application-production.yaml      # 운영 환경
            ├── log4j2.xml                       # 로깅 설정
            ├── mybatis/mapper/                  # MyBatis SQL XML
            ├── static/                          # 정적 파일 (JS, CSS, 이미지)
            └── templates/                       # Thymeleaf 화면 소스
```

---

## 🛠 기술 스택

### Backend

- **Spring Boot** `2.7.3` — 웹 애플리케이션 서버
- **Java** `11`
- **Spring Security** — 인증/권한 관리
- **Spring WebSocket** — 실시간 통신
- **MyBatis** `2.2.0` — SQL Mapper ORM
- **MS-SQL Server** — `mssql-jdbc:8.4.1.jre11`

### Frontend

- **Thymeleaf** — SSR 템플릿 엔진
- **Thymeleaf Layout Dialect** — 레이아웃 재사용
- **Vue.js** `2.x` — 클라이언트 컴포넌트
- **jQuery** / **jqxGrid** / **jQuery UI** — UI 컴포넌트
- **axios** — HTTP 통신
- **moment.js** — 날짜 처리

### 파일 처리

- **SFTP** — `JSch 0.1.55` — 파일 서버 연동
- **FTP** — `commons-net 3.6` — FTP 업로드/다운로드
- **commons-compress** `1.21` — 파일 압축
- **파일 업로드** — 500MB 지원 (Multipart)

### 메일

- **Spring Boot Starter Mail** — Gmail SMTP
- **javax.mail** `1.4.7`

### 유틸리티

- **commons-lang3** `3.12.0` — 문자열 / 날짜 유틸
- **fastjson** `1.2.83` — JSON 처리
- **hutool-log** `5.8.9` — 로깅 유틸
- **OkHttp3** `4.10.0` — HTTP 클라이언트
- **Unirest (Jackson)** `3.13.10` — REST API 호출

### 로깅

- **log4jdbc-log4j2** `1.16` — DB 쿼리 로깅
- **log4j2** — 애플리케이션 로깅

### 빌드 & CI/CD

- **Gradle** — 빌드 도구 (WAR → `app.war`)
- **Jenkins** (`ci/` 디렉토리) — 자동화 빌드 & 배포

---

## 🗂 도메인 구분

| 패키지 | 역할                     | 주요 기능                                         |
| ------ | ------------------------ | ------------------------------------------------- |
| `co`   | 공통 (표시 + HACCP 공유) | 계정관리, 코드관리, 고객사관리, 공지사항          |
| `fs`   | 표시 컨설팅 서비스       | 컨설팅 신청·관리, 요금 계산, 용역확인서           |
| `hc`   | HACCP 서비스             | HACCP 컨설팅 신청·관리, 요금 계산                 |
| `sys`  | 프레임워크 공통          | Security 설정, MyBatis 설정, WebSocket, 공통 유틸 |

---

## ⚙️ 환경 설정

| 프로파일 | 파일                          | 설명                            |
| -------- | ----------------------------- | ------------------------------- |
| 기본     | `application.yaml`            | 공통 설정 (DB, Mail, SFTP, FTP) |
| 개발     | `application-develop.yaml`    | 개발 서버 환경                  |
| 테스트   | `application-test.yaml`       | 테스트 환경                     |
| 운영     | `application-production.yaml` | 운영 서버 환경                  |

### 주요 설정 항목

- **DB**: MS-SQL Server (`mssql-jdbc`, log4jdbc 래핑)
- **파일 업로드**: 최대 500MB
- **SFTP 연결**: JSch 기반 파일 서버 연동
- **E-FORM**: `https://deveform.cesco.co.kr` 연동

---

## 🚀 실행 방법

### 개발 서버 실행

```bash
# Gradle Wrapper 사용 (권장)
./gradlew bootRun

# 프로파일 지정
./gradlew bootRun --args='--spring.profiles.active=develop'
```

### VS Code F5 실행

> IDE에서 `F5` → Spring Boot 내장 서버로 직접 실행

### 빌드 (WAR)

```bash
./gradlew bootWar
# 결과: build/libs/app.war
```

### Jenkins CI/CD

> `ci/` 폴더 내 Jenkins Pipeline 설정으로 자동 빌드 및 배포

---

## 🗄 데이터베이스

- **종류**: Microsoft SQL Server 19
- **드라이버**: `mssql-jdbc:8.4.1.jre11` + `log4jdbc` (쿼리 로깅)
- **Connection Pool**: HikariCP (최소 2 / 최대 10 커넥션)
- **MyBatis Mapper**: `mybatis/mapper/**/*.xml`

---

> ⚠️ 이 프로젝트는 CESCO 내부용 표시컨설팅 & HACCP 지원 시스템입니다.
