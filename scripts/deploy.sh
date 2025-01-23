#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=posts-webservice

echo "> Build 파일 복사"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동 중인 애플리케이션 pid 확인"

CURRENT_PID=$(lsof -ti :8080)

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동 중인 애플리케이션이 없습니다."
else
  echo "> 현재 구동 중인 애플리케이션 PID: $CURRENT_PID"
  echo "> 프로세스 종료 시도: kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5

  # 프로세스가 종료되지 않은 경우 강제 종료
  if ps -p $CURRENT_PID > /dev/null; then
    echo "> 프로세스가 종료되지 않아 강제 종료합니다: kill -9 $CURRENT_PID"
    kill -9 $CURRENT_PID
  fi
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar \
  -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,\
file:/home/ec2-user/app/application-oauth.properties,\
file:/home/ec2-user/app/application-real-db.properties \
  -Dspring.profiles.active=real \
  $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &


