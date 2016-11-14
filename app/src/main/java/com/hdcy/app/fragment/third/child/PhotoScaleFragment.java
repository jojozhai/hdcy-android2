package com.hdcy.app.fragment.third.child;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.base.BaseData;
import com.hdcy.base.BaseInfo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.squareup.picasso.Picasso;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.rong.photoview.PhotoView;
import io.rong.photoview.PhotoViewAttacher;


/**
 * Created by WeiYanGeorge on 2016-09-05.
 */

public class PhotoScaleFragment extends BaseBackFragment {

    String[] imgurls ;
    private int position;
    private ViewPager vp_photo_scale;
    private List<PhotoView> photoViewList ;

    private ImageLoader imageLoader = ImageLoader.getInstance();

    public static PhotoScaleFragment newInstance(String[] imgurls, int position){
        PhotoScaleFragment fragment = new PhotoScaleFragment();
        Bundle args = new Bundle();
        args.putStringArray("param", imgurls);
        args.putInt("param1", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_scala,container,false);
        initView(view);
        imgurls = getArguments().getStringArray("param");
        position = getArguments().getInt("param1",0);
        Log.e("photoscale",position+"");
        Log.e("scale",imgurls+"");
        initData();
        setListener();
        return view;
    }

    private void initView(View view){
        vp_photo_scale = (ViewPager) view.findViewById(R.id.vp_photo_scale);
    }

    private void initData() {
        photoViewList = new ArrayList<>();

        vp_photo_scale.setAdapter(new PhotoScaleAdapter(getContext(),imgurls));
        vp_photo_scale.setCurrentItem(position);
    }

    private void setListener() {
        vp_photo_scale.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                resetPhotoView();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class PhotoScaleAdapter extends PagerAdapter {
        /**
         * 传入的参数
         */

        private Context context;
        private String[] imgUrls;

        public PhotoScaleAdapter(Context context, String[] imgUrls ) {
            super();
            this.context = context;
            this.imgUrls = imgUrls;
        }

        @Override
        public int getCount() {
            return imgUrls.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String url = imgUrls[position];
            View view = View.inflate(context,
                    R.layout.item_photo_view_scale, null);
            final PhotoView img_img = (PhotoView) view
                    .findViewById(R.id.img_img);
            final PhotoView img_zoom = (PhotoView) view
                    .findViewById(R.id.img_zoom);
            final ProgressBar bar = (ProgressBar) view.findViewById(R.id.bar);
            final TextView tv_progress = (TextView) view
                    .findViewById(R.id.tv_progress);
            Picasso.with(context).load(url)// 图片地址
                    .placeholder(BaseInfo.PICASSO_PLACEHOLDER)// 加载过程中的图片
                    .error(BaseInfo.PICASSO_ERROR)// 加载错误的图片
                    .resize(240, 240)// 需要的图片宽高
                    .centerCrop()// 根据设置的宽高对图片进行的处理(centerCrop裁剪,centerInside缩小)
                    .tag(PhotoScaleFragment.class.getName())// TAG暂停/继续加载所需要
                    .into(img_zoom);// 图片控件
            if (url.startsWith("http")) {
                LogUtil.e(url);
                imageLoader.displayImage(url, img_img, BaseData.options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        bar.setVisibility(View.VISIBLE);
                        tv_progress.setVisibility(View.VISIBLE);
                        tv_progress.setText("0%");
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        bar.setVisibility(View.GONE);
                        tv_progress.setVisibility(View.GONE);
                        img_img.setVisibility(View.GONE);
                        img_zoom.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        img_img.setImageBitmap(loadedImage);
                        bar.setVisibility(View.GONE);
                        tv_progress.setVisibility(View.GONE);
                        img_img.setVisibility(View.VISIBLE);
                        img_zoom.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        bar.setVisibility(View.GONE);
                        tv_progress.setVisibility(View.GONE);
                        img_img.setVisibility(View.GONE);
                        img_zoom.setVisibility(View.VISIBLE);
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        float progress = (float) current / total;
                        tv_progress.setText((int) (progress * 100) + "%");
                    }
                });
            } else {
                bar.setVisibility(View.GONE);
                tv_progress.setVisibility(View.GONE);
                img_img.setVisibility(View.GONE);
                img_zoom.setVisibility(View.VISIBLE);
            }
            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            img_zoom.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

                @Override
                public void onPhotoTap(View view, float v, float v1) {
                   closePage();
                }

                @Override
                public void onOutsidePhotoTap() {

                }
            });
            img_img.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

                @Override
                public void onPhotoTap(View view, float v, float v1) {

                    closePage();
                }

                @Override
                public void onOutsidePhotoTap() {

                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    /**
     * 打开页面
     */
    private void openPage() {
        setEnterTransition(R.anim.anim_bottom_in);
        setExitTransition(R.anim.anim_null);
    }

    /**
     * 关闭页面
     */
    private void closePage() {
        _mActivity.onBackPressed();
    }
}
