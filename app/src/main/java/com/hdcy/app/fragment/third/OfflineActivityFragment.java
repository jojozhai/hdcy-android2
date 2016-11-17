package com.hdcy.app.fragment.third;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hdcy.app.R;
import com.hdcy.app.adapter.ImageListViewAdapter;
import com.hdcy.app.adapter.OfflineActivityCommentListAdapter;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.event.StartBrotherEvent;
import com.hdcy.app.fragment.Message.MessageFragment;
import com.hdcy.app.fragment.second.PublishCommentFragment;
import com.hdcy.app.fragment.third.child.OfflineActivityDialogFragment;
import com.hdcy.app.fragment.third.child.PhotoScaleFragment;
import com.hdcy.app.model.ActivityDetails;
import com.hdcy.app.model.CommentsContent;
import com.hdcy.app.model.Result;
import com.hdcy.app.model.RootListInfo;
import com.hdcy.app.view.NoScrollListView;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xutils.common.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.HListView;

import static com.hdcy.app.fragment.third.child.OfflineActivityDialogFragment.KEY_RESULT_ACTIVITY;
import static com.hdcy.base.BaseData.URL_BASE;

/**
 * Created by WeiYanGeorge on 2016-10-16.
 */

public class OfflineActivityFragment extends BaseBackFragment{

    public static final int REQ_PUBLISH_FRAGMENT = 1;
    private static final int REQ_REGISTER_FRAGMENT = 100;
    static final String KEY_RESULT_REGISTER = "register";
    private boolean isRegister;

    private Toolbar mToolbar;
    private TextView title;
    private TextView tv_desc_html;
    private TextView tv_actvity_title;
    private TextView tv_attend_count;
    private TextView tv_activity_sponsor;
    private TextView tv_activity_starttime;
    private TextView tv_activity_register_end;
    private TextView tv_people_limits;
    private TextView tv_activity_fee;
    private TextView tv_activity_address;
    private ImageView iv_activity_phone;
    private ImageView iv_activity_comment;
    private TextView tv_more_comment;
    private TextView tv_activity_comment_status;
    private ImageView fl_activity_dialog;
    private TextView tv_activity_ask;


    private String target="activity";


    //客服dialog
    private TextView tv_activity_waiter;
    private TextView tv_activity_waiter_phone;
    private ImageView iv_call_phone;
    private ImageView iv_cal_cancel;

    private Button button_submit;
    private Button bt_register;
    private Button bt_cancel;
    private Button bt_show_more;

    private HListView lv_imgs;
    private NoScrollListView lv_activity_comment;

   // private ActivityCommentListAdapter mAdapter;

    ExpandableTextView expTv1;

    private ImageListViewAdapter imageListViewAdapter;
    private OfflineActivityCommentListAdapter mAdapter;

    private int pagecount = 0;

    private String activityid;
    private ActivityDetails activityDetails = new ActivityDetails();
    private Result result;
    private List<CommentsContent> commentsList = new ArrayList<>();
    private CommentsContent commentsContent =new CommentsContent();
    private RootListInfo rootListInfo = new RootListInfo();

    private List<String> imgurls = new ArrayList<String>();

    private String htmlcontent;
    private String Url = URL_BASE + "/views/activityDetail.html?id=";
    private String loadurl;

    private AlertDialog alertDialogphone;

    private FrameLayout mProgressBar;


    public static OfflineActivityFragment newInstance(String ActivityId){
        OfflineActivityFragment fragment = new OfflineActivityFragment();
        Bundle args = new Bundle();
        args.putString("param", ActivityId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline_activity, container, false);
        activityid = getArguments().getString("param");
        loadurl = Url + activityid;
        initView(view);
        initData();
        setListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(requestCode == REQ_REGISTER_FRAGMENT && resultCode == RESULT_OK && data !=null){
            isRegister = data.getBoolean(KEY_RESULT_REGISTER);
            if(isRegister){
                activityDetails.setFinish(false);
                button_submit.setBackgroundResource((R.color.main_font_gray_2));
                button_submit.setText("已报名");
            }
        }
        if(requestCode == REQ_PUBLISH_FRAGMENT && resultCode == RESULT_OK && data != null){
            commentsContent = JSON.parseObject(data.getString(KEY_RESULT_ACTIVITY),CommentsContent.class);
            commentsList.add(0,commentsContent);
            Log.e("commentlistsizeafter",commentsList.size()+"");
            mAdapter.notifyDataSetChanged();
        }
        //if(requestCode== )
    }

