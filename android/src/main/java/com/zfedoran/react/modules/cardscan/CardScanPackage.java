package com.zfedoran.react.modules.cardscan;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;

public class CardScanPackage implements ReactPackage {

    Activity mActivity = null;
    CardScanModule mModuleInstance = null;

    public CardScanPackage(Activity activity) {
        this.mActivity = activity;
    }

    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        mModuleInstance.handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        mModuleInstance = new CardScanModule(reactContext, mActivity);

        return Arrays.<NativeModule>asList(mModuleInstance);
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.asList();
    }
}
