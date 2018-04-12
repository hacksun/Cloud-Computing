package edu.xidian.sselab.cloudcourse.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.List;

@Data
@ToString
public class Record {

    private String eid;

    private Long time;

    private Integer placeId;

    private String address;

    private Double longitude;

    private Double latitude;

    @Override
    public String toString() {
        return "Record{" +
                "eid='" + eid + '\'' +
                ", time=" + time +
                ", placeId=" + placeId +
                ", address='" + address + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public Record mapFrom(Result result) {
        // 1.分解行键
        String[] rowKey = Bytes.toString(result.getRow()).split("##");
        setPlaceId(Integer.parseInt(rowKey[0]));
        setTime(Long.parseLong(rowKey[1]));
        setEid(rowKey[2]);
        // 2.解析所有的列信息
        List<Cell> cellList =  result.listCells();
        for (Cell cell : cellList) {
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            switch (qualifier) {
                case "address": setAddress(value); break;
                case "longitude": setLongitude(Double.parseDouble(value)); break;
                case "latitude": setLatitude(Double.parseDouble(value)); break;
            }
        }

        return this;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public Long getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

    public String getEid() {
        return eid;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }
}
