package com.mreturn.zhihudaily.utils;

import com.mreturn.zhihudaily.app.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by mReturn
 * on 2017/5/27.
 */

public class FileUtils {
    /**
     * rxjava保存文件到本地
     * @param body
     * @param fileUrl
     * @return
     */
    public static String writeResponseBodyToDisk(ResponseBody body,String fileUrl) {
        String name = fileUrl.substring(fileUrl.lastIndexOf("/"),fileUrl.length());
        try {
            //创建文件夹
            File file = new File(Constant.DOWNLOAD_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
//            //截取后缀 生成文件名
//            String ext = fileUrl.substring(fileUrl.lastIndexOf("."),fileUrl.length());
//            String filePath = Constant.DOWNLOAD_PATH + new Date().getTime()+ext;
            String filePath = Constant.DOWNLOAD_PATH + fileUrl.substring(fileUrl.lastIndexOf("/",fileUrl.length()));
            File futureStudioIconFile = new File(filePath);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                }

                outputStream.flush();

                return filePath;
            } catch (IOException e) {
                MyLog.e("startDownload ex0: ",e.toString());
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            MyLog.e("startDownload ex1: ",e.toString());
            return null;
        }
    }
}
