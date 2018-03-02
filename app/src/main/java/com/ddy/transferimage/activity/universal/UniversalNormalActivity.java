package com.ddy.transferimage.activity.universal;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.transferee.transferee.style.index.NumberIndexIndicator;
import com.transferee.transferee.style.progress.ProgressPieIndicator;
import com.transferee.transferee.transfer.TransferConfig;
import com.transferee.transferee.transfer.Transferee;
import com.ddy.transferimage.R;
import com.ddy.transferimage.activity.BaseActivity;
import com.transferee.universalloader.UniversalImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;

public class UniversalNormalActivity extends BaseActivity {
    private DisplayImageOptions options;

    {
        thumbnailImageList = new ArrayList<>();
        thumbnailImageList.add("http://p4.so.qhmsg.com/t01e6a5e96377408709.jpg");
        thumbnailImageList.add("http://p0.so.qhimgs1.com/t01f627a39a004137ec.jpg");
        thumbnailImageList.add("http://p0.so.qhmsg.com/t01f9c724154b494425.jpg");
        thumbnailImageList.add("http://p0.so.qhimgs1.com/t01a1d06e3fb39c46e4.jpg");
        thumbnailImageList.add("http://p0.so.qhimgs1.com/t019594190e00c6c65a.jpg");
        thumbnailImageList.add("http://p2.so.qhimgs1.com/t01ccb486e64d7e9802.jpg");
        thumbnailImageList.add("http://p1.so.qhmsg.com/t0134362e4061984546.jpg");
        thumbnailImageList.add("http://p1.so.qhimgs1.com/t017f3e617fac1fdc1d.jpg");
        thumbnailImageList.add("http://ww1.sinaimg.cn/large/9be2329dgw1etlyb1yu49j20c82p6qc1.jpg");

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
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        options = new DisplayImageOptions
                .Builder()
                .showImageOnLoading(R.mipmap.ic_empty_photo)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .build();

        config = TransferConfig.build()
                .setSourceImageList(sourceImageList)
                .setThumbnailImageList(thumbnailImageList)
                .setMissPlaceHolder(R.mipmap.ic_empty_photo)
                .setErrorPlaceHolder(R.mipmap.ic_empty_photo)
                .setProgressIndicator(new ProgressPieIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setJustLoadHitImage(true)
                .setImageLoader(UniversalImageLoader.with(getApplicationContext()))
                .setOnLongClcikListener(new Transferee.OnTransfereeLongClickListener() {
                    @Override
                    public void onLongClick(ImageView imageView, int pos) {
                        saveImageByUniversal(imageView);
                    }
                })
                .create();

        gvImages.setAdapter(new UniversalNormalActivity.NineGridAdapter());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != WRITE_EXTERNAL_STORAGE) {
            Toast.makeText(this, "请允许获取相册图片文件写入权限", Toast.LENGTH_SHORT).show();
        }
    }

    private class NineGridAdapter extends CommonAdapter<String> {

        public NineGridAdapter() {
            super(UniversalNormalActivity.this, R.layout.item_grid_image, thumbnailImageList);
        }

        @Override
        protected void convert(ViewHolder viewHolder, String item, final int position) {
            final ImageView imageView = viewHolder.getView(R.id.image_view);
            ImageLoader.getInstance().displayImage(item, imageView, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    bindTransferee(imageView, position);

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                }
            });
        }
    }

    private void bindTransferee(ImageView imageView, final int position) {
        // 如果指定了缩略图，那么缩略图一定要先加载完毕
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.setNowThumbnailIndex(position);
                config.setOriginImageList(wrapOriginImageViewList(thumbnailImageList.size()));
                transferee.apply(config).show();
            }
        });
    }

}
