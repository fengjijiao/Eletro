package com.fjj.eletro.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.fjj.eletro.R;

import java.util.List;

public class PowerListAdapter extends RecyclerView.Adapter<PowerListAdapter.ViewHolder> {
    public static class DataItem {
        public String dormitoryNo, starttime, endtime;
        public double currentpower;
        public int type;

        public DataItem(String dormitoryNo, double currentpower, String starttime, String endtime, int type) {
            this.dormitoryNo = dormitoryNo;
            this.currentpower = currentpower;
            this.starttime = starttime;
            this.endtime = endtime;
            this.type = type;
        }

        public String getDormitoryNo() {
            return dormitoryNo;
        }

        public void setDormitoryNo(String dormitoryNo) {
            this.dormitoryNo = dormitoryNo;
        }

        public double getCurrentpower() {
            return currentpower;
        }

        public void setCurrentpower(double currentpower) {
            this.currentpower = currentpower;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    private List<DataItem> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_dormitoryNo, textView_currentpower, textView_starttime, textView_endtime;
        private ImageView imageView_type;

        public ViewHolder(View view) {
            super(view);
            textView_dormitoryNo = view.findViewById(R.id.textView_dormitoryNo);
            textView_currentpower = view.findViewById(R.id.textView_currentpower);
            textView_starttime = view.findViewById(R.id.textView_starttime);
            textView_endtime = view.findViewById(R.id.textView_endtime);
            imageView_type = view.findViewById(R.id.imageView_type);
        }

        public TextView getTextView_dormitoryNo() {
            return textView_dormitoryNo;
        }

        public TextView getTextView_currentpower() {
            return textView_currentpower;
        }

        public TextView getTextView_starttime() {
            return textView_starttime;
        }

        public TextView getTextView_endtime() {
            return textView_endtime;
        }

        public ImageView getImageView_type() {
            return imageView_type;
        }
    }

    public PowerListAdapter(List<DataItem> dataSet) {
        localDataSet = dataSet;
    }

    public void updateDataSet(List<DataItem> dataSet) {
        localDataSet = dataSet;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_powerlistitem, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView_dormitoryNo().setText(localDataSet.get(position).getDormitoryNo());
        viewHolder.getTextView_currentpower().setText(StringUtils.format("%s: %.2f kwh", StringUtils.getString(R.string.current_power), localDataSet.get(position).getCurrentpower()));
        viewHolder.getTextView_starttime().setText(StringUtils.format("%s: %s", StringUtils.getString(R.string.start_time), localDataSet.get(position).getStarttime()));
        viewHolder.getTextView_endtime().setText(StringUtils.format("%s: %s", StringUtils.getString(R.string.end_time), localDataSet.get(position).getEndtime()));
        //viewHolder.getImageView_type().setImageResource(localDataSet.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
