package j;

import org.apache.hadoop.conf.Configuration;

public class HbaseConf {
    // 配置hbase的主节点ip
    private final static String master = "192.168.31.254";
    // 配置hbase的zookeeper节点ip
    private final static String zookeeper = "192.168.31.254,192.168.31.251,192.168.31.160";
    // 配置hbase的zookeeper连接端口，默认值为2181
    private final static String port = "2181";

    public static Configuration getConf() {
        Configuration hbaseConf = new Configuration();
        hbaseConf.set("hbase.master", master);
        hbaseConf.set("hbase.zookeeper.quorum", zookeeper);
        hbaseConf.set("hbase.zookeeper.property.clientPort", port);
        return hbaseConf;
    }

}
