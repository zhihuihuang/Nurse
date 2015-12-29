package com.guc.bureau2.domain;

import com.guc.bureau2.base.BaseDTO;

public class BureauInfoResquestDTO extends BaseDTO {
	public BureauInfoResquestDTO() {
	}

	public BureauInfoResquestDTO(String dblist_id, String phoneNumber, String PWD) {
		this.dblist_id = dblist_id;
		this.phoneNumber = phoneNumber;
		this.PWD = PWD;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dblist_id;
	private String phoneNumber;
	private String PWD;

	public String getDblist_id() {
		return dblist_id;
	}

	public void setDblist_id(String dblist_id) {
		this.dblist_id = dblist_id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPWD() {
		return PWD;
	}

	public void setPWD(String pWD) {
		PWD = pWD;
	}
}
