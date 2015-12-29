package com.guc.bureau2.domain;

import com.guc.bureau2.base.BaseDTO;


public class ArchivesInDTO extends BaseDTO {
    private String item_code;
    private String item_value;
    private String item_total;
    private String item_ratio;
    private String item_type;

    public String getItem_value() {
        return item_value;
    }

    public void setItem_value(String item_value) {
        this.item_value = item_value;
    }

    public String getItem_total() {
        return item_total;
    }

    public void setItem_total(String item_total) {
        this.item_total = item_total;
    }

    public String getItem_ratio() {
        return item_ratio;
    }

    public void setItem_ratio(String item_ratio) {
        this.item_ratio = item_ratio;
    }


    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }
}
