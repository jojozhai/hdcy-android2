package com.hdcy.app.fragment.third;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdcy.app.R;
import com.hdcy.app.adapter.ImageListViewAdapter;
import com.hdcy.app.adapter.OfflineActivityCommentListAdapter;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.event.StartBrotherEvent;
import com.hdcy.app.fragment.second.PublishCommentFragment;
import com.hdcy.app.fragment.third.child.OfflineActivityDialogFragment;
import com.hdcy.app.fragment.third.child.PhotoScaleFragment;
import com.hdcy.app.model.ActivityDetails;
import com.hdcy.app.model.CommentsContent;
import com.hdcy.app.model.Result;
import com.hdcy.app.view.NoScrollListView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.HListView;

import static com.hdcy.base.BaseData.URL_BASE;

/**
 * Created by WeiYanGeorge on 2016-10-16.
 */

public class OfflineActivityFragment extends BaseBackFragment {


    private static final int REQ_REGISTER_FRAGMENT = 100;
    final static String KEY_RESULT_REGISTER = "register";
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
    private ActivityDetails activityDetails;
    private Result result;
    private List<CommentsContent> commentsList = new ArrayList<>();

    private List<String> imgurls = new ArrayList<String>();

    private String htmlcontent;
    private String Url = URL_BASE + "/activityDetails.html?id=";
    private String loadurl;

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
        //GetCommentsList();
        initData();
        return view;
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
                        .withTargetUrl(loadurl+"&show=YES")
                        .withMedia(new UMImage(getContext(),activityDetails.getImage()))
                        .setListenerList(umShareListener)
                        .open();
                Toast.makeText(getActivity(),   " 分享成功啦", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }

    private void initData(){
        GetCommentsList();
        GetActivityAttendStatus();
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

            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(result.getContent() == false && activityDetails.getFinish() ==false) {
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
        tv_actvity_title.setText(activityDetails.getName()+"");
        tv_attend_count.setText(activityDetails.getHot()+"");
        tv_activity_sponsor.setText(activityDetails.getSponsorName()+"");
        SimpleDateFormat foramt = new SimpleDateFormat("yyyy年MM月dd日");
        String dateformat1 = foramt.format(activityDetails.getStartTime()).toString();
        String dateformat2 = foramt.format(activityDetails.getSignEndTime()).toString();
        tv_activity_starttime.setText(dateformat1);
        tv_activity_register_end.setText(dateformat2);
        tv_people_limits.setText(activityDetails.getPeopleLimit()+"");
        tv_activity_fee.setText(activityDetails.getPrice()+"");
        tv_activity_address.setText(activityDetails.getAddress()+"");


        mAdapter.notifyDataSetChanged();
        if(commentsList.isEmpty()){
            tv_activity_comment_status.setVisibility(View.VISIBLE);
            bt_show_more.setVisibility(View.GONE);
        }else {
            tv_activity_comment_status.setVisibility(View.GONE);
            bt_show_more.setVisibility(View.VISIBLE);
        }
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
