package com.gw.tools.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.gw.tools.R;
import com.gw.tools.lib.util.IntentCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * Created by GongWen on 17/10/11.
 * 超完整！Android获取图片的三种方法

 * http://www.jianshu.com/p/d4793d32a5fb
 */

public class CameraFragment extends BaseToolBarFragment {
    private static final int REQUEST_CODE_THUMBNAILS = 0;
    private static final int REQUEST_CODE_ORIGIN = 1;
    private Uri outputFileUri;

    @BindView(R.id.ivThumbnails)
    ImageView ivThumbnails;
    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_camera;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle("Camera使用");
    }


    @OnClick({R.id.tvThumbnails, R.id.tv})
    void onClick(View mView) {
        switch (mView.getId()) {
            case R.id.tvThumbnails:
                IntentCompat.actionImageCapture(CameraFragment.this, REQUEST_CODE_THUMBNAILS);
                break;
            case R.id.tv:
                File file = createImageFile();
                outputFileUri = Uri.fromFile(file);
                IntentCompat.actionImageCapture(CameraFragment.this, REQUEST_CODE_ORIGIN, outputFileUri);
                break;
        }
    }

    public static File createImageFile() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        try {
            File image = File.createTempFile(imageFileName,
                    ".jpg",
                    Environment.getExternalStorageDirectory());
            return image;
        } catch (IOException e) {
            //do noting
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Log.d(TAG, "canceled or other exception!");
            return;
        }

        if (requestCode == REQUEST_CODE_THUMBNAILS) {
            Log.d(TAG, "REQUEST_IMAGE_CAPTURE");
            try {
                //"data"这个居然没用常量定义,也是醉了,我们可以发现它直接把bitmap序列化到intent里面了。
                Bitmap bitmap = data.getExtras().getParcelable("data");
                //TODO:do something with bitmap, Do NOT forget call Bitmap.recycler();
                ivThumbnails.setImageBitmap(bitmap);
            } catch (ClassCastException e) {
                //do something with exceptions
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CODE_ORIGIN) {
            Log.d(TAG, "REQUEST_IMAGE_CAPTURE");
            iv.setImageBitmap(BitmapFactory.decodeFile(outputFileUri.getPath()));
        }

    }
}
