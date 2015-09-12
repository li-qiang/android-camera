package com.example.twer.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by twer on 9/11/15.
 */
public class CameraActivity extends BaseActivity {

    public final String TAG = "my app";

    public Camera camera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        camera = Camera.open();
        Log.d(TAG, "Open Camera");
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.preview);
        if (checkCameraHardware(this)) {
            Log.i("my app", "open camera");
            frameLayout.addView(new CemareView(this, camera));
        } else {
            Log.e("my app", "no access to camera");
        }
        Button takePhotoBtn = (Button) findViewById(R.id.take_photo);
        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, mPicture);
            }
        });
    }

    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }


    public String getMyTitle() {
        return getMyString("title", getDefaultImageName());
    }

    private static String getDefaultImageName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "Image_" + timeStamp;
    }

    ;

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile();

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };

    private File getOutputMediaFile() {
        File pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File mediaStorageDir = new File(pictures, "myDir");
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdir();
        }
        return new File(mediaStorageDir, getMyTitle() + ".jpg");
    }


}
