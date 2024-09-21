# jsonbin-spring


### 고생 일기

1. spring-kafka 버전 문제   

공식문서 가라사대 최신버전 spring 3.3에 대응하는 spring-kafka는 현재 unreliable한 상태라고 한다. 그래서 3.2.4 버전까지가 추천되며, 
더 권장되는 것은 그냥 버전을 생략하여 자동으로 권장버전을 찾도록 하는 것이라고 한다.
또한 최신버전 spring-kafka는 kafka-client를 내장하고 있는듯 하다.
구글링으로 찾으면 온통 구버전 얘기뿐이라, kafka-client를 깔아야 한다는 식으로 알려준다.

참고로 kafka 공식 홈페이지에서 알려주고 있는 kafka docker 실행 튜토리얼은 zookeeper가 아닌 kraft 방식이다. 
왜인지는 모르겠는데 kraft는 현업에서는 기피되고 있다고 한다.
그러니 kafka docker 의 설치는 위의 사례와는 정 반대로 구글링으로 구버전 방식(zookeeper)을 찾는 것이 이득이다.


2. docker compose 

DB와 application을 연동하려고 하면 맨날 까먹어서 연속적인 오류에 시달리게 된다. 다음을 참고하자.
```jdbc:mysql://mysql:3306/json_bin?useSSL=false&allowPublicKeyRetrieval=true```

또한 localhost가 아니라 service_name이어야 한다(container_name이 아니다)는 점을 항상 기억하자.
또또한 port를 local에 설치된 mysql과 충돌을 피하기 위해 3307:3306 이런 식으로 잡는데,
image로 build한 컨테이너 끼리는 3307이 아니라 내부 포트인 3306에 연결된다는 것을 기억하자.

볼륨 관련 해서 macOS에 이슈가 있었는데 기억이 안난다. 나중에 생각나면 정리해두자.


3. jackson 설정 문제

{  "payload": "{\"name\":\"hyeonjae\",\"age\":35}"  }
위의 문구를 {String: String} 으로 인식시키고 싶었는데, 
자꾸 자동으로 {String:{String:String}} 으로 인식해서 오류가 나는 이슈가 있었다.

내 mac은 java 버전이 21인데, linux container는 java 17 이어서 이런 문제가 생기는가 싶긴 한데,
아무튼 결론은 jackson의 설정 이슈라고 한다.
```
  jackson:
    deserialization:
      fail-on-unknown-properties: true
```
챗gpt가 위와같이 하라고 알려줬지만, ```./gradlew build``` 로 재빌드를 하고 
```docker compose up```을 해도 cache에 남은 기존 설정대로 이미지가 빌드가 되는 바람에  
결국 ```docker builder prune``` 으로 cache를 날려버리고서야 정상 작동 하게 되었다.


4. redis 붙이기
docker redis는 redis-server 커맨드를 반드시 실행시켜줘야 한다.
```
# docker
docker --name some-redis -p 6379:6379 redis redis-server

# docker compose
command redis-server
```

