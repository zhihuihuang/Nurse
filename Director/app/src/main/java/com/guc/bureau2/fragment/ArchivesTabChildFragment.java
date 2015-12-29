package com.guc.bureau2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.guc.bureau2.R;
import com.guc.bureau2.adapter.ArchivesTabChildAdapter;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.ArchivesInDTO;
import com.guc.bureau2.domain.ArchivesOutDTO;

import java.util.ArrayList;
import java.util.List;


public class ArchivesTabChildFragment extends BaseFragment {
    private ListView listView;
    private ArrayList<ArchivesInDTO> data;
    private ArrayList<ArchivesOutDTO> code_data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_archives_region_tab_child);
    }

    @Override
    protected void initData() {
        data = (ArrayList<ArchivesInDTO>) getArguments().getSerializable("data");
        code_data = (ArrayList<ArchivesOutDTO>) getArguments().getSerializable("code_data");
        ArchivesTabChildAdapter adapter = new ArchivesTabChildAdapter(data);
        listView.setAdapter(adapter);
    }

    @Override
    protected void setListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArchivesInDTO dto = data.get(position);
                ArchivesOutDTO dto_out = code_data.get(0);
                String code = dto_out.getItem_code();
                String type = dto.getItem_type();
                String name = dto_out.getItem_name();
                mActivity.replace("ArchivesOrgFragment", ArchivesOrgFragment.newInstance(code, type, name), true);
            }
        });
    }

    @Override
    protected void initWidget(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
    }

    @Override
    public void onClick(View v) {

    }

    public static ArchivesTabChildFragment newInstance(ArrayList<ArchivesInDTO> data, ArrayList<ArchivesOutDTO> code_data) {
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        args.putSerializable("code_data", code_data);
        ArchivesTabChildFragment fragment = new ArchivesTabChildFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
