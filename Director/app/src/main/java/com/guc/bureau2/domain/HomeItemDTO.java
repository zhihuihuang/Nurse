package com.guc.bureau2.domain;

import com.guc.bureau2.base.BaseDTO;


public class HomeItemDTO extends BaseDTO {
    private int sourceId;
    private String lable;

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }
}
