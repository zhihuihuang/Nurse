package com.guc.bureau2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guc.bureau2.R;
import com.guc.bureau2.adapter.ArchivesPageAdapter;
import com.guc.bureau2.base.BaseFragment;

import java.util.ArrayList;


public class ArchivesRegionTabFragment extends BaseFragment {
    private ArchivesPageAdapter adapter;
    private boolean isload = false;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private ArrayList<String> tab_text = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_archives_region_tab);
    }

    @Override
    protected void initWidget(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tableLayout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        adapter = new ArchivesPageAdapter(this.getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        //expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
    }

    @Override
    protected void setListeners() {
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                ArchivesInDTO dto = data.get(groupPosition).getList().get(childPosition);
//                ArchivesOutDTO dto_out = data.get(groupPosition);
//                String code = dto_out.getItem_code();
//                String type = dto.getItem_type();
//                String name = dto_out.getItem_name();
//                if (!code.equals("000000")) {
//                    mActivity.replace("ArchivesOrgFragment", ArchivesOrgFragment.newInstance(code, type, name), true);
//                }
//                return true;
//            }
//        });
        ll_back.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    protected void initData() {
        if (!isload) {
//            getPreView();
            isload = true;
        }
        int size = tab_text.size();
        for (int i = 0; i < size; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tab_text.get(i)));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
        adapter = new ArchivesPageAdapter(this.getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        controlBar(R.string.archive_statistics, R.string.back, true, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                mActivity.popBackStack(1);
                break;
            default:
                break;
        }
    }

    public static ArchivesRegionTabFragment newInstance() {
        return new ArchivesRegionTabFragment();
    }


//    private void getPreView() {
//        mProgressDialog.setMessage(getResources().getString(R.string.is_loading_please_wait));
//        mProgressDialog.show();
//        GucNetEnginClient.getPreview(new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                mProgressDialog.dismiss();
//                JSONObject json = JSON.parseObject(response);
//                JSONObject obj_res = json.getJSONObject("getEhrDataNewResult");
//                String resultErrInfo = obj_res.getString("resultErrInfo");
//                if (resultErrInfo == null) {
//                    ListDTO<List<ArchivesOutDTO<List<ArchivesInDTO>>>> temp = JSON.parseObject(obj_res.toJSONString(), new TypeReference<ListDTO<List<ArchivesOutDTO<List<ArchivesInDTO>>>>>() {
//                    });
//                    int size = temp.getList().size();
//                    if (size <= 0) {
//                        ToastUtils.showLong(mActivity, R.string.not_data);
//                        return;
//                    }
//                    for (int i = 0; i < size; i++) {
//                        ArchivesOutDTO out_dto = temp.getList().get(i);
//                        ArrayList<ArchivesOutDTO> code_data = new ArrayList<>();
//                        code_data.add(out_dto);
//                        fragments.add(ArchivesTabChildFragment.newInstance((ArrayList<ArchivesInDTO>) out_dto.getList(), code_data));
//                        tabLayout.addTab(tabLayout.newTab().setText(out_dto.getItem_name()));
//                        tab_text.add(out_dto.getItem_name());
//                    }
//                    adapter.notifyDataSetChanged();
//                    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//                        @Override
//                        public void onTabSelected(TabLayout.Tab tab) {
//                            viewPager.setCurrentItem(tab.getPosition());
//                        }
//
//                        @Override
//                        public void onTabUnselected(TabLayout.Tab tab) {
//
//                        }
//
//                        @Override
//                        public void onTabReselected(TabLayout.Tab tab) {
//
//                        }
//                    });
//                } else {
//                    mProgressDialog.dismiss();
//                    ToastUtils.showLong(mActivity, resultErrInfo);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                ToastUtils.showLong(mActivity, error.toString());
//                mProgressDialog.dismiss();
//            }
//        });
//    }
}
