package com.fjj.eletro.dataSet;
import com.blankj.utilcode.util.ArrayUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class DataSet {

    private List<Building_work> building_work;
    private HashMap<String, List<Object>> detail;
    private Room_work_power room_work_power;
    private Power power;

    public void setPower(Power power) {
        this.power = power;
    }

    public Power getPower() {
        return power;
    }

    public void setBuilding_work(List<Building_work> building_work) {
        this.building_work = building_work;
    }

    public List<Building_work> getBuilding_work() {
        return building_work;
    }

    public void setDetail(HashMap<String, List<Object>> detail) {
        this.detail = detail;
    }

    public HashMap<String, List<Object>> getDetail() {
        return detail;
    }

    public Room_work_power getRoom_work_power() {
        return room_work_power;
    }

    public void setRoom_work_power(Room_work_power room_work_power) {
        this.room_work_power = room_work_power;
    }

    public List<String> getNameList() {
        List<String> resList = new ArrayList<>();
        List<Object> nameObjList = detail.get("name_list");
        if(nameObjList == null) return null;
        for (Object obj: nameObjList) {
            resList.add((String)obj);
        }
        return resList;
    }

    public String[] getNameArray() {
        List<String> nameList = getNameList();
        if(nameList == null) return null;
        return nameList.toArray(new String[nameList.size()]);
    }

    public int getDormitoryUsedTotal() {
        return detail.size() - 3;
    }

    public Map<String, List<Object>> getDormitoryUsedMap() {
        Map<String, List<Object>> usedMap = new HashMap<>();
        String[] reservedKey = new String[]{"avg_list", "hour_list", "name_list"};
        Set<String> allInfo = detail.keySet();
        for (String key:allInfo) {
            if (ArrayUtils.contains(reservedKey, key)) continue;
            usedMap.put(key, Objects.requireNonNull(detail.get(key)));
        }
        return usedMap;
    }

    public Object[] getDormitoryUsedArray(String name) {
        return Objects.requireNonNull(detail.get(name)).toArray();
    }

    public String[] getDormitoryUsedXAXisArray() {
        return detail.get("hour_list").toArray(new String[detail.get("hour_list").size()]);
    }
}