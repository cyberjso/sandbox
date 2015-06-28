import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

public class WordCounter implements IRichBolt {
    private Integer id;
    private String name;
    private Map<String, Integer> counters;
    private OutputCollector collector;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        counters = new HashMap<String, Integer>();
        collector = outputCollector;
        name = topologyContext.getThisComponentId();
        id = topologyContext.getThisTaskId();
    }

    public void execute(Tuple tuple) {
        String str = tuple.getString(0);
        if (!counters.containsKey(str)) {
            counters.put(str, 1);
        } else {
            Integer c = counters.get(str) + 1;
            counters.put(str, c);
        }
        collector.ack(tuple);
    }

    public void cleanup() {
        System.out.println(" -- word counter [" + name + " - " + id + "]" );
        for (Map.Entry<String, Integer> entry : counters.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

}
