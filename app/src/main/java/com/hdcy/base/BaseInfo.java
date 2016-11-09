package com.hdcy.base;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.hdcy.app.model.UserBaseInfo;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.DBHelper;
import com.hdcy.base.utils.DateUtil;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.hdcy.base.utils.DateUtil.str2Date;

/**
 * Created by WeiYanGeorge on 2016-08-09.
 */

public class BaseInfo implements BaseData {
    /**
     * 版本名
     */
    public static String version_name;
    /**
     * 版本号
     */
    public static int version_code = 0;

    /**
     * 包名
     */
    public static String package_name;

    /**
     * 渠道名
     */
    public static String channel_name;

    /**
     * 网络错误提示
     */
    public static String net_error_tips;
    /**
     * 默认保存路径
     */
    public static File savePath;

    /**
     * 我的个人信息
     */
    public static UserBaseInfo me;

    SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");

    public static Date defaultdate = str2Date("1980-01-01 00:00:00");

    /**
     *获取我的个人信息
     */
    public static UserBaseInfo getMe(){
        if (me == null){
            me = new UserBaseInfo();
        }
        me.setId(DBHelper.getIntData(DBHelper.KEY_USER_ID, 0));
        me.setUsername(DBHelper.getStringData(DBHelper.KEY_USER_NAME,null));
        me.setPassword(DBHelper.getStringData(DBHelper.KEY_USER_PASSWORD,null));
        me.setNickname(DBHelper.getStringData(DBHelper.KEY_USER_NICKNAME,null));
        me.setRealname(DBHelper.getStringData(DBHelper.KEY_USER_REALNAME,null));
        me.setMobile(DBHelper.getStringData(DBHelper.KEY_USER_MOBILE,null));
        me.setSex(DBHelper.getStringData(DBHelper.KEY_USER_SEX,null));
        me.setWeixin(DBHelper.getStringData(DBHelper.KEY_USER_WEIXIN,null));
        me.setWeixinOpenId(DBHelper.getStringData(DBHelper.KEY_USER_WEXIN_OPEN_ID,null));
        me.setWeixinUnionId(DBHelper.getStringData(DBHelper.KEY_USER_WEIXIN_UNION_ID,null));
        me.setCity(DBHelper.getStringData(DBHelper.KEY_USER_CITY,null));
        me.setCountry(DBHelper.getStringData(DBHelper.KEY_USER_COUNTRY,null));
        me.setProvince(DBHelper.getStringData(DBHelper.KEY_USER_PROVINCE,null));
        me.setAddress(DBHelper.getStringData(DBHelper.KEY_USER_ADDRESS,null));
        me.setHeadimgurl(DBHelper.getStringData(DBHelper.KEY_USER_HEADIMGURL,null));
        me.setPoint(DBHelper.getIntData(DBHelper.KEY_USER_POINT,0));
        me.setUnreadMessages(DBHelper.getIntData(DBHelper.KEY_USER_UNREAD_MESSAGES,0));
        me.setBeans(DBHelper.getIntData(DBHelper.KEY_USER_BEANS,0));
        me.setLevel(DBHelper.getStringData(DBHelper.KEY_USER_LEVEL,null));
        me.setTags(DBHelper.getStringData(DBHelper.KEY_USER_TAGS,null));
        me.setBirthday(DBHelper.getDateData(DBHelper.KEY_USER_birthday,defaultdate));
        me.setVipExpired(DBHelper.getDateData(DBHelper.KEY_USER_VIPEXPIRED,defaultdate));
        me.setVipValid(DBHelper.getBooleanData(DBHelper.KEY_USER_VIPVALID,false));
        me.setVip(DBHelper.getBooleanData(DBHelper.KEY_USER_VIP,false));
        me.setCar(DBHelper.getStringData(DBHelper.KEY_USER_CAR,null));
        me.setMoney(DBHelper.getIntData(DBHelper.KEY_USER_MONEY,0));
        me.setMoneyPlus(DBHelper.getIntData(DBHelper.KEY_USER_MONEYPLUS,0));
        me.setJob(DBHelper.getStringData(DBHelper.KEY_USER_JOB,null));
        me.setParticipationCount(DBHelper.getIntData(DBHelper.KEY_USER_PARTICIPATIONCOUNT,0));
        return null;
    }
    /**
     * 设置我的个人信息
     */

