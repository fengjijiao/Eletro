package com.fjj.eletro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.StringUtils;
import com.fjj.eletro.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.fjj.eletro.AAChartCoreLib.AAChartCreator.AAChartView;
import com.fjj.eletro.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.fjj.eletro.AAChartCoreLib.AAChartEnum.AAChartType;
import com.fjj.eletro.R;
import com.fjj.eletro.dataSet.ParserJson;

import java.util.Map;

public class ElectricityConsumptionRankingFragment extends Fragment implements FragmentI {
    private AAChartView aaChartView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_electricityconsumptionranking, container, false);
        aaChartView = view.findViewById(R.id.AAChartView_electricityconsumptionranking);
        initChart();
        return view;
    }

    public void initChart() {
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Column)
                .title(StringUtils.getString(R.string.level_title))
                .dataLabelsEnabled(false)
                .legendEnabled(false)
                .yAxisGridLineWidth(0f)
                .xAxisLabelsEnabled(false)
                .yAxisTitle(StringUtils.getString(R.string.electricity_consumption))
                .series(getNewData())
                .legendEnabled(true);
        aaChartView.aa_drawChartWithChartModel(aaChartModel);
    }

    public AASeriesElement[] getNewData() {
        Map<String, Double> electricityConsumptionMap = ParserJson.getDataSet().getRoom_work_power().getElectricityConsumptionMapTop(18);
        AASeriesElement[] aaSeriesElements = new AASeriesElement[electricityConsumptionMap.size()];
        int i = 0;
        for (String key: electricityConsumptionMap.keySet()) {
            aaSeriesElements[i] = new AASeriesElement().name(key).data(new Double[] {electricityConsumptionMap.get(key)});
            i++;
        }
        return aaSeriesElements;
    }

    public void updateData() {
        aaChartView.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(getNewData());
    }
}
