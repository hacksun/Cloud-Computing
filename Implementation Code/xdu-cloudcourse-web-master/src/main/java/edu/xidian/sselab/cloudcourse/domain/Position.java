package edu.xidian.sselab.cloudcourse.domain;

public class Position {
    private double longitude;
    private double latitude;
    private static String eid;
    private static String time1;
    private static String time2;

    public static String getEid() {
        return eid;
    }

    public static void setEid(String eid) {
        Position.eid = eid;
        System.out.println(eid);
    }

    public static String getTime1() {
        return time1;
    }

    public static void setTime1(String time) {
        time1 =  time;
        System.out.println(time1);
    }

    public static String getTime2() {
        return time2;
    }

    public static void setTime2(String time) {
        time2 = time;
        System.out.println(time2);
    }

    public Position() {
    }

    @Override
    public String toString() {
        return "Position{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public Position(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
