package com.example.lesson_11_3.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    private static final String DATE_FORMAT = "yyyyMMdd_HHmmss";
    private static String currentPhotoPath;
    public static final String FILES_AUTHORITY = "com.example.lesson_11_3.provider";

    public static File createImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public static String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

}
