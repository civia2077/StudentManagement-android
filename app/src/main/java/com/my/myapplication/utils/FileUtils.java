package com.my.myapplication.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {
    //    选择图片大小限制
    public static final int IMAGE_MAX_SIZE = 1024 * 100;

    //或者文件大小 拍照或者选择照片回调时候用到
    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                size = fis.available();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return size;
    }


    /**
     * 转换
     */
    public static String getPath(String path) {

        return path;
    }

    /**
     * 得到图片
     */
    public static Bitmap getBitmap(String path) {
        return BitmapFactory.decodeFile(path);

    }
}
