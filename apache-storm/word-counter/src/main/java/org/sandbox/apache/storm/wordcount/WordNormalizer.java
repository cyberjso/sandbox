package org.sandbox.apache.storm.wordcount;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordNormalizer implements IRichBolt {
    private OutputCollector collector;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        collector =  outputCollector;
    }

    public void execute(Tuple tuple) {
        System.out.println("**** word normalizing **** " + tuple.getString(0));
        String sentence = tuple.getString(0);
        System.out.println(sentence);
        String[] words = sentence.split(" ");
        for (String word : words) {
            if (!word.isEmpty()) {
                word.toLowerCase();
                List a = new ArrayList();
                a.add(tuple);
                collector.emit(a, new Values(word));
            }
        }
        collector.ack(tuple);
    }

    public void cleanup() { }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}
