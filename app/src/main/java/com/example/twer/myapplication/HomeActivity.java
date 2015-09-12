package com.example.twer.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.util.logging.Logger;

/**
 * Created by twer on 8/18/15.
 */
public class HomeActivity extends BaseActivity {

    public static final int REQUEST_CODE = 0;

    private static String name;

    private FrameLayout frame;

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        name = getMyString("title", "No edit");
        title = ((TextView) findViewById(R.id.title));
        title.setText(name);
        frame = (FrameLayout) findViewById(R.id.image_preview);
        frame.setVisibility(View.GONE);
        Button takePhotoBtn = (Button) findViewById(R.id.take_photo);
        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getFileForImage()));
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String flag = resultCode == -1 ? "success" : "error";
        Toast.makeText(this, flag, Toast.LENGTH_LONG).show();
        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.GONE);
        FrameLayout frame = (FrameLayout) findViewById(R.id.image_preview);
        frame.removeAllViews();
        frame.setVisibility(View.VISIBLE);
        title.setVisibility(View.GONE);
        ImageView imagePreview = new ImageView(this);
        imagePreview.setImageURI(Uri.fromFile(getFileForImage()));
        frame.addView(imagePreview);
    }

    private File getFileForImage() {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String path = "myDir";
        File myDir = new File(file, path);
        if (!myDir.exists()) {
            myDir.mkdir();
        }
        return new File(myDir, name + ".jpg");
    }
}
