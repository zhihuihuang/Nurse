package com.guc.bureau2.domain;

import com.guc.bureau2.base.BaseDTO;

public class InComeDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String base_charge;
	private String base_rate;
	private String mz_charge;
	private String resultCode;
	private String resultErrInfo;
	private String total_charge;
	private String total_charge_yp;
	private String total_charge_zl;
	private String yp_rate;
	private String zy_charge;
	public String getBase_charge() {
		return base_charge;
	}
	public void setBase_charge(String base_charge) {
		this.base_charge = base_charge;
	}
	public String getBase_rate() {
		return base_rate;
	}
	public void setBase_rate(String base_rate) {
		this.base_rate = base_rate;
	}
	public String getMz_charge() {
		return mz_charge;
	}
	public void setMz_charge(String mz_charge) {
		this.mz_charge = mz_charge;
	}
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
	public String getTotal_charge() {
		return total_charge;
	}
	public void setTotal_charge(String total_charge) {
		this.total_charge = total_charge;
	}
	public String getTotal_charge_yp() {
		return total_charge_yp;
	}
	public void setTotal_charge_yp(String total_charge_yp) {
		this.total_charge_yp = total_charge_yp;
	}
	public String getTotal_charge_zl() {
		return total_charge_zl;
	}
	public void setTotal_charge_zl(String total_charge_zl) {
		this.total_charge_zl = total_charge_zl;
	}
	public String getYp_rate() {
		return yp_rate;
	}
	public void setYp_rate(String yp_rate) {
		this.yp_rate = yp_rate;
	}
	public String getZy_charge() {
		return zy_charge;
	}
	public void setZy_charge(String zy_charge) {
		this.zy_charge = zy_charge;
	}

}
