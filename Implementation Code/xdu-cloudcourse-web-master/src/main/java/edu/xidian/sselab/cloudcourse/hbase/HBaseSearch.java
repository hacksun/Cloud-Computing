package edu.xidian.sselab.cloudcourse.hbase;

import edu.xidian.sselab.cloudcourse.domain.Position;
import edu.xidian.sselab.cloudcourse.domain.Record;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

/**
 * 类描述：
 *      次类用于做HBase查找工作
 */
public class HBaseSearch {

    private RecordMap recordMap = new RecordMap();
    private final String RECORD_TABLENAME = "Record";     //给出的用例中只有Record一个表，这里直接写死

    /**
     * @description 通过eId，扫描全表，获得表中该车辆所有信息
     * @param eId
     * @return
     */

    public List<Position> searchByEID(int eId, int time1, int time2){
        List<Record> resultRecords = new ArrayList<>();
        List<Position> resultPositions = new ArrayList<>();
        Table table = HBaseConf.getTableByName(RECORD_TABLENAME);
        Scan scan = new Scan();
        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL , new SubstringComparator("##"+eId));
        scan.setFilter(rowFilter);
        ResultScanner rs;
        try {
            rs = table.getScanner(scan);
            for(Result result : rs){
                Record record = recordMap.resultMapToRecord(result);
                if(record.getTime()>=time1&&record.getTime()<=time2)
                    resultRecords.add(record);
            }
            Collections.sort(resultRecords,new SortByTime());
            double longitude;
            double latitude;
            for(Record record:resultRecords){
                longitude=record.getLongitude();
                latitude=record.getLatitude();
                Position position=new Position(longitude,latitude);
                resultPositions.add(position);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("根据EID查询出错，返回一个空的列表");
            return resultPositions;
        }
        return resultPositions;
    }
    class SortByTime implements Comparator<Record>{
        @Override
        public int compare(Record o1, Record o2) {
            if(o1.getTime()==o2.getTime())
                return 0;
            if(o1.getTime()>o2.getTime())
                return 1;
            else return -1;
        }
    }
}
