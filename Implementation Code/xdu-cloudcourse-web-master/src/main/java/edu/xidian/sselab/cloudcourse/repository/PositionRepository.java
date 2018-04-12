package edu.xidian.sselab.cloudcourse.repository;

import edu.xidian.sselab.cloudcourse.domain.Position;
import edu.xidian.sselab.cloudcourse.domain.Record;
import edu.xidian.sselab.cloudcourse.hbase.HBaseConf;
import edu.xidian.sselab.cloudcourse.hbase.HbaseClient;
import edu.xidian.sselab.cloudcourse.hbase.RecordMap;
import lombok.Lombok;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class PositionRepository {

    private static final String TABLE_NAME = "Record";

    private final HbaseClient hbaseClient;

    @Autowired
    public PositionRepository(HbaseClient hbaseClient) {
        this.hbaseClient = hbaseClient;
    }

    public List<Position> findAllByPosition(Position position2){
        long time1 = 0;
        long time2 = 0;
        if(position2.getTime1() != null){
            time1 = Long.parseLong(position2.getTime1());
        }
        if(position2.getTime2() != null){
            time2 = Long.parseLong(position2.getTime2());
        }
        String eId = position2.getEid();
        System.out.println(time1+" "+time2+" "+eId );
        List<Position> resultPositions = new ArrayList<>();

        List<Record> resultRecords = new ArrayList<>();
        hbaseClient.getConnection();
        Table table = hbaseClient.getTableByName(TABLE_NAME);

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);


        if (StringUtils.isNotEmpty(eId)) {
            RowFilter rowFilter = new RowFilter(
                    CompareFilter.CompareOp.EQUAL,
                    new RegexStringComparator("##" + eId + "$"));
            filterList.addFilter(rowFilter);
        }

        Scan scan = new Scan();
        if (filterList.getFilters().size() != 0) {
            scan.setFilter(filterList);
        }
        ResultScanner rs;
        try {
            if (table != null) {
                rs = table.getScanner(scan);
                for (Result result : rs) {
                    Record tempRecord = new Record().mapFrom(result);
                   //if(tempRecord.getTime() >= time1 && tempRecord.getTime() <= time2)
                            resultRecords.add(tempRecord);
                            System.out.println(tempRecord);
                }
               Collections.sort(resultRecords, new SortByTime());
                double longitude;
                double latitude;
                for (Record record : resultRecords) {
                    longitude = record.getLongitude();
                    latitude = record.getLatitude();
                    Position position = new Position(longitude, latitude);
                    resultPositions.add(position);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("根据EID查询出错，返回一个空的列表");
            return resultPositions;
        }
        return resultPositions;
    }
    class SortByTime implements Comparator<Record> {
        @Override
        public int compare(Record o1, Record o2) {
            if(o1.getTime() > o2.getTime())
                return 1;
            else return -1;
        }
    }

}
