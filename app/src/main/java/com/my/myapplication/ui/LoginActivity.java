package com.my.myapplication.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.my.myapplication.bean.UserBean;
import com.my.myapplication.helper.MyHelper;
import com.my.myapplication.utils.SpUtils;

//登录页
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //是否记住账号 SharedPreferences存储用的key
    private final String key_is_remember = "key_is_remember";
    // 存储账号 SharedPreferences用的key
    private final String key_account = "ke_account";
    //存储密码 SharedPreferences用的key
    private final String key_password = "key_password";
    //账号
    private EditText mEtName;
    private EditText mEtPassword;
    //密码
    private Button mBtLogin;
    //记住密码
    private CheckBox mCkRemember;
    //注册按钮
    private TextView mTvRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        //查找控件
        mEtName = findViewById(R.id.et_name);
        mEtPassword = findViewById(R.id.et_password);
        mBtLogin = findViewById(R.id.bt_login);
        mCkRemember = findViewById(R.id.ck_remember);
        //获取是否记账密码的状态
        boolean is_remember = SpUtils.getInstance(this).getBoolean(key_is_remember, false);
//如果记住密码 则从SharedPreferences存储中获取 账号密码 并赋值给对应的控件
        if (is_remember) {
            mCkRemember.setChecked(true);
            mEtName.setText(SpUtils.getInstance(this).getString(key_account));
            mEtPassword.setText(SpUtils.getInstance(this).getString(key_password));
        }
        //绑定 登录注册按钮点击事件
        mBtLogin.setOnClickListener(this);
        mTvRegister = findViewById(R.id.tv_register);
        mTvRegister.setOnClickListener(this);
    }

    //登录注册按钮点击事件处理
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                login();
                break;
            case R.id.tv_register:
                // 点注册按钮跳注册页面
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    //登录逻辑处理
    private void login() {
        //获取账号密码
        String userName = mEtName.getText().toString().trim();
        String passWord = mEtPassword.getText().toString().trim();
        //判断账号密码是否为空 如果是则提示用户 并返回 不在继续执行后续代码
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Account number is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passWord)) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return;
        }
//判断记住密码按钮是否是被勾选 如果是 则把记住密码的状态 账号 密码 写入SharedPreferences中
        if (mCkRemember.isChecked()) {
            SpUtils.getInstance(this).putBoolean(key_is_remember, true);
            SpUtils.getInstance(this).putString(key_account, userName);
            SpUtils.getInstance(this).putString(key_password, passWord);
        } else {
            //没有勾选记住密码 清空SharedPreferences保存的 记住账号密码状态 用户名 和密码
            SpUtils.getInstance(this).putBoolean(key_is_remember, false);
            SpUtils.getInstance(this).putString(key_account, "");
            SpUtils.getInstance(this).putString(key_password, "");
        }
        //开个线程进行登录 因为耗时操作不能再ui线程中操作 否则会引起ui界面卡顿
        new Thread(new Runnable() {
            @SuppressLint("WrongConstant")
            @Override
            public void run() {

                try {
                    // 这里是调用耗时操作方法 调用数据库方法 判断账号密码是否正确
                    UserBean user = MyHelper.getInstance(LoginActivity.this).matchAccount(userName, passWord);
                    //返回不为空 则说明查到用户 账号密码正则
                    if (user != null) {
                        //再ui线程中更新ui 因为安卓不能子子线程中更新ui
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //登录成功 跳主页面 并销毁登录页面
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //登录失败 提示用户
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        }).start();


    }


}
