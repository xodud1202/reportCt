# Project linux 설치 방법
### 1. 프로젝트 설치 workspace에 git clone 받기
    sudo git clone https://github.com/xodud1202/reportCt.git

### 2. 프로젝트 빌드
    cd reportCt
    
    * gradlew 실행 권한 추가
    sudo chmod -x gradlew

    * gradlew 실행하여 빌드
    sudo ./gradlew build

### 3. 빌드된 jar 파일을 java로 실행
    cd build/libs
    java -jar musinsa-1.0-SNAPSHOT.jar

    * java 실행이 안된다면 jdk 설치하여 실행 필요.