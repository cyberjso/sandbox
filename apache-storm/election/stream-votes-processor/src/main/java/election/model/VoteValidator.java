package election.model;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import election.dao.CandiateDao;
import election.dao.CandidateEntity;

import java.util.ArrayList;
import java.util.Map;

public class VoteValidator extends BaseRichBolt {
    private OutputCollector _collector;
    private CandiateDao candiateDao;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream("validVote", new Fields("candidateId"));
        declarer.declareStream("invalidVote", new Fields("candidateId"));
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
        candiateDao = new CandiateDao();
    }

    private boolean isValid(Vote vote) {
        return !candiateDao.findById(vote.getCandidateId()).orElseGet(() -> new ArrayList<CandidateEntity>()).isEmpty();
    }

    @Override
    public void execute(Tuple input) {
        Vote vote = (Vote) input.getValueByField("parsedVote");
        if (isValid(vote))
            _collector.emit("validVote" , input,  new Values(vote.getCandidateId()));
        else
            _collector.emit("invalidVote", input, new Values(vote.getCandidateId()));
    }

}
