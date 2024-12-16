package com.my.myapplication.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.my.myapplication.bean.StudentBean;
import com.my.myapplication.helper.MyHelper;
import com.my.myapplication.utils.FileUtils;
import com.my.myapplication.utils.ImageUtils;
import com.my.myapplication.utils.PathUirls;
import com.my.myapplication.utils.PermissionUtils;

import java.util.Calendar;

//查看 添加 编辑 页 共用
public class AddAndEditActivity extends AppCompatActivity {
    //各种权限申请回调
    public static final int PHOTO_REQUEST_CODE = 1;
    public static final int SD_REQUEST_CODE = 2;
    public static final int CAMERA_PHOTO_REQUEST_CODE = 3;
    //图片
    private ImageView mIvPic;
    //写入编辑按钮
    private Button mBtWrite;
    //存图片地址数据
    private String tupian = "";
    //类型 判断是添加 还是编辑修改
    private String type = "";
    //数据的id
    private Long mId;
    private StudentBean mBean;

    private EditText mEtName;
    private EditText mEtDate;
    private TextView mTvXuanriqi;
    private RadioGroup mRgXingbie;
    private RadioButton mRbNan;
    private RadioButton mRbNv;
    private EditText mEtPhone;
    private EditText mEtJiatingdizhi;
    private EditText mEtXuehao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit);
        //设置ActionBar返回箭头 和 标题
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Added");
        initView();
    }

    private void initView() {
        //查找控件id
        mIvPic = findViewById(R.id.iv_pic);
        mBtWrite = findViewById(R.id.bt_write);
        mEtName = findViewById(R.id.et_name);
        mEtDate = findViewById(R.id.et_date);
        mTvXuanriqi = findViewById(R.id.tv_xuanriqi);
        mRgXingbie = findViewById(R.id.rg_xingbie);
        mRbNan = findViewById(R.id.rb_nan);
        mRbNv = findViewById(R.id.rb_nv);
        mEtPhone = findViewById(R.id.et_phone);
        mEtJiatingdizhi = findViewById(R.id.et_jiatingdizhi);
        mEtXuehao = findViewById(R.id.et_xuehao);
//        选择日期按钮点击事件
        mTvXuanriqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
//                创建个选择日期对话框
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddAndEditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // 处理选择的日期 赋予日期输入框
                                mEtDate.setText(year + "years" + (monthOfYear + 1) + "month" + dayOfMonth + "date");
                            }
                        },
                        year,
                        month,
                        day
                );

                datePickerDialog.show();
            }
        });

        //得到类型 判断是查看 添加还是编辑
        type = getIntent().getStringExtra("type");
