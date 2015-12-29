/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.guc.bureau2.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class GucPreferenceUtils {
	/**
	 * 保存Preference的name
	 */
	public static final String PREFERENCE_NAME = "saveInfo";
	private static SharedPreferences mSharedPreferences;
	private static GucPreferenceUtils mPreferenceUtils;
	private static SharedPreferences.Editor editor;

	private String SHARED_KEY_SET_SHOW_PASSWORD = "shared_key_set_show_passwrod";
	private String SHARED_KEY_SET_SAVE_PASSWORD = "shared_key_set_save_passwrod";

	private GucPreferenceUtils(Context cxt) {
		mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		editor = mSharedPreferences.edit();
	}

	public static synchronized void init(Context cxt) {
		if (mPreferenceUtils == null) {
			mPreferenceUtils = new GucPreferenceUtils(cxt);
		}
	}

	/**
	 * 单例模式，获取instance实例
	 * 
	 * @param cxt
	 * @return
	 */
	public static GucPreferenceUtils getInstance() {
		if (mPreferenceUtils == null) {
			throw new RuntimeException("please init first!");
		}

		return mPreferenceUtils;
	}

	public void setIsShowPwd(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SET_SHOW_PASSWORD, paramBoolean);
		editor.commit();
	}

	public boolean getIsShowPwd() {
		return mSharedPreferences.getBoolean(SHARED_KEY_SET_SHOW_PASSWORD, false);
	}

	public void setIsSavePwd(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SET_SAVE_PASSWORD, paramBoolean);
		editor.commit();
	}

	public boolean getIsSavePwd() {
		return mSharedPreferences.getBoolean(SHARED_KEY_SET_SAVE_PASSWORD, false);
	}

}
