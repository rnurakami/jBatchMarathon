package com.sample.jbatchmarathon.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * マラソンレースを表すオブジェクトです。
 * 
 * @author akira_abe
 */
class Race implements Serializable{

    /**大会名*/
    private String name;
    /**開催日時*/
    private Date date;
    /**距離(km)*/
    private Double distance;
    /** 結果(00h00m00s形式)*/
    private String result;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Race{" + "name=" + name + ", date=" + date + ", distance=" + distance + ", result=" + result + '}';
    }
}
