package com.hdcy.app.fragment.mine;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.event.StartBrotherEvent;
import com.hdcy.app.model.AvatarResult;
import com.hdcy.app.model.PraiseResult;
import com.hdcy.app.model.UserBaseInfo;
import com.hdcy.app.permission.PermissionsActivity;
import com.hdcy.app.permission.PermissionsChecker;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.SizeUtils;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * Created by WeiYanGeorge on 2016-10-28.
 */

public class MineInfoFragment extends BaseBackFragment implements  OnDateSetListener{

    static final String KEY_RESULT_CHOOSE_CITY = "choose_city";
    static final String KEY_RESULT_CHOOSE_CAR = "choose_car";
    static final String KEY_RESULT_CHOOSE_INTEREST = "choose_interest";
    private static final int REQUEST_SELECT_CITY = 1001;
    private static final int REQUEST_SELECT_CAR = 1002;
    private static final int REQUEST_SELECT_INTEREST = 1003;
    private static final int REQUEST_IMAGE = 2;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    private Toolbar mToolbar;
    private TextView title;

    private UserBaseInfo userBaseInfo;


    //编辑按钮
    private LinearLayout ll_mineinfo_avatar;
    private LinearLayout ll_mineinfo_nickname;
    private LinearLayout ll_mineinfo_realname;
    private LinearLayout ll_mineinfo_sex;
    private LinearLayout ll_mineinfo_phone;
    private LinearLayout ll_mineinfo_password;
    private LinearLayout ll_mineinfo_birthday;
    private LinearLayout ll_mineinfo_city;
    private LinearLayout ll_mineinfo_cartype;
    private LinearLayout ll_mineinfo_interest;


    private ImageView iv_mine_personalinfo_avatar;
    private TextView tv_mine_personalinfo_nickname;
    private TextView tv_mine_personalinfo_name;
    private TextView tv_mine_personalinfo_gender;
    private TextView tv_mine_personalinfo_phone;
    private TextView tv_mine_personalinfo_password;
    private TextView tv_mine_personalinfo_birthday;
    private TextView tv_mine_personalinfo_address;
    private TextView tv_mine_personalinfo_cartype;
    private TextView tv_mine_personalinfo_interests;
    private TimePickerDialog mDialogYearMonthDay;

    int avatarWidth;
    int avatarHeight;

    //昵称选择
    AlertDialog generalalertDialog;
    TextView tv_nickname_title;
    Button bt_general_cancel;
    Button bt_general_submit;
    EditText editText_general;

    //真实姓名
    TextView tv_mine_name;

    //性别选择
    AlertDialog sexAlertDialog;
    TextView tv_sex_man;
    TextView tv_sex_woman;
    TextView tv_sex_cancel;

    //头像选择
    AlertDialog avatarAlertDialog;
    TextView tv_avatar_camera;
    TextView tv_avatar_photo;
    TextView tv_avatar_cancel;
    ArrayList<String> images = new ArrayList<>();
    private File avatarfile;
    AvatarResult avatarResult;

    private String editType;
    private String content;
    TextView mTvTime;

    private boolean isEdit;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    private PermissionsChecker mPermissionsChecker;

