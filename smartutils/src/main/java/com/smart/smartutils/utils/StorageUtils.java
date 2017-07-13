package com.smart.smartutils.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;

/**
 * Created by fengjh on 17/2/13.
 */

public class StorageUtils {

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);  //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();  //获取根目录
        }
        if (sdDir != null) {
            return sdDir.toString();
        } else {
            return "";
        }
    }

    public static float getSDCardTotalSize() {
        String sdPath = getSDPath();
        if (StringUtils.isNotEmpty(sdPath)) {
            long space = new File(sdPath).getTotalSpace();
            LogUtils.i("StorageUtils", "getTotalSpace==space=" + space);
            float size = space / 1024.0f / 1024.0f / 1024.0f;
            return size;
        }
        return 0;
    }

    public static float getSDCardAvailableSize() {
        String sdPath = getSDPath();
        if (StringUtils.isNotEmpty(sdPath)) {
            long space = new File(sdPath).getFreeSpace();
            LogUtils.i("StorageUtils", "getFreeSpace==space=" + space);
            float size = space / 1024.0f / 1024.0f / 1024.0f;
            return size;
        }
        return 0;
    }

    public static float getSDCardUsableSize() {
        String sdPath = getSDPath();
        if (StringUtils.isNotEmpty(sdPath)) {
            long space = new File(sdPath).getUsableSpace();
            LogUtils.i("StorageUtils", "getUsableSpace==space=" + space);
            float size = space / 1024.0f / 1024.0f / 1024.0f;
            return size;
        }
        return 0;
    }

    private static float format(float size) {
        while (size >= 1024.0f) {
            size = size / 1024.0f;
        }
        return size;
    }


    public void test(Context context) {
        // 得到文件系统的信息：存储块大小，总的存储块数量，可用存储块数量
        // 获取sd卡空间
        // 存储设备会被分为若干个区块
        // 每个区块的大小 * 区块总数 = 存储设备的总大小
        // 每个区块的大小 * 可用区块的数量 = 存储设备可用大小
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize;
        long totalBlocks;
        long availableBlocks;
        // 由于API18（Android4.3）以后getBlockSize过时并且改为了getBlockSizeLong
        // 因此这里需要根据版本号来使用那一套API
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
            availableBlocks = stat.getAvailableBlocks();
        }
        String sslockSize = formatSize(context, blockSize);
        String stotalBlocks = formatSize(context, totalBlocks);
        String savailableBlocks = formatSize(context, availableBlocks);
        LogUtils.i("StorageUtils", "sslockSize=" + sslockSize);
        LogUtils.i("StorageUtils", "stotalBlocks=" + stotalBlocks);
        LogUtils.i("StorageUtils", "savailableBlocks=" + savailableBlocks);
    }

    //封装Formatter.formatFileSize方法，具体可以参考安卓的API
    private String formatSize(Context context, long size) {
        return Formatter.formatFileSize(context, size);
    }


}
