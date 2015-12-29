package com.guc.bureau2.net;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.guc.bureau2.application.GucApplication;

public class GucNetEnginClient {
    public static String DBID = "";
    public static String ORG_CODE = "";

    public static Request<String> getDbid(String number, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.GETDBID + Constant.APP_ID+ "/" + number;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getBureauInfo(String account, String password, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.GETBUREAUINFO;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dblist_id", DBID);
        jsonObject.put("phoneNumber", account);
        jsonObject.put("PWD", password);
        return post(url, jsonObject.toJSONString(), listener, errorListener);
    }

    public static Request<String> getOrganization(Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.GETREGISTERORGANIZATION + Constant.APP_ID;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> register(String org_id, String account, String password, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.REGISTER;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dblist_id", org_id);
        jsonObject.put("phoneNumber", account);
        jsonObject.put("PWD", password);
        return post(url, jsonObject.toJSONString(), listener, errorListener);
    }

    public static Request<String> getHospital(String org_code, String item_id, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.GETORGANIZATIONFILTER + DBID + "/" + org_code + "/" + item_id;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getArchives(String org_code, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.GETSTATREPORT + DBID + "/" + org_code;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getCheckRegion(Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.CHECKREGION + DBID + "/" + GucApplication.getInstance().getUserName();
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getCheckOrg(String org_code, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.CHECKORG + DBID + "/" + org_code;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getDrugRankRegion(Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.DRUGRANKREGION + DBID + "/" + GucApplication.getInstance().getUserName();
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getDrugRankRegionDetail(String code, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.DRUGRANKREGIONDETAIL + DBID + "/" + code;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getDrugOrg(String area_code, String item_code, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.DRUGRANKORG + DBID + "/" + area_code + "/" + item_code;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getInComeInfo(String hospital_code, String begin_time, String end_time, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.GETINCOME + hospital_code + "/" + begin_time + "/" + end_time;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getVisitCount(String hospital_code, String begin_time, String end_time, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.GETVISITCOUNT + hospital_code + "/" + begin_time + "/" + end_time;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getDrugTop(String hospital_code, String begin_time, String end_time, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.GETDRUGTOP + hospital_code + "/" + begin_time + "/" + end_time;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getDiseaseRankDetail(String code, String begin_time, String end_time, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.DISEASERANKREGION + DBID + "/" + code + "/" + begin_time + "/" + end_time;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getDisease(String hospital_code, String begin_time, String end_time, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.GETDIAGTOP + hospital_code + "/" + begin_time + "/" + end_time;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getVersion(Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.GETVERSION;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getAppUrl(String version, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.APP_URL + version;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getPreview(String code, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.PREVIEW + DBID + "/" + code;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getPreviewDetal(String code, String type, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.PREVIEWDETAIL + DBID + "/" + code + "/" + type;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getInComeRegion(String code, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.BUSINESSREGIONDETAIL + DBID + "/" + code;
        return get(url, null, listener, errorListener);
    }

    //
    public static Request<String> getInComeOrg(String code, String type, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.GETINCOMEREORG + DBID + "/" + code + "/" + type;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getHospitaliz(String start_data, String end_data, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.GETHOSPITALIZSTATISTICS + DBID + "/" + GucApplication.getInstance().getUserName() + "/" + start_data + "/" + end_data;
        return get(url, null, listener, errorListener);
    }

    public static Request<String> getHospitalizTwo(String code, String start_data, String end_data, Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.GETHOSPITALIZSTATISTICSTWO + DBID + "/" + code + "/" + start_data + "/" + end_data;
        return get(url, null, listener, errorListener);
    }

//    public static Request<String> getDiseaseRank(String start_data, String end_data, Listener<String> listener, ErrorListener errorListener) {
//        String url = Constant.URL_ROOT + Constant.GETDISEASERANK + DBID + "/" + GucApplication.getInstance().getUserName() + "/" + start_data + "/" + end_data;
//        return get(url, null, listener, errorListener);
//    }

//    public static Request<String> getDiseaseStatistics(String start_data, String end_data, Listener<String> listener, ErrorListener errorListener) {
//        String url = Constant.URL_ROOT + Constant.GETDISEASESTATISTICS + DBID + "/" + GucApplication.getInstance().getUserName() + "/" + start_data + "/" + end_data;
//        return get(url, null, listener, errorListener);
//    }

    public static Request<String> loginLog(Listener<String> listener, ErrorListener errorListener) {
        String url = Constant.URL_ROOT + Constant.LOGINLOG + GucApplication.getInstance().getUserName();
        return get(url, null, listener, errorListener);
    }

    public static Request<String> get(String url, String requestBody, Listener<String> listener, ErrorListener errorListener) {
        StringRequest request = new StringRequest(Method.GET, url, requestBody, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return GucApplication.mRequestQueue.add(request);
    }

    public static Request<String> post(String url, String requestBody, Listener<String> listener, ErrorListener errorListener) {
        StringRequest request = new StringRequest(Method.POST, url, requestBody, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return GucApplication.mRequestQueue.add(request);
    }
}
