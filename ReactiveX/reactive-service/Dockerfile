FROM  podbox/java8

MAINTAINER Jackson Oliveira <jackson.oliveira.rs@gmail.com>

EXPOSE 8080

COPY rxnetty-cassandra-async/ /opt/rxnetty-cassandra-async/

WORKDIR /opt/rxnetty-cassandra-async/

ENTRYPOINT ["/bin/bash", "gradlew", "run"]
 
