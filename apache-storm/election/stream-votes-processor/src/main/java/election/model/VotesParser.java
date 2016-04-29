package election.model;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class VotesParser extends BaseRichBolt {
    private OutputCollector _collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String vote = input.getStringByField("vote");
        ObjectMapper mappper = new ObjectMapper();
        try {
            _collector.emit(input, new Values(mappper.readValue(vote, Vote.class)));
        } catch (IOException e) {
            throw new RuntimeException("Error when desserizling vote: " + e.getMessage());
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("parsedVote"));
    }

}
