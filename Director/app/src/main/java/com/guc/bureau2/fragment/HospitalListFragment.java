package com.guc.bureau2.fragment;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.guc.bureau2.R;
import com.guc.bureau2.adapter.HospitalAdapter;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.HospitalDTO;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.ToastUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HospitalListFragment extends BaseFragment {
    private ListView mListView;
    private List<HospitalDTO> data = new ArrayList<>();
    private String title = "";
    private HospitalAdapter adapter;
    private boolean isGetData = false;
    private String item_id;
    private String org_name;

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

    private void getHospital(String org_code, String item_id) {
        if (!isGetData) {
            GucNetEnginClient.getHospital(org_code, item_id, new Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject jsonObject = JSON.parseObject(response);
                    JSONObject res_obj = jsonObject.getJSONObject("GetOrganizationHisResult");
                    String errInfo = res_obj.getString("errInfo");
                    if (res_obj.getString("resultCode").equals("-1")) {
                        ToastUtils.showLong(mActivity, errInfo);
                        return;
                    }
                    JSONArray array = res_obj.getJSONArray("organizationList");
                    if (array.size() <= 0) {
                        ToastUtils.showLong(mActivity, R.string.not_data);
                        return;
                    }
                    data.addAll(JSON.parseArray(array.toJSONString(), HospitalDTO.class));
                    adapter.notifyDataSetChanged();
                    isGetData = true;
                }
            }, new ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    isGetData = false;
                }
            });
        }
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        item_id = bundle.getString("item_id");
        String org_code = bundle.getString("org_code");
        org_name = bundle.getString("org_name");
        if (item_id.equals("5")) {
            getHospital(org_code, "1");
        } else {
            getHospital(org_code, item_id);
        }
        setTitle();
        adapter = new HospitalAdapter(mActivity, data, R.layout.layout_simple_list_item_1);
        mListView.setAdapter(adapter);

    }

    private void setTitle() {
        switch (item_id) {
            case "1":
                title = org_name;
                break;
            case "5":
                title = getResources().getString(R.string.disease_rank);
                break;
            default:
                break;
        }
        controlBar(title, R.string.back, true, false);
    }

    @Override
    protected void setListeners() {
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HospitalDTO dto = data.get(position);
                switch (item_id) {
                    case "1":
                        mActivity.replace("checkorgfragment", CheckOrgFragment.newInstance(dto.getOrg_code(), dto.getOrg_name()), true);
                        break;
                    case "5":
                        mActivity.replace("diseaserankorgdetailfragment", DiseaseRankOrgDetailFragment.newInstance(dto.getHospital_code(), dto.getOrg_name()), true);
                        break;
                    default:
                        break;
                }


//                    case 0:
//                        mActivity.replace("healtharchivefragment", ArchivesDetailFragment.newInstance(dto.getOrg_code(), dto.getOrg_name()), true);
//                        break;
//                    case 1:
//                        mActivity.replace("healtharchivefragment", CheckOrgFragment.newInstance(dto.getOrg_code(), dto.getOrg_name()), true);
//                        break;
//                    case 4:
//                        mActivity.replace("drugrankfragment", DrugRankFragment.newInstance(dto.getHospital_code(), dto.getOrg_name()), true);
//                        break;
//                    case 5:
//                        mActivity.replace("DiseaseRankRegionFragment",DiseaseRankRegionFragment.newInstance(dto.getHospital_code(),dto.getOrg_name()),true);
//                        break;
//                    default:
//                        mActivity.replace("choosedatefragment", ChooseDateFragment.newInstance(dto.getHospital_code(), dto.getOrg_name(), item_id), true);
//                        break;
            }
//            }
        });
        ll_back.setOnClickListener(this);
    }

    @Override
    protected void initWidget(View view) {
        mListView = (ListView) view.findViewById(R.id.listView);
    }

    public static Fragment newInstance(String org_code, String item_id, String org_name) {
        HospitalListFragment fragment = new HospitalListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("item_id", item_id);
        bundle.putString("org_code", org_code);
        bundle.putString("org_name", org_name);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Fragment newInstance(String item_id) {
        HospitalListFragment fragment = new HospitalListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("item_id", item_id);
        fragment.setArguments(bundle);
        return fragment;
    }
}
