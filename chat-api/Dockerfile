# 1. 종속성 해결 및 캐싱을 위한 스테이지
FROM gradle:8.4-jdk17-alpine AS deps
WORKDIR /app
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
# chat-core, chat-api, chat-websocket의 build.gradle 복사
COPY chat-core/build.gradle ./chat-core/
COPY chat-api/build.gradle ./chat-api/
COPY chat-websocket/build.gradle ./chat-websocket/
# --go-offline 옵션으로 종속성만 다운로드
RUN ./gradlew build --go-offline

# 2. 소스 코드 복사 및 애플리케이션 빌드 스테이지
FROM gradle:8.4-jdk17-alpine AS build
WORKDIR /app
# 이전 스테이지에서 캐싱된 종속성 복사
COPY --from=deps /app ./
# 전체 소스 코드 복사
COPY . .
# chat-api 모듈 빌드 (테스트는 제외하여 빌드 시간 단축)
RUN ./gradlew :chat-api:build -x test

# 헬스체크 (선택)
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s CMD wget --spider -q http://ALB:9000/actuator/health || exit 1

# 3. 최종 실행 이미지 생성 스테이지
FROM openjdk:17-jdk-alpine
WORKDIR /app
# 빌드된 JAR 파일만 최종 이미지에 복사
COPY --from=build /app/chat-api/build/libs/*-SNAPSHOT.jar app.jar
EXPOSE 9000
# ENTRYPOINT exec 포맷 적용
ENTRYPOINT ["sh", "-c", "exec java -jar app.jar"]
