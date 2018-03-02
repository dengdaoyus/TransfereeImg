package com.ddy.transferimage.activity.glide;

import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.transferee.glideloader.GlideImageLoader;
import com.transferee.transferee.style.progress.ProgressPieIndicator;
import com.transferee.transferee.transfer.TransferConfig;
import com.transferee.transferee.transfer.Transferee;
import com.ddy.transferimage.R;
import com.ddy.transferimage.activity.BaseActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;

public class GlideNoThumActivity extends BaseActivity {

    {
        sourceImageList = new ArrayList<>();
        sourceImageList.add("http://p4.so.qhmsg.com/t01e6a5e96377408709.jpg");
        sourceImageList.add("http://p0.so.qhimgs1.com/t01f627a39a004137ec.jpg");
        sourceImageList.add("http://p0.so.qhmsg.com/t01f9c724154b494425.jpg");
        sourceImageList.add("http://p0.so.qhimgs1.com/t01a1d06e3fb39c46e4.jpg");
        sourceImageList.add("http://p0.so.qhimgs1.com/t019594190e00c6c65a.jpg");
        sourceImageList.add("http://p2.so.qhimgs1.com/t01ccb486e64d7e9802.jpg");
        sourceImageList.add("http://p1.so.qhmsg.com/t0134362e4061984546.jpg");
        sourceImageList.add("http://p1.so.qhimgs1.com/t017f3e617fac1fdc1d.jpg");
        sourceImageList.add("http://ww1.sinaimg.cn/large/9be2329dgw1etlyb1yu49j20c82p6qc1.jpg");

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_grid_view;
    }

    @Override
    protected void initView() {
        gvImages = (GridView) findViewById(R.id.gv_images);
    }

    @Override
    protected void testTransferee() {
        config = TransferConfig.build()
                .setSourceImageList(sourceImageList)
                .setMissPlaceHolder(R.mipmap.ic_empty_photo)
                .setProgressIndicator(new ProgressPieIndicator())
                .setImageLoader(GlideImageLoader.with(getApplicationContext()))
                .setOnLongClcikListener(new Transferee.OnTransfereeLongClickListener() {
                    @Override
                    public void onLongClick(ImageView imageView, int pos) {
                        saveImageByGlide(imageView);
                    }
                })
                .create();

        gvImages.setAdapter(new NineGridAdapter());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != WRITE_EXTERNAL_STORAGE) {
            Toast.makeText(this, "请允许获取相册图片文件写入权限", Toast.LENGTH_SHORT).show();
        }
    }

    private class NineGridAdapter extends CommonAdapter<String> {

        public NineGridAdapter() {
            super(GlideNoThumActivity.this, R.layout.item_grid_image, sourceImageList);
        }

        @Override
        protected void convert(ViewHolder viewHolder, String item, final int position) {
            ImageView imageView = viewHolder.getView(R.id.image_view);

            Glide.with(GlideNoThumActivity.this)
                    .load(item)
                    .centerCrop()
                    .placeholder(R.mipmap.ic_empty_photo)
                    .into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    config.setNowThumbnailIndex(position);
                    config.setOriginImageList(wrapOriginImageViewList(sourceImageList.size()));

                    transferee.apply(config).show(new Transferee.OnTransfereeStateChangeListener() {
                        @Override
                        public void onShow() {
                            Glide.with(GlideNoThumActivity.this).pauseRequests();
                        }

                        @Override
                        public void onDismiss() {
                            Glide.with(GlideNoThumActivity.this).resumeRequests();
                        }
                    });
                }
            });
        }
    }

}
