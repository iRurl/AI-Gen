package com.ai.ai_gen.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ai.ai_gen.R;

import net.csdn.roundview.RoundButton;

public class Item_Detail_type3Activity extends AppCompatActivity {
    TextView tvDesc;
    TextView tv_content;
    ImageView iv_sc;
    LinearLayout btn_copy;

    String content;
    String desc;
    String title;
    private ClipData mClipData;   //剪切板Data对象
    private ClipboardManager mClipboardManager;   //剪切板管理工具类


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail_type3);
        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        // 初始化ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        tv_content = findViewById(R.id.tv_content);
        tvDesc = findViewById(R.id.tvDesc);
        btn_copy = findViewById(R.id.btn_copy);
        iv_sc = findViewById(R.id.iv_sc);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            content = bundle.getString("content");
            desc = bundle.getString("desc");
            title = bundle.getString("title");
        }

        initData();
    }

    private void initData() {
        getSupportActionBar().setTitle(title);
        tv_content.setText(content);
        tvDesc.setText(desc);

        iv_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.equals(String.valueOf(iv_sc.getTag()),"1")){
                    iv_sc.setImageResource(R.mipmap.detail_collect_sel);
                    iv_sc.setTag("1");
                }else {
                    iv_sc.setImageResource(R.mipmap.detail_collect_nor);
                    iv_sc.setTag("0");
                }
            }
        });
        btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mData = tv_content.getText().toString();
                //创建一个新的文本clip对象
                mClipData = ClipData.newPlainText("text",mData);
                //把clip对象放在剪贴板中
                mClipboardManager.setPrimaryClip(mClipData);
                Toast.makeText(getApplicationContext(), "复制成功！",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    // 处理ActionBar返回按钮点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
