package election.model;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

import java.util.Map;

public class ResultProcessor extends BaseRichBolt {
    private OutputCollector _collector;
    private Result  result;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
        result = new Result();
    }

    @Override
    public void execute(Tuple input) {
        updateResult(input.getStringByField("candidateId"));
        _collector.ack(input);
    }

    private void updateResult(String candidateId) {
        result.setCandidateId(candidateId);
        result.setVotes(result.getVotes() + 1);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) { }

}
