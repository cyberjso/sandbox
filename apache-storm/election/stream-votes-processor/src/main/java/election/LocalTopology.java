package election;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import election.model.ResultProcessor;
import election.model.VoteReader;
import election.model.VoteValidator;
import election.model.VotesParser;

public class LocalTopology {

    public static  void main(String[] args) {
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("votes-reader", new VoteReader());
        topologyBuilder.setBolt("votes-parser", new VotesParser(), 1).shuffleGrouping("votes-reader");
        topologyBuilder.setBolt("votes-validator", new VoteValidator(), 1).shuffleGrouping("votes-parser");
        topologyBuilder.setBolt("votes-counter", new ResultProcessor(), 3).fieldsGrouping("votes-validator", "validVote", new Fields("candidateId"));


        Config conf = new Config();
        conf.put("votes.file", args[0]);
        conf.setNumWorkers(4);
        conf.setDebug(true);
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("election-topology", conf, topologyBuilder.createTopology());
        try {
            Thread.sleep(20000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cluster.shutdown();

    }

}

