FROM sdorra/oracle-java-8
MAINTAINER Jackson Oliveira <jackson.oliveira.rs@gmail.com>

RUN wget -q -O - http://apache.mirrors.pair.com/zookeeper/zookeeper-3.4.6/zookeeper-3.4.6.tar.gz | tar -xzf - -C /opt \
    && mv /opt/zookeeper-3.4.6 /opt/zookeeper \
    && mkdir -p /tmp/zookeeper

WORKDIR /opt/zookeeper

COPY zoo.cfg /opt/zookeeper-3.4.6/conf/

VOLUME ["/opt/zookeeper/conf", "/tmp/zookeeper"]

ENTRYPOINT ["/opt/zookeeper/bin/zkServer.sh"]

CMD ["start-foreground"]