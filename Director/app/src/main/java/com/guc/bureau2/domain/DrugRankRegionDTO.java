package com.guc.bureau2.domain;

import com.guc.bureau2.base.BaseDTO;

public class DrugRankRegionDTO extends BaseDTO {

    private String item_code;
    private String area_code;
    private String item_name;

    private String item_format;
    private String item_unit;
    private String item_num;

    public DrugRankRegionDTO() {
    }

    public DrugRankRegionDTO(String item_num, String item_unit, String item_format, String item_name) {
        this.item_num = item_num;
        this.item_unit = item_unit;
        this.item_format = item_format;
        this.item_name = item_name;

    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
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

    @Override
    public String toString() {
        return item_code
                + area_code +
                item_name +
                item_format +
                item_unit +
                item_num;
    }
}