    private static final int REQUEST_CODE = 0;

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){

        }
    };

    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    public static MineInfoFragment newInstance(UserBaseInfo userBaseInfo){
        Bundle args = new Bundle();
        args.putSerializable("param", userBaseInfo);
        MineInfoFragment fragment = new MineInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_info_detail,container,false);
        userBaseInfo = (UserBaseInfo) getArguments().getSerializable("param");
        Log.e("传递的个人资料",userBaseInfo.getId()+"");
        initView(view);
        mPermissionsChecker = new PermissionsChecker(getActivity());
        avatarHeight = SizeUtils.dpToPx(50);
        avatarWidth = SizeUtils.dpToPx(50);
        setListener();
        return view;
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerDialog, long millseconds) {
        String text = getDateToString(millseconds);
        mTvTime.setText(text);
    }
    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Log.e("resultok:","haha");
            List<String> selectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            File file = new File(selectPath.get(0));
            long size = file.length();

            Luban.get(getActivity())
                    .load(file)
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            Log.e("resultok:","开始");
                        }

                        @Override
                        public void onSuccess(File file) {
                            avatarfile = file;
                            UploadAvatar();
                            Log.e("resultok:","成功");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("resultok:","失败");
                        }
                    }).launch();


        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPermissionsChecker.lacksPermissions(PERMISSIONS)){
            startPermissionsActivity();
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(getActivity(), REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_SELECT_CITY && resultCode == RESULT_OK){
            editType = "city";
            content =  data.getString(KEY_RESULT_CHOOSE_CITY);
            Log.e("citychoose",content+"");
            PublishPersonalInfo();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    GetPersonalInfo();
                }
            },1000);

        }else if(requestCode == REQUEST_SELECT_CAR && resultCode == RESULT_OK){
            editType = "car";
            content = data.getString(KEY_RESULT_CHOOSE_CAR);
            Log.e("carchoose",content+"");
            PublishPersonalInfo();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    GetPersonalInfo();
                }
            },1000);

        }else if(requestCode == REQUEST_SELECT_INTEREST && resultCode ==RESULT_OK){
            editType = "tags";
            String result = data.getString(KEY_RESULT_CHOOSE_INTEREST);
            result.substring(0,result.length()-1);
            content = result;
            Log.e("interests",content+"");
            PublishPersonalInfo();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    GetPersonalInfo();
                }
            },1000);
        }
    }

    private void initView(View view){
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        title =(TextView) view.findViewById(R.id.toolbar_title);
        title.setText("个人资料");
        initToolbarNav(mToolbar);
        iv_mine_personalinfo_avatar = (ImageView) view.findViewById(R.id.iv_personal_info_avatar);
        tv_mine_personalinfo_nickname =(TextView) view.findViewById(R.id.tv_mine_personalinfo_nickname);
        tv_mine_personalinfo_name =(TextView) view.findViewById(R.id.tv_mine_personalinfo_name);
        tv_mine_personalinfo_gender =(TextView) view.findViewById(R.id.tv_mine_personalinfo_gender);
        tv_mine_personalinfo_phone =(TextView) view.findViewById(R.id.tv_mine_personalinfo_phone);
        tv_mine_personalinfo_password =(TextView) view.findViewById(R.id.tv_mine_personalinfo_password);
        tv_mine_personalinfo_birthday=(TextView) view.findViewById(R.id.tv_mine_personalinfo_birthday);
        tv_mine_personalinfo_address =(TextView) view.findViewById(R.id.tv_mine_personalinfo_address);
        tv_mine_personalinfo_cartype =(TextView) view.findViewById(R.id.tv_mine_personalinfo_cartype);
        tv_mine_personalinfo_interests =(TextView) view.findViewById(R.id.tv_mine_personalinfo_interests);

        //编辑事件
        ll_mineinfo_avatar = (LinearLayout) view.findViewById(R.id.ll_mineinfo_avatar);
        ll_mineinfo_nickname = (LinearLayout) view.findViewById(R.id.ll_mineinfo_nickname);
        ll_mineinfo_realname = (LinearLayout) view.findViewById(R.id.ll_mineinfo_realname);
        ll_mineinfo_sex = (LinearLayout) view.findViewById(R.id.ll_mineinfo_sex);
        ll_mineinfo_phone = (LinearLayout) view.findViewById(R.id.ll_mineinfo_phone);
        ll_mineinfo_password = (LinearLayout) view.findViewById(R.id.ll_mineinfo_password);
        ll_mineinfo_birthday = (LinearLayout) view.findViewById(R.id.ll_mineinfo_birthday);
        ll_mineinfo_city = (LinearLayout) view.findViewById(R.id.ll_mineinfo_city);
        ll_mineinfo_cartype = (LinearLayout) view.findViewById(R.id.ll_mineinfo_cartype);
        ll_mineinfo_interest = (LinearLayout) view.findViewById(R.id.ll_mineinfo_interest);


        setData();
    }

    private void setListener(){
        ll_mineinfo_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editType = "nickname";
                ShowNickNameDialog("编辑昵称");
            }
        });
        ll_mineinfo_realname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editType = "realname";
                ShowNickNameDialog("编辑姓名");
            }
        });

        ll_mineinfo_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("datepick","onClick");
                mDialogYearMonthDay = new TimePickerDialog.Builder()
                        .setType(Type.YEAR_MONTH_DAY)
                        .setTitleStringId("")
                        .setMinMillseconds(445555555)
                        .setCallBack(new OnDateSetListener() {
                            @Override
                            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                                String text = getDateToString(millseconds);
                                content = text;
                                editType = "birthday";
                                PublishPersonalInfo();
                                tv_mine_personalinfo_birthday.setText(text);
                            }
                        })
                        .build();
                mDialogYearMonthDay.show(getFragmentManager(),"year_month_day");

            }
        });

        ll_mineinfo_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowSexDialog();
            }
        });

        ll_mineinfo_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowUploadAvatarDialog();
            }
        });

        ll_mineinfo_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForResult(ChooseCityFragment.newInstance(),REQUEST_SELECT_CITY);
                //EventBus.getDefault().post(new StartBrotherEvent(ChooseCityFragment.newInstance()));
            }
        });

        ll_mineinfo_cartype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForResult(ChooseCarTypeFragment.newInstance(),REQUEST_SELECT_CAR);
            }
        });

        ll_mineinfo_interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForResult(ChooseInterestsFragment.newInstance(),REQUEST_SELECT_INTEREST);
            }
        });

        ll_mineinfo_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(EditPhoneFragment.newInstance()));
            }
        });
        ll_mineinfo_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(EditPasswordFragment.newInstance()));
            }
        });
    }

    private boolean checkData(){
        content = editText_general.getText().toString();
        if(BaseUtils.isEmptyString(content)||content.trim().isEmpty()){
            Toast.makeText(getActivity(), "请输入你要编辑的文字", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }

    private void setData(){
        if(!userBaseInfo.getHeadimgurl().isEmpty()) {
            Picasso.with(getContext()).load(userBaseInfo.getHeadimgurl())
                    .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                    .resize(50, 50)
                    .centerCrop()
                    .config(Bitmap.Config.RGB_565)
                    .into(iv_mine_personalinfo_avatar);
        }
        tv_mine_personalinfo_nickname.setText(userBaseInfo.getNickname()+"");
        if(userBaseInfo.getSex()=="1"){
            tv_mine_personalinfo_gender.setText("男");
        }else {
            tv_mine_personalinfo_gender.setText("女");
        }
        tv_mine_personalinfo_password.setText("********");
        tv_mine_personalinfo_name.setText(userBaseInfo.getRealname()+"");
        String address = userBaseInfo.getCity();
        if(BaseUtils.isEmptyString(address)){
            tv_mine_personalinfo_address.setText("");
        }else {
            tv_mine_personalinfo_address.setText(address+"");
        }
        String secretphone = userBaseInfo.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        tv_mine_personalinfo_phone.setText(secretphone+"");

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String date = format.format(userBaseInfo.getBirthday());
        tv_mine_personalinfo_birthday.setText(date);
        tv_mine_personalinfo_cartype.setText(userBaseInfo.getCar());
        tv_mine_personalinfo_interests.setText(userBaseInfo.getTags());
    }

    private void ShowNickNameDialog(String name){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alertdialog_edit_nickname,null);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEdit = s.length() > 0;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        tv_mine_name = (TextView) view.findViewById(R.id.tv_mine_name);
        tv_mine_name.setText(name);
        bt_general_cancel = (Button) view.findViewById(R.id.bt_nickname_cancel);
        bt_general_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generalalertDialog.dismiss();
            }
        });
        bt_general_submit = (Button) view.findViewById(R.id.bt_nickname_submit);
        bt_general_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()){
                    PublishPersonalInfo();
                }
            }
        });
        editText_general = (EditText) view.findViewById(R.id.edt_nickname);
        editText_general.addTextChangedListener(textWatcher);
        editText_general.requestFocus();
        builder.setView(view);
        builder.create();
        generalalertDialog = builder.create();
        Window windowManager = generalalertDialog.getWindow();
        windowManager.setGravity(Gravity.CENTER);
        generalalertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText_general, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        generalalertDialog.show();

    }

    private void ShowUploadAvatarDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alertdialog_edit_avatar,null);
        tv_avatar_cancel = (TextView) view.findViewById(R.id.tv_avatar_cancel);
        tv_avatar_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarAlertDialog.dismiss();
            }
        });
        tv_avatar_camera = (TextView) view.findViewById(R.id.tv_avatar_camera);
        tv_avatar_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                        && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        &&ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                            getString(R.string.mis_permission_rationale),
                            REQUEST_STORAGE_READ_ACCESS_PERMISSION);
                    requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            getString(R.string.mis_permission_rationale_write_storage),
                            REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
                    requestPermission(Manifest.permission.CAMERA,
                            getString(R.string.mis_permission_rationale_write_storage),
                            REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);

                }else {
                    MultiImageSelector.create(getContext())
                            .single()
                            .showCamera(true)
                            .origin(images)
                            .count(1)
                            .start(getTopFragment(), REQUEST_IMAGE);
                }
            }
        });
        tv_avatar_photo = (TextView) view.findViewById(R.id.tv_avatar_photos);
        tv_avatar_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                        && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        &&ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                            getString(R.string.mis_permission_rationale),
                            REQUEST_STORAGE_READ_ACCESS_PERMISSION);
                    requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            getString(R.string.mis_permission_rationale_write_storage),
                            REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
                    requestPermission(Manifest.permission.CAMERA,
                            getString(R.string.mis_permission_rationale_write_storage),
                            REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);

                }else {
                    MultiImageSelector.create(getContext())
                            .single()
                            .showCamera(true)
                            .origin(images)
                            .count(1)
                            .start(getTopFragment(), REQUEST_IMAGE);
                }
            }
        });

        builder.setView(view);
        builder.create();
        avatarAlertDialog = builder.create();
        Window windowManager = avatarAlertDialog.getWindow();
        windowManager.setGravity(Gravity.BOTTOM);
        avatarAlertDialog.show();
    }

    private void ShowSexDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alertdialog_edit_sex,null);
        tv_sex_cancel = (TextView) view.findViewById(R.id.tv_sex_cancel);
        tv_sex_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexAlertDialog.dismiss();
            }
        });
        tv_sex_man = (TextView) view.findViewById(R.id.tv_sex_man);
        tv_sex_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editType = "sex";
                content = "2";
                PublishPersonalInfo();
                tv_mine_personalinfo_gender.setText("女");
                sexAlertDialog.dismiss();
            }
        });
        tv_sex_woman = (TextView) view.findViewById(R.id.tv_sex_woman);
        tv_sex_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editType = "sex";
                content = "1";
                PublishPersonalInfo();
                tv_mine_personalinfo_gender.setText("男");
                sexAlertDialog.dismiss();

            }
        });

        builder.setView(view);
        builder.create();
        sexAlertDialog = builder.create();
        Window windowManager = sexAlertDialog.getWindow();
        windowManager.setGravity(Gravity.BOTTOM);
        sexAlertDialog.show();

    }

    private void PublishPersonalInfo(){
        NetHelper.getInstance().EditMineInfomation(editType, content, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getActivity(), "修改成功!", Toast.LENGTH_SHORT).show();
                if(generalalertDialog !=null) {
                    generalalertDialog.dismiss();
                }
                GetPersonalInfo();
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    private void GetPersonalInfo(){
        NetHelper.getInstance().GetCurrentUserInfo(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                userBaseInfo = responseInfo.getUserBaseInfo();
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

    private void UploadAvatar(){
        NetHelper.getInstance().UploadAvatar(avatarfile, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                avatarResult = responseInfo.getAvatarResult();
                editType = "headimgurl";
                content = avatarResult.getContent();
                PublishPersonalInfo();
                avatarAlertDialog.dismiss();

            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    private void requestPermission(final String permission, String rationale, final int requestCode){
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)){
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
