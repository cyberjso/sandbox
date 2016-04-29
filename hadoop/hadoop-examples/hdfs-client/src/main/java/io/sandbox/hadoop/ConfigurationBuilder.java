package io.sandbox.hadoop;

import org.apache.hadoop.conf.Configuration;

public class ConfigurationBuilder {

    public Configuration build() {
        Configuration config = new Configuration();
        config.set("fs.default.name","hdfs://192.168.34.10:54310" );
        return config;
    }

}
