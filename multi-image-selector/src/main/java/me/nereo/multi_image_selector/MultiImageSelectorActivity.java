package me.nereo.multi_image_selector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

/**
 * 多图选择
 * Created by Nereo on 2015/4/7.
 * Updated by nereo on 2016/1/19.
 */
public class MultiImageSelectorActivity extends FragmentActivity implements MultiImageSelectorFragment.Callback {

    /**
     * 最大图片选择次数，int类型，默认9
     */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /**
     * 图片选择模式，默认多选
     */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /**
     * 选择类型，默认选择图片
     */
    public static final String EXTRA_SELECT_TYPE = "select_count_type";
    /**
     * 是否显示相机，默认显示
     */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /**
     * 选择结果，返回为 ArrayList<String> 图片路径集合
     */
    public static final String EXTRA_RESULT = "select_result";
    /**
     * 选择的缩略图结果，返回为 ArrayList<String> 缩略图路径集合
     */
    public static final String EXTRA_THUMB_RESULT = "select_thumb_result";
    /**
     * 默认选择集
     */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";

    /**
     * 单选
     */
    public static final int MODE_SINGLE = 0;
    /**
     * 多选
     */
    public static final int MODE_MULTI = 1;

    /**
     * 选择图片和视频
     */
    public static final int TYPE_ALL = 1;
    /**
     * 选择图片
     */
    public static final int TYPE_IMAGE = 2;
//    /**
//     * 选择视频
//     */
//    public static final int TYPE_VIDEO = 3;

    private ArrayList<String> resultList = new ArrayList<>();
    private ArrayList<String> thumbPathList = new ArrayList<>();
    private Button mSubmitButton;
    private int mDefaultCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, 9);
        int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
        int type = intent.getIntExtra(EXTRA_SELECT_TYPE, TYPE_IMAGE);
        boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        if (mode == MODE_MULTI && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
            resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        }

        Bundle bundle = new Bundle();
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, mDefaultCount);
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_TYPE, type);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow);
        bundle.putStringArrayList(MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST, resultList);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle))
                .commit();

        // 返回按钮
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        // 完成按钮
        mSubmitButton = (Button) findViewById(R.id.commit);
        if (resultList == null || resultList.size() <= 0) {
            mSubmitButton.setText(R.string.action_done);
            mSubmitButton.setEnabled(false);
        } else {
            updateDoneText();
            mSubmitButton.setEnabled(true);
        }
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resultList != null && resultList.size() > 0) {
                    // 返回已选择的图片数据
                    Intent data = new Intent();
                    data.putStringArrayListExtra(EXTRA_RESULT, resultList);
                    data.putStringArrayListExtra(EXTRA_THUMB_RESULT, thumbPathList);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }

    private void updateDoneText() {
        mSubmitButton.setText(String.format("%s(%d/%d)",
                getString(R.string.action_done), resultList.size(), mDefaultCount));
    }

    @Override
    public void onSingleImageSelected(String path, String thumbPath) {
        Intent data = new Intent();
        resultList.add(path);
        thumbPathList.add(thumbPath);
        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
        data.putStringArrayListExtra(EXTRA_THUMB_RESULT, thumbPathList);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onImageSelected(String path, String thumbPath) {
        if (!resultList.contains(path)) {
            resultList.add(path);
            thumbPathList.add(thumbPath);
        }
        // 有图片之后，改变按钮状态
        if (resultList.size() > 0) {
            updateDoneText();
            if (!mSubmitButton.isEnabled()) {
                mSubmitButton.setEnabled(true);
            }
        }
    }

    @Override
    public void onImageUnselected(String path, String thumbPath) {
        if (resultList.contains(path)) {
            resultList.remove(path);
            thumbPathList.remove(thumbPath);
        }
        updateDoneText();
        // 当为选择图片时候的状态
        if (resultList.size() == 0) {
            mSubmitButton.setText(R.string.action_done);
            mSubmitButton.setEnabled(false);
        }
    }

    @Override
    public void onCameraShot(File imageFile) {
        if (imageFile != null) {

            // notify system
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));

            Intent data = new Intent();
            resultList.add(imageFile.getAbsolutePath());
            thumbPathList.add(imageFile.getAbsolutePath());
            data.putStringArrayListExtra(EXTRA_RESULT, resultList);
            data.putStringArrayListExtra(EXTRA_THUMB_RESULT, thumbPathList);
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
