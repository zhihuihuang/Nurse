package com.guc.bureau2.domain;

import com.guc.bureau2.base.BaseDTO;

public class DiseaseDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String amount;
	private String diagname;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDiagname() {
		return diagname;
	}

	public void setDiagname(String diagname) {
		this.diagname = diagname;
	}

}
