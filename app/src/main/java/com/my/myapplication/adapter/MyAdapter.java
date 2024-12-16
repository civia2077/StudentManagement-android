package com.my.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.bean.StudentBean;
import com.my.myapplication.ui.AddAndEditActivity;
import com.my.myapplication.ui.R;
import com.my.myapplication.utils.ImageUtils;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {
    private final Context context;
    private List<StudentBean> mBeanList;

    // 构造方法 初始化数据
    public MyAdapter(Context context, List<StudentBean> beanList) {
        this.context = context;
        this.mBeanList = beanList;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        // LayoutInflater.from指定写法 加载布局
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new VH(v);
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final VH holder, @SuppressLint("RecyclerView") int position) {
        // 绑定数据
        holder.tv_name.setText("Name: " + mBeanList.get(position).getName());
        holder.tv_xuehao.setText("Student ID: " + mBeanList.get(position).getXuehao());
        holder.tv_xingbie.setText("Gender: " + mBeanList.get(position).getXingbie());
        holder.tv_date.setText("Enrollment Date: " + mBeanList.get(position).getDate());

        // 条目点击事件 携带type 和 id type区分是查看 还是编辑 还是新增 因为新增和编辑共用一个页面
        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddAndEditActivity.class);
                intent.putExtra("beanId", mBeanList.get(position).getId());
                intent.putExtra("type", "view");
                context.startActivity(intent);
            }
        });

        // 显示图片
        ImageUtils.displayImage(context, holder.iv_pic, mBeanList.get(position).getTupian());
    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    // 更新数据
    public void upDate(List<StudentBean> beanList) {
        this.mBeanList = beanList;
        notifyDataSetChanged();
    }

    // 查找控件
    public class VH extends RecyclerView.ViewHolder {
        ImageView iv_pic;
        LinearLayout ll_main;
        TextView tv_name, tv_xuehao, tv_xingbie, tv_date;

        public VH(View v) {
            super(v);
            tv_name = v.findViewById(R.id.tv_name);
            tv_xuehao = v.findViewById(R.id.tv_xuehao);
            tv_xingbie = v.findViewById(R.id.tv_xingbie);
            tv_date = v.findViewById(R.id.tv_date);
            ll_main = v.findViewById(R.id.ll_main);
            iv_pic = v.findViewById(R.id.iv_pic);
        }
    }
}
