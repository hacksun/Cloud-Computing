package j;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapred.TableOutputFormat;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapred.JobConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;
import scala.Tuple4;


public class MeetCount {
    static String columnFamilyName = "info";
    static String inputTableName = "Record";
    static String outputTableName = "MeetCount";

    public static void main(String[] args) {
        if (HbaseUtils.createTable(outputTableName, new String[]{columnFamilyName})) {

            JavaSparkContext sc = SC.getLocalSC("MeetCount");
            Configuration inputHbaseConf = HbaseConf.getConf();
            inputHbaseConf.set(TableInputFormat.INPUT_TABLE, inputTableName);

            Configuration outputHbaseConf = HbaseConf.getConf();
            JobConf jobConf = new JobConf(outputHbaseConf);
            jobConf.setOutputFormat(TableOutputFormat.class);
            jobConf.set(TableOutputFormat.OUTPUT_TABLE, outputTableName);
            JavaPairRDD rdd1 = sc.newAPIHadoopRDD(inputHbaseConf, TableInputFormat.class, ImmutableBytesWritable.class, Result.class)
                    .mapToPair((Tuple2<ImmutableBytesWritable, Result> input) ->
                            {
                                String row = Bytes.toString(input._2.getRow());
                                String placeId = row.split("##")[0];
                                String time = row.split("##")[1];
                                String eid = row.split("##")[2];
                                String address = Bytes.toString(input._2.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("address")));
                                String latitude = Bytes.toString(input._2.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("latitude")));
                                String longitude = Bytes.toString(input._2.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("longitude")));
                                return new Tuple2<String,Tuple2<String, String>>(placeId, new Tuple2<>(eid, time));
                            }
                    );
            JavaPairRDD<String, Tuple2<Tuple2<String,String>, Tuple2<String,String>>> rdd2 = rdd1.join(rdd1);
            rdd2 = rdd2.filter((Tuple2<String,Tuple2<Tuple2<String, String>, Tuple2<String, String>>> input)->
                        {
                            Tuple2<Tuple2<String, String>, Tuple2<String, String>> placeInfo = input._2();
                            String eid1 = placeInfo._1()._1();
                            String time1 = placeInfo._1()._2();
                            String eid2 = placeInfo._2()._1();
                            String time2 = placeInfo._2()._2();
                            return (!(eid1.equals(eid2))) && (Math.abs(Integer.valueOf(time1).intValue() - Integer.valueOf(time2).intValue()) < 60);
                        }
                    );

            rdd2.mapToPair((Tuple2<String,Tuple2<Tuple2<String, String>, Tuple2<String, String>>> input) -> {
                Tuple2<Tuple2<String, String>, Tuple2<String, String>> placeInfo = input._2();
                String eid1 = placeInfo._1()._1();
                String eid2 = placeInfo._2()._1();
                return new Tuple2<Tuple2<String, String>, Integer>(new Tuple2<>(eid1,eid2),1);
            })
            .reduceByKey((x, y) -> (x + y))
            .mapToPair((Tuple2<Tuple2<String, String>, Integer> input) -> {
                Tuple2<String, String> placeInfo = input._1;
                Integer count = input._2;
                Put put = new Put(Bytes.toBytes(placeInfo._1()));
                String eid2 = placeInfo._2();
                put.addColumn(Bytes.toBytes(columnFamilyName), Bytes.toBytes(eid2), Bytes.toBytes(count));
                //put.addColumn(Bytes.toBytes(columnFamilyName), Bytes.toBytes("count"), Bytes.toBytes(count));
                return new Tuple2<>(new ImmutableBytesWritable(), put);
            })
                    .saveAsHadoopDataset(jobConf);
            sc.stop();

        }
    }
}