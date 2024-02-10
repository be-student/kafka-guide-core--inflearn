# kafka-topics command

cli

옵션들

--bootstrap-server 토픽을 생성할 서버 주소

--create 토픽 생성

--list 토픽 리스트 보기

--describe 토픽 상세 정보 보기

_와 . 2개를 같이 쓰면 충돌이 될 수 있으니 하나만 쓰기

분산 환경이기에 핵심은 파티션을 여러개 만드는 것이다

--partions 파티션 수 지정

--replication-factor 복제 수 지정

### 토픽 생성

```bash
docker-compose exec kafka-1 kafka-topics --create --topic welcome-topic --bootstrap-server kafka-1:9092 --partitions 3
```

### 토픽 리스트 보기

```bash
docker-compose exec kafka-1 kafka-topics --list --bootstrap-server kafka-1:9092
```

### 토픽 상세 정보 보기

```bash
kafka-topics --bootstrap-server kafka-1:9092 --describe --topic welcome-topic-2
```

### 토픽 삭제

```bash
docker-compose exec kafka-1 kafka-topics --delete --topic welcome-topic --bootstrap-server kafka-1:9092
```

복제의 수는 브로커의 수보다 같거나 작아야 한다

server.properties 쪽 가보면
num.partitions=1 기본값이 1이다.

없으면 저 값이 된다

로그 파일은 파티션 단위로 저장된다

```
00000000000000000000.index  00000000000000000000.timeindex  partition.metadata
00000000000000000000.log    leader-epoch-checkpoint
```

가보면 index 는 인덱스, timeindex는 시간 인덱스, log는 실제 데이터가 들어가는 곳, metadata는 메타데이터가 들어가는 곳, leader-epoch-checkpoint는 리더가 누구인지 체크하는
곳
같은 것들이 있다

producer 는 어떤 브로커의 파티션으로 보낼지 전략적으로 결정한다

ProducerRecord = Record = Message = Event

토픽의 어떤 파티션으로 보낼지 결정

Record 에는 다음과 같은 것들이 있다

1. Topic 어떤 토픽으로 보낼지
2. Value 실제 데이터
3. Partition 파티션
4. Key 파티션을 결정하는데 사용 (null 이면 라운드로빈)
5. Header 메타데이터

## kafka console producer

/confluent/bin 폴더에 있음

kafka-console-producer --bootstrap-server kafka-1:9092 --topic welcome-topic

이런 형태로 보낼 수 있음

모두 다 문자열로 처리되고, serialize

## kafka console consumer

kafka-console-consumer --bootstrap-server kafka-1:9092 --topic welcome-topic --from-beginning

auto.offset.reset 을 해야한다
latest, earliest 이렇게 2개가 있음

console-consumer 사용시 --from-beginning 옵션을 주면 처음부터 볼 수 있다
