package org.sandbox.apache.storm.wordcount;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word-reader", new WordReader());
        builder.setBolt("word-normalizer", new WordNormalizer(), 3).shuffleGrouping("word-reader");
        builder.setBolt("word-counter", new WordCounter(), 3).shuffleGrouping("word-normalizer");


        Config conf = new Config();
        conf.put("wordsFile", args[0]);
        conf.setNumWorkers(4);
        conf.setDebug(true);
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("My-first-topology", conf, builder.createTopology());
        Thread.sleep(20000);
        cluster.shutdown();

    }

}
