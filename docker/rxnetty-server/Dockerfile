FROM  podbox/java8

MAINTAINER Jackson Oliveira <jackson.oliveira.rs@gmail.com>

EXPOSE 8080

ADD rxnetty-cassandra-async /opt

WORKDIR /opt/rxnetty-cassandra-async/

ENTRYPOINT ["./gradlew", "run"]
 