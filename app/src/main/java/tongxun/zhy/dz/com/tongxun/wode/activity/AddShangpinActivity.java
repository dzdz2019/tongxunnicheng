package tongxun.zhy.dz.com.tongxun.wode.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.dhc.gallery.GalleryConfig;
import com.dhc.gallery.GalleryHelper;
import com.dhc.gallery.ui.GalleryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.base.BaseActivity;
import tongxun.zhy.dz.com.tongxun.base.ShopWebActivity;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.FenLeiBean;
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.UpdateshangpinBean;
import tongxun.zhy.dz.com.tongxun.tools.ACache;
import tongxun.zhy.dz.com.tongxun.tools.DialogCallBack;
import tongxun.zhy.dz.com.tongxun.tools.DialogImpl;
import tongxun.zhy.dz.com.tongxun.tools.GetHeightUtil;
import tongxun.zhy.dz.com.tongxun.tools.GsonUtils;
import tongxun.zhy.dz.com.tongxun.tools.HttpUtils;
import tongxun.zhy.dz.com.tongxun.tools.IDialog;
import tongxun.zhy.dz.com.tongxun.tools.LoadingDialog;
import tongxun.zhy.dz.com.tongxun.tools.MyGridview;
import tongxun.zhy.dz.com.tongxun.tools.MyListView;
import tongxun.zhy.dz.com.tongxun.tools.TextCallBack;
import tongxun.zhy.dz.com.tongxun.tools.ToumingUtil;
import tongxun.zhy.dz.com.tongxun.utils.Gongjuutils;
import tongxun.zhy.dz.com.tongxun.utils.Pop;
import tongxun.zhy.dz.com.tongxun.utils.StorageType;
import tongxun.zhy.dz.com.tongxun.utils.StorageUtil;
import tongxun.zhy.dz.com.tongxun.wode.ImgAdapter;

public class AddShangpinActivity extends BaseActivity {


