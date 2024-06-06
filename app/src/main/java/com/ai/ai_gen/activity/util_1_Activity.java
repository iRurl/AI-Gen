package com.ai.ai_gen.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ai.ai_gen.R;

public class util_1_Activity extends AppCompatActivity {
    String visiable = null;
    TextView yjzt;
    TextView qkwb;
    EditText nr;
    LinearLayout ll_col;
    String title;
    private ClipData mClipData;   //剪切板Data对象
    private ClipboardManager mClipboardManager;   //剪切板管理工具类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util1);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ll_col = findViewById(R.id.ll_col);
        yjzt = findViewById(R.id.yjzt);
        qkwb = findViewById(R.id.qkwb);
        nr = findViewById(R.id.nr);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            visiable = bundle.getString("visable");
            title = bundle.getString("title");
        }
        if (TextUtils.equals(visiable, "no")) {
            ll_col.setVisibility(View.GONE);
        } else if (TextUtils.equals(visiable, "yes")) {
            ll_col.setVisibility(View.VISIBLE);
        }

        getSupportActionBar().setTitle(title);
        yjzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mClipData = mClipboardManager.getPrimaryClip();
                    //获取到剪贴板中的内容
                    ClipData.Item item = mClipData.getItemAt(0);
                    String txt = item.getText().toString();
                    nr.setText(txt);
                }catch (Exception e){

                }

            }
        });
        qkwb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nr.setText("");
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