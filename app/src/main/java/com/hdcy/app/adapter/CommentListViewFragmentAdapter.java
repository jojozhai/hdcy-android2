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
import com.hdcy.app.model.PraiseResult;
import com.hdcy.app.model.Replys;
import com.hdcy.base.BaseInfo;
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
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by WeiYanGeorge on 2016-09-03.
 */

public class CommentListViewFragmentAdapter extends BaseAdapter {

    private List<CommentsContent> data = new ArrayList<>();
    List<Replys> replysList = new ArrayList<>();
    List<Replys> tempreplys = new ArrayList<>();
    private Replys replys;


    private LayoutInflater mInflater;
    private Context context;


    ReplysAdapter replysAdapter;

    private boolean isPraised = false;

    private int actualcount;

    private boolean PraisedStatus;

    private PraiseResult praiseResult;


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

    private OnAvatarClickListener onAvatarClickListener;
    private OnReplyClickListener onReplyClickListener;


    public void setOnAvatarClickListener(OnAvatarClickListener onAvatarClickListener) {
        this.onAvatarClickListener = onAvatarClickListener;
    }

    public void setOnReplyClickListener(OnReplyClickListener onReplyClickListener) {
        this.onReplyClickListener = onReplyClickListener;
    }

    public CommentListViewFragmentAdapter(Context context, List<CommentsContent> data) {
        super();
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        convertView = View.inflate(context, R.layout.item_comments,null);
        holder = new ViewHolder(convertView);
        convertView.setTag(holder);
        AutoUtils.autoSize(convertView);
        holder.setTag(position);
        ((SupportActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setView(position, holder);
            }
        });
        return convertView;
    }


    private void setView(final int position, final ViewHolder holder) {
        final CommentsContent item = getItem(position);
        replysList = item.getReplys();
        Log.e("Replyssize", replysList.size() + "");
        // holder.lv_replys.setAdapter(new ReplysAdapter(context,replysList));
        if(replysList.isEmpty()){
            holder.lv_replys.setVisibility(View.GONE);
        }else {
            holder.lv_replys.setVisibility(View.VISIBLE);
        }

/*        View footview = View.inflate(context,R.layout.item_replys_showmore,null);
        holder.lv_replys.addFooterView(footview);
        final TextView tv_more = (TextView) footview.findViewById(R.id.tv_more);*/
       // holder.tv_more.setVisibility(View.GONE);

        replysAdapter = new ReplysAdapter(context, replysList);
        /*if(replysList.size()>2){
            replysAdapter.addItemNum(2);
        }else{
            replysAdapter.addItemNum(replysList.size());
        }*/
        holder.lv_replys.setTag(replysList);// set tags
        holder.lv_replys.setAdapter(replysAdapter);
        replysAdapter.setOnItemsClickListeners(new ReplysAdapter.OnItemsClickListeners() {
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
            }
        });

        holder.tv_more.setTag(replysList);
        if(replysList.size() > 2){
            holder.tv_more.setVisibility(View.VISIBLE);
            holder.tv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replysList=(ArrayList<Replys>)v.getTag();
                    replysAdapter = new ReplysAdapter(context, replysList);
                    holder.lv_replys.setAdapter(replysAdapter);
                    replysAdapter.addItemNum(replysList.size());
                    replysAdapter.notifyDataSetChanged();
                    holder.tv_more.setVisibility(View.GONE);
                }
            });
        }else {
            holder.tv_more.setVisibility(View.GONE);
        }


        holder.tv_name.setText(item.getCreaterName() + "");
        Picasso.with(context).load(item.getCreaterHeadimgurl())
                .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                .resize(50, 50)
                .centerCrop()
                .into(holder.iv_avatar);
        Date time = item.getCreatedTime();
        String nowdate = RelativeTimeUtils.format(time);
        holder.tv_time.setText(nowdate);
        Log.e("CommentContent", item.getContent() + "");
        holder.tv_comment_content.setText(item.getContent());
        holder.tv_praise_count.setText(item.getPraiseCount() + "");
        actualcount = item.getPraiseCount();
        Log.e("actualcount", actualcount + "");
        holder.iv_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) holder.getTag();
                if (tag == position) {
                    String targetId = item.getId() + "";
                    if (!isPraised) {
                        NetHelper.getInstance().DoPraise(targetId, new NetRequestCallBack() {
                            @Override
                            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                                praiseResult = responseInfo.getPraiseResult();
                                PraisedStatus = praiseResult.getContent();
                                if (PraisedStatus) {
                                    Log.e("点赞成功1", praiseResult.getContent() + "");
                                    isPraised = true;
                                    int count = actualcount + 1;
                                    Log.e("actualcount", count + "");
                                    holder.iv_praise.setImageResource(R.drawable.content_icon_zambia_pressed);
                                    holder.tv_praise_count.setText(count + "");
                                } else {
                                    Toast.makeText(context, "你已经赞过", Toast.LENGTH_SHORT).show();
                                    holder.iv_praise.setImageResource(R.drawable.content_icon_zambia_pressed);
                                    isPraised = true;
                                    return;
                                }
                            }

                            @Override
                            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

                            }

                            @Override
                            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

                            }

                        });


                    } else {
                        UndoPraise(targetId);
                        int count = actualcount;
                        if (isPraised) {
                            count = count - 1;
                            holder.tv_praise_count.setText(actualcount + "");
                        } else {
                            holder.tv_praise_count.setText(actualcount);
                        }
                        holder.iv_praise.setImageResource(R.drawable.content_con_zambia_default);
                        Toast.makeText(context, "取消赞成功", Toast.LENGTH_SHORT).show();
                        isPraised = false;
                    }
                }
            }
        });
        holder.layout_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "点击的位置" + position, Toast.LENGTH_SHORT).show();
                int tag = (int) holder.getTag();
                if (tag == position) {
                    if (onAvatarClickListener != null) {
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
        private LinearLayout ly_sub_replys,layout_root;
        private LinearLayout tv_more;

        private Object tag;

        public Object getTag() {
            return tag;
        }

        public void setTag(Object tag) {
            this.tag = tag;
        }

        public ViewHolder(View itemView) {

            ButterKnife.bind(this, itemView);

            layout_root =(LinearLayout) itemView.findViewById(R.id.layout_root);
            iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            iv_praise = (ImageView) itemView.findViewById(R.id.iv_praise);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_praise_count = (TextView) itemView.findViewById(R.id.tv_praise_count);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            ly_sub_replys = (LinearLayout) itemView.findViewById(R.id.ly_sub_reply);
            lv_replys = (ListView) itemView.findViewById(R.id.lv_replys);
            tv_more = (LinearLayout) itemView.findViewById(R.id.tv_more);
            tv_comment_content = (TextView) itemView.findViewById(R.id.tv_comment_content);

        }
    }


    public void doPraise(final String targetId) {
        NetHelper.getInstance().DoPraise(targetId, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    public void UndoPraise(final String targetId) {
        NetHelper.getInstance().UnDoPraise(targetId, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("取消赞成功1", targetId);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("取消赞失败", targetId);

            }
        });
    }

    private boolean checkData() {
        content = editText.getText().toString();
        return true;
    }

    /**
     * 刷新控件数据
     */
    private void resetViewData() {
        int fontcount = 200 - editText.length();
        tv_limit.setText(fontcount + "");
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

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {


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

}