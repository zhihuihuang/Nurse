package com.guc.bureau2.domain;

import com.guc.bureau2.base.BaseDTO;

public class BureauInfoResponseDTO extends BaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resultCode;
	private String resultErrInfo;
	private String org_code;
	private String person_code;
	private String person_name;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultErrInfo() {
		return resultErrInfo;
	}

	public void setResultErrInfo(String resultErrInfo) {
		this.resultErrInfo = resultErrInfo;
	}

	public String getOrg_code() {
		return org_code;
	}

	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}

	public String getPerson_code() {
		return person_code;
	}

	public void setPerson_code(String person_code) {
		this.person_code = person_code;
	}

	public String getPerson_name() {
		return person_name;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}

}
