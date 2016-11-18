package com.hdcy.app.fragment.first;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hdcy.app.R;
import com.hdcy.app.adapter.VideoCommentListAdapter;
import com.hdcy.app.basefragment.BaseFragment;
import com.hdcy.app.event.StartBrotherEvent;
import com.hdcy.app.fragment.second.PublishCommentFragment;
import com.hdcy.app.model.CommentsContent;
import com.hdcy.app.model.Replys;
import com.hdcy.app.model.RootListInfo;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by WeiYanGeorge on 2016-10-26.
 */

public class FirstTabVideoChatFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {

    private static final int REQ_PUBLISH_FRAGMENT = 1;
    private ListView mListView;
    private BGARefreshLayout mRefreshLayout;
    private RootListInfo rootListInfo = new RootListInfo();


    private int pagecount = 0;

    private String tagId;
    private String target;

    private ImageView iv_live_edit_button;

    private VideoCommentListAdapter mAdapter;


    private List<CommentsContent> commentsList = new ArrayList<>();
    private CommentsContent commentsContent = new CommentsContent();
    private List<Boolean> praisestatus = new ArrayList<>();


    private List<Replys> replysList = new ArrayList<>();
    private Replys replys;

    private RootListInfo rootobjet = new RootListInfo();
    private boolean isLast;

    private AlertDialog alertDialog;
    TextView tv_comment_submit;
    TextView tv_comment_cancel;
    TextView tv_limit;
    EditText editText;
    private boolean isEdit;
    private String content;
    String replyid;
    int globalposition;
    String targetid;

    private List<BaseFragment> mFragments = new ArrayList<>();

    TextView tv_activity_comment_status;



    public static FirstTabVideoChatFragment newInstance(String tagId , String target) {
        FirstTabVideoChatFragment fragment = new FirstTabVideoChatFragment();
        Bundle bundle = new Bundle();
        bundle.putString("param", tagId);
        bundle.putString("param1", target);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_tab_chat, container, false);
        Bundle bundle = getArguments();
        if (bundle != null){
            tagId = bundle.getString("param");
            target = bundle.getString("param1");
        }
        initView(view);
        initData();
        setListener();
        return view;
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        pagecount++;
        if(isLast){
            mRefreshLayout.endLoadingMore();
            Toast.makeText(getContext(), "没有更多的数据了",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            initData();
            return true;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
/*        commentsList.clear();
        pagecount = 0;
        initData();
        mRefreshLayout.endRefreshing();*/
        mRefreshLayout.endRefreshing();
    }


    private void initView(View view){
        mListView = (ListView) view.findViewById(R.id.lv_tab_chat);
        mRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(),true));
        iv_live_edit_button = (ImageView) view.findViewById(R.id.iv_edt_button);
        mAdapter  = new VideoCommentListAdapter(getContext(),commentsList);
        mListView.setAdapter(mAdapter);
        tv_activity_comment_status = (TextView) view.findViewById(R.id.tv_activity_comment_status);

    }

    private void setListener(){
        iv_live_edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyid =null;
                ShowInputDialog();
            }
        });
        mAdapter.setOnAvatarClickListener(new VideoCommentListAdapter.OnAvatarClickListener() {
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

    private void initData(){
        GetCommentSList();
    }

    private void setData(){
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.endLoadingMore();
        if(commentsList.isEmpty()){
            tv_activity_comment_status.setVisibility(View.VISIBLE);
        }else {
            tv_activity_comment_status.setVisibility(View.GONE);
        }

    }

    public void GetCommentSList(){
        NetHelper.getInstance().GetCommentsList(tagId, "video", pagecount, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                    List<CommentsContent> temp = responseInfo.getCommentsContentList();
                    commentsList.addAll(temp);
                    rootListInfo = responseInfo.getRootListInfo();
                    isLast = rootListInfo.isLast();
                    GetPraiseStatus();
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    public void GetPraiseStatus(){
        NetHelper.getInstance().GetCommentPraiseStatus(tagId, "video", pagecount, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray jsonArray = responseInfo.getDataArr();
                praisestatus = JSON.parseArray(jsonArray.toString(),Boolean.class);
                for (int i = 0; i < praisestatus.size(); i++){
                    commentsList.get(i+pagecount*10).setLike(praisestatus.get(i));
                }
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
        NetHelper.getInstance().PublishComments(tagId, content, target, replyid, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                alertDialog.dismiss();
                if (replyid == null) {
                    commentsContent = responseInfo.getCommentsContent();
                    commentsContent.setLike(false);
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
                Toast.makeText(getActivity(), "发布成功", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                alertDialog.dismiss();
                Toast.makeText(getActivity(),"评论发布失败,请进入我的界面进行登录操作",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                alertDialog.dismiss();
                Toast.makeText(getContext(), "评论发布失败,请进入我的界面进行登录操作", Toast.LENGTH_LONG).show();


            }
        });
    }

    private void ShowInputDialog() {
        AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

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




}