    public static void setMe(UserBaseInfo userBaseInfo){
        me = userBaseInfo;
        me.setHeadimgurl(userBaseInfo.getHeadimgurl());
        DBHelper.putIntData(DBHelper.KEY_USER_ID,me.getId());
        DBHelper.putStringData(DBHelper.KEY_USER_NAME,me.getUsername());
        DBHelper.getStringData(DBHelper.KEY_USER_PASSWORD,me.getPassword());
        DBHelper.getStringData(DBHelper.KEY_USER_NICKNAME, me.getNickname());
        DBHelper.getStringData(DBHelper.KEY_USER_REALNAME, me.getRealname());
        DBHelper.getStringData(DBHelper.KEY_USER_MOBILE, me.getMobile());
        DBHelper.getStringData(DBHelper.KEY_USER_SEX , me.getSex());
        DBHelper.getStringData(DBHelper.KEY_USER_WEIXIN, me.getWeixin());
        DBHelper.getStringData(DBHelper.KEY_USER_WEXIN_OPEN_ID, me.getWeixinOpenId());
        DBHelper.getStringData(DBHelper.KEY_USER_WEIXIN_UNION_ID , me.getWeixinUnionId());
        DBHelper.getStringData(DBHelper.KEY_USER_CITY , me.getCity());
        DBHelper.getStringData(DBHelper.KEY_USER_COUNTRY, me.getCountry());
        DBHelper.getStringData(DBHelper.KEY_USER_PROVINCE, me.getProvince());
        DBHelper.getStringData(DBHelper.KEY_USER_ADDRESS, me.getAddress());
        DBHelper.getStringData(DBHelper.KEY_USER_HEADIMGURL, me.getHeadimgurl());
        DBHelper.getIntData(DBHelper.KEY_USER_POINT, me.getPoint());
        DBHelper.getIntData(DBHelper.KEY_USER_BEANS, me.getBeans());
        DBHelper.getIntData(DBHelper.KEY_USER_UNREAD_MESSAGES, me.getUnreadMessages());
        DBHelper.getStringData(DBHelper.KEY_USER_LEVEL, me.getLevel());
        DBHelper.getStringData(DBHelper.KEY_USER_TAGS, me.getTags());
        DBHelper.getDateData(DBHelper.KEY_USER_birthday,me.getBirthday());
        DBHelper.getDateData(DBHelper.KEY_USER_VIPVALID, me.getVipExpired());
        DBHelper.getBooleanData(DBHelper.KEY_USER_VIP, me.getVip());
        DBHelper.getBooleanData(DBHelper.KEY_USER_VIPVALID, me.getVipValid());
        DBHelper.getStringData(DBHelper.KEY_USER_CAR , me.getCar());
        DBHelper.getIntData(DBHelper.KEY_USER_MONEY, me.getMoney());
        DBHelper.getIntData(DBHelper.KEY_USER_MONEYPLUS, me.getMoneyPlus());
        DBHelper.getStringData(DBHelper.KEY_USER_JOB, me.getJob());
        DBHelper.getIntData(DBHelper.KEY_USER_PARTICIPATIONCOUNT, me.getParticipationCount());
    }
    /**
     * 设置我的个人信息
     */
    public static void setMe(JSONObject obj) {
        me = JSON.parseObject(obj.toString(),UserBaseInfo.class);
        if (me == null){
            me = new UserBaseInfo();
        }
        setMe(me);
    }

    /**
     * 用户令牌
     */
    public static String pp_token;

    /**
     * 获取用户令牌
     */
    public static String getPp_token(){
        pp_token = DBHelper.getStringData(DBHelper.KEY_PP_TOKEN, null);
        return pp_token;
    }

    /**
     * 设置用户令牌
     */
    public static boolean setPp_token(String pp_token){
        BaseInfo.pp_token = pp_token;
        return DBHelper.putStringData(DBHelper.KEY_PP_TOKEN, pp_token);
    }
    /**
     * 清楚用户令牌
     */

    public static void cleearPp_token(){
        pp_token = null;
        setPp_token(null);
    }
    /**
     * 是否已经登录
     */
    public static boolean isLogin() {
        return !BaseUtils.isEmptyString(getPp_token());
    }

    /**
     * 检查登录
     *
     */
    public static boolean checkLogin(Context context){
        if (isLogin()){
            return true;
        }else {
            return false;
            //Intent intent = new Intent(context,Log)
        }
    }

}
