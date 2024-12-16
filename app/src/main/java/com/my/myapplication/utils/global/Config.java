package com.my.myapplication.utils.global;

//全局配置文件 如服务器地址 跳转界面Intetn的key 本地存储sp用到的Key等
public class Config {

    //网络请求基地址 如果是androidstudio自带的模拟器 IP可改为http://10.0.2.2:端口号 真机局域网ip
    public static String HTTP_BASE_URL = "http://10.0.2.2:8888";


    //注册
    public static String REGISTER_URL = HTTP_BASE_URL + "/register";
    public static String REGISTER_TAG = "register";

    public static String LOGIN_URL = HTTP_BASE_URL + "/login";
    public static String LOGIN_TAG = "login";

    public static String IS_REGISTER_URL = HTTP_BASE_URL + "/isregister";
    public static String I_SREGISTER_TAG = "isregister";


    public static String ADDDATA_URL = HTTP_BASE_URL + "/addData";
    public static String ADDDATA_TAG = "addData";


    public static String GETALLDATA_URL = HTTP_BASE_URL + "/getAllData";
    public static String GETALLDATA_TAG = "getAllData";

    public static String UPDATEDATABYID_URL = HTTP_BASE_URL + "/updateDataById";
    public static String UPDATEDATABYID_TAG = "updateDataById";

    public static String GETDATABYID_URL = HTTP_BASE_URL + "/getDataById";
    public static String GETDATABYID_TAG = "getDataById";

    public static String DELDATABYID_URL = HTTP_BASE_URL + "/delDataById";
    public static String DELDATABYID_TAG = "delDataById";

    public static String GETDATABYLEIXING_URL = HTTP_BASE_URL + "/getDataByLeiXing";
    public static String GETDATABYLEIXING_TAG = "getDataByLeiXing";

    public static String GETDATALIKEKEY_URL = HTTP_BASE_URL + "/getDataLikeKey";
    public static String GETDATALIKEKEY_TAG = "getDataLikeKey";

    public static String TOKEN_KEY = "token";

}
