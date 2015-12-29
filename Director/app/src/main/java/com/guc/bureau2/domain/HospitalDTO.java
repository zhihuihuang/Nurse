package com.guc.bureau2.domain;

import com.guc.bureau2.base.BaseDTO;

public class HospitalDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String org_code;
	private String org_name;
	private String org_type;
	private String hospital_code;
	private String org_name_short;
	public String getOrg_code() {
		return org_code;
	}
	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getOrg_type() {
		return org_type;
	}
	public void setOrg_type(String org_type) {
		this.org_type = org_type;
	}
	public String getHospital_code() {
		return hospital_code;
	}
	public void setHospital_code(String hospital_code) {
		this.hospital_code = hospital_code;
	}
	public String getOrg_name_short() {
		return org_name_short;
	}
	public void setOrg_name_short(String org_name_short) {
		this.org_name_short = org_name_short;
	}
	
}
