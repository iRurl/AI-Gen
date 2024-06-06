package com.ai.ai_gen.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ai.ai_gen.R;
import com.ai.ai_gen.activity.DiaryPageActivity;
import com.ai.ai_gen.activity.EditUserActivity;
import com.ai.ai_gen.activity.EmptyActivity;
import com.ai.ai_gen.activity.FeedBackActivity;
import com.ai.ai_gen.activity.GuideActivity;
import com.ai.ai_gen.activity.LoginActivity;
import com.ai.ai_gen.activity.MainActivity;
import com.ai.ai_gen.activity.SettingActivity;
import com.ai.ai_gen.bean.User;
import com.ai.ai_gen.utils.BitmapUtils;
import com.ai.ai_gen.utils.DBOpenHelper;
import com.makeramen.roundedimageview.RoundedImageView;


public class UserFragment extends BaseFragment implements View.OnClickListener {

    private Button btn_out;
    private Context context;
    DBOpenHelper mDBOpenHelper;
    User user;
    RoundedImageView iv_advatar;
    TextView iv_username;
    TextView bjzl;

    ImageView iv_setting;
    ImageView iv_tips;



    //常用功能
    Button cy_aizp;
    Button cy_xxzx;
    Button cy_wdtz;

    //积分功能
    Button jf_zjf;
    Button jf_hhw;
    Button jf_jfdb;

    //其他功能
    Button qt_setting;
    Button qt_yjfk;
    Button qt_gxhzb;
    Button qt_smrj;
    Button qt_wdqb;
    Button qt_wdhd;
    Button qt_llls;
    Button qt_dhhy;
    Button qt_wdsc;
    Button qt_zmxbj;
    Button qt_jswa;
    Button qt_yhjzx;


    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_user,null);
        btn_out=view.findViewById(R.id.btn_out);
        iv_advatar=view.findViewById(R.id.avatar);
        iv_username=view.findViewById(R.id.tv_username);
        bjzl=view.findViewById(R.id.bjzl);
        iv_tips=view.findViewById(R.id.iv_tips);
        iv_setting=view.findViewById(R.id.iv_setting);
        qt_setting=view.findViewById(R.id.qt_setting);
        qt_yjfk=view.findViewById(R.id.qt_yjfk);

        cy_aizp=view.findViewById(R.id.cy_aizp);
        cy_xxzx=view.findViewById(R.id.cy_xxzx);
        cy_wdtz=view.findViewById(R.id.cy_wdtz);


        jf_zjf=view.findViewById(R.id.jf_zjf);
        jf_hhw=view.findViewById(R.id.jf_hhw);
        jf_jfdb=view.findViewById(R.id.jf_jfdb);


        qt_gxhzb=view.findViewById(R.id.qt_gxhzb);
        qt_smrj=view.findViewById(R.id.qt_smrj);
        qt_wdqb=view.findViewById(R.id.qt_wdqb);
        qt_wdhd=view.findViewById(R.id.qt_wdhd);
        qt_llls=view.findViewById(R.id.qt_llls);
        qt_dhhy=view.findViewById(R.id.qt_dhhy);
        qt_wdsc=view.findViewById(R.id.qt_wdsc);
        qt_zmxbj=view.findViewById(R.id.qt_zmxbj);
        qt_jswa=view.findViewById(R.id.qt_jswa);
        qt_yhjzx=view.findViewById(R.id.qt_yhjzx);
        context=getContext();
        return view;
    }

    @Override
    public void   onResume() {
        super.onResume();
        initInfo();

    }

