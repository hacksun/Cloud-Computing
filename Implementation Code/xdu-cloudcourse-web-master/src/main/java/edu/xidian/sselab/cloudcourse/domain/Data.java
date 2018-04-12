package edu.xidian.sselab.cloudcourse.domain;

import org.apache.commons.math3.linear.EigenDecomposition;

import java.util.Random;

public class Data
{
    private long Eid;
    private int time;

    public long getEid() {
        return Eid;
    }

    public int getTime() {
        return time;
    }

    public Data(long Eid, int time){
        this.Eid = Eid;
        this.time= time;
    }

    public static Data getData(){
        Random random = new Random();
        Data temp = new Data(random.nextInt(50),random.nextInt(52));
        return temp;
    }
    public static Data myData[] = new Data[]{new Data(33041100038780l,1),new Data(33040210009839l,2),new Data(33041100047968l,2),new Data(33041100027281l,1),new Data(33041100048437l,1),new Data(33041100019325l,1),new Data(33041100009049l,2),new Data(33041100018783l,1),};
}
