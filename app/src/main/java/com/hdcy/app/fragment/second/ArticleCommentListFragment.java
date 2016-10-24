package com.hdcy.app.fragment.second;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hdcy.app.R;
import com.hdcy.app.adapter.CommentListViewFragmentAdapter;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.event.StartBrotherEvent;
import com.hdcy.app.model.CommentsContent;
import com.hdcy.app.model.Replys;
import com.hdcy.app.model.RootListInfo;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-10-12.
 */

public class ArticleCommentListFragment extends BaseBackFragment {

    private static final int REQ_PUBLISH_FRAGMENT = 1;
    static final String KEY_RESULT_COMMENT = "comment";

    private ListView mRecy;

    private Toolbar mToolbar;
    private TextView title;
    private ImageView iv_edt_comment;

    private String tagId;
    private String target;

    private int pagecount =0;

    private List<CommentsContent> commentsList = new ArrayList<>();
    private CommentsContent commentsContent = new CommentsContent();

    private List<Replys> replysList = new ArrayList<>();
    private Replys replys;

    private RootListInfo rootobjet = new RootListInfo();
    private boolean isLast;

    private CommentListViewFragmentAdapter mAdapter;

    public static ArticleCommentListFragment newInstance(String tagId, String target){
        ArticleCommentListFragment fragment = new ArticleCommentListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("param", tagId);
        bundle.putString("param1", target);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commentlist, container,false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tagId = bundle.getString("param");
            target = bundle.getString("param1");
        }
        initView(view);
        initData();
        return view;

    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(requestCode == REQ_PUBLISH_FRAGMENT && resultCode == RESULT_OK && data != null){
            Log.e("commentlistsize",commentsList.size()+"");
            commentsContent = JSON.parseObject(data.getString(KEY_RESULT_COMMENT),CommentsContent.class);
            commentsList.add(0,commentsContent);
            Log.e("commentlistsizeafter",commentsList.size()+"");
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initView(View view) {
        mRecy = (ListView) view.findViewById(R.id.recy);
        title = (TextView) view.findViewById(R.id.toolbar_title);
        title.setText("评论");
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        iv_edt_comment = (ImageView) view.findViewById(R.id.iv_edt_button);
        initToolbarNav(mToolbar);
        mAdapter = new CommentListViewFragmentAdapter(getContext(),commentsList);
        mRecy.setAdapter(mAdapter);

        iv_edt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForResult(PublishCommentFragment.newInstance(tagId+"","article"),REQ_PUBLISH_FRAGMENT);
                //EventBus.getDefault().post(new StartBrotherEvent(PublishCommentFragment.newInstance(tagId + "", "article")))
            }
        });

    }

    private void initData() {
        GetCommentsList();
    }

    private void setData() {
        mAdapter.notifyDataSetChanged();
    }


    public void GetCommentsList() {
        NetHelper.getInstance().GetCommentsList(tagId,target, pagecount, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                List<CommentsContent> commentListFragmentListtemp = responseInfo.getCommentsContentList();
                commentsList.addAll(commentListFragmentListtemp);
                rootobjet = responseInfo.getRootListInfo();
                isLast = rootobjet.isLast();
                Log.e("CommentisLast",isLast+""+tagId);
                Log.e("CommentListsize", commentsList.size() + "");
                setData();
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }





















}
