package election.model;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class VoteReader extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private TopologyContext context;
    private FileReader fileReader;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("vote"));
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        this.context = context;
        try {
            fileReader = new FileReader(conf.get("votes.file").toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void nextTuple() {
        String str;
        BufferedReader reader = new BufferedReader(fileReader);
        try {
            while ((str = reader.readLine()) != null) {
                collector.emit(new Values(str), str);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error when reading tuple", e);
        }
    }

}
