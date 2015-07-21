package sandbox.storm.drpc;

import backtype.storm.Config;
import backtype.storm.LocalDRPC;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.Map;

public class LocalCluster {

    public void start()  {
        LocalDRPC localDRPC = new LocalDRPC();

        Config conf = new Config();
        conf.setDebug(true);

        backtype.storm.LocalCluster localCluster = new backtype.storm.LocalCluster();
        localCluster.submitTopology("drpc-word-counter-topology", conf, new TopologyBuilder().build(localDRPC));

        String result =  localDRPC.execute("word-counter", "now now it goes!");


        try {
            final Map<String,Object> values= new ObjectMapper().readValue(result,new TypeReference<Map<String,Integer>>(){});
            values.entrySet().stream().map(key -> values.get(key));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




        localDRPC.shutdown();

    }

}
