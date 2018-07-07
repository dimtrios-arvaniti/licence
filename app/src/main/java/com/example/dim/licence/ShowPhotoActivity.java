package com.example.dim.licence;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;

public class ShowPhotoActivity extends AppCompatActivity {

    private ImageView imageView;
    private String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);

        imageView = (ImageView) findViewById(R.id.myImageView);

        Intent intent = getIntent();
        if (null != intent) {
            imagePath = getIntent().getStringExtra("path");
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

            if (null != bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }


        Log.i(ARG_DEBUG, "onCreate: complete!");
    }
}
