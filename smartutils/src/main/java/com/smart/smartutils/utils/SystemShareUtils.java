package com.smart.smartutils.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by fengjh on 17/3/30.
 */

public class SystemShareUtils {

    public static void shareText(Context context, String shareText) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        context.startActivity(Intent.createChooser(intent, "分享到"));
    }

    public static void shareSingleLocalImage(Context context, String localImagePath) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(localImagePath)));
        intent.setType("image/*");
        context.startActivity(Intent.createChooser(intent, "分享到"));
    }

    public static void shareMultiLocalImage(Context context, ArrayList<String> localImagePath) {
        ArrayList<Uri> uriList = new ArrayList<>();
        for(String path:localImagePath){
            uriList.add(Uri.fromFile(new File(path)));
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,uriList);
        intent.setType("image/*");
        context.startActivity(Intent.createChooser(intent,"分享到"));
    }
}
