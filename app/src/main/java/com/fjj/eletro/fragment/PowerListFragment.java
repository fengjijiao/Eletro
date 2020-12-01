package com.fjj.eletro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fjj.eletro.R;
import com.fjj.eletro.custom.PowerListAdapter;
import com.fjj.eletro.dataSet.ParserJson;
import com.fjj.eletro.dataSet.Power_detail;

import java.util.ArrayList;
import java.util.List;

public class PowerListFragment extends Fragment implements FragmentI {
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView_powerlist;
    private PowerListAdapter powerListAdapter;
    private List<PowerListAdapter.DataItem> dataItemList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_powerlist, container, false);
        initView(view);
        initRecycler();
        return view;
    }

    private void initRecycler() {
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView_powerlist.setLayoutManager(layoutManager);
        dataItemList = getNewData();
        powerListAdapter = new PowerListAdapter(dataItemList);
        recyclerView_powerlist.setAdapter(powerListAdapter);
    }

    private void initView(View view) {
        recyclerView_powerlist = view.findViewById(R.id.recyclerView_powerlist);
    }

    private List<PowerListAdapter.DataItem> getNewData() {
        List<Power_detail> powerDetailList = ParserJson.getDataSet().getPower().getDetail();
        List<PowerListAdapter.DataItem> dataItemList = new ArrayList<>();
        for (Power_detail powerDetail: powerDetailList) {
            dataItemList.add(new PowerListAdapter.DataItem(powerDetail.getName(), powerDetail.getPower(), powerDetail.getStart_time(), powerDetail.getEnd_time(), powerDetail.getLevel()));
        }
        return dataItemList;
    }

    public void updateData() {
        dataItemList.clear();
        dataItemList.addAll(getNewData());
        powerListAdapter.updateDataSet(dataItemList);
        powerListAdapter.notifyDataSetChanged();
    }
}
