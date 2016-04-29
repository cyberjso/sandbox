package sandbox.storm.reader;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.apache.log4j.Logger;
import org.apache.storm.http.HttpResponse;
import org.apache.storm.http.StatusLine;
import org.apache.storm.http.auth.AuthScope;
import org.apache.storm.http.auth.UsernamePasswordCredentials;
import org.apache.storm.http.client.methods.HttpGet;
import org.apache.storm.http.impl.client.BasicCredentialsProvider;
import org.apache.storm.http.impl.client.DefaultHttpClient;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class TweetStreamSpout extends BaseRichSpout{
    static String STREAMING_API_URL="https://stream.twitter.com/1/statuses/filter.json?track=";
    private String track;
    private String user;
    private String password;
    private DefaultHttpClient client;
    private SpoutOutputCollector collector;
    private UsernamePasswordCredentials credentials;
    private BasicCredentialsProvider credentialProvider;
    LinkedBlockingQueue<String> tweets = new LinkedBlockingQueue<String>();

    static Logger LOG = Logger.getLogger(TweetStreamSpout.class);
    static JSONParser jsonParser = new JSONParser();


    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("criteria","tweet"));
    }

    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        int spoutSize = context.getComponentTasks(context.getThisComponentId()).size();
        int id = context.getThisTaskIndex();
        String[] tracks = ((String) conf.get("track")).split(",");
        StringBuffer tracksBuffer = new StringBuffer();
        for(int i=0; i< tracks.length;i++){
            if( i % spoutSize == id){
                tracksBuffer.append(",");
                tracksBuffer.append(tracks[i]);
            }
        }
        if(tracksBuffer.length() == 0)
            throw new RuntimeException("No track found for spout" +
                    " [spoutsSize:"+spoutSize+", tracks:"+tracks.length+"] the amount" +
                    " of tracks must be more then the spout paralellism");


        this.track =  tracksBuffer.substring(1).toString();

        credentials = new UsernamePasswordCredentials(conf.get("user").toString(), conf.get("password").toString());
        credentialProvider = new BasicCredentialsProvider();
        credentialProvider.setCredentials(AuthScope.ANY, credentials);
        this.collector = collector;
    }

    public void nextTuple() {
        client = new DefaultHttpClient();
        client.setCredentialsProvider(credentialProvider);
        HttpGet get = new HttpGet(STREAMING_API_URL + track);
        HttpResponse httpResponse;
        try {
            httpResponse = client.execute(get);
            StatusLine statusLine = httpResponse.getStatusLine();
            if (statusLine.getStatusCode() == 200){
                InputStream inputStream = httpResponse.getEntity().getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String in;
                while ((in  = bufferedReader.readLine()) != null) {
                    try {
                        Object json = jsonParser.parse(in);
                        collector.emit(new Values(track, json));
                    } catch (ParseException e) {
                        LOG.error("Error parsing twitter feed", e);
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("Error in communication with twitter api ["+get.getURI().toString()+"]");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e1) {
            }
        }
    }

}
