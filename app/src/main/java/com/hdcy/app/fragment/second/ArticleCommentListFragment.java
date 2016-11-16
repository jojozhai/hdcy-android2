package com.hdcy.app.fragment.second;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hdcy.app.R;
import com.hdcy.app.adapter.CommentListViewFragmentAdapter;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.event.StartBrotherEvent;
import com.hdcy.app.model.CommentsContent;
import com.hdcy.app.model.LoginResult;
import com.hdcy.app.model.Replys;
import com.hdcy.app.model.RootListInfo;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by WeiYanGeorge on 2016-10-12.
 */

public class ArticleCommentListFragment extends BaseBackFragment implements BGARefreshLayout.BGARefreshLayoutDelegate{

    private static final int REQ_PUBLISH_FRAGMENT = 1;
    static final String KEY_RESULT_COMMENT = "articlecomment";

    private ListView mRecy;
    private BGARefreshLayout mRefreshLayout;


    private Toolbar mToolbar;
    private TextView title;
    private ImageView iv_edt_comment;
    //对主评论进行回复
    TextView tv_comment_submit;
    TextView tv_comment_cancel;
    TextView tv_limit;
    EditText editText;
    AlertDialog alertDialog;
    Button sendButton;

    private String content;

    String targetid;
    String replyid;

    int globalposition;

    private boolean isEdit;//是否编辑过


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
        setListener();
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

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        commentsList.clear();
        pagecount = 0;
        initData();
        mRefreshLayout.endRefreshing();

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        pagecount++;
        Log.e("isLastStatus",isLast+"");
        Log.e("loadTagid", tagId+"");
        if(isLast){
            mRefreshLayout.endLoadingMore();
            Toast.makeText(getActivity(), "没有更多的数据了", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            initData();
            return true;
        }
    }

    private void initView(View view) {
        mRecy = (ListView) view.findViewById(R.id.recy);
        title = (TextView) view.findViewById(R.id.toolbar_title);
        title.setText("评论");
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(),true));
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

    private void setListener(){
        mAdapter.setOnAvatarClickListener(new CommentListViewFragmentAdapter.OnAvatarClickListener() {
            @Override
            public void onAvatar(int position) {
                Log.e("replyid", position+"");
                replyid = commentsList.get(position).getId() + "";
                Log.e("replyid", replyid);
                targetid = tagId;
                globalposition = position;
                ShowInputDialog();
            }
        });
    }

    private void initData() {
        GetCommentsList();
    }

    private void setData() {
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.endLoadingMore();
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

    public void PublishComment() {
        NetHelper.getInstance().PublishComments(targetid, content, target, replyid, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                alertDialog.dismiss();
                if (replyid == null) {
                    commentsContent = responseInfo.getCommentsContent();
                    commentsList.add(0, commentsContent);
                } else {
                    Log.e("评论成功后的数据", commentsList.size()+"");
                    replys = responseInfo.getReplys();
                    replysList = commentsList.get(globalposition).getReplys();
                    replysList.add(0, replys);
                    commentsContent = commentsList.get(globalposition);
                    commentsContent.setReplys(replysList);
                    commentsList.set(globalposition,commentsContent);
                    Log.e("评论成功后的数据", commentsList.size()+"");

                }
                mAdapter.notifyDataSetChanged();
                setData();
                Toast.makeText(getActivity(), "发布成功", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("发布成功", targetid);

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getContext(), "评论发布失败", Toast.LENGTH_LONG).show();


            }
        });
    }

    private boolean checkData() {
        content = editText.getText().toString();
        if (BaseUtils.isEmptyString(content)||content.trim().isEmpty()) {
            Toast.makeText(getActivity(), "请输入你要发布的文字", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 刷新控件数据
     */
    private void resetViewData() {
        int fontcount = 250 - editText.length();
        tv_limit.setText(fontcount + "");
    }


    private void ShowInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_edit_dialog, null);

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
        tv_comment_submit = (TextView) view.findViewById(R.id.tv_submit_comment);
        tv_comment_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkData()) {
                    PublishComment();
                }
            }
        });
        tv_comment_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_comment_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        editText = (EditText) view.findViewById(R.id.edt_comment);
        editText.addTextChangedListener(textWatcher);
        editText.requestFocus();
        builder.setView(view);
        builder.create();
        alertDialog = builder.create();
        Window windowManager = alertDialog.getWindow();
        windowManager.setGravity(Gravity.BOTTOM);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        alertDialog.show();
    }






















}