    private void initView(View view){
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        title = (TextView) view.findViewById(R.id.toolbar_title);
        title.setText("线下活动");
        initToolbarNav(mToolbar);
        mToolbar.inflateMenu(R.menu.hierachy);
        lv_activity_comment = (NoScrollListView) view.findViewById(R.id.lv_activity_comment);
        lv_activity_comment.setFocusable(false);
        tv_actvity_title = (TextView) view.findViewById(R.id.tv_activity_detail_title);
        tv_desc_html = (TextView) view.findViewById(R.id.expandable_text);
        lv_imgs = (HListView) view.findViewById(R.id.lv_imgs_list);
        tv_attend_count = (TextView) view.findViewById(R.id.tv_attend_count);
        tv_activity_sponsor = (TextView) view.findViewById(R.id.tv_activity_sponsor);
        tv_activity_starttime = (TextView) view.findViewById(R.id.tv_activity_starttime);
        tv_activity_register_end = (TextView) view.findViewById(R.id.tv_activity_register_end);
        tv_people_limits = (TextView) view.findViewById(R.id.tv_people_limits);
        tv_activity_fee = (TextView) view.findViewById(R.id.tv_activity_fee);
        tv_activity_comment_status =(TextView) view.findViewById(R.id.tv_activity_comment_status);
        tv_activity_address = (TextView) view.findViewById(R.id.tv_activity_address);
        bt_show_more = (Button) view.findViewById(R.id.tv_show_more);
        iv_activity_phone = (ImageView) view.findViewById(R.id.iv_activity_phone);
        fl_activity_dialog = (ImageView) view.findViewById(R.id.iv_activity_fl_bt);
        tv_activity_ask = (TextView) view.findViewById(R.id.tv_activity_ask);
        mProgressBar = (FrameLayout) view.findViewById(R.id.progress);

        //iv_activity_comment = (ImageView) view.findViewById(R.id.iv_activity_comment);
        button_submit = (Button) view.findViewById(R.id.bt_send);

        mAdapter = new OfflineActivityCommentListAdapter(getContext(),commentsList, "limit");
        lv_activity_comment.setAdapter(mAdapter);

        bt_show_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(OfflineActivityDialogFragment.newInstance(activityid)));
            }
        });

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new ShareAction(getActivity()).setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withTitle(activityDetails.getName())
                        .withText("好多车友")
                        .withTargetUrl(loadurl)
                        .withMedia(new UMImage(getContext(),activityDetails.getImage()))
                        .setListenerList(umShareListener)
                        .open();
                return false;
            }
        });

        iv_activity_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPhoneAlertDialog();
            }
        });


    }


    public void doCallPhone(String phone) {
        if(BaseUtils.isEmptyString(phone)){
            Toast.makeText(getActivity(), "客服电话为空", Toast.LENGTH_LONG).show();
            return;
        }
        LogUtil.i("doCallPhone：" + phone);
        final String finalPhone = phone.startsWith("tel:") ? phone : ("tel:" + phone);
        LogUtil.i("doCallPhone finalPhone：" + finalPhone);
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(finalPhone));
        startActivity(intent);
    }

    private void initData(){
        GetCommentsList();
        GetActivityAttendStatus();
    }

    private void setListener(){
        fl_activity_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForResult(PublishCommentFragment.newInstance(activityid, target),REQ_PUBLISH_FRAGMENT );
            }
        });
    }

    private void setData(){
        htmlcontent = activityDetails.getDesc();
        ActivityDetails temp = activityDetails;
        Document document = Jsoup.parse(htmlcontent);
        String htmlcontent = document.select("p").text();

        ExpandableTextView expTv1 = (ExpandableTextView) getView().findViewById(R.id.expand_text_view);
        expTv1.setText(htmlcontent);
        imgurls = activityDetails.getImages();
        imageListViewAdapter = new ImageListViewAdapter(getContext(), imgurls);
        lv_imgs.setAdapter(imageListViewAdapter);

        lv_imgs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String imgUrls[] =new String[imgurls.size()];
                for(int t =0 ; t< imgurls.size(); t++ ){
                    String str = imgurls.get(t);
                    imgUrls[t] = str;
                }
                EventBus.getDefault().post(new StartBrotherEvent(PhotoScaleFragment.newInstance(imgUrls, i)));
                //EventBus.getDefault().post(new StartBrotherEvent(MessageFragment.newInstance()));

            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(result.getContent() == false && activityDetails.getFinish() ==false || activityDetails.isSignFinish() ==true) {
                    startForResult(RegisterActivityFragment.newInstance(activityid),REQ_REGISTER_FRAGMENT);
                }else {
                    if(activityDetails.getFinish()==false){
                        Toast.makeText(getActivity(), "您已完成报名!", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getActivity(), "活动已结束!", Toast.LENGTH_LONG).show();
                    }
                    return;
                }

            }
        });

        //填充数据
        tv_activity_ask.setText("("+rootListInfo.getTotalElements()+")");
        tv_actvity_title.setText(activityDetails.getName()+"");
        tv_attend_count.setText(activityDetails.getSignCount()+"");
        tv_activity_sponsor.setText(activityDetails.getSponsorName()+"");
        SimpleDateFormat foramt = new SimpleDateFormat("yyyy年MM月dd日");
        String dateformat1 = foramt.format(activityDetails.getStartTime()).toString();
        String dateformat2 = foramt.format(activityDetails.getSignEndTime()).toString();
        tv_activity_starttime.setText(dateformat1);
        tv_activity_register_end.setText(dateformat2);
        tv_people_limits.setText(activityDetails.getPeopleLimit()+"");
        if(activityDetails.getPrice() ==0.0){
            tv_activity_fee.setText("免费");
        }else {
            tv_activity_fee.setText(activityDetails.getPrice()+"");
        }

        tv_activity_address.setText(activityDetails.getProvince()+activityDetails.getCity()+activityDetails.getAddress()+"");

        mAdapter.notifyDataSetChanged();
        if(commentsList.isEmpty()){
            tv_activity_comment_status.setVisibility(View.VISIBLE);
            bt_show_more.setVisibility(View.GONE);
        }else {
            tv_activity_comment_status.setVisibility(View.GONE);
            bt_show_more.setVisibility(View.VISIBLE);
        }
        if(activityDetails.isSignFinish() ==true&& result.getContent() == false&&activityDetails.getFinish()==false ){
            button_submit.setBackgroundResource((R.color.main_font_gray_2));
            button_submit.setText("报名已截止");
            button_submit.setClickable(false);
        }

        mProgressBar.setVisibility(View.GONE);
    }

    private void ShowPhoneAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alertdialog_phone,null);
        tv_activity_waiter = (TextView) view.findViewById(R.id.tv_activity_waiter);
        tv_activity_waiter_phone = (TextView) view.findViewById(R.id.tv_activity_waiter_phone);
        iv_call_phone = (ImageView) view.findViewById(R.id.iv_call_phone);
        iv_cal_cancel = (ImageView) view.findViewById(R.id.iv_call_cancel);
        tv_activity_waiter.setText(activityDetails.getWaiterInfo().getName()+"");
        tv_activity_waiter_phone.setText(activityDetails.getWaiterInfo().getPhone()+"");
        builder.setView(view);
        builder.create();
        alertDialogphone = builder.create();
        Window wm = alertDialogphone.getWindow();
        wm.setGravity(Gravity.CENTER);

        iv_cal_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogphone.dismiss();
            }
        });
        iv_call_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCallPhone(activityDetails.getWaiterInfo().getPhone()+"");
            }
        });
        alertDialogphone.show();

    }



    private void GetActivityDetails(){
        NetHelper.getInstance().GetActivityDeatil(activityid, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                ActivityDetails temp = responseInfo.getActivityDetails();
                activityDetails = temp;
                Log.e("activityinfo", activityDetails.getId()+"");

                if(activityDetails.getFinish() == true){
                    button_submit.setBackgroundResource((R.color.main_font_gray_2));
                    button_submit.setText("已结束");
                }
                Log.e("activitydetail", activityDetails.getName()+"");
                setData();
                //setData();
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    public void GetCommentsList() {
        NetHelper.getInstance().GetCommentsList(activityid,target, pagecount, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                if (commentsList.isEmpty()) {
                    List<CommentsContent> commentListFragmentListtemp = responseInfo.getCommentsContentList();
                    commentsList.addAll(commentListFragmentListtemp);
                    Log.e("CommentListsize", commentsList.size() + "");
                    rootListInfo = responseInfo.getRootListInfo();
                    GetActivityDetails();
                }

                //setData();
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    public void GetActivityAttendStatus(){
        NetHelper.getInstance().GetCurrentPaticipationStatus(activityid, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                result =responseInfo.getResultinfo();
                if(result.getContent() == true){
                    button_submit.setBackgroundResource((R.color.main_font_gray_2));
                    button_submit.setText("已报名");
                }
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            com.umeng.socialize.utils.Log.d("plat","platform"+platform);
            Toast.makeText(getActivity(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(),platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                com.umeng.socialize.utils.Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(),platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


}
