package com.ddy.transferimage.activity.glide;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.transferee.glideloader.GlideImageLoader;
import com.transferee.transferee.style.progress.ProgressBarIndicator;
import com.transferee.transferee.transfer.TransferConfig;
import com.transferee.transferee.transfer.Transferee;
import com.ddy.transferimage.R;
import com.ddy.transferimage.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class TouchMoveActivity extends BaseActivity {

    private ImageView imageView1, imageView2, imageView3;
    private List<ImageView> imageViewList;

    private List<String> imageStrList;

    {
        imageStrList = new ArrayList<>();
        imageStrList.add("http://p4.so.qhmsg.com/t01e6a5e96377408709.jpg");
        imageStrList.add("http://p0.so.qhimgs1.com/t01f627a39a004137ec.jpg");
        imageStrList.add("http://p0.so.qhmsg.com/t01f9c724154b494425.jpg");
        imageStrList.add("http://p0.so.qhimgs1.com/t01a1d06e3fb39c46e4.jpg");
        imageStrList.add("http://p0.so.qhimgs1.com/t019594190e00c6c65a.jpg");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_touch_move;
    }

    @Override
    protected void initView() {
        imageView1 = (ImageView) findViewById(R.id.image_view1);
        imageView2 = (ImageView) findViewById(R.id.image_view2);
        imageView3 = (ImageView) findViewById(R.id.image_view3);

        TouchMoveActivity.TouchViewMotion touchViewMotion = new TouchMoveActivity.TouchViewMotion();
        imageView1.setOnTouchListener(touchViewMotion);
        imageView2.setOnTouchListener(touchViewMotion);
        imageView3.setOnTouchListener(touchViewMotion);

        TouchMoveActivity.ShowViewHDListener showViewHDListener = new TouchMoveActivity.ShowViewHDListener();
        imageView1.setOnClickListener(showViewHDListener);
        imageView2.setOnClickListener(showViewHDListener);
        imageView3.setOnClickListener(showViewHDListener);

        imageViewList = new ArrayList<>();
        imageViewList.add(imageView1);
        imageViewList.add(imageView2);
        imageViewList.add(imageView3);
    }

    @Override
    protected void testTransferee() {
        config = TransferConfig.build()
                .setImageLoader(GlideImageLoader.with(getApplicationContext()))
                .setMissPlaceHolder(R.mipmap.ic_empty_photo)
                .setOriginImageList(imageViewList)
                .setSourceImageList(imageStrList)
                .setProgressIndicator(new ProgressBarIndicator())
                .setJustLoadHitImage(true)
                .setOnLongClcikListener(new Transferee.OnTransfereeLongClickListener() {
                    @Override
                    public void onLongClick(ImageView imageView, int pos) {
                        saveImageByGlide(imageView);
                    }
                })
                .create();

        Glide.with(this)
                .load(imageStrList.get(0))
                .placeholder(R.mipmap.ic_empty_photo)
                .into(imageView1);
        Glide.with(this)
                .load(imageStrList.get(1))
                .placeholder(R.mipmap.ic_empty_photo)
                .into(imageView2);
        Glide.with(this)
                .load(imageStrList.get(2))
                .placeholder(R.mipmap.ic_empty_photo)
                .into(imageView3);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != WRITE_EXTERNAL_STORAGE) {
            Toast.makeText(this, "请允许获取相册图片文件写入权限", Toast.LENGTH_SHORT).show();
        }
    }

    private class ShowViewHDListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            config.setNowThumbnailIndex(imageViewList.indexOf(v));
            transferee.apply(config)
                    .show(new Transferee.OnTransfereeStateChangeListener() {
                        @Override
                        public void onShow() {
                            Glide.with(TouchMoveActivity.this).pauseRequests();
                        }

                        @Override
                        public void onDismiss() {
                            Glide.with(TouchMoveActivity.this).resumeRequests();
                        }
                    });
        }
    }

    private class TouchViewMotion implements View.OnTouchListener {
        private float preX, preY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    preX = event.getRawX();
                    preY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float diffX = event.getRawX() - preX;
                    float diffY = event.getRawY() - preY;

                    v.setX(v.getX() + diffX);
                    v.setY(v.getY() + diffY);

                    preX = event.getRawX();
                    preY = event.getRawY();
                    break;
            }
            return false;
        }
    }
}
