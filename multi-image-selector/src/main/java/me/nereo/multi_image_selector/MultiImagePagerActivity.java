package me.nereo.multi_image_selector;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.rong.photoview.PhotoView;
import me.nereo.multi_image_selector.utils.ScreenUtils;

/**
 * 多图选择预览
 * Created by lishilin on 2016/4/8.
 */
public class MultiImagePagerActivity extends FragmentActivity {
    public static final String TAG = MultiImagePagerActivity.class.getName();

    private View btn_back;
    private TextView tv_title;
    private Button commit;
    private ViewPager vp_photo;
    private ImageView checkmark;

    private int width, height;

    /**
     * 图片已选择集合
     */
    private ArrayList<String> resultList;
    /**
     * 缩略图集合
     */
    private ArrayList<String> thumbPathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        Intent intent = getIntent();
        resultList = intent.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
        thumbPathList = intent.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_THUMB_RESULT);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        btn_back = findViewById(R.id.btn_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        commit = (Button) findViewById(R.id.commit);
        vp_photo = (ViewPager) findViewById(R.id.vp_photo);
        checkmark = (ImageView) findViewById(R.id.checkmark);

        commit.setVisibility(View.GONE);
    }

    private void setListener() {
        // 返回按钮
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        vp_photo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetViewData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        Point point = ScreenUtils.getScreenSize(this);
        width = point.x;
        height = point.y - getResources().getDimensionPixelOffset(R.dimen.base_title_height);

        resetViewData();

        vp_photo.setAdapter(new ImagePagerAdapter());
    }

    /**
     * 刷新View数据
     */
    private void resetViewData() {
        tv_title.setText(String.format("%d/%d", vp_photo.getCurrentItem() + 1, resultList.size()));
    }

    private class ImagePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(container.getContext(), R.layout.item_image_pager, null);
            PhotoView photoView = (PhotoView) view.findViewById(R.id.img_zoom);
            ImageView img_play = (ImageView) view.findViewById(R.id.img_play);
            String url = "file://" + thumbPathList.get(position);
            Picasso.with(container.getContext())
                    .load(url)
                    .placeholder(R.drawable.default_error)
                    .tag(MultiImagePagerActivity.TAG)
                    .resize(width, height)
                    .centerInside()
                    .into(photoView);

            final String path = resultList.get(position);
            if (path.endsWith(".mp4")) {
                img_play.setVisibility(View.VISIBLE);
            } else {
                img_play.setVisibility(View.GONE);
            }
            img_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doViewVideo(path);
                }
            });
            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    /**
     * 查看视频
     *
     * @param path
     */
    private void doViewVideo(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse(path);
        intent.setDataAndType(data, "video/*");
        startActivity(intent);
    }

}
