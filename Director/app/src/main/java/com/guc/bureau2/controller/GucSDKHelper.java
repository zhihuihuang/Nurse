package com.guc.bureau2.controller;


import com.guc.bureau2.mode.DefaultHXSDKModel;
import com.guc.bureau2.mode.GucSDKModel;

import android.content.Context;

public abstract class GucSDKHelper {
	private static GucSDKHelper me = null;
	/**
	 * application context
	 */
	protected Context appContext = null;
	protected GucSDKModel hxModel = null;
	/**
	 * ID in cache
	 */
	protected String hxId =null;

	/**
	 * password in cache
	 */
	protected String password = null;

	protected GucSDKHelper() {
		me = this;
	}

	public synchronized boolean onInit(Context context) {
		appContext = context;
		hxModel = createModel();
		if (hxModel == null) {
			hxModel = new DefaultHXSDKModel(appContext);
		}
		return true;
	}

	/**
	 * get global instance
	 * 
	 * @return
	 */
	public static GucSDKHelper getInstance() {
		return me;
	}

	public Context getAppContext() {
		return appContext;
	}

	public GucSDKModel getModel() {
		return hxModel;
	}

	public String getHXId() {
		if (hxId == null) {
			hxId = hxModel.getHXId();
		}
		return hxId;
	}

	public String getPassword() {
		if (password == null) {
			password = hxModel.getPwd();
		}
		return password;
	}

	public void setHXId(String hxId) {
		if (hxId != null) {
			if (hxModel.saveHXId(hxId)) {
				this.hxId = hxId;
			}
		}
	}

	public void setPassword(String password) {
		if (hxModel.savePassword(password)) {
			this.password = password;
		}
	}

	abstract protected GucSDKModel createModel();

}