//根据类型判断是 编辑还是查看 设置新增 编辑 按钮的是否可见
        if ("edit".equals(type) || "view".equals(type)) {
            //类型是查看 编辑 新增等按钮不可见 和 使能
            if ("view".equals(type)) {
                mBtWrite.setVisibility(View.GONE);
                mEtName.setEnabled(false);
                mEtDate.setEnabled(false);
                mTvXuanriqi.setEnabled(false);

                mRgXingbie.setEnabled(false);
                mRbNan.setEnabled(false);
                mRbNv.setEnabled(false);
                mEtPhone.setEnabled(false);
                mEtJiatingdizhi.setEnabled(false);
                mEtXuehao.setEnabled(false);

                getSupportActionBar().setTitle("Check");
            }
            //类型是编辑 编辑 新增按钮可见
            if ("edit".equals(type)) {
                mBtWrite.setText("edit");
                getSupportActionBar().setTitle("edit");
            }
            //获取数据的id 只有在查看和编辑时候才会携带id数据
            mId = getIntent().getLongExtra("beanId", 0);
            //开线程 根据id查对应的数据
            new Thread(new Runnable() {
                @SuppressLint("WrongConstant")
                @Override
                public void run() {

                    try {
                        // 这里是调用耗时操作方法 调用数据方法 根据id查数据
                        mBean = MyHelper.getInstance(AddAndEditActivity.this).getDataById(mId);
                        //切换ui线程
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //不为空 则把数据显示在对应的控件上
                                if (mBean != null) {

                                    mEtName.setText(mBean.getName());
                                    mEtDate.setText(mBean.getDate());
                                    if ("男生".equals(mBean.getXingbie())) {
                                        mRbNan.setChecked(true);
                                    } else {
                                        mRbNv.setChecked(true);
                                    }

                                    mEtPhone.setText(mBean.getDianhua());
                                    mEtJiatingdizhi.setText(mBean.getJiatingdizhi());
                                    mEtXuehao.setText(mBean.getXuehao());

                                    if (mBean.getTupian() != null) {
                                        tupian = mBean.getTupian();
                                        //图片控件显示选择的图片
                                        ImageUtils.displayImage(AddAndEditActivity.this, mIvPic, tupian);
                                    }

                                } else {
                                    Toast.makeText(AddAndEditActivity.this, "Data search failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddAndEditActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                }
            }).start();
        }

//申请权限 sd存储权限 拍照权限
        requestPermissions();
        //点击图片按钮选择图片
        mIvPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("view".equals(type)) {
                    //跳页面 携带图片数据 看大图
                    Intent intent = new Intent(AddAndEditActivity.this, PhotoViewActivity.class);
                    intent.putExtra("tupian", tupian);
                    startActivity(intent);
                } else {
                    //弹对话框 是选择还是拍照
                    final String[] items = {"Photo Album", "Photograph", "Default Avatar"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddAndEditActivity.this);
                    builder.setTitle("Selection method");
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent;
                            switch (which) {
//选择图片 跳转到相册
                                case 0:
                                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent.setType("image/*");
                                    startActivityForResult(intent, PHOTO_REQUEST_CODE);
                                    break;
                                case 1:
                                    //拍照跳 拍照
                                    intent = new Intent();
                                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//相机action
                                    startActivityForResult(intent, CAMERA_PHOTO_REQUEST_CODE);
                                    break;
                                case 2:
                                    //默认
                                    tupian = "";
                                    mIvPic.setImageResource(R.drawable.d_user);
                                    break;
                            }
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }

            }
        });

//写入编辑按钮 点击事件
        mBtWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户填写数据
                String Name = mEtName.getText().toString().trim();
                String Date = mEtDate.getText().toString().trim();
                String Phone = mEtPhone.getText().toString().trim();

                String Jiatingdizhi = mEtJiatingdizhi.getText().toString().trim();
                String Xuehao = mEtXuehao.getText().toString().trim();


//校验数据

                if (TextUtils.isEmpty(Name)) {
                    Toast.makeText(AddAndEditActivity.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Xuehao)) {
                    Toast.makeText(AddAndEditActivity.this, "Student ID cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Date)) {
                    Toast.makeText(AddAndEditActivity.this, "Admission date cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Phone)) {
                    Toast.makeText(AddAndEditActivity.this, "Phone cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                //创建一个数据
                StudentBean bean = new StudentBean();
                if (mRbNan.isChecked()) {
                    bean.setXingbie(mRbNan.getText().toString());
                } else {
                    bean.setXingbie(mRbNv.getText().toString());
                }

                bean.setTupian(tupian);
                bean.setName(Name);
                bean.setDate(Date);
                bean.setDianhua(Phone);

                bean.setJiatingdizhi(Jiatingdizhi + "");

                bean.setXuehao(Xuehao);


                //判断类型 是新增 还是编辑修改
                if ("add".equals(type)) {
                    //新增 开线程
                    new Thread(new Runnable() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void run() {

                            try {
                                // 这里是调用耗时操作方法  调数据库方法插入新的数据
                                long result = MyHelper.getInstance(AddAndEditActivity.this).addData(bean);
                                //判断结果 提示用户 销毁当前窗口
                                if (result != -1) {
                                    //切ui线程
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AddAndEditActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AddAndEditActivity.this, "Add failure", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }


                            } catch (Exception e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AddAndEditActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }

                        }
                    }).start();
                }
                //编辑
                if ("edit".equals(type)) {
                    //设置id
                    bean.setId(mId);
                    new Thread(new Runnable() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void run() {

                            try {
                                // 这里是调用耗时操作方法 调数据库方法更新数据
                                long result = MyHelper.getInstance(AddAndEditActivity.this).updateDataById(bean);
                                //判断结果 提示用户
                                if (result != -1) {
                                    //切ui线程
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AddAndEditActivity.this, "Modification successful", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AddAndEditActivity.this, "Modification failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }


                            } catch (Exception e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AddAndEditActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }

                        }
                    }).start();
                }


            }
        });



    }

    public int findString(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //将菜单资源加载到当前的菜单资源
        if ("view".equals(type)) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        return true;
    }

    //菜单栏 点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        返回箭头
        if (android.R.id.home == item.getItemId()) {
            finish();
        }

//        修改按钮
        if (item.getItemId() == R.id.id1) {
            Intent intent = new Intent(AddAndEditActivity.this, AddAndEditActivity.class);
            intent.putExtra("type", "edit");
            intent.putExtra("beanId", mId);
            startActivity(intent);
            finish();
        }
