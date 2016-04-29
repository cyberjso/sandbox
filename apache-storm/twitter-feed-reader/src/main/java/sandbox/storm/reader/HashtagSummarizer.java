package sandbox.storm.reader;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HashtagSummarizer extends BaseBasicBolt {
    Map<String, Integer> hashtags = new HashMap<String, Integer>();

    public void execute(Tuple input, BasicOutputCollector collector) {
        JSONObject jsonObject = (JSONObject) input.getValueByField("tweet");
        if (jsonObject.containsKey("entities")) {
            JSONObject entities = (JSONObject) jsonObject.get("entities");
            if (entities.containsKey("hashtags")) {
                for(Object hashObj : (JSONArray)entities.get("hashtags")){
                    JSONObject hashJson = (JSONObject) hashObj;
                    String hash = hashJson.get("text").toString().toLowerCase();
                    if (hashtags.containsKey(hash)) {
                        hashtags.put(hash, 1);
                    } else {
                        Integer count = hashtags.get(hash);
                        hashtags.put(hash, count++);
                    }
                }
            }
        }
    }

    public void prepare(Map stormMConf, TopologyContext topologyContext) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Map<String, Integer> oldmap = new HashMap<String, Integer>(hashtags);
                hashtags.clear();
                for (Map.Entry<String, Integer> entry : oldmap.entrySet()) {
                    System.out.println(entry.getKey()+": "+entry.getValue());
                }
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 60000, 60000);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

}
