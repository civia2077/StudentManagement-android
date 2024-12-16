package com.my.myapplication.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.my.myapplication.utils.ImageUtils;

//主界面点头像时候调用 查看大图片
public class PhotoViewActivity extends AppCompatActivity {

    private ImageView mIvPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        //设置ActionBar返回箭头 和 标题
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Image Preview");
        initView();
    }

    private void initView() {
        mIvPic = findViewById(R.id.iv_pic);
        //图片控件显示选择的图片
        ImageUtils.displayImage(PhotoViewActivity.this, mIvPic, getIntent().getStringExtra("tupian"));
        mIvPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //返回箭头点击事件 销毁退出页面
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
        }
        return true;
    }
}