package j;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;


public class HbaseUtils {

    public static boolean createTable(String tableName, String[] cols) {
        try {
            Connection connection = ConnectionFactory.createConnection(HbaseConf.getConf());
            Admin admin = connection.getAdmin();
            TableName table = TableName.valueOf(tableName);
            if (admin.tableExists(table)) {
                admin.disableTable(table);
                admin.deleteTable(table);
                System.out.println(tableName + " is exists! recreate");
            }
            HTableDescriptor hTableDescriptor = new HTableDescriptor(table);
            for (String col : cols) {
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);
                hTableDescriptor.addFamily(hColumnDescriptor);
            }
            admin.createTable(hTableDescriptor);
            admin.close();
            connection.close();
            return true;

        } catch (IOException e) {
            System.out.println("create " + tableName + " failed: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteTable(String tableName) {
        try {
            Connection connection = ConnectionFactory.createConnection(HbaseConf.getConf());
            Admin admin = connection.getAdmin();
            TableName table = TableName.valueOf(tableName);

            if (admin.tableExists(table)) {
                admin.disableTable(table);
                admin.deleteTable(table);
            }
            admin.close();
            connection.close();
            return true;
        } catch (IOException e) {
            System.out.println("delete " + tableName + " failed: " + e.getMessage());
            return false;
        }
    }
}
