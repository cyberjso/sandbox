package io.sandbox.rxnetty;

import org.cassandraunit.DataLoader;
import org.cassandraunit.dataset.xml.ClassPathXmlDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;

public class Main {

    public static void main(String[] args) {
        try {
            EmbeddedCassandraServerHelper.startEmbeddedCassandra();
            DataLoader dataLoader = new DataLoader("TestCluster", "localhost:9171");
            dataLoader.load(new ClassPathXmlDataSet("dataset.xml"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
