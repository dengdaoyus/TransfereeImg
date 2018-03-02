package com.ddy.transferimage.activity;

import android.support.v4.view.ViewPager;

import com.transferee.transferee.adapter.TransferAdapter;
import com.transferee.transferee.transfer.TransferConfig;
import com.ddy.transferimage.R;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class ImagePreviewActivity extends BaseActivity {
    private ViewPager transViewPager;
    private TransferAdapter transAdapter;
    private TransferConfig transConfig;
    @Override
    protected int getContentView() {
        return R.layout.activity_preview;
    }

    @Override
    protected void initView() {
//        viewPager = (ViewPager) findViewById(R.id.viewPager);

    }
    private void initAdapter(){
//        transAdapter = new TransferAdapter(this,
//                transConfig.getSourceImageList().size(),
//                transConfig.getNowThumbnailIndex());
//        transAdapter.setOnInstantListener(instantListener);
//
//        // 先隐藏，待 ViewPager 下标为 config.getCurrOriginIndex() 的页面创建完毕再显示
//        transViewPager.setVisibility(View.INVISIBLE);
//        transViewPager.setOffscreenPageLimit(transConfig.getOffscreenPageLimit() + 1);
//        transViewPager.setAdapter(transAdapter);
//        transViewPager.setCurrentItem(transConfig.getNowThumbnailIndex());

    }

    @Override
    protected void testTransferee() {

    }
}
