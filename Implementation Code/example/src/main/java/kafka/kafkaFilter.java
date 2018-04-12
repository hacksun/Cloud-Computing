package kafka;

import com.google.gson.Gson;
import data.Record;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import redis.clients.jedis.Jedis;

import java.util.Properties;

public class kafkaFilter {
    private static String rediskey = "filtererror";//redis key
    private static String redisHost = "192.168.31.71";
    private static int redisPort = 6379;
    private static String BeforeFilterTopicName = "before-filter";
    private static String AfterFilterTopicName = "after-filter";
    private static Record record;
    private static Gson gson = new Gson();
    private static Jedis jedis = new Jedis(redisHost, redisPort);

    public static void main(final String[] args) throws Exception {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "filtertestdata");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.31.10:9092,192.168.31.71:9092");
        config.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        KStreamBuilder builder = new KStreamBuilder();

        //filter
        KStream<String,String> source = builder.stream(BeforeFilterTopicName);
        source.filter((key, value) -> {
            record = gson.fromJson(value, Record.class);
            if (record.getLongitude() > 130 || record.getLatitude() > 40) {
                jedis.sadd(rediskey, value);
                System.out.println("error :" + value);
                return false;
            } else {
                return true;
            }
        }).to( AfterFilterTopicName);
        KafkaStreams streams = new KafkaStreams(builder, config);
        streams.start();
    }
}
