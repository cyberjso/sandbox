import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordReader implements IRichSpout {
    private SpoutOutputCollector collector;
    private FileReader fileReader;
    private TopologyContext context;
    private boolean isCompleted = false;

    public boolean isDistributed() {
        return false;
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        List<String> l =  new ArrayList<String>();
        l.add("line");

        outputFieldsDeclarer.declare(new Fields(l));
    }

    public void open(Map conf, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        context = topologyContext;
        try {
            fileReader = new FileReader(conf.get("wordFile").toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error when reading file "+ conf.get("wordFile"), e );
        }
        collector = spoutOutputCollector;
    }

    public void close() {

    }

    public void nextTuple() {
        if (isCompleted) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.print("WTF!! Unhandled exception");
            }
            String str;
            BufferedReader reader = new BufferedReader(fileReader);
            try {
                while((str = reader.readLine()) != null) {
                    collector.emit(new Values(str), str);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error when reading tuple", e);
            } finally {
                isCompleted = true;
            }
        }

    }

    public void ack(Object o) {

    }

    public void fail(Object o) {
        System.out.println("Fail: " + o);
    }
}
