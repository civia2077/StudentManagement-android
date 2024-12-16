package com.my.myapplication.utils.http;


import com.alibaba.fastjson.JSON;
import com.my.myapplication.bean.StudentBean;
import com.my.myapplication.bean.UserBean;
import com.my.myapplication.utils.global.Config;

import java.io.File;
import java.io.IOException;

//网络请求api
public class HttpRequest {

    /**
     * 取消网络请求
     */
    public static void cancel(String tag) {
        HttpClient.getInstance().cancel(tag);
    }


    public static JsonBean register(UserBean userBean) throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.REGISTER_URL, Config.REGISTER_TAG)
                        .params("username", userBean.getName())
                        .params("password", userBean.getPassword())
                        .execute().body().string(), JsonBean.class);
    }


    public static JsonBean login(String name, String psw) throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.LOGIN_URL, Config.LOGIN_TAG)
                        .params("username", name)
                        .params("password", psw)
                        .execute().body().string(), JsonBean.class);
    }


    public static JsonBean IsRegister(String username) throws IOException {
        return JSON.parseObject(
                HttpClient.getInstance().post(Config.IS_REGISTER_URL, Config.I_SREGISTER_TAG)
                        .params("username", username)
                        .execute().body().string(), JsonBean.class);

    }

    public static JsonBean addData(StudentBean bean) throws IOException {
        File file = new File(bean.getTupian());

        if (!file.exists()) {
            return JSON.parseObject(
                    HttpClient.getInstance().post(Config.ADDDATA_URL, Config.ADDDATA_TAG)
                            .headers("Authorization", " " + TokenUtil.getToken())
                            .params("name", bean.getName())
                            .params("date", bean.getDate())
                            .params("xuehao", bean.getXuehao())
                            .params("xingbie", bean.getXingbie())
                            .params("dianhua", bean.getDianhua())
                            .params("jiatingdizhi", bean.getJiatingdizhi())
                            .params("tupian", bean.getTupian())
                            .execute().body().string(), JsonBean.class);
        } else {
            return JSON.parseObject(
                    HttpClient.getInstance().post(Config.ADDDATA_URL, Config.ADDDATA_TAG)
                            .headers("Authorization", " " + TokenUtil.getToken())
                            .params("name", bean.getName())
                            .params("date", bean.getDate())
                            .params("xuehao", bean.getXuehao())
                            .params("xingbie", bean.getXingbie())
                            .params("dianhua", bean.getDianhua())
                            .params("jiatingdizhi", bean.getJiatingdizhi())
                            .params("file", new File(bean.getTupian()))
                            .execute().body().string(), JsonBean.class);
        }


    }

    public static JsonBean getAllData() throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.GETALLDATA_URL, Config.GETALLDATA_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .execute().body().string(), JsonBean.class);

    }

    public static JsonBean updateDataById(StudentBean bean) throws IOException {
        File file = new File(bean.getTupian());

        if (!file.exists()) {
            return JSON.parseObject(
                    HttpClient.getInstance().post(Config.UPDATEDATABYID_URL, Config.UPDATEDATABYID_TAG)
                            .headers("Authorization", " " + TokenUtil.getToken())
                            .params("Id", bean.getId())
                            .params("name", bean.getName())
                            .params("date", bean.getDate())
                            .params("xuehao", bean.getXuehao())
                            .params("xingbie", bean.getXingbie())
                            .params("dianhua", bean.getDianhua())
                            .params("jiatingdizhi", bean.getJiatingdizhi())
                            .params("tupian", bean.getTupian())
                            .execute().body().string(), JsonBean.class);
        } else {
            return JSON.parseObject(
                    HttpClient.getInstance().post(Config.UPDATEDATABYID_URL, Config.UPDATEDATABYID_TAG)
                            .headers("Authorization", " " + TokenUtil.getToken())
                            .params("Id", bean.getId())
                            .params("name", bean.getName())
                            .params("date", bean.getDate())
                            .params("xuehao", bean.getXuehao())
                            .params("xingbie", bean.getXingbie())
                            .params("dianhua", bean.getDianhua())
                            .params("jiatingdizhi", bean.getJiatingdizhi())
                            .params("tupian", bean.getTupian())
                            .params("file", new File(bean.getTupian()))
                            .execute().body().string(), JsonBean.class);
        }

    }

    public static JsonBean getDataById(Long Id) throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.GETDATABYID_URL, Config.GETDATABYID_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("Id", Id)
                        .execute().body().string(), JsonBean.class);

    }

    public static JsonBean delDataById(Long Id) throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.DELDATABYID_URL, Config.DELDATABYID_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("Id", Id)
                        .execute().body().string(), JsonBean.class);

    }

    public static JsonBean getDataLikeKey(String key) throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.GETDATALIKEKEY_URL, Config.GETDATALIKEKEY_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("key", key)
                        .execute().body().string(), JsonBean.class);

    }

    public static JsonBean getDataByLeiXing(String leixing) throws IOException {

        return JSON.parseObject(
                HttpClient.getInstance().post(Config.GETDATABYLEIXING_URL, Config.GETDATABYLEIXING_TAG)
                        .headers("Authorization", " " + TokenUtil.getToken())
                        .params("leixing", leixing)

                        .execute().body().string(), JsonBean.class);

    }

}
