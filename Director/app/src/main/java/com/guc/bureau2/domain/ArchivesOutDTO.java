package com.guc.bureau2.domain;

import com.guc.bureau2.base.BaseDTO;


public class ArchivesOutDTO<T> extends BaseDTO {
    private String item_code;
    private String item_name;
    private T list;

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

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }
}
