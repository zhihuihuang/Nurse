package com.guc.bureau2.controller;

import com.guc.bureau2.mode.DefaultHXSDKModel;
import com.guc.bureau2.mode.GucSDKModel;

public class DefaultHelper extends GucSDKHelper {

    @Override
    protected GucSDKModel createModel() {
        return new DefaultHXSDKModel(appContext);
    }
}
