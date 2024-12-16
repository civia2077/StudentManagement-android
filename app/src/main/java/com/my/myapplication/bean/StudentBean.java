package com.my.myapplication.bean;

//数据类 基类
public class StudentBean {
    private Long id;

    public String getXuehao() {
        return xuehao;
    }

    public void setXuehao(String xuehao) {
        this.xuehao = xuehao;
    }

    private String xuehao;
    private String xingbie;
    private String dianhua;
    private String jiatingdizhi;
    private String name;
    private String date;
    private String tupian;



    public String getXingbie() {
        return xingbie;
    }

    public void setXingbie(String xingbie) {
        this.xingbie = xingbie;
    }


    public String getDianhua() {
        return dianhua;
    }

    public void setDianhua(String dianhua) {
        this.dianhua = dianhua;
    }



    public String getJiatingdizhi() {
        return jiatingdizhi;
    }

    public void setJiatingdizhi(String jiatingdizhi) {
        this.jiatingdizhi = jiatingdizhi;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTupian() {
        return tupian;
    }

    public void setTupian(String tupian) {
        this.tupian = tupian;
    }

}
