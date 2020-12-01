package com.fjj.eletro.dataSet;

public class Power_detail {

    private double avg;
    private String end_time;
    private String image_url;
    private int level;
    private String name;
    private double power;
    private String start_time;
    public void setAvg(double avg) {
        this.avg = avg;
    }
    public double getAvg() {
        return avg;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
    public String getEnd_time() {
        return end_time;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
    public String getImage_url() {
        return image_url;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setPower(double power) {
        this.power = power;
    }
    public double getPower() {
        return power;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
    public String getStart_time() {
        return start_time;
    }

}