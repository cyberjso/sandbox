package sandbox.storm.drpc;

import backtype.storm.Config;
import backtype.storm.LocalDRPC;

public class LocalCluster {

    public void start()  {
        LocalDRPC localDRPC = new LocalDRPC();

        Config conf = new Config();
        conf.setDebug(true);

        backtype.storm.LocalCluster localCluster = new backtype.storm.LocalCluster();
        localCluster.submitTopology("drpc-word-counter-topology", conf, new TopologyBuilder().build(localDRPC));

        String result =  localDRPC.execute("word-counter", "now now it goes!");

        System.out.println("**** Tuples ****" + result);

        localDRPC.shutdown();
    }

}
