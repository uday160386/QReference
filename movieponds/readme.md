
### Running Kafka

```
    bin/zookeeper-server-start.sh config/zookeeper.properties
    bin/kafka-server-start.sh config/server.properties
    
    Topic: movie-record-event-topic
```

### Patterns
```
    - Event driven - CQRS
    - CircuitBreaker
    - Retry
```
### Start
```agsl
    mvn clean install spring-boot:start
```