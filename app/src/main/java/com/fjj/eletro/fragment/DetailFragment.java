package com.fjj.eletro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.StringUtils;
import com.fjj.eletro.AAChartCoreLib.AAChartCreator.AAChartView;
import com.fjj.eletro.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.fjj.eletro.AAChartCoreLib.AAChartEnum.AAChartType;
import com.fjj.eletro.AAChartCoreLib.AAOptionsModel.AAChart;
import com.fjj.eletro.AAChartCoreLib.AAOptionsModel.AAOptions;
import com.fjj.eletro.AAChartCoreLib.AAOptionsModel.AATitle;
import com.fjj.eletro.AAChartCoreLib.AAOptionsModel.AAXAxis;
import com.fjj.eletro.AAChartCoreLib.AAOptionsModel.AAYAxis;
import com.fjj.eletro.R;
import com.fjj.eletro.dataSet.ParserJson;

public class DetailFragment extends Fragment implements FragmentI {
    private AAChartView aaChartView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        aaChartView = view.findViewById(R.id.AAChartView_detail);
        initChart();
        return view;
    }

    public void initChart() {
        AAXAxis aaxAxis = new AAXAxis().visible(true).categories(ParserJson.getDataSet().getDormitoryUsedXAXisArray());
        AAYAxis aayAxis = new AAYAxis().visible(true).title(new AATitle().text(StringUtils.getString(R.string.electricity_consumption)));
        AAOptions aaOptions = new AAOptions()
                .chart(new AAChart().type(AAChartType.Area))
                .xAxis(aaxAxis)
                .title(new AATitle().text(StringUtils.getString(R.string.details_title)))
                .yAxis(aayAxis)
                .series(getNewData());
        aaChartView.aa_drawChartWithChartOptions(aaOptions);
    }

    public AASeriesElement[] getNewData() {
        int total = ParserJson.getDataSet().getDormitoryUsedTotal();
        AASeriesElement[] aaSeriesElements = new AASeriesElement[total+1];
        String[] nameArr = ParserJson.getDataSet().getNameArray();
        for (int i= 0;i< total;i++) {
            aaSeriesElements[i] = new AASeriesElement().name(nameArr[i]).data(ParserJson.getDataSet().getDormitoryUsedArray(nameArr[i]));
        }
        aaSeriesElements[total] = new AASeriesElement().name(StringUtils.getString(R.string.avg)).data(ParserJson.getDataSet().getDormitoryUsedArray("avg_list"));
        return aaSeriesElements;
    }

    public void updateData() {
        aaChartView.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(getNewData());
    }
}
