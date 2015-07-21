package sandbox.storm.drpc;

import backtype.storm.LocalDRPC;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Count;
import storm.trident.operation.builtin.Debug;
import storm.trident.testing.Split;

public class TopologyBuilder {

    public StormTopology build(LocalDRPC localDRPC)    {
        TridentTopology topology = new TridentTopology();

        topology.newDRPCStream("word-counter", localDRPC).
                each(new Fields("args"), new Split(), new Fields("word")).
                groupBy(new Fields("word")).
                aggregate(new Fields("word"), new Count(), new Fields("count")).
                each(new Fields("word", "count"), new Debug());
        return topology.build();
    }

}
