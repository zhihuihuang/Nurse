package com.guc.bureau2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.guc.bureau2.R;
import com.guc.bureau2.adapter.InComeRegionAdapter;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.InComeRegionDTO;

import java.util.ArrayList;
import java.util.List;

public class BusinessOrgTableFragment extends BaseFragment {
    private ListView listView;
    private List<InComeRegionDTO> data = new ArrayList<>();
    private InComeRegionAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_business_org_table);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        ArrayList<InComeRegionDTO> temp = (ArrayList<InComeRegionDTO>) bundle.getSerializable("data");
        data.addAll(temp);
        adapter = new InComeRegionAdapter(R.layout.layout_item_income_org, mActivity, data);
        listView.setAdapter(adapter);

    }

    @Override
    protected void setListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InComeRegionDTO dto = data.get(position);
                mActivity.replace("BusinessDetailFragment", BusinessDetailFragment.newInstance(dto.getItem_code(), dto.getHospital_name()), true);
            }
        });
    }

    @Override
    protected void initWidget(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
    }

    public static Fragment newInstance(ArrayList<InComeRegionDTO> data) {
        BusinessOrgTableFragment comeInfoFragmen = new BusinessOrgTableFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        comeInfoFragmen.setArguments(bundle);
        return comeInfoFragmen;
    }
}
