package com.hdcy.app.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdcy.app.R;
import com.hdcy.app.model.CommentsContent;
import com.hdcy.app.model.Replys;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.RelativeTimeUtils;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;
import com.squareup.picasso.Picasso;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by WeiYanGeorge on 2016-10-24.
 */

public class VideoCommentListAdapter  extends BaseAdapter{

    private List<CommentsContent> data = new ArrayList<>();
    List<Replys> replysList = new ArrayList<>();
    private Replys replys;
    ReplysVideoAdapter replysAdapter;

    private String replyid;
    private String targetid;
    private String target;

    TextView tv_comment_submit;
    TextView tv_comment_cancel;
    TextView tv_limit;
    EditText editText;
    AlertDialog alertDialog;

    private String content;

    private boolean isEdit = false;//是否编辑过

    private LayoutInflater mInflater;
    private Context context;

    private int actualcount;

    private OnAvatarClickListener onAvatarClickListener;

    public void setOnAvatarClickListener(OnAvatarClickListener onAvatarClickListener) {
        this.onAvatarClickListener = onAvatarClickListener;
    }


    public VideoCommentListAdapter(Context context, List<CommentsContent> data){
        super();
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
            return data.size();
    }

    @Override
    public CommentsContent getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder holder;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.item_video_comments,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setTag(position);
        setView(position, holder);
        return convertView;
    }

    private void setView(final int position, final ViewHolder holder){
        final CommentsContent item = getItem(position);
        replysList = item.getReplys();
        if(replysList.isEmpty()){
            holder.lv_replys.setVisibility(View.GONE);
        }else {
            holder.lv_replys.setVisibility(View.VISIBLE);
        }
        holder.tv_more.setTag(replysList);
        if(replysList.size() > 2){
            holder.tv_more.setVisibility(View.VISIBLE);
            holder.tv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replysList=(ArrayList<Replys>)v.getTag();
                    replysAdapter = new ReplysVideoAdapter(context, replysList);
                    holder.lv_replys.setAdapter(replysAdapter);
                    replysAdapter.addItemNum(replysList.size());
                    replysAdapter.notifyDataSetChanged();
                    holder.tv_more.setVisibility(View.GONE);
                }
            });
        }else {
            holder.tv_more.setVisibility(View.GONE);
        }

        replysAdapter = new ReplysVideoAdapter(context, replysList);
        holder.lv_replys.setTag(replysList);
        holder.lv_replys.setAdapter(replysAdapter);
        replysAdapter.setOnItemsClickListeners(new ReplysVideoAdapter.OnItemsClickListeners() {
            @Override
            public void onFrameLayout(int replyposition) {
                int tag = (int) holder.getTag();
                if (tag == position){
                    replysList =data.get(position).getReplys();
                }
                replyid = replysList.get(replyposition).getId() + "";
                target = "article";
                targetid = replysList.get(replyposition).getTargetId() + "";
                //Toast.makeText(context, "点击的位置" + replyid, Toast.LENGTH_SHORT).show();
                ShowInputDialog();
            }
        });
        holder.tv_name.setText(item.getCreaterName()+"");
        if(!BaseUtils.isEmptyString(item.getCreaterHeadimgurl())) {
            Picasso.with(context).load((item.getCreaterHeadimgurl()))
                    .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                    .resize(50, 50)
                    .into(holder.iv_avatar);
        }
        Date time = item.getCreatedTime();
        String nowdate = RelativeTimeUtils.format(time);
        holder.tv_time.setText(nowdate);
        holder.tv_comment_content.setText(item.getContent());
        holder.tv_praise_count.setText(item.getPraiseCount()+"");
        if(item.isLike()){
            holder.iv_praise.setImageResource(R.drawable.video_praise_pressed);
            // holder.iv_praise.setVisibility(View.VISIBLE);
        }else {
            holder.iv_praise.setImageResource(R.drawable.content_con_zambia_default);
            //holder.iv_praise.setVisibility(View.GONE);
        }
        actualcount = item.getPraiseCount();
        final String targetId = item.getId() + "";
        holder.iv_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,   actualcount+"点击位置"+position, Toast.LENGTH_SHORT).show();
                int tag = (int) holder.getTag();
                if(tag == position) {
                    if (item.isLike()==true) {
                        NetHelper.getInstance().UnDoPraise(targetId, new NetRequestCallBack() {
                            @Override
                            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                                holder.iv_praise.setImageResource(R.drawable.content_con_zambia_default);
                                item.setLike(false);
                                int number = actualcount;
                                holder.tv_praise_count.setText(number + "");
                            }

                            @Override
                            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

                            }

                            @Override
                            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

                            }
                        });
                    } else {
                        NetHelper.getInstance().DoPraise(targetId, new NetRequestCallBack() {
                            @Override
                            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                                holder.iv_praise.setImageResource(R.drawable.video_praise_pressed);
                                item.setLike(true);
                                int number = actualcount + 1;
                                holder.tv_praise_count.setText(number + "");

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
            }
        });
        holder.ll_layout_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) holder.getTag();
                if (tag == position){
                    if (onAvatarClickListener != null ){
                        onAvatarClickListener.onAvatar(position);
                    }
                }
            }
        });

    }

    public class ViewHolder {
        private ImageView iv_avatar;
        private ImageView iv_praise;
        private TextView tv_name, tv_praise_count, tv_time, tv_comment_content;
        private ListView lv_replys;
        private LinearLayout ly_sub_replys;
        private LinearLayout tv_more;
        private LinearLayout ll_layout_root;

        private Object tag;

        public Object getTag() {
            return tag;
        }

        public void setTag(Object tag) {
            this.tag = tag;
        }

        public ViewHolder(View itemView) {

            ButterKnife.bind(this, itemView);

            ll_layout_root = (LinearLayout) itemView.findViewById(R.id.layout_root) ;
            iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            iv_praise = (ImageView) itemView.findViewById(R.id.iv_praise);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_praise_count = (TextView) itemView.findViewById(R.id.tv_praise_count);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            ly_sub_replys = (LinearLayout) itemView.findViewById(R.id.ly_sub_reply);
            lv_replys = (ListView) itemView.findViewById(R.id.lv_replys);
            tv_comment_content = (TextView) itemView.findViewById(R.id.tv_comment_content);
            tv_more = (LinearLayout) itemView.findViewById(R.id.tv_more);

        }
    }

    public void PublishComment() {
        NetHelper.getInstance().PublishComments(targetid, content, target, replyid, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("评论成功后的数据", responseInfo.toString());
                alertDialog.dismiss();
                replys = responseInfo.getReplys();
                replysList.add(0, replys);
                replysAdapter.notifyDataSetChanged();
                Toast.makeText(context, "评论发布成功", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(context,"评论发布失败,请进入我的界面进行登录操作",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

                Toast.makeText(context,"评论发布失败,请进入我的界面进行登录操作",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnAvatarClickListener {
        void onAvatar(int position);
    }

    public void OnAvatarClickListener(OnAvatarClickListener onAvatarClickListener) {
        this.onAvatarClickListener = onAvatarClickListener;
    }

    public interface OnReplyClickListener {
        void onReplyClick(int position, Replys replys);
    }

    private boolean checkData() {
        content = editText.getText().toString();
        return true;
    }

    /**
     * 刷新控件数据
     */
    private void resetViewData() {
        int fontcount = 250 - editText.length();
        tv_limit.setText(fontcount + "");
    }

    public void ShowInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        View view = mInflater.inflate(R.layout.fragment_edit_dialog, null);

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
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        alertDialog.show();
    }

}
