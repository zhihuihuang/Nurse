package com.guc.bureau2.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.guc.bureau2.R;
import com.guc.bureau2.adapter.ArchivesDetailAdapter;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.ArchivesInDTO;
import com.guc.bureau2.domain.ArchivesStatisticsDTO;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.ToastUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ArchivesDetailFragment extends BaseFragment {
    private ListView mListView;
    private ArchivesDetailAdapter adapter;
    private ArrayList<Map<String, String>> datas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_list);
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

    @Override
    protected void initData() {
        adapter = new ArchivesDetailAdapter(mActivity, datas);
        mListView.setAdapter(adapter);
        Bundle bundle = getArguments();
        String org_code = bundle.getString("org_code");
        String org_name = bundle.getString("org_name");
        controlBar(org_name, R.string.back, true, false);
        getData(org_code);

    }

    private void getData(String org_code) {
        mProgressDialog.setMessage(getResources().getString(R.string.is_loading_please_wait));
        mProgressDialog.show();
        GucNetEnginClient.getArchives(org_code, new Listener<String>() {

            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject res_obj = jsonObject.getJSONObject("GetStatReportResult");
                String resultErrInfo = res_obj.getString("resultErrInfo");
                if (resultErrInfo != null) {
                    ToastUtils.showLong(mActivity, resultErrInfo);
                    return;
                }
                JSONArray array = res_obj.getJSONArray("statItemList");
                if (array.size() <= 0) {
                    ToastUtils.showLong(mActivity, R.string.not_data);
                } else {
                    List<ArchivesStatisticsDTO> listDto = JSON.parseArray(array.toJSONString(), ArchivesStatisticsDTO.class);
                    analytical(listDto);
                }
            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
            }
        });
    }

    @Override
    protected void setListeners() {
        ll_back.setOnClickListener(this);
    }

    @Override
    protected void initWidget(View view) {
        mListView = (ListView) view.findViewById(R.id.listView);
    }

    public static Fragment newInstance(String org_code, String org_name) {
        ArchivesDetailFragment archivesFragment = new ArchivesDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("org_code", org_code);
        bundle.putString("org_name", org_name);
        archivesFragment.setArguments(bundle);
        return archivesFragment;
    }

    public static ArchivesDetailFragment newInstance(ArrayList<ArchivesInDTO> data) {
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        ArchivesDetailFragment fragment = new ArchivesDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void analytical(List<ArchivesStatisticsDTO> listDto) {
        if (listDto != null && !listDto.isEmpty()) {
            Map<String, String> map1 = new HashMap<>();
            Map<String, String> map2 = new HashMap<>();
            Map<String, String> map3 = new HashMap<>();
            Map<String, String> map4 = new HashMap<>();
            Map<String, String> map5 = new HashMap<>();
            Map<String, String> map6 = new HashMap<>();
            Map<String, String> map7 = new HashMap<>();
            for (int i = 0; i < listDto.size(); i++) {
                if (listDto.get(i).getItem_type().equals("总人口情况")) {
                    map1.put("item_type", listDto.get(i).getItem_type());
                    if (listDto.get(i).getItem_name().equals("普查常住人口数")) {
                        map1.put("item_name1", listDto.get(i).getItem_name());
                        map1.put("item_result1", listDto.get(i).getItem_result());
                    } else if (listDto.get(i).getItem_name().equals("建档数")) {
                        map1.put("item_name2", listDto.get(i).getItem_name());
                        map1.put("item_result2", listDto.get(i).getItem_result());
                    } else if (listDto.get(i).getItem_name().equals("建档率")) {
                        map1.put("item_name3", listDto.get(i).getItem_name());
                        map1.put("item_result3", listDto.get(i).getItem_result());
                    }
                } else if (listDto.get(i).getItem_type().equals("儿童情况(0～6岁)")) {
                    map2.put("item_type", listDto.get(i).getItem_type());
                    if (listDto.get(i).getItem_name().equals("普查儿童数")) {
                        map2.put("item_name1", listDto.get(i).getItem_name());
                        map2.put("item_result1", listDto.get(i).getItem_result());
                    } else if (listDto.get(i).getItem_name().equals("建档数")) {
                        map2.put("item_name2", listDto.get(i).getItem_name());
                        map2.put("item_result2", listDto.get(i).getItem_result());
                    } else if (listDto.get(i).getItem_name().equals("建档率")) {
                        map2.put("item_name3", listDto.get(i).getItem_name());
                        map2.put("item_result3", listDto.get(i).getItem_result());
                    }
                } else if (listDto.get(i).getItem_type().equals("孕产妇情况")) {
                    map3.put("item_type", listDto.get(i).getItem_type());
                    if (listDto.get(i).getItem_name().equals("普查孕产妇数")) {
                        map3.put("item_name1", listDto.get(i).getItem_name());
                        map3.put("item_result1", listDto.get(i).getItem_result());
                    } else if (listDto.get(i).getItem_name().equals("建档数")) {
                        map3.put("item_name2", listDto.get(i).getItem_name());
                        map3.put("item_result2", listDto.get(i).getItem_result());
                    } else if (listDto.get(i).getItem_name().equals("建档率")) {
                        map3.put("item_name3", listDto.get(i).getItem_name());
                        map3.put("item_result3", listDto.get(i).getItem_result());
                    }
                } else if (listDto.get(i).getItem_type().equals("老年人情况")) {
                    map4.put("item_type", listDto.get(i).getItem_type());
                    if (listDto.get(i).getItem_name().equals("普查老人数")) {
                        map4.put("item_name1", listDto.get(i).getItem_name());
                        map4.put("item_result1", listDto.get(i).getItem_result());
                    } else if (listDto.get(i).getItem_name().equals("建档数")) {
                        map4.put("item_name2", listDto.get(i).getItem_name());
                        map4.put("item_result2", listDto.get(i).getItem_result());
                    } else if (listDto.get(i).getItem_name().equals("建档率")) {
                        map4.put("item_name3", listDto.get(i).getItem_name());
                        map4.put("item_result3", listDto.get(i).getItem_result());
                    }
                } else if (listDto.get(i).getItem_type().equals("高血压")) {
                    map5.put("item_type", listDto.get(i).getItem_type());
                    if (listDto.get(i).getItem_name().equals("规范管理人数")) {
                        map5.put("item_name1", listDto.get(i).getItem_name());
                        map5.put("item_result1", listDto.get(i).getItem_result());
                    } else if (listDto.get(i).getItem_name().equals("建档数")) {
                        map5.put("item_name2", listDto.get(i).getItem_name());
                        map5.put("item_result2", listDto.get(i).getItem_result());
                    } else if (listDto.get(i).getItem_name().equals("所占比率")) {
                        map5.put("item_name3", listDto.get(i).getItem_name());
                        map5.put("item_result3", listDto.get(i).getItem_result());
                    }
                } else if (listDto.get(i).getItem_type().equals("２型糖尿病")) {
                    map6.put("item_type", listDto.get(i).getItem_type());
                    if (listDto.get(i).getItem_name().equals("规范管理人数")) {
                        map6.put("item_name1", listDto.get(i).getItem_name());
                        map6.put("item_result1", listDto.get(i).getItem_result());
                    } else if (listDto.get(i).getItem_name().equals("建档数")) {
                        map6.put("item_name2", listDto.get(i).getItem_name());
                        map6.put("item_result2", listDto.get(i).getItem_result());
                    } else if (listDto.get(i).getItem_name().equals("所占比率")) {
                        map6.put("item_name3", listDto.get(i).getItem_name());
                        map6.put("item_result3", listDto.get(i).getItem_result());
                    }
                } else if (listDto.get(i).getItem_type().equals("重性精神疾病")) {
                    map7.put("item_type", listDto.get(i).getItem_type());
                    if (listDto.get(i).getItem_name().equals("建档数")) {
                        map7.put("item_name2", listDto.get(i).getItem_name());
                        map7.put("item_result2", listDto.get(i).getItem_result());
                    } else if (listDto.get(i).getItem_name().equals("所占比率")) {
                        map7.put("item_name3", listDto.get(i).getItem_name());
                        map7.put("item_result3", listDto.get(i).getItem_result());
                    }
                }
            }
            datas.add(map1);
            datas.add(map2);
            datas.add(map3);
            datas.add(map4);
            datas.add(map5);
            datas.add(map6);
            datas.add(map7);
            adapter.notifyDataSetChanged();
        }
    }
}
