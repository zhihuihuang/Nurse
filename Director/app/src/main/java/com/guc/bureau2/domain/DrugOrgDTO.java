package com.guc.bureau2.domain;

import com.guc.bureau2.base.BaseDTO;


public class DrugOrgDTO extends BaseDTO {
    private String hos_name;
    private String hos_code;
    private String item_name;
    private String item_format;
    private String item_unit;
    private String item_num;

    public String getHos_name() {
        return hos_name;
    }

    public void setHos_name(String hos_name) {
        this.hos_name = hos_name;
    }

    public String getHos_code() {
        return hos_code;
    }

    public void setHos_code(String hos_code) {
        this.hos_code = hos_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_format() {
        return item_format;
    }

    public void setItem_format(String item_format) {
        this.item_format = item_format;
    }

    public String getItem_unit() {
        return item_unit;
    }

    public void setItem_unit(String item_unit) {
        this.item_unit = item_unit;
    }

    public String getItem_num() {
        return item_num;
    }

    public void setItem_num(String item_num) {
        this.item_num = item_num;
    }
}
