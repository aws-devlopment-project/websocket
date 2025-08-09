#!/bin/bash

# 설정
AWS_REGION="ap-northeast-2"
AWS_ACCOUNT_ID="YOUR_ACCOUNT_ID"
ECR_REGISTRY="${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"

# 모듈 목록
MODULES=("chat-api" "chat-websocket")

# ECR 로그인
aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REGISTRY}

# 각 모듈 빌드 및 푸시
for MODULE in "${MODULES[@]}"; do
    echo "Building ${MODULE}..."
    
    # Gradle 빌드
    ./gradlew :${MODULE}:bootJar
    
    # Docker 이미지 빌드
    docker build -t ${MODULE} -f ${MODULE}/Dockerfile ${MODULE}
    
    # ECR 태그
    docker tag ${MODULE}:latest ${ECR_REGISTRY}/${MODULE}:latest
    docker tag ${MODULE}:latest ${ECR_REGISTRY}/${MODULE}:$(date +%Y%m%d-%H%M%S)
    
    # ECR 푸시
    docker push ${ECR_REGISTRY}/${MODULE}:latest
    docker push ${ECR_REGISTRY}/${MODULE}:$(date +%Y%m%d-%H%M%S)
    
    echo "${MODULE} pushed successfully!"
done