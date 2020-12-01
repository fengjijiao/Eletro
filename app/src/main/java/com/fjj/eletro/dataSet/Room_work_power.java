package com.fjj.eletro.dataSet;

import com.fjj.eletro.utils.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Room_work_power {

    private double avg;
    private ArrayList<ArrayList<Object>> data;
    public void setAvg(double avg) {
        this.avg = avg;
    }
    public double getAvg() {
        return avg;
    }

    public void setData(ArrayList<ArrayList<Object>> data) {
        this.data = data;
    }
    public ArrayList<ArrayList<Object>> getData() {
        return data;
    }

    public Map<String, Double> getPowerMap() {
        Map<String, Double> powerMap = new HashMap<>();
        for(ArrayList<Object> objList: data) {
            powerMap.put((String)objList.get(2), (Double)objList.get(1));
        }
        return powerMap;
    }

    public Map<String, Double> getPowerMapTop(int limitSize) {
        Map<String, Double> powerMap = new HashMap<>();
        Map<String, Double> topTen = MapUtils.sortByValue(getPowerMap(), limitSize);
        Set<String> powerMapSortedKey = topTen.keySet();
        for(String key: powerMapSortedKey) {
            powerMap.put(key, topTen.get(key));
        }
        return powerMap;
    }

    public Map<String, Double> getElectricityConsumptionMap() {
        Map<String, Double> powerMap = new HashMap<>();
        for(ArrayList<Object> objList: data) {
            powerMap.put((String)objList.get(2), (Double)objList.get(0));
        }
        return powerMap;
    }

    public Map<String, Double> getElectricityConsumptionMapTop(int limitSize) {
        Map<String, Double> powerMap = new HashMap<>();
        Map<String, Double> topTen = MapUtils.sortByValue(getElectricityConsumptionMap(), limitSize);
        Set<String> powerMapSortedKey = topTen.keySet();
        for(String key: powerMapSortedKey) {
            powerMap.put(key, topTen.get(key));
        }
        return powerMap;
    }
}