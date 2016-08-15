package io.sandbox.rxnetty.dao;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class SessionBuilder {
    private String HOST = "localhost";
    private String KEYSPACE = "keyspace1";

    public Session build() {
        return Cluster.builder()
                .addContactPoint(HOST)
                .build()
                .connect(KEYSPACE);
    }

}