public void initInfo(){
    mDBOpenHelper=new DBOpenHelper(context);
    user=mDBOpenHelper.getAllLoggedInUsers().get(0);
    iv_username.setText(user.getName());
    String advatar=user.getAvatar();
    if(advatar!=null){
        Bitmap bitmap=BitmapUtils.base64ToBitmap(advatar);
        if(bitmap!=null){
            iv_advatar.setImageBitmap(bitmap);
        }
    }
}


    @Override
    public void initData() {
        super.initData();
        initInfo();
        btn_out.setOnClickListener(this::onClick);
        iv_setting.setOnClickListener(this::onClick);
        qt_setting.setOnClickListener(this::onClick);
        qt_yjfk.setOnClickListener(this::onClick);
        iv_tips.setOnClickListener(this::onClick);
        bjzl.setOnClickListener(this::onClick);

        cy_aizp.setOnClickListener(this::onClick);
        cy_xxzx.setOnClickListener(this::onClick);
        cy_wdtz.setOnClickListener(this::onClick);
        jf_zjf.setOnClickListener(this::onClick);
        jf_hhw.setOnClickListener(this::onClick);
        jf_jfdb.setOnClickListener(this::onClick);
        qt_gxhzb.setOnClickListener(this::onClick);
        qt_smrj.setOnClickListener(this::onClick);
        qt_wdqb.setOnClickListener(this::onClick);
        qt_wdhd.setOnClickListener(this::onClick);
        qt_llls.setOnClickListener(this::onClick);
        qt_dhhy.setOnClickListener(this::onClick);
        qt_wdsc.setOnClickListener(this::onClick);
        qt_zmxbj.setOnClickListener(this::onClick);
        qt_jswa.setOnClickListener(this::onClick);
        qt_yhjzx.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.bjzl){
            Intent intent = new Intent(context, EditUserActivity.class);
            context.startActivity(intent);
        }
        else if(id==R.id.btn_out){
            mDBOpenHelper.updateLoginStatus(user.getPhonenum(),0);
            Intent intent = new Intent(context, LoginActivity.class);	//第二个参数即为执行完跳转后的Activity
            startActivity(intent);
            getActivity().finish();
        } else if (id==R.id.iv_setting || id==R.id.qt_setting) {
            Intent intent = new Intent(context, SettingActivity.class);
            context.startActivity(intent);

        } else if (id==R.id.qt_yjfk) {
            Intent intent = new Intent(context, FeedBackActivity.class);
            context.startActivity(intent);
        } else if (id==R.id.iv_tips||id==R.id.cy_xxzx) {
            Intent intent = new Intent(context, EmptyActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("title","消息中心");
            bundle.putString("content","暂未发现您的消息列表~");
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
        else if (id==R.id.cy_wdtz) {
            Intent intent = new Intent(context, EmptyActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("title","我的贴子");
            bundle.putString("content","空空如也~");
            intent.putExtras(bundle);
            context.startActivity(intent);
        }        else if (id==R.id.cy_aizp) {
            Intent intent = new Intent(context, EmptyActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("title","AI作品");
            bundle.putString("content","暂时未发现您的作品~");
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
        else if (id==R.id.jf_zjf||id==R.id.jf_hhw||id==R.id.jf_jfdb) {
            Intent intent = new Intent(context, EmptyActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("title","积分任务");
            bundle.putString("content","活动暂未开始，敬请期待~");
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
        else if (id==R.id.qt_gxhzb) {
            Intent intent = new Intent(context, EmptyActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("title","我的装扮");
            bundle.putString("content","暂时未开启装扮功能，敬请期待~");
            intent.putExtras(bundle);
            context.startActivity(intent);
        }        else if (id==R.id.qt_smrj) {
            Intent intent = new Intent(context, DiaryPageActivity.class);
            context.startActivity(intent);
        }
        else if (id==R.id.qt_wdqb) {
            Intent intent = new Intent(context, EmptyActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("title","我的钱包");
            bundle.putString("content","钱包为空哦~");
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
        else if (id==R.id.qt_wdhd) {
            Intent intent = new Intent(context, EmptyActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("title","我的互动");
            bundle.putString("content","您还没有互动哦~");
            intent.putExtras(bundle);
            context.startActivity(intent);
        }        else if (id==R.id.qt_llls) {
            Intent intent = new Intent(context, EmptyActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("title","浏览历史");
            bundle.putString("content","浏览历史为空哦~");
            intent.putExtras(bundle);
            context.startActivity(intent);
        }        else if (id==R.id.qt_dhhy) {
            Intent intent = new Intent(context, EmptyActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("title","兑换会员");
            bundle.putString("content","暂时未开启会员功能，敬请期待~");
            intent.putExtras(bundle);
            context.startActivity(intent);
        }  else if (id==R.id.qt_wdsc) {
            Intent intent = new Intent(context, EmptyActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("title","我的收藏");
            bundle.putString("content","收藏为空~");
            intent.putExtras(bundle);
            context.startActivity(intent);
        }else if (id==R.id.qt_zmxbj) {
            Toast.makeText(context, "桌面小部件功能暂不支持", Toast.LENGTH_SHORT).show();
        }else if (id==R.id.qt_jswa) {
            Intent intent = new Intent(context, EmptyActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("title","解锁文案");
            bundle.putString("content","空空如也~");
            intent.putExtras(bundle);
            context.startActivity(intent);
        }else if (id==R.id.qt_yhjzx) {
            Intent intent = new Intent(context, EmptyActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("title","优惠券中心");
            bundle.putString("content","暂时未开启优惠券功能，敬请期待~");
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
}
