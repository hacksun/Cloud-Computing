package j;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;


public class SC {

    public static JavaSparkContext getLocalSC(String appName) {
        SparkConf conf = new SparkConf()
                .setMaster("local[2]")
                .setAppName(appName);
        return new JavaSparkContext(conf);
    }

    public static JavaSparkContext getCloudSC(String appName) {
        SparkConf conf = new SparkConf()
                .setMaster("spark://master:7077")
                .setAppName(appName)
                .setJars(new String[]{"out\\artifacts\\sparkhbase_jar\\sparkhbase.jar"});
        return new JavaSparkContext(conf);
    }
}
