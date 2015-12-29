package com.guc.bureau2.net;

public class Constant {
    public static final String PROTOCOL = "http://";
     public static final String HOSTNAME = "wap.guc258.com";
   // public static final String HOSTNAME = "192.168.0.109";
    public static final String PORT = ":8088";
    public static final String URL_ROOT = PROTOCOL + HOSTNAME + PORT;
    public static final String APP_ID = "20";
    public static final String GETVERSION = "/publicservice/publicservice/getVersion/" + APP_ID;
    public static final String APP_URL = "/publicService/publicservice/getAppURL/" + APP_ID + "/";
    public static final String GETDBID = "/publicService/publicservice/getDBID/";
    public static final String GETBUREAUINFO = "/restful/bureau/BureauService/GetBureauInfo";
    public static final String GETREGISTERORGANIZATION = "/publicservice/publicservice/getOrganization/";
    public static final String REGISTER = "/restful/bureau/BureauService/Register";
    public static final String GETSTATREPORT = "/restful/bureau/BureauService/getStatReport/";
    public static final String CHECKREGION = "/restful/bureau/BureauService/getAreaData/";
    public static final String CHECKORG = "/restful/bureau/BureauService/GetAssessReport/";
    public static final String DRUGRANKREGION = "/restful/bureau/BureauService/getAreaCodeData/";
    public static final String DRUGRANKREGIONDETAIL = "/restful/bureau/BureauService/getAreaMedicalTop/";
    public static final String DRUGRANKORG = "/restful/bureau/BureauService/getAreaMedicalHospitalTop/";

    public static final String GETORGANIZATIONFILTER = "/restful/bureau/BureauService/getOrganizationHis/";
    public static final String GETINCOME = "/restful/bureau/BureauService/GetInCome/";
    public static final String GETVISITCOUNT = "/restful/bureau/BureauService/GetVisitCount/";
    public static final String GETDRUGTOP = "/restful/bureau/BureauService/GetDrugTop/";
    public static final String GETDIAGTOP = "/restful/bureau/BureauService/GetDiagTop/";
    public static final String PREVIEW = "/restful/bureau/BureauService/getEhrLastDayResult/";
    public static final String PREVIEWDETAIL = "/restful/bureau/BureauService/getEhrHospitalData/";
    public static final String GETREGIONINCOME = "/restful/bureau/BureauService/getAreaBusinessNewData/";
    public static  final String BUSINESSREGIONDETAIL="/restful/bureau/BureauService/getAreaBusinessDay/";

    public static final String GETINCOMEREORG = "/restful/bureau/BureauService/getHospitalBusinessData/";
    public static final String GETHOSPITALIZSTATISTICS = "/restful/bureau/BureauService/getAreaVisitCount/";
    public static final String GETHOSPITALIZSTATISTICSTWO = "/restful/bureau/BureauService/getHospitalVisitCount/";
    public static final String DISEASERANKREGION = "/restful/bureau/BureauService/getAreaDiseaseDataNew/";
    public static final String LOGINLOG = "/publicservice/publicservice/saveLog/" + APP_ID + "/";
}