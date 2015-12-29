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
package com.guc.bureau2.mode;

import com.guc.bureau2.utils.GucPreferenceUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DefaultHXSDKModel extends GucSDKModel {
	private static final String PREF_USERNAME = "username";
	private static final String PREF_PWD = "pwd";
	protected Context context = null;

	public DefaultHXSDKModel(Context ctx) {
		context = ctx;
		GucPreferenceUtils.init(context);
	}

	@Override
	public boolean saveHXId(String hxId) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.edit().putString(PREF_USERNAME, hxId).commit();
	}

	@Override
	public String getHXId() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getString(PREF_USERNAME, null);
	}

	@Override
	public boolean savePassword(String pwd) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.edit().putString(PREF_PWD, pwd).commit();
	}

	@Override
	public String getPwd() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getString(PREF_PWD, null);
	}

}
