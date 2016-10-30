package com.hdcy.app.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.hdcy.app.R;
import com.hdcy.app.adapter.VideoCommentListAdapter;
import com.hdcy.app.basefragment.BaseFragment;
import com.hdcy.app.model.CommentsContent;
import com.hdcy.app.model.Replys;
import com.hdcy.app.model.RootListInfo;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-10-26.
 */

public class FirstTabVideoChatFragment extends BaseFragment {
    private ListView mListView;

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

    private void initView(View view){
        mListView = (ListView) view.findViewById(R.id.lv_tab_chat);
        mAdapter  = new VideoCommentListAdapter(getContext(),commentsList,praisestatus);
        mListView.setAdapter(mAdapter);
        iv_live_edit_button = (ImageView) view.findViewById(R.id.iv_edt_button);

    }

    private void setListener(){
        iv_live_edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initData(){
        GetCommentSList();
    }

    private void setData(){
        mAdapter.notifyDataSetChanged();
    }

    public void GetCommentSList(){
        NetHelper.getInstance().GetCommentsList(tagId, target, pagecount, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                    List<CommentsContent> temp = responseInfo.getCommentsContentList();
                    commentsList.addAll(temp);
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
        NetHelper.getInstance().GetCommentPraiseStatus(tagId, target, 0, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray jsonArray = responseInfo.getDataArr();
                praisestatus = JSON.parseArray(jsonArray.toString(),Boolean.class);
                for (int i = 0; i < praisestatus.size(); i++){
                    commentsList.get(i).setLike(praisestatus.get(i));
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








































}
