package com.ddy.transferimage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.transferee.glideloader.GlideImageLoader;
import com.transferee.transferee.transfer.Transferee;
import com.ddy.transferimage.R;
import com.ddy.transferimage.activity.glide.GlideLocalActivity;
import com.ddy.transferimage.activity.glide.GlideNoThumActivity;
import com.ddy.transferimage.activity.glide.TouchMoveActivity;
import com.ddy.transferimage.activity.universal.UniversalLocalActivity;
import com.ddy.transferimage.activity.universal.UniversalNoThumActivity;
import com.ddy.transferimage.activity.universal.UniversalNormalActivity;
import com.transferee.universalloader.UniversalImageLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_glide_no_thum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GlideNoThumActivity.class));
            }
        });

        findViewById(R.id.btn_touch_move).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TouchMoveActivity.class));
            }
        });

        findViewById(R.id.btn_glide_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GlideLocalActivity.class));
            }
        });

        findViewById(R.id.btn_universal_normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UniversalNormalActivity.class));
            }
        });

        findViewById(R.id.btn_universal_no_thum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UniversalNoThumActivity.class));
            }
        });

        findViewById(R.id.btn_universal_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UniversalLocalActivity.class));
            }
        });

        findViewById(R.id.btn_clear_glide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transferee.clear(GlideImageLoader.with(getApplicationContext()));
            }
        });

        findViewById(R.id.btn_clear_universal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transferee.clear(UniversalImageLoader.with(getApplicationContext()));
            }
        });

    }
}
