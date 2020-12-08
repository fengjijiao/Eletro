package com.fjj.eletro.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.StringUtils;
import com.fjj.eletro.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.fjj.eletro.AAChartCoreLib.AAChartCreator.AAChartView;
import com.fjj.eletro.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.fjj.eletro.AAChartCoreLib.AAChartEnum.AAChartType;
import com.fjj.eletro.AAChartCoreLib.AAOptionsModel.AADataLabels;
import com.fjj.eletro.AAChartCoreLib.AAOptionsModel.AAStyle;
import com.fjj.eletro.R;
import com.fjj.eletro.activity.MainActivity;
import com.fjj.eletro.dataSet.Building_work;
import com.fjj.eletro.dataSet.ParserJson;

import java.util.ArrayList;
import java.util.List;

public class BuildingStatisticsFragment extends Fragment implements FragmentI {
    private AAChartView aaChartView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buildingstatistics, container, false);
        aaChartView = view.findViewById(R.id.AAChartView_buildingstatistics);
        initChart();
        return view;
    }

    public void initChart() {
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Pie)
                .title(StringUtils.getString(R.string.statistics_title))
                .dataLabelsEnabled(true)
                .touchEventEnabled(true)
                .series(getNewData());
        aaChartView.aa_drawChartWithChartModel(aaChartModel);
    }

    public AASeriesElement[] getNewData() {
        List<Building_work> buildingWorkList = ParserJson.getDataSet().getBuilding_work();
        AASeriesElement[] aaSeriesElements = new AASeriesElement[1];
        List<List<Object>> buildingWorkDataList = new ArrayList<>();
        for (Building_work buildingWork: buildingWorkList) {
            List<Object> buildingWorkData = new ArrayList<>();
            buildingWorkData.add(buildingWork.getName());
            buildingWorkData.add(buildingWork.getValue());
            buildingWorkDataList.add(buildingWorkData);
        }
        aaSeriesElements[0] = new AASeriesElement().name("Now").data(buildingWorkDataList.toArray()).dataLabels(
                new AADataLabels()
                        .enabled(true)
                        .style(new AAStyle()
                                .color("#000000")
                                .fontSize(10f)
                        )
        );
        return aaSeriesElements;
    }

    public void updateData() {
        aaChartView.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(getNewData());
    }
}
