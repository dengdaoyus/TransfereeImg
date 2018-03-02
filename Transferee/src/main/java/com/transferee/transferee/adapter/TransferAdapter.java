package com.transferee.transferee.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.transferee.transferee.R;
import com.transferee.transferee.transfer.TransferConfig;
import com.transferee.transferee.transfer.TransferLayout;
import com.transferee.transferee.view.image.TransferImage;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * 展示大图组件 ViewPager 的图片数据适配器
 * <p>
 * Created by hitomi on 2017/1/23.
 * <p>
 * email: 196425254@qq.com
 */
public class TransferAdapter extends PagerAdapter {

    private TransferLayout transfer;
    private int showIndex;
    private int imageSize;

    private OnInstantiateItemListener onInstantListener;
    private SparseArray<FrameLayout> containLayoutArray;
    private SparseArray<TransferImage> transferImageSparseArray;

    public TransferAdapter(TransferLayout transfer, int imageSize, int nowThumbnailIndex) {
        this.transfer = transfer;
        this.imageSize = imageSize;
        this.showIndex = nowThumbnailIndex + 1 == imageSize
                ? nowThumbnailIndex - 1 : nowThumbnailIndex + 1;
        this.showIndex = showIndex < 0 ? 0 : showIndex;
        containLayoutArray = new SparseArray<>();
        transferImageSparseArray = new SparseArray<>();
    }

    @Override
    public int getCount() {
        return imageSize;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    /**
     * 获取指定索引页面中的 TransferImage
     *
     * @param position
     * @return
     */
    public TransferImage getImageItem(int position) {
//        TransferImage transImage = null;
//        FrameLayout parentLayout = containLayoutArray.get(position);
//        if (parentLayout != null) {
//            int childCount = parentLayout.getChildCount();
//            for (int i = 0; i < childCount; i++) {
//                View view = parentLayout.getChildAt(i);
//                if (view instanceof ImageView) {
//                    transImage = (TransferImage) view;
//                    break;
//                }
//            }
//        }
        return transferImageSparseArray.get(position);
    }

    public FrameLayout getParentItem(int position) {
        return containLayoutArray.get(position);
    }

    public void setOnInstantListener(OnInstantiateItemListener listener) {
        this.onInstantListener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // ViewPager instantiateItem 顺序：按 position 递减 OffscreenPageLimit，
        // 再从 positon 递增 OffscreenPageLimit 的次序创建页面
//        FrameLayout parentLayout = containLayoutArray.get(position);
//        if (parentLayout == null) {
//            parentLayout = newParentLayout(container, position);
//            containLayoutArray.put(position, parentLayout);
//            if (position == showIndex && onInstantListener != null)
//                onInstantListener.onComplete();
//        }
//        container.addView(parentLayout);

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photoview, null);
        final TransferImage transferImage = (TransferImage) view.findViewById(R.id.imageView);
        containLayoutArray.put(position, (FrameLayout) view);
        transferImageSparseArray.put(position,transferImage);
        displayImage(transferImage, position);
        if (position == showIndex && onInstantListener != null) onInstantListener.onComplete();
        container.addView(view);
        return view;
    }

    private void displayImage(TransferImage imageView, int pos) {
        imageView.setZoomEnabled(true);
        imageView.getImageZoomer().setReadMode(true);
        imageView.setBlockDisplayLargeImageEnabled(true);
        TransferConfig config = transfer.getTransConfig();
        imageView.setDuration(config.getDuration());
        imageView.setBackgroundColor(config.getBackgroundColor());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        if (config.isJustLoadHitImage())
            transfer.getTransferState(pos).prepareTransfer(imageView, pos);
    }

    @NonNull
    private FrameLayout newParentLayout(ViewGroup container, final int pos) {
        Context context = container.getContext();
        TransferConfig config = transfer.getTransConfig();

        // create inner ImageView
        TransferImage imageView = new TransferImage(context);
        imageView.setDuration(config.getDuration());
        imageView.setBackgroundColor(config.getBackgroundColor());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));

        // create outer ParentLayout
        FrameLayout parentLayout = new FrameLayout(context);
        parentLayout.setLayoutParams(new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        parentLayout.addView(imageView);

        if (config.isJustLoadHitImage())
            transfer.getTransferState(pos).prepareTransfer(imageView, pos);

        return parentLayout;
    }

    public  interface OnInstantiateItemListener {
        void onComplete();
    }

}
