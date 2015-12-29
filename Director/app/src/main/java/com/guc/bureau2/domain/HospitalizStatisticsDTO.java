package com.guc.bureau2.domain;

import com.guc.bureau2.base.BaseDTO;


public class HospitalizStatisticsDTO extends BaseDTO {
    private String item_code;
    private String item_name;
    private String outp_num;
    private String inp_in_num;
    private String inp_out_num;
    private String outp_fee;
    private String inp_fee;
    private String base_drug_fee;
    private String avg_outp_fee;
    private String avg_inp_fee;

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getOutp_num() {
        return outp_num;
    }

    public void setOutp_num(String outp_num) {
        this.outp_num = outp_num;
    }

    public String getInp_in_num() {
        return inp_in_num;
    }

    public void setInp_in_num(String inp_in_num) {
        this.inp_in_num = inp_in_num;
    }

    public String getInp_out_num() {
        return inp_out_num;
    }

    public void setInp_out_num(String inp_out_num) {
        this.inp_out_num = inp_out_num;
    }

    public String getOutp_fee() {
        return outp_fee;
    }

    public void setOutp_fee(String outp_fee) {
        this.outp_fee = outp_fee;
    }

    public String getInp_fee() {
        return inp_fee;
    }

    public void setInp_fee(String inp_fee) {
        this.inp_fee = inp_fee;
    }

    public String getBase_drug_fee() {
        return base_drug_fee;
    }

    public void setBase_drug_fee(String base_drug_fee) {
        this.base_drug_fee = base_drug_fee;
    }

    public String getAvg_outp_fee() {
        return avg_outp_fee;
    }

    public void setAvg_outp_fee(String avg_outp_fee) {
        this.avg_outp_fee = avg_outp_fee;
    }

    public String getAvg_inp_fee() {
        return avg_inp_fee;
    }

    public void setAvg_inp_fee(String avg_inp_fee) {
        this.avg_inp_fee = avg_inp_fee;
    }
}
