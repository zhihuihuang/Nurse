package com.guc.bureau2.domain;

import com.guc.bureau2.base.BaseDTO;


public class DiseaseRankRegionDTO extends BaseDTO {
    private String item_name;
    private String item_value;
    private String his_code;

    public String getHis_code() {
        return his_code;
    }

    public void setHis_code(String his_code) {
        this.his_code = his_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_value() {
        return item_value;
    }

    public void setItem_value(String item_value) {
        this.item_value = item_value;
    }
}
