package edu.xidian.sselab.cloudcourse.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import java.util.*;

import java.util.List;

@Data
@ToString
public class MeetRecord {

    private static  String eid1;
    private String eid2;
    private String count;
    private static List<MeetRecord> meetList;


    public MeetRecord(String eid2, String count) {
        this.eid2 = eid2;
        this.count = count;
    }

    public static List<MeetRecord> mapFrom(Result result) {
        meetList = new ArrayList<>();
        if(!meetList.isEmpty()){
            meetList.clear();
        }
        // 1.分解行键
        String rowKey = Bytes.toString(result.getRow());
        setEid1(rowKey);
        // 2.解析所有的列信息
        List<Cell> cellList =  result.listCells();
        for (Cell cell : cellList) {
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            int value = Bytes.toInt(CellUtil.cloneValue(cell));
            String s = Integer.toString(value);
            System.out.println(qualifier);
            System.out.println(value);
            MeetRecord meetRecord = new MeetRecord(qualifier, s);
            meetList.add(meetRecord);
        }
        return meetList;
    }

    public MeetRecord() {
    }

    public static  void setEid(String eid){eid1 = eid;}
    public static String getEid() {
        return eid1;
    }

    public static  void setEid1(String eid){eid1 = eid;}
    public static String getEid1() {
        return eid1;
    }

    public String getEid2() {
        return eid2;
    }

    public void setEid2(String eid2) {
        this.eid2 = eid2;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
