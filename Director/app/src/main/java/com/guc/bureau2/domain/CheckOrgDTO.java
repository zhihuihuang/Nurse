package com.guc.bureau2.domain;

import com.guc.bureau2.base.BaseDTO;

public class CheckOrgDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String assess_item_name;
	private String assess_result;
	public String getAssess_item_name() {
		return assess_item_name;
	}
	public void setAssess_item_name(String assess_item_name) {
		this.assess_item_name = assess_item_name;
	}
	public String getAssess_result() {
		return assess_result;
	}
	public void setAssess_result(String assess_result) {
		this.assess_result = assess_result;
	}
	
}
