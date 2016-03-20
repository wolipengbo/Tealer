package com.tealer.app.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件处理类
 * Created by lipengbo on 2015/7/8.
 */
public class FileUtils {
    /**
     * SD卡根目录
     */
    private static String mSdRootPath= Environment.getExternalStorageDirectory().getPath();
    /**
     * 手机缓存根目录
     */
    private static String mDataRootPath=null;
    /**
     *缓存目录
     */
    private final static String APK_FLODER="lottoxinyu";
    /**
     * 缓存照片目录
     */
    private final static String CAMERA="camera";
    /**
     * 缓存图片目录
     */
    private final static String IMAGE_CACHE="imagecache";


    public FileUtils(Context context){
        mDataRootPath=context.getCacheDir().getPath();
    }

    public String getRootFloder(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
                mSdRootPath : mDataRootPath;

    }

    public String getApkFloder(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
                mSdRootPath + File.separator+ APK_FLODER : mDataRootPath +File.separator+ APK_FLODER;
    }

    /**
     * 获取缓存照片目录
     * @return
     */
    public String getCameraPath(){
        //  return getApkFloder()+File.separator+CAMERA;
        return getRootFloder()+File.separator+"DCIM"+File.separator+"Camera";
    }




    /**
     * 获取缓存图片目录
     * @return
     */
    public String getImageCachePath(){
        return getApkFloder()+File.separator+IMAGE_CACHE;
    }


    // 创建目录
    public File createDir(String path) throws IOException {
        File dir = new File(path);
        dir.mkdir();
        return dir;
    }

    /**
     *
     *@方法名称:createFile
     *@描述: 创建文件
     *@创建人：lipegnbo
     *@创建时间：2014年11月15日 下午12:06:26
     *@param @param path
     *@param @param fileName
     *@param @return
     *@param @throws IOException
     *@返回类型：File
     *@throws
     */
    public File createFile(String path,String fileName) throws IOException{
        File file = new File(path + fileName);
        file.createNewFile();
        return file;
    }

    /**
     *
     *@方法名称:isExist
     *@描述: 判断文件是否存在
     *@创建人：lipengbo
     *@创建时间：2014年11月15日 上午11:49:29
     *@param @param filePath
     *@param @return
     *@返回类型：boolean
     *@throws
     */
    public boolean isFileExist(String filePath){
        File file = new File(filePath);
        return file.exists();
    }


    /**
     * SD卡是否挂载
     *
     * @return true 已挂载 否则 false
     */
    public static boolean sdcardUsable() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    /**
     *
     *@方法名称:writeToSDPATHFromInput
     *@描述: 根据路径创建文件
     *@创建人：lipengbo
     *@创建时间：2014年11月15日 下午12:08:29
     *@param @param path
     *@param @param fileName
     *@param @param inputstream
     *@param @return
     *@返回类型：File
     *@throws
     */
    public File writeToSDPATHFromInput(String path, String fileName,
                                       InputStream inputstream) {
        File file = null;
        OutputStream outputstream = null;
        try {
            file = createFile(path, fileName);
            outputstream = new FileOutputStream(file);
            byte buffer[] = new byte[1024];
            // 将输入流中的内容先输入到buffer中缓存，然后用输出流写到文件中
            while ((inputstream.read(buffer)) != -1) {
                outputstream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    /**
     *
     *@方法名称:writeFileToPath
     *@描述: 根据路径删除原文件再创建保存
     *@创建人：lipengbo
     *@创建时间：2014年11月15日 下午12:09:06
     *@param @param path
     *@param @param fileName
     *@param @param inputStream
     *@param @return
     *@返回类型：File
     *@throws
     */
    public File writeFileToPath(String path, String fileName, InputStream inputStream){
        File file = null;
        OutputStream outputstream = null;
        try {
            file = createFile(path, fileName);
            deleteFile(file);

            createDir(path);
            outputstream = new FileOutputStream(file);
            byte buffer[] = new byte[1024];
            // 将输入流中的内容先输入到buffer中缓存，然后用输出流写到文件中
            while ((inputStream.read(buffer)) != -1) {
                outputstream.write(buffer);
            }
            outputstream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }



    // 将SD卡文件删除
    public static void deleteFile(File file) {

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            }
            // 如果它是一个目录
            else if (file.isDirectory()) {
                // 声明目录下所有的文件 files[];
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        }
    }
    /**
     * 判断安卓系统否安装某个应用
     * @param packageName
     * @return
     */
    public static boolean isInstallDesApp(String packageName){
        return new File("/data/data/" + packageName).exists();
    }
}
