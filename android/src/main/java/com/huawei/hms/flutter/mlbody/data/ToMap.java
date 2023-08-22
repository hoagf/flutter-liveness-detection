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

package com.huawei.hms.flutter.mlbody.data;

import android.graphics.PointF;

import androidx.annotation.NonNull;

import com.huawei.hms.mlsdk.livenessdetection.MLLivenessCaptureResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToMap {
    // Static Biometric Verification
    public static class LivenessToMap {
        public static Map<String, Object> getResult(MLLivenessCaptureResult result) {
            final Map<String, Object> map = new HashMap<>();
            map.put("score", result.getScore());
            map.put("pitch", result.getPitch());
            map.put("roll", result.getRoll());
            map.put("yaw", result.getYaw());
            map.put("bitmap", Commons.bitmapToByteArray(result.getBitmap()));
            map.put("isLive", result.isLive());
            return map;
        }
    }

}
