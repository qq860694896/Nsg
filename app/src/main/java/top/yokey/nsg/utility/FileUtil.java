package top.yokey.nsg.utility;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/*
*
* 作者：Yokey软件工作室
*
* 企鹅：1002285057
*
* 网址：www.yokey.top
*
* 作用：文件工具类
*
*/

public class FileUtil {

    public static String pathString = "Android/Yokey/Nsg/";
    public static String downPathString = pathString + "Down/";
    public static String cachePathString = pathString + "Cache/";
    public static String imagePathString = pathString + "Image/";

    /*
    * 作用：获取根目录路径
    */
    public static String getRootPath() {
        if (hasSDCard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data/";
        }
    }

    /*
    * 作用：获取程序缓存路径
    */
    public static String getCachePath() {
        if (hasSDCard()) {
            return getRootPath() + cachePathString;
        } else {
            return getRootPath() + cachePathString;
        }
    }

    /*
    * 作用：获取程序图片存放路径
    */
    public static String getImagePath() {
        if (hasSDCard()) {
            return getRootPath() + imagePathString;
        } else {
            return getRootPath() + imagePathString;
        }
    }

    /*
    * 作用：检查下载文件夹路径
    */
    public static String getDownPath() {
        if (hasSDCard()) {
            return getRootPath() + downPathString;
        } else {
            return getRootPath() + downPathString;
        }
    }

    /*
    * 作用：判断有没有内存卡
    */
    public static boolean hasSDCard() {
        String str = Environment.getExternalStorageState();
        return str.equals(Environment.MEDIA_MOUNTED);
    }

    /*
    * 作用：创建图片文件夹
    */
    public static void createImagePath() {
        File file = new File(getImagePath());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /*
    * 作用：创建缓存文件夹
    */
    public static void createCachePath() {
        File file = new File(getCachePath());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /*
    * 作用：创建下载文件夹
    */
    public static void createDownPath() {
        File file = new File(getDownPath());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /*
    * 作用：创建一个 JPG 文件
    */
    public static String createJpgByBitmap(String name, Bitmap bitmap) {

        String path = FileUtil.getImagePath() + name + ".jpg";

        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fileOutputStream;
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;

    }

}
