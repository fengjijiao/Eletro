package com.fjj.eletro.dataSet;

import com.blankj.utilcode.util.GsonUtils;
import com.fjj.eletro.App;
import com.fjj.eletro.R;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ParserJson {
    private static DataSet dataSet;

    public ParserJson() {
        InputStream is = App.getContext().getResources().openRawResource(R.raw.test);
        byte[] buffer;
        try {
            buffer = new byte[is.available()];
            is.read(buffer);
        }catch (IOException e) {
            e.printStackTrace();
            return;
        }
        String jsonStr = new String(buffer, StandardCharsets.UTF_8);
        dataSet = GsonUtils.fromJson(jsonStr, DataSet.class);
    }

    public ParserJson(String jsonStr) {
        dataSet = GsonUtils.fromJson(jsonStr, DataSet.class);
    }

    public static void updateDataSet(String jsonStr) {
        dataSet = GsonUtils.fromJson(jsonStr, DataSet.class);
    }

    public static DataSet getDataSet() {
        if (dataSet == null) new ParserJson();
        return dataSet;
    }
}
