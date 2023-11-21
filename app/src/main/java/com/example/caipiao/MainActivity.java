package com.example.caipiao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.caipiao.shuangseqiu.activity.MainShuangSeQiuActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.shuang_se_qiu).setOnClickListener(this);
        findViewById(R.id.da_le_tou).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shuang_se_qiu:
                Intent shuangIntent=new Intent(this, MainShuangSeQiuActivity.class);
                startActivity(shuangIntent);
                break;
            case R.id.da_le_tou:
                break;
        }
    }
}