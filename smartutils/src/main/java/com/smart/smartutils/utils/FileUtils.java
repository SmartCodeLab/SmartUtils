package com.smart.smartutils.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Copyright © 2016 Phoenix New Media Limited All Rights Reserved.
 * Created by fengjh on 2016/1/29.
 */
public class FileUtils {

    public static String readFileFromAssets(Context context, String fileName) {
        InputStream is = null;
        try {
            is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] butter = new byte[size];
            is.read(butter);
            is.close();
            String content = new String(butter, "UTF-8");
//            LogUtils.e("-------------------content=" + content);
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
            return content;
        } catch (IOException e) {
            LogUtils.e("-------------------content=catch");
        }
        return "";
    }
}