//删除按钮
        if (item.getItemId() == R.id.id2) {
            new AlertDialog.Builder(AddAndEditActivity.this)//绑定当前窗口
                    .setTitle("hint")//设置标题
                    .setMessage("Are you sure you want to delete?")//设置提示细信息
                    .setIcon(R.mipmap.ic_launcher)//设置图标
                    .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //点确定按钮 开线程 删数据
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        // 这里是调用耗时操作方法 调数据库删数据 根据id
                                        int result = MyHelper.getInstance(AddAndEditActivity.this).delDataById(mId);

                                        //切ui线程  判断删除结果 更新界面
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (result != -1) {
                                                    Toast.makeText(AddAndEditActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(AddAndEditActivity.this, "Deletion failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    } catch (Exception e) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(AddAndEditActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    }
                                }
                            }).start();
                        }
                    })//取消按钮什么都不做
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    })//添加取消按钮
                    .create()//创建对话框
                    .show();//显示对话框
        }

        return true;
    }

    //权限申请
    public void requestPermissions() {
        PermissionUtils.requestPermissions(this, PermissionUtils.PERMISSIONS, 1,
                new PermissionUtils.OnPermissionListener() {//实现接口方法

                    @Override
                    public void onPermissionGranted() {//获取权限成功
                        // 先判断有没有权限
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            // 先判断有没有权限
                            if (Environment.isExternalStorageManager()) {
                                //大于等于安卓11 并且同意了所有权限 做某事
                            } else {
                                //大于等于安卓11 没有权限 跳转到权限赋予界面
                                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivityForResult(intent, SD_REQUEST_CODE);
                            }
                        } else {
                            //小等于安卓11  做某事
                        }
                    }

                    @Override
                    public void onPermissionDenied() {//获取权限失败
                        Toast.makeText(getApplicationContext(), "Deny Permission", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    //权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //再次判断如果大于等于安卓11跳到权限界面 返回是否赋予权限
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SD_REQUEST_CODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 检查是否有权限
            if (Environment.isExternalStorageManager()) {
                // 授权成功

            } else {
                Toast.makeText(this, "User denied SD storage permission", Toast.LENGTH_SHORT).show();
                // 授权失败
            }
        }
        if (data == null) {
            Toast.makeText(this, "No photo or video selection", Toast.LENGTH_SHORT).show();
            return;
        }

//选择照片返回
        if (requestCode == PHOTO_REQUEST_CODE) {
            if (data.getData() == null) {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
                return;
            }
            //等到选择的图片的真实地址
            String filePathFromUri = PathUirls.getFilePathFromUri(AddAndEditActivity.this, data.getData());
//判断选择图片的大小
            long fileSize = FileUtils.getFileSize(filePathFromUri);
            //如果超过限制 则弹对话框提示用户
            if (fileSize / 1024 > FileUtils.IMAGE_MAX_SIZE) {
                new AlertDialog.Builder(AddAndEditActivity.this)//绑定当前窗口
                        .setCancelable(false)
                        .setTitle("error message")//设置标题
                        .setMessage("Maximum image size" + FileUtils.IMAGE_MAX_SIZE + "kb")//设置提示细信息
                        .setIcon(R.mipmap.ic_launcher)//设置图标
                        .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })//添加确定按钮
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        })//添加取消按钮
                        .create()//创建对话框
                        .show();//显示对话框
            } else {
                try {
                    //选择图片后处理 图片大小没有超过限制
                    tupian = FileUtils.getPath(filePathFromUri);
                    //图片控件显示选择的图片
                    Bitmap bitmap = BitmapFactory.decodeFile(filePathFromUri);

                    if (bitmap != null) {
                        mIvPic.setImageBitmap(bitmap);
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Image processing error, please reselect the image", Toast.LENGTH_SHORT).show();
                }

            }

        }


        //拍照回调 返回 逻辑同上
        if (requestCode == CAMERA_PHOTO_REQUEST_CODE) {
            /**
             * 通过这种方法取出的拍摄会默认压缩，因为如果相机的像素比较高拍摄出来的图会比较高清，
             * 如果图太大会造成内存溢出（OOM），因此此种方法会默认给图片尽心压缩
             */

            if (data.getData() == null && data.getExtras() == null) {
                Toast.makeText(this, "No pictures taken", Toast.LENGTH_SHORT).show();
                return;
            }
            String takePhotoPath = PathUirls.getTakePhotoPath(this, data);
            long fileSize = FileUtils.getFileSize(takePhotoPath);
            if (fileSize / 1024 > FileUtils.IMAGE_MAX_SIZE) {
                new AlertDialog.Builder(AddAndEditActivity.this)//绑定当前窗口
                        .setCancelable(false)
                        .setTitle("error message")//设置标题
                        .setMessage("Maximum image size" + FileUtils.IMAGE_MAX_SIZE + "kb")//设置提示细信息
                        .setIcon(R.mipmap.ic_launcher)//设置图标
                        .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })//添加确定按钮
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        })//添加取消按钮
                        .create()//创建对话框
                        .show();//显示对话框
            } else {
                try {
                    tupian = FileUtils.getPath(takePhotoPath);
                    Bitmap bitmap = BitmapFactory.decodeFile(takePhotoPath);

                    if (bitmap != null) {
                        mIvPic.setImageBitmap(bitmap);
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Image processing error, please reselect the image", Toast.LENGTH_SHORT).show();
                }

            }


        }

    }

}