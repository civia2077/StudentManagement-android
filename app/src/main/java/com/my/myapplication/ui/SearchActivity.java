package com.my.myapplication.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.my.myapplication.adapter.MyAdapter;
import com.my.myapplication.bean.StudentBean;
import com.my.myapplication.helper.MyHelper;

import java.util.ArrayList;
import java.util.List;

//搜索页
public class SearchActivity extends AppCompatActivity {
    //搜索控件
    private SearchView mSearchView;
    //recyclerview 适配器
    private MyAdapter mMyAdapter;
    //recyclerview 数据
    private List<StudentBean> mBeanList = new ArrayList<>();
    //RecyclerView
    private RecyclerView mRecyclerview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //设置ActionBar返回箭头 和 标题
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("search");
        //查找控件
        mSearchView = findViewById(R.id.searchView);
        //查找Recyclerview控件
        mRecyclerview = findViewById(R.id.recyclerview);
        //绑定Recyclerview适配器
        mMyAdapter = new MyAdapter(SearchActivity.this, mBeanList);
        //设置Recyclerview 布局管理器
        mRecyclerview.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        //  mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        //设置Recyclerview适配器
        mRecyclerview.setAdapter(mMyAdapter);



//搜索按钮处理逻辑
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //点击输入法键盘上的的搜索按钮时候调用
            @Override
            public boolean onQueryTextSubmit(String newText) {
                //判断搜索内容是否为空
                if ("".equals(newText)) {
                    //搜索内容为空 开个线程获取所有数据 并更新recyclerview界面
                    new Thread(new Runnable() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void run() {
                            try {
                                // 这里是调用耗时操作方法 获取所有数据
                                mBeanList = MyHelper.getInstance(SearchActivity.this).getAllData();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        //更新recyclerview显示数据
                                        mMyAdapter.upDate(mBeanList);
                                    }
                                });

                            } catch (Exception e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SearchActivity.this, "未知错误", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }

                        }
                    }).start();
                } else {
                    //搜索数据不为空
                    new Thread(new Runnable() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void run() {
                            try {
                                // 这里是调用耗时操作方法 根据用户输入的搜索内容查找数据 调用数据库方法 模糊查找 根据标题
                                mBeanList = MyHelper.getInstance(SearchActivity.this).getDataLikeKey( newText);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //更新recyclerview数据
                                        mMyAdapter.upDate(mBeanList);
                                    }
                                });

                            } catch (Exception e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SearchActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }

                        }
                    }).start();
                }
                return false;
            }

            //实时的获取输入的文字 输入字符时候事实获取 逻辑同上
            @Override
            public boolean onQueryTextChange(String newText) {
                if ("".equals(newText)) {
                    new Thread(new Runnable() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void run() {
                            try {
                                // 这里是调用耗时操作方法
                                mBeanList = MyHelper.getInstance(SearchActivity.this).getAllData();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mMyAdapter.upDate(mBeanList);
                                    }
                                });

                            } catch (Exception e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SearchActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }

                        }
                    }).start();
                } else {
                    new Thread(new Runnable() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void run() {
                            try {
                                // 这里是调用耗时操作方法
                                mBeanList = MyHelper.getInstance(SearchActivity.this).getDataLikeKey( newText);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mMyAdapter.upDate(mBeanList);
                                    }
                                });

                            } catch (Exception e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SearchActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }

                        }
                    }).start();
                }
                return false;
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

    @Override
    protected void onResume() {
        super.onResume();
        mSearchView.setQuery("", true);
        mSearchView.clearFocus();
        new Thread(new Runnable() {
            @SuppressLint("WrongConstant")
            @Override
            public void run() {
                try {
                    // 这里是调用耗时操作方法
                    mBeanList = MyHelper.getInstance(SearchActivity.this).getAllData();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMyAdapter.upDate(mBeanList);
                        }
                    });

                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SearchActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        }).start();
    }

}