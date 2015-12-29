package com.guc.bureau2.domain;


import com.guc.bureau2.base.BaseDTO;

public class ListDTO<T> extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T list;
	private String resultCode;
	public T getList() {
		return list;
	}
	public void setList(T list) {
		this.list = list;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
}
