package com.guc.bureau2.domain;

import com.guc.bureau2.base.BaseDTO;

public class CheckRegionDTO<T> extends BaseDTO {

    private String item_name;
    private String item_code;
    private T t;

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
