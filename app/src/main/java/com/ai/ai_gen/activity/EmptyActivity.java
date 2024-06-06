package com.ai.ai_gen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ai_gen.R;

public class EmptyActivity extends AppCompatActivity {
    TextView empty_title;
    TextView empty_content;

    String title;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        getSupportActionBar().hide();
        empty_title=findViewById(R.id.empty_title);
        empty_content=findViewById(R.id.empty_content);
        ImageView iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            content = bundle.getString("content");
        }
        empty_title.setText(title);
        empty_content.setText(content);
    }
}