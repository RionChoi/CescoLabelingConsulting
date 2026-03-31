# CescoLabelingConsulting — Food Labeling Consulting & HACCP System

> A web application supporting **food labeling consulting (FS) and HACCP certification (HC)** for CESCO.  
> Both the Food & Safety Labeling Consulting service and the HACCP Hazard Analysis service are integrated into a single system.
> ⚠️ This is an internal CESCO system for food labeling consulting and HACCP support.

---

## 📌 Project Overview

| Item            | Details                            |
| --------------- | ---------------------------------- |
| Group ID        | `com.cesco`                        |
| Version         | `0.0.1-SNAPSHOT`                   |
| Java            | 11                                 |
| Framework       | Spring Boot `2.7.3`                |
| Template Engine | Thymeleaf + Vue.js 2.x             |
| Database        | MS-SQL Server 19                   |
| ORM             | MyBatis `2.2.0`                    |
| Build           | Gradle (WAR packaging → `app.war`) |
| Server          | Resin 4.0 / CentOS                 |
| Deployment      | Jenkins CI/CD                      |

---

## 📁 Project Structure

```
CescoLabelingConsulting/
├── build.gradle                # Gradle dependencies & build config
├── settings.gradle             # Project name settings
├── gradlew / gradlew.bat       # Gradle Wrapper
├── ci/                         # Jenkins CI/CD automation config
└── src/
    └── main/
        ├── java/
        │   └── com/cesco/
        │       ├── CescoApplication.java        # Spring Boot entry point
        │       ├── co/                          # Common service (shared by FS & HC)
        │       │   ├── acctMgt/                 # Account management
        │       │   ├── codeMgt/                 # Common code management
        │       │   ├── cusMgt/                  # Customer management
        │       │   └── notice/                  # Notices
        │       ├── fs/                          # Food Labeling Consulting service
        │       │   ├── calculate/               # Fee calculation
        │       │   ├── consultMgt/              # Consulting management
        │       │   ├── consulting/              # Consulting workflow
        │       │   ├── dto/                     # Data Transfer Objects
        │       │   ├── main/                    # Dashboard / main page
        │       │   └── serviceStatement/        # Service statement documents
        │       ├── hc/                          # HACCP service
        │       │   ├── calculate_hc/            # HACCP fee calculation
        │       │   ├── consultMgt_hc/           # HACCP consulting management
        │       │   ├── consulting_hc/           # HACCP consulting workflow
        │       │   └── main_hc/                 # HACCP dashboard
        │       └── sys/                         # Framework common layer
        │           ├── comm/                    # Common utilities
        │           ├── common/                  # Common components
        │           ├── communityhandlers/       # WebSocket handlers
        │           └── config/                  # Spring config (Security, MyBatis, etc.)
        └── resources/
            ├── application.yaml                 # Base configuration
            ├── application-develop.yaml         # Development environment
            ├── application-test.yaml            # Test environment
            ├── application-production.yaml      # Production environment
            ├── log4j2.xml                       # Logging configuration
            ├── mybatis/mapper/                  # MyBatis SQL XML mappers
            ├── static/                          # Static files (JS, CSS, images)
            └── templates/                       # Thymeleaf HTML templates
```

---

## 🛠 Tech Stack

### Backend

- **Spring Boot** `2.7.3` — Web application framework
- **Java** `11`
- **Spring Security** — Authentication & authorization
- **Spring WebSocket** — Real-time communication
- **MyBatis** `2.2.0` — SQL Mapper ORM
- **MS-SQL Server** — `mssql-jdbc:8.4.1.jre11`

### Frontend

- **Thymeleaf** — Server-side rendering template engine
- **Thymeleaf Layout Dialect** — Layout reuse
- **Vue.js** `2.x` — Client-side components
- **jQuery** / **jqxGrid** / **jQuery UI** — UI components
- **axios** — HTTP communication
- **moment.js** — Date handling

### File Handling

- **SFTP** — `JSch 0.1.55` — File server integration
- **FTP** — `commons-net 3.6` — File upload/download
- **commons-compress** `1.21` — File compression
- **File Upload** — Up to 500MB (Multipart)

### Email

- **Spring Boot Starter Mail** — Gmail SMTP
- **javax.mail** `1.4.7`

### Utilities

- **commons-lang3** `3.12.0` — String / date utilities
- **fastjson** `1.2.83` — JSON processing
- **hutool-log** `5.8.9` — Logging utility
- **OkHttp3** `4.10.0` — HTTP client
- **Unirest (Jackson)** `3.13.10` — REST API calls

### Logging

- **log4jdbc-log4j2** `1.16` — DB query logging
- **log4j2** — Application logging

### Build & CI/CD

- **Gradle** — Build tool (WAR → `app.war`)
- **Jenkins** (`ci/` directory) — Automated build & deployment

---

## 🗂 Domain Overview

| Package | Role                       | Key Features                                                             |
| ------- | -------------------------- | ------------------------------------------------------------------------ |
| `co`    | Common (shared by FS & HC) | Account management, code management, customer management, notices        |
| `fs`    | Food Labeling Consulting   | Consulting application & management, fee calculation, service statements |
| `hc`    | HACCP Service              | HACCP consulting application & management, fee calculation               |
| `sys`   | Framework common layer     | Security config, MyBatis config, WebSocket, common utilities             |

---

## ⚙️ Environment Configuration

| Profile     | File                          | Description                           |
| ----------- | ----------------------------- | ------------------------------------- |
| Default     | `application.yaml`            | Common settings (DB, Mail, SFTP, FTP) |
| Development | `application-develop.yaml`    | Development server settings           |
| Test        | `application-test.yaml`       | Test environment                      |
| Production  | `application-production.yaml` | Production server settings            |

### Key Configuration Items

- **Database**: MS-SQL Server (via `mssql-jdbc` + `log4jdbc` query logging wrapper)
- **File Upload**: Up to 500MB supported
- **SFTP Integration**: File server connection via JSch
- **E-FORM**: Integration with `https://deveform.cesco.co.kr`

---

## 🚀 Getting Started

### Run Development Server

```bash
# Using Gradle Wrapper (recommended)
./gradlew bootRun

# With profile
./gradlew bootRun --args='--spring.profiles.active=develop'
```

### VS Code F5 Quick Start

> Press `F5` in VS Code → Runs directly with the Spring Boot embedded server

### Build (WAR)

```bash
./gradlew bootWar
# Output: build/libs/app.war
```

### Jenkins CI/CD

> Automated build and deployment pipeline configured in the `ci/` directory.

---

## 🗄 Database

- **Type**: Microsoft SQL Server 19
- **Driver**: `mssql-jdbc:8.4.1.jre11` + `log4jdbc` (query logging)
- **Connection Pool**: HikariCP (min 2 / max 10 connections)
- **MyBatis Mappers**: `mybatis/mapper/**/*.xml`

---

> ⚠️ This project is an internal Food Labeling Consulting & HACCP support system for CESCO.
