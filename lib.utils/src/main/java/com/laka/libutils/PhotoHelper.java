package com.laka.libutils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;


/**
 * @Author:summer
 * @Date:2019/7/12
 * @Description:拍照工具类，所有照片存放于列表中，列表中只有一个数据，最近一次拍照存放的照片的路径
 */
public class PhotoHelper {

    private static final String PHOTO_SAVE_NAME = "laka_regou";
    private static final String PICTURE_FORMAT_JPG = ".jpg";
    private static final String PICTURE_FORMAT_PNG = ".png";
    private static final int POST_IMAGE_MAX_SIZE = 1024; // 单位 kb
    // 从相册拿图片 = 1 ， 从相册拿视频 = 2 ， 录视频 = 3， 裁剪图片 = 4 ， 拍照 = 5 , 素材库 = 6
    public static final int ALBUM_PHOTO = 1, ALBUM_VIDEO = 2, RECORD_VIDEO = 3, CUTS_PHOTO = 4, TAKE_PHOTO = 5, MATERIAL_STORE = 6;
    private static ArrayList<File> mPhotoList = new ArrayList<>();
    private DecodeResultCallback mCallback;
    private int mRequestCode;
    private Activity mActivity;

    public PhotoHelper(Activity activity) {
        mActivity = activity;
    }

    public void onResult(int requestCode, int resultCode, Intent data, DecodeResultCallback callback) {
        if (data == null && requestCode != TAKE_PHOTO) {
            return;
        }
        this.mRequestCode = requestCode;
        this.mCallback = callback;
        if (mRequestCode == ALBUM_PHOTO) { // 从相册拿照片
            String realPath = getRealPathFromURI(mActivity, data.getData());
            compressBitmap(mActivity, realPath, callback);
        } else if (mRequestCode == ALBUM_VIDEO) { // 从相册拿视频

        } else if (mRequestCode == RECORD_VIDEO) { // 录视频

        } else if (mRequestCode == CUTS_PHOTO) { // 裁剪图片

        } else if (mRequestCode == TAKE_PHOTO) { // 拍照
            File photoFile = getPhotoFile();
            compressBitmap(mActivity, photoFile.getAbsolutePath(), callback);
        } else if (mRequestCode == MATERIAL_STORE) { // 素材库

        }
    }

    public void takePhoto(Activity activity, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 保存图片的路径
        File file = new File(Environment.getExternalStorageDirectory().getPath(), PHOTO_SAVE_NAME + System.currentTimeMillis() + PICTURE_FORMAT_JPG);
        mPhotoList.clear();
        mPhotoList.add(file);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //大于等于 7.0，私有人间访问权限
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Uri imageUri = FileProvider.getUriForFile(activity, "com.laka.ergou.fileprovider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            activity.startActivityForResult(intent, requestCode);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 获取拍照的照片路径
     */
    public File getPhotoFile() {
        if (!mPhotoList.isEmpty()) {
            return mPhotoList.get(mPhotoList.size() - 1);
        }
        return new File("unkown");
    }

    /**
     * 图片选择
     */
    public boolean choicePhoto(Activity activity, int requestCode) {
        boolean result = true;
        Intent intent;
        if (Build.VERSION.SDK_INT <= 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            result = false;
        }
        return result;
    }

    /**
     * uri 转换为 path
     */
    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        try {
            Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) { // Source is Dropbox or other similar local file path
                result = contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                if (idx == -1) {
                    return null;
                }
                result = cursor.getString(idx);
                cursor.close();
            }
        } catch (Exception e) {
            return "";
        }
        return result;
    }

    /**
     * 图片压缩
     */
    private void compressBitmap(Activity context, String filePath, final DecodeResultCallback callback) {
        if (TextUtils.isEmpty(filePath) || !new File(filePath).exists()) {
            if (callback != null) {
                callback.onDecodeResultFail("获取照片失败", mRequestCode);
            }
            return;
        }
        Luban.compress(context, new File(filePath))
                .setMaxSize(POST_IMAGE_MAX_SIZE)
                .putGear(Luban.CUSTOM_GEAR)
                .launch(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        if (file != null && file.exists()) {
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            if (callback != null) {
                                long size = file.length() / 1024;
                                LogUtils.info("size", "size:" + size);
                                callback.onDecodeResultSuccess(bitmap, file.getAbsolutePath(), file.length() / 1024, 0);
                            }
                        } else {
                            if (callback != null) {
                                callback.onDecodeResultFail("压缩失败，请重试", mRequestCode);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    public interface DecodeResultCallback {
        void onDecodeResultSuccess(Bitmap bitmap, String path, long size, long duration);

        void onDecodeResultFail(String message, int requestCode);
    }

}
