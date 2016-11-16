package com.hdcy.app.fragment.second;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.fragment.third.child.OfflineActivityDialogFragment;
import com.hdcy.app.model.CommentsContent;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

/**
 * Created by WeiYanGeorge on 2016-10-12.
 */

public class PublishCommentFragment extends BaseBackFragment {

    private Toolbar mToolbar;
    private TextView title;
    private TextView toolbar_right;

    private EditText edt_comment_content;

    private TextView tv_limit;

    private boolean isEdit;
    private String content;
    private String targetid;
    private String target;

    private CommentsContent commentsContent;



    public static PublishCommentFragment newInstance(String tagId, String target){
        PublishCommentFragment fragment = new PublishCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("param", tagId);
        bundle.putString("param1", target);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pusblish_comment, container, false);
        Bundle bundle = getArguments();
        targetid = bundle.getString("param");
        target = bundle.getString("param1");

        initView(view);

        return view;
    }

    private boolean checkData() {
        content = edt_comment_content.getText().toString();
        content.trim();
        if (BaseUtils.isEmptyString(content)||content.trim().isEmpty()) {
            Toast.makeText(getActivity(), "请输入你要发布的文字", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void resetViewData() {
        int fontcount = 250 - edt_comment_content.length();
        tv_limit.setText(fontcount + "");
    }

    private void initView(View view){

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        title = (TextView) view.findViewById(R.id.toolbar_title);
        toolbar_right = (TextView) view.findViewById(R.id.toolbar_right);
        toolbar_right.setText("发布");
        title.setText("写评论");
        toolbar_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkData()) {
                    PublishComment();
                }
            }
        });
        initToolbarNav(mToolbar);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEdit = s.length() > 0;
                resetViewData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        tv_limit = (TextView) view.findViewById(R.id.tv_limit);
        edt_comment_content = (EditText) view.findViewById(R.id.edt_comment_content);
        edt_comment_content.addTextChangedListener(textWatcher);
        edt_comment_content.requestFocus();

    }

    private void setInfo(){
        if(target == "article"){
            Bundle bundle = new Bundle();
            bundle.putString(ArticleCommentListFragment.KEY_RESULT_COMMENT, JSON.toJSONString(commentsContent));
            setFramgentResult(RESULT_OK,bundle);
            Toast.makeText(getActivity(), "评论发布成功", Toast.LENGTH_LONG).show();
            _mActivity.onBackPressed();
        }else if(target == "activity"){
            Bundle bundle = new Bundle();
            bundle.putString(OfflineActivityDialogFragment.KEY_RESULT_ACTIVITY,JSON.toJSONString(commentsContent));
            setFramgentResult(RESULT_OK,bundle);
            Toast.makeText(getActivity(), "信息发布成功", Toast.LENGTH_LONG).show();
            _mActivity.onBackPressed();
        }
    }

    public void PublishComment() {
        NetHelper.getInstance().PublishComments(targetid, content, target, null, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                CommentsContent temp = responseInfo.getCommentsContent();
                temp.setLike(false);
                Log.e("Callbackinfo",temp.getContent()+"");
                commentsContent = temp;
                setInfo();
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getActivity(), "评论发布失败", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getContext(), "评论发布失败", Toast.LENGTH_LONG).show();


            }
        });
    }
}
