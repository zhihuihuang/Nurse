package com.guc.bureau2.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.guc.bureau2.R;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.ArchivesDTO;
import com.guc.bureau2.widget.MyMarkerView;

import java.util.ArrayList;


public class ArchivesOrgChartFragment extends BaseFragment {
    private LineChart mChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_archives_chart);
    }

    @Override
    protected void initWidget(View view) {
        mChart = (LineChart) view.findViewById(R.id.chart);
    }

    @Override
    protected void setListeners() {
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        ArrayList<ArchivesDTO> temp = (ArrayList<ArchivesDTO>) bundle.getSerializable("data");
        int size = temp.size();
        ArrayList<Entry> e1 = new ArrayList<>();
        ArrayList<String> m = new ArrayList<>();
        ArchivesDTO dto1 = temp.get(0);
        String lineName = "";
        if (dto1 != null) {
            lineName = dto1.getItem_value().split(":")[0].trim();
        }
        for (int i = 0; i < size; i++) {
            ArchivesDTO dto = temp.get(i);
            String name = dto.getItem_name().trim();
            String valuestr = dto.getItem_value();
            String strings[] = valuestr.split(":");
            valuestr = strings[1].trim();
            int value = Integer.parseInt(valuestr);
            e1.add(new Entry(value, i));
            m.add(name);
        }
        LineDataSet d1 = new LineDataSet(e1, lineName);
        d1.setLineWidth(2.5f);
        d1.setCircleSize(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);
        ArrayList<LineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        LineData cd = new LineData(m, sets);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setSpaceBetweenLabels(10);
        YAxis yAxis = mChart.getAxisRight();
        yAxis.setEnabled(false);
        MyMarkerView mv = new MyMarkerView(mActivity, R.layout.custom_marker_view, temp);
        mChart.setMarkerView(mv);
        mChart.setDescription("");
        mChart.setData(cd);
//        for (DataSet<?> set : mChart.getData().getDataSets()) {
//            set.setDrawValues(!set.isDrawValuesEnabled());
//        }
//        mChart.invalidate();
        mChart.animateX(3000);
    }

    @Override
    public void onClick(View v) {

    }


    public static ArchivesOrgChartFragment newInstance(ArrayList<ArchivesDTO> data) {
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        ArchivesOrgChartFragment fragment = new ArchivesOrgChartFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