    @BindView(R.id.titleqtop)
    TextView titleqtop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.titleleftimg)
    ImageView titleleftimg;
    @BindView(R.id.titleback)
    LinearLayout titleback;
    @BindView(R.id.titleright)
    TextView titleright;
    @BindView(R.id.titlerightimg)
    ImageView titlerightimg;
    @BindView(R.id.mEtaddSp_name)
    EditText mEtaddSpName;
    @BindView(R.id.mTvaddSp_namenum)
    TextView mTvaddSpNamenum;
    @BindView(R.id.mLnaddSp_fenlei)
    LinearLayout mLnaddSpFenlei;
    @BindView(R.id.mLnaddSp_kuaidi)
    LinearLayout mLnaddSpKuaidi;
    @BindView(R.id.mEtaddSp_jianjie)
    EditText mEtaddSpJianjie;
    @BindView(R.id.mTvaddSp_jianjienum)
    TextView mTvaddSpJianjienum;
    @BindView(R.id.mIvaddSp_img)
    ImageView mIvaddSpImg;
    @BindView(R.id.mGvaddSp_img)
    MyGridview mGvaddSpImg;
    @BindView(R.id.mRladdSp_queding)
    RelativeLayout mRladdSpQueding;
    @BindView(R.id.mTvaddSp_fenlei)
    TextView mTvaddSpFenlei;
    @BindView(R.id.mTvaddSp_youfei)
    TextView mTvaddSpYoufei;
    @BindView(R.id.mGvaddsp_guige)
    MyListView mGvaddspGuige;
    @BindView(R.id.mTvaddsp_tianjiaguige)
    TextView mTvaddspTianjiaguige;
    @BindView(R.id.mLnaddsp_view)
    LinearLayout mLnaddspView;
    @BindView(R.id.mSvaddsp_scroll)
    ScrollView mSvaddspScroll;
    private Activity context;
    private ACache aCache;
    private ImgAdapter imgAdapter;
    private List<String> imglist;
    private LoadingDialog loadingDialog;
    private List<String> baoyoulist;
    private List<FenLeiBean.DataBean> fenleilist;
    private Gongjuutils gongjuutils = new Gongjuutils();
    private Pop pop;
    private String fenleiid = "", youfeiid = "2";
    private UpdateshangpinBean updateshangpinBean;
    private List<UpdateshangpinBean.DataBean.SmallBean> list;
    private String FengmianImg = "";//商品封面图
    private EditText name, money, kucun;
    private TextView smallid;

    /**
     * photo
     */
    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private FenLeiBean fenLeiBean;
    private int flag = 0;
    private static final String TAG = "AddShangpinActivity";

    String outputPath;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    break;
                case 2:
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_shangpin);
        ButterKnife.bind(this);
        context = this;
        aCache = ACache.get(context);
        loadingDialog = new LoadingDialog(context);
        ToumingUtil.touming(this);
        imglist = new ArrayList<>();
        list = new ArrayList<>();
        titleqtop.getLayoutParams().height = GetHeightUtil.getztl(context);
        titleqtop.setBackgroundResource(R.color.zhuse);
        title.setText("添加商品");
        titleright.setTextColor(getResources().getColor(R.color.white));
        titleright.setText("发布");
        titleright.setTextSize(14);
        titleright.setVisibility(View.VISIBLE);
        fenleilist = new ArrayList<>();
        baoyoulist = new ArrayList<>();
        baoyoulist.add("到付");
        baoyoulist.add("包邮");
        title.setTextColor(getResources().getColor(R.color.white));
        titleleftimg.setImageResource(R.mipmap.fanhuibai);
        titleleftimg.setVisibility(View.VISIBLE);
        title.setBackgroundResource(R.color.zhuse);
        getFenlei();
        if (getIntent().getStringExtra("type").equals("2")) {
            seachshangpin();
        }

    }

    @Override
    protected void initData() {

        mEtaddSpName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTvaddSpNamenum.setText(s.length() + "/60");
            }
        });
        mEtaddSpJianjie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTvaddSpJianjienum.setText(s.length() + "/200");
            }
        });

    }


    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View view) {

    }


    private void setimgadapter() {
        if (imgAdapter == null) {
            imgAdapter = new ImgAdapter(context, imglist);
            mGvaddSpImg.setAdapter(imgAdapter);
        } else {
            imgAdapter.notifyDataSetChanged();
        }
        mGvaddSpImg.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                IDialog iDialog = new DialogImpl();
                iDialog.showDialog(context, "", "确认要删除这张图片吗？", "确认", "取消", new DialogCallBack() {
                    @Override
                    public void onClick(int what) {
                        if (what == IDialog.YES) {
                            imglist.remove(i);
                            setimgadapter();
                        }
                    }
                }).show();
                return true;
            }
        });
        mGvaddSpImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                imgAdapter.bian(i);
                FengmianImg = imglist.get(i);
            }
        });
    }


    @OnClick({R.id.mLnaddSp_fenlei, R.id.mLnaddSp_kuaidi, R.id.mIvaddSp_img, R.id.titleback, R.id.mRladdSp_queding, R.id.titleright})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mLnaddSp_fenlei:
                if (fenleilist.size() < 0) {
                    getFenlei();
                    flag = 1;

                } else {
                    pop = new Pop(context, 1, mLnaddSpKuaidi, fenleilist);
                }
                pop.fenleilistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        pop.dismiss();
                        mTvaddSpFenlei.setText(fenleilist.get(i).getName());
                        fenleiid = fenleilist.get(i).getId();
                    }
                });
                break;
            case R.id.mLnaddSp_kuaidi:
                pop = new Pop(context, 2, mLnaddSpKuaidi, fenleilist);
                pop.baoyou.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mTvaddSpYoufei.setText("包邮");
                        youfeiid = "1";
                        pop.dismiss();
                    }
                });
                pop.daofu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mTvaddSpYoufei.setText("到付");
                        youfeiid = "2";
                        pop.dismiss();
                    }
                });
                break;
            case R.id.mIvaddSp_img:
                if (imglist.size() == 8) {
                    Toast.makeText(context, "最多上传8张图片", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    xuanze();
                }
                break;
            case R.id.titleback:
                finish();
                break;
            case R.id.mRladdSp_queding:
                if (mEtaddSpName.getText().length() < 1) {
                    Toast.makeText(context, "请输入商品名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEtaddSpJianjie.getText().length() < 1) {
                    Toast.makeText(context, "请输入商品简介", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fenleiid.equals("")) {
                    Toast.makeText(context, "请重新选择分类", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (imglist.size() < 1) {
                    Toast.makeText(context, "请选择图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                setshangpin();
                break;
            case R.id.titleright:
                if (mEtaddSpName.getText().length() < 1) {
                    Toast.makeText(context, "请输入商品名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEtaddSpJianjie.getText().length() < 1) {
                    Toast.makeText(context, "请输入商品简介", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fenleiid.equals("")) {
                    Toast.makeText(context, "请重新选择分类", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (imglist.size() < 1) {
                    Toast.makeText(context, "请选择图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                //   getDataList();
                loadingDialog.show();
                setshangpin();
                break;
        }
    }

    private void setshangpin() {
        loadingDialog.show();
        JSONObject jsonObject = new JSONObject();
        JSONArray ja = new JSONArray();
        try {
            List<UpdateshangpinBean.DataBean.SmallBean> datalist = new ArrayList<>();
            datalist = getDataList();
            if (datalist.size() < 1) {
                loadingDialog.dismiss();
                Toast.makeText(context, "请输入商品规格", Toast.LENGTH_SHORT).show();
                return;
            }
            for (int i = 0; i < datalist.size(); i++) {
                JSONObject js = new JSONObject();
                js.put("small_name", datalist.get(i).getSmall_name());
                js.put("small_price", datalist.get(i).getSmall_price());
                js.put("small_stock", datalist.get(i).getSmall_stock());
                js.put("small_id", datalist.get(i).getSmall_id());
                ja.put(js);
            }
            if (getIntent().getStringExtra("type").equals("2")) {
                jsonObject.put("id", getIntent().getStringExtra("id"));
            }
            jsonObject.put("uid", aCache.getAsString("uid"));
            jsonObject.put("name", mEtaddSpName.getText().toString());
            jsonObject.put("cid", fenleiid);
            jsonObject.put("pinkage", youfeiid);
            jsonObject.put("picture", FengmianImg);
            jsonObject.put("detail", mEtaddSpJianjie.getText().toString());
            String imgli = "";
            for (int i = 0; i < imglist.size(); i++) {
                imgli = imgli + imglist.get(i) + ",";
            }
            jsonObject.put("img", imgli);
            jsonObject.put("small", ja);
            Log.e("添加商品", "json" + jsonObject);
            HttpUtils.post(context, Common.AddShangpin, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    loadingDialog.dismiss();
                    Log.e("添加商品text", text);
                    try {
                        JSONObject json = new JSONObject(text);
                        Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                        if (json.getString("status").equals("1")) {
                            Intent intent = new Intent(context, ShopWebActivity.class);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                protected void onFailure(ResponseException e) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void seachshangpin() {
        loadingDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getIntent().getStringExtra("id"));

            Log.e("修改商品json", "" + jsonObject);
            HttpUtils.post(context, Common.SeachShangpin, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    Log.e("修改商品text", text);
                    loadingDialog.dismiss();
                    updateshangpinBean = GsonUtils.JsonToBean(text, UpdateshangpinBean.class);
                    if (updateshangpinBean.getStatus().equals("1")) {
                        mEtaddSpName.setText(updateshangpinBean.getData().getName());
                        mTvaddSpFenlei.setText(updateshangpinBean.getData().getCname());
                        fenleiid = updateshangpinBean.getData().getCid();
                        FengmianImg = updateshangpinBean.getData().getPicture();
                        if (updateshangpinBean.getData().getPinkage().equals("1")) {
                            mTvaddSpYoufei.setText("包邮");
                        } else {
                            mTvaddSpYoufei.setText("到付");
                        }
                        mEtaddSpJianjie.setText(updateshangpinBean.getData().getDetail());
                        for (int i = 0; i < updateshangpinBean.getData().getSlideshow().size(); i++) {
                            imglist.add(updateshangpinBean.getData()
                                    .getSlideshow().get(i));
                        }
                        setimgadapter();
                        for (int i = 0; i < updateshangpinBean.getData().getSlideshow().size(); i++) {
                            if (updateshangpinBean.getData().getPicture().equals(updateshangpinBean.getData().getSlideshow().get(i))) {
                                imgAdapter.bian(i);
                            }
                        }
                        for (int i = 0; i < updateshangpinBean.getData().getSmall().size(); i++) {
                            addViewItem(updateshangpinBean.getData().getSmall().get(i).getSmall_id(), updateshangpinBean.getData().getSmall().get(i).getSmall_name(),
                                    updateshangpinBean.getData().getSmall().get(i).getSmall_price(), updateshangpinBean.getData().getSmall().get(i).getSmall_stock(), 2);
                        }
                    }

                }

                @Override
                protected void onFailure(ResponseException e) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getFenlei() {
        JSONObject jsonObject = new JSONObject();
        HttpUtils.post(context, Common.FenLei, jsonObject, new TextCallBack() {
            @Override
            protected void onSuccess(String text) {
                fenLeiBean = GsonUtils.JsonToBean(text, FenLeiBean.class);
                if (fenLeiBean.getStatus() == 1) {
                    for (int i = 0; i < fenLeiBean.getData().size(); i++) {
                        fenleilist.add(fenLeiBean.getData().get(i));
                    }
                } else {
                    Toast.makeText(context, "分类获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void onFailure(ResponseException e) {

            }
        });
    }


    /**
     * 相机
     */
    public void xuanze() {
        final XuanzeDialog imageDialog = new XuanzeDialog(AddShangpinActivity.this);
        imageDialog.setData();
        imageDialog.setOnConfirmListener(new XuanzeDialog.OnConfirmListener() {
            @Override
            public void onConfirm() {
                int text = imageDialog.getText();
                switch (text) {
                    case 0:

                        break;
                    case 1:
                        //   takePhoto();
                        /***拍照片onActivityResult {@link GalleryActivity.PHOTOS}*/
                        GalleryHelper.with(AddShangpinActivity.this).type(GalleryConfig.TAKE_PHOTO).requestCode(12).execute();
//                        /***拍照片并裁剪 onActivityResult{@link GalleryActivity.CROP}*/
//                        outputPath = StorageUtil.getWritePath(StorageUtil.get32UUID() + ".jpg", StorageType.TYPE_TEMP);
//                        GalleryHelper.with(AddShangpinActivity.this).type(GalleryConfig.TAKE_PHOTO).isNeedCropWithPath(outputPath).requestCode(12).execute();


                        break;
                    case 2:
                        //   choiseImg();
                        /*** 选择单张图片 onActivityResult{@link GalleryActivity.PHOTOS}*/
                        GalleryHelper.with(AddShangpinActivity.this).type(GalleryConfig.SELECT_PHOTO).requestCode(12).singlePhoto().execute();
//                        /***选择单张图片并裁剪 onActivityResult{@link GalleryActivity.PHOTOS}*/
//                        outputPath = StorageUtil.getWritePath(StorageUtil.get32UUID() + ".jpg", StorageType.TYPE_TEMP);
//                        GalleryHelper.with(AddShangpinActivity.this).type(GalleryConfig.SELECT_PHOTO).requestCode(12).singlePhoto().isNeedCropWithPath(outputPath).execute();

                        break;
                }


            }
        });
        imageDialog.show();
    }

    private void takePhoto() {
        //创建File对象，用于存储拍照后的照片
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        } else {
            //权限已经被授予，在这里直接写要执行的相应方法即可
            if (Build.VERSION.SDK_INT >= 24) {
                imageUri = FileProvider.getUriForFile(AddShangpinActivity.this, "tongxun.zhy.dz.com.tongxun.Fileprovider", outputImage);
            } else {
                imageUri = Uri.fromFile(outputImage);
            }
        }


        // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);


        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    private void choiseImg() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }

    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);//打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "拒绝了权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data.getStringArrayListExtra(GalleryActivity.PHOTOS) != null) {//选择图片返回
//
                ArrayList<String> path = data.getStringArrayListExtra(GalleryActivity.PHOTOS);
//                Toast.makeText(MainActivity.this, path.toString(), Toast.LENGTH_SHORT).show();
                try {
                    FileInputStream fis = new FileInputStream(path.get(0));
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);

                    Matrix matrix = new Matrix();
                    matrix.postRotate(readPictureDegree(path.get(0))); /*翻转90度*/
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);


                    ByteArrayOutputStream baos;
                    // mIvaddSpImg.setImageBitmap(bitmap);
                    baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imgs;
                    //                        bytes = baos.toByteArray();
                    imgs = baos.toByteArray();

                    String base = Base64.encodeToString(imgs, Base64.DEFAULT);
                    chuantupian(base);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
//
            if (data.getStringExtra(GalleryActivity.DATA) != null) {//裁剪回来
                Log.e(TAG, "onActivityResult: "+"111111111111111111" );
                if (outputPath == null) {//没有传入返回裁剪路径

                    Log.e(TAG, "onActivityResult: "+"22222222222222222222" );
                    byte[] datas = data.getByteArrayExtra(GalleryActivity.DATA);
                    try {
                        FileInputStream fis = new FileInputStream(datas.toString());
                        Bitmap bitmap = BitmapFactory.decodeStream(fis);

                        Matrix matrix = new Matrix();
                        matrix.postRotate(readPictureDegree(datas.toString())); /*翻转90度*/
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
                        ByteArrayOutputStream baos;
                        // mIvaddSpImg.setImageBitmap(bitmap);
                        baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imgs;
                        //                        bytes = baos.toByteArray();
                        imgs = baos.toByteArray();

                        String base = Base64.encodeToString(imgs, Base64.DEFAULT);
                        chuantupian(base);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

//
        }
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        //将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                        ByteArrayOutputStream baos;

                        baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                        byte[] imgs;
                        //                        bytes = baos.toByteArray();
                        imgs = baos.toByteArray();

                        String base = Base64.encodeToString(imgs, Base64.DEFAULT);
                        chuantupian(base);
//                        if (photoflag.equals("1")) {
                        //   mIvziliaoTouxing.setImageBitmap(bitmap);
//                        } else {
//                            iMsmFanmian.setImageBitmap(bitmap);
//                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        //4.4以下系统使用这个放出处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        diaplayImage(imagePath);//根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        diaplayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void diaplayImage(String imagePath) {

        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);


            ByteArrayOutputStream baos;

            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imgs;
            //                        bytes = baos.toByteArray();
            imgs = baos.toByteArray();

            String base = Base64.encodeToString(imgs, Base64.DEFAULT);
            chuantupian(base);
//            if (photoflag.equals("1")) {
            // img.setImageBitmap(bitmap);
//            } else {
//                iMsmFanmian.setImageBitmap(bitmap);
//            }
        } else {
            Toast.makeText(this, "failed to get iamge", Toast.LENGTH_SHORT).show();
        }
    }


    private void chuantupian(String baseimg) {
        loadingDialog.show();
        JSONObject jsonObject = new JSONObject();

        try {
            //  jsonObject.put("uid",aCache.getAsString("uid"));
            jsonObject.put("img", "data:image/jpeg;base64," + baseimg);
            Log.e("tupianimg", jsonObject + "");
            HttpUtils.post(context, Common.Setimg, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    loadingDialog.dismiss();
                    try {
                        JSONObject json = new JSONObject(text);
                        if (json.getString("status").equals("1")) {
                            imglist.add(json.getString("data"));
                            if (imglist.size() == 1) {
                                FengmianImg = imglist.get(0);
                            }
                            setimgadapter();
                        } else {
                            Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                protected void onFailure(ResponseException e) {

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.mTvaddsp_tianjiaguige)
    public void onViewClicked() {
        List<UpdateshangpinBean.DataBean.SmallBean> linshilist = new ArrayList<>();
        addViewItem("", "", "", "", 1);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    /**
     * 添加item
     */
    private void addViewItem(String ids, String names, String moneys, String kucuns, int type) {
        View viewItem = LayoutInflater.from(this).inflate(R.layout.item_add_vip_num, mLnaddspView, false);
        mLnaddspView.addView(viewItem);
        smallid = viewItem.findViewById(R.id.tv_indexid);
        name = (EditText) viewItem.findViewById(R.id.mIvshangpinguigeada_name);
        money = viewItem.findViewById(R.id.mIvshangpinguigeada_money);
        kucun = viewItem.findViewById(R.id.mIvshangpinguigeada_kucun);
        if (type == 2) {
            smallid.setText(ids);
            name.setText(names);
            money.setText(moneys);
            kucun.setText(kucuns);
        }

        sortViewItem();
        //添加并且排序之后将布局滚动到底部，方便用户继续添加
        mSvaddspScroll.post(new Runnable() {
            @Override
            public void run() {
                mSvaddspScroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

    }

    /**
     * 该方法主要用于排序（每个item中的序号），主要针对从中间删除item的情况
     */
    private void sortViewItem() {
        for (int i = 0; i < mLnaddspView.getChildCount(); i++) {
            final View viewItem = mLnaddspView.getChildAt(i);
            TextView tvIndex = (TextView) viewItem.findViewById(R.id.tv_index);
            tvIndex.setText((i + 1) + "");
            LinearLayout llDelete = (LinearLayout) viewItem.findViewById(R.id.ll_delete);
            llDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLnaddspView.removeView(viewItem);
                    sortViewItem();
                }
            });
        }
    }

    private List<UpdateshangpinBean.DataBean.SmallBean> getDataList() {
        List<UpdateshangpinBean.DataBean.SmallBean> result = new ArrayList<>();
        for (int i = 0; i < mLnaddspView.getChildCount(); i++) {
            View itemView = mLnaddspView.getChildAt(i);
            EditText name = (EditText) itemView.findViewById(R.id.mIvshangpinguigeada_name);
            EditText money = (EditText) itemView.findViewById(R.id.mIvshangpinguigeada_money);
            EditText kucun = (EditText) itemView.findViewById(R.id.mIvshangpinguigeada_kucun);
            TextView smallid = itemView.findViewById(R.id.tv_indexid);
            if (!TextUtils.isEmpty(name.getText().toString().trim()) && !TextUtils.isEmpty(money.getText().toString().trim()) && !TextUtils.isEmpty(kucun.getText().toString().trim())) {
                String names = name.getText().toString().trim();
                String moneys = money.getText().toString().trim();
                String kucuns = kucun.getText().toString().trim();
                String smallids = smallid.getText().toString().trim();
//                Log.e(TAG, "getDataListnames: " + names);
//                Log.e(TAG, "getDataListmoneys: " + moneys);
                UpdateshangpinBean.DataBean.SmallBean date = new UpdateshangpinBean.DataBean.SmallBean();
                date.setSmall_name(names);
                date.setSmall_price(moneys);
                date.setSmall_stock(kucuns);
                date.setSmall_id(smallids);
                result.add(date);
            }
//            Log.e(TAG, "getDataListname: " + result.get(i).getSmall_name());
//            Log.e(TAG, "getDataListmoney: " + result.get(i).getSmall_price());
        }
        Log.e(TAG, "getDataList: " + result.size());
        return result;
    }


    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path 照片路径
     * @return角度
     */
    public static int readPictureDegree(String path) {
        //传入图片路径
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


}
