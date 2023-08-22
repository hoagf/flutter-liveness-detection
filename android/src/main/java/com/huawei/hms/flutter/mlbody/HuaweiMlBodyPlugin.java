/*
    Copyright 2021-2022. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License")
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        https://www.apache.org/licenses/LICENSE-2.0
        
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.huawei.hms.flutter.mlbody;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.huawei.hms.flutter.mlbody.constant.Constants;
import com.huawei.hms.flutter.mlbody.handlers.AppHandler;
import com.huawei.hms.flutter.mlbody.handlers.LivenessHandler;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.TextureRegistry;

public class HuaweiMlBodyPlugin implements FlutterPlugin, ActivityAware {
    private FlutterPluginBinding mFlutterPluginBinding;
    private MethodChannel appChannel;
    private MethodChannel livenessMethodChannel;

    private void onAttachedToEngine(final BinaryMessenger messenger, final Activity activity, final TextureRegistry registry) {
        initChannels(messenger);
        setHandlers(activity, messenger, registry);
    }

    private void initChannels(BinaryMessenger messenger) {
        appChannel = new MethodChannel(messenger, Constants.APP_CHANNEL);
        livenessMethodChannel = new MethodChannel(messenger, Constants.LIVENESS_CHANNEL);
    }

    private void setHandlers(Activity activity, BinaryMessenger messenger, TextureRegistry registry) {
        appChannel.setMethodCallHandler(new AppHandler(activity));
        livenessMethodChannel.setMethodCallHandler(new LivenessHandler(activity));
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        this.mFlutterPluginBinding = flutterPluginBinding;
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        this.mFlutterPluginBinding = null;
        removeChannels();
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        if (mFlutterPluginBinding != null) {
            onAttachedToEngine(mFlutterPluginBinding.getBinaryMessenger(), binding.getActivity(), mFlutterPluginBinding.getTextureRegistry());
        }
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        if (mFlutterPluginBinding != null) {
            onAttachedToEngine(mFlutterPluginBinding.getBinaryMessenger(), binding.getActivity(), mFlutterPluginBinding.getTextureRegistry());
        }
    }

    private void removeChannels() {
        appChannel = null;
        livenessMethodChannel = null;
    }

    @Override
    public void onDetachedFromActivity() {
        appChannel.setMethodCallHandler(null);
        livenessMethodChannel.setMethodCallHandler(null);
    }
}
