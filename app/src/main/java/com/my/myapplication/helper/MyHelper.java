package com.my.myapplication.helper;


import android.annotation.SuppressLint;
import android.content.Context;

import com.my.myapplication.bean.StudentBean;
import com.my.myapplication.bean.UserBean;
import com.my.myapplication.utils.http.HttpRequest;
import com.my.myapplication.utils.http.JsonBean;
import com.my.myapplication.utils.http.JsonUtil;
import com.my.myapplication.utils.http.TokenUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyHelper {

    private static MyHelper instance = null;


    public MyHelper(Context context) {

    }

    public static MyHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MyHelper(context);
        }
        return instance;
    }


    // 添加一条数据 用户
    public long addUser(UserBean user) throws IOException {
        JsonBean jsonBean = HttpRequest.register(user);
        if (jsonBean.getCode() == 200) {

            return 1;
        }
        return -1;
    }


    /**
     * 登录校验账号密码 用户
     */
    public UserBean matchAccount(String name, String psw) throws IOException {
        UserBean re_user = null;
        JsonBean jsonBean = HttpRequest.login(name, psw);
        if (jsonBean.getCode() == 200) {
            TokenUtil.setToken(jsonBean.getData());
            re_user = new UserBean();
        }
        return re_user;
    }

    /**
     * 判断账号是否被注册 用户
     */
    public boolean isRegister(String name) throws IOException {

        JsonBean jsonBean = HttpRequest.IsRegister(name);
        return jsonBean.getCode() == 200;
    }

    // 添加一条数据
    public long addData(StudentBean bean) throws IOException {
        JsonBean jsonBean = HttpRequest.addData(bean);
        if (jsonBean.getCode() == 200) {
            return 1;
        }

        return -1;

    }

    /**
     * 查所有数据 查
     */

    @SuppressLint("Range")
    public List<StudentBean> getAllData() throws IOException {
        List<StudentBean> list = new ArrayList<>();

        JsonBean jsonBean = HttpRequest.getAllData();
        if (jsonBean.getCode() == 200) {
            list = JsonUtil.getJsonToList(jsonBean.getData(), StudentBean.class);
        }
        return list;
    }

    /**
     * 根据ID查一条数据
     */

    @SuppressLint("Range")
    public StudentBean getDataById(Long id) throws IOException {
        List<StudentBean> list = new ArrayList<>();
        JsonBean jsonBean = HttpRequest.getDataById(id);
        if (jsonBean.getCode() == 200) {
            list = JsonUtil.getJsonToList(jsonBean.getData(), StudentBean.class);
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据分类查数据
     */

    @SuppressLint("Range")
    public List<StudentBean> getDataByLeiXing(String leixing) throws IOException {
        List<StudentBean> list = new ArrayList<>();

        JsonBean jsonBean = HttpRequest.getDataByLeiXing(leixing);
        if (jsonBean.getCode() == 200) {
            list = JsonUtil.getJsonToList(jsonBean.getData(), StudentBean.class);
        }
        return list;
    }

    /**
     * 根据id更新数据
     */
    public int updateDataById(StudentBean bean) throws IOException {

        JsonBean jsonBean = HttpRequest.updateDataById(bean);
        if (jsonBean.getCode() == 200) {
            return 1;
        }
        return -1;
    }

    //模糊查数据
    @SuppressLint("Range")
    public List<StudentBean> getDataLikeKey( String key) throws IOException {
        List<StudentBean> list = new ArrayList<>();

        JsonBean jsonBean = HttpRequest.getDataLikeKey( key);
        if (jsonBean.getCode() == 200) {
            list = JsonUtil.getJsonToList(jsonBean.getData(), StudentBean.class);
        }
        return list;
    }

    /**
     * 根据id删除数据
     */

    public int delDataById(long id) throws IOException {
        JsonBean jsonBean = HttpRequest.delDataById(id);
        if (jsonBean.getCode() == 200) {
            return 1;
        }
        return -1;
    }
}
