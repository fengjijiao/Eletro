package com.fjj.eletro.dataSet;

import java.util.List;

public class Power {

    private List<Power_detail> detail;
    private List<String> name;
    public void setDetail(List<Power_detail> detail) {
        this.detail = detail;
    }
    public List<Power_detail> getDetail() {
        return detail;
    }

    public void setName(List<String> name) {
        this.name = name;
    }
    public List<String> getName() {
        return name;
    }

}