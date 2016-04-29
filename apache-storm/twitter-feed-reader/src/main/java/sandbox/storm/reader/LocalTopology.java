package sandbox.storm.reader;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

public class LocalTopology {

    public static void main(String[] args) {
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("tweets-collector", new TweetStreamSpout(), 1);
        topologyBuilder.setBolt("hashtag-summirizer", new HashtagSummarizer()).shuffleGrouping("tweets-collector");

        LocalCluster  localCluster = new LocalCluster();
        Config config = new Config();
        config.put("track", "cyber_jso,something");
        config.put("user", "cyber_jso");
        config.put("password", "baratinha5796");
        localCluster.submitTopology("tweeter-test", config, topologyBuilder.createTopology());
    }

}
