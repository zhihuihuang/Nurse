package com.guc.bureau2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.guc.bureau2.R;
import com.guc.bureau2.adapter.ArchivesAdapter;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.ArchivesDTO;

import java.util.ArrayList;


public class ArchivesOrgTableFragment extends BaseFragment {
    private ListView recyclerView;
    private ArrayList<ArchivesDTO> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_archives_tab);
    }

    @Override
    protected void initWidget(View view) {
        recyclerView = (ListView) view.findViewById(R.id.recyclerView);
    }

    @Override
    protected void setListeners() {
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArchivesDTO dto = data.get(position);
                String code = dto.getItem_code();
                String name = dto.getItem_name();
                mActivity.replace("healtharchivefragment", ArchivesDetailFragment.newInstance(code, name), true);
            }
        });
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        data.addAll((ArrayList<ArchivesDTO>) bundle.getSerializable("data"));
        ArchivesAdapter adapter = new ArchivesAdapter(data, R.layout.layout_item_archives_org, mActivity);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }

    public static ArchivesOrgTableFragment newInstance(ArrayList<ArchivesDTO> data) {
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        ArchivesOrgTableFragment fragment = new ArchivesOrgTableFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
