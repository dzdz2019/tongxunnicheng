package tongxun.zhy.dz.com.tongxun.shipin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dhc.gallery.GalleryConfig;
import com.dhc.gallery.GalleryHelper;
import com.dhc.gallery.ui.GalleryActivity;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.base.BaseActivity;
import tongxun.zhy.dz.com.tongxun.base.ShopWebActivity;
import tongxun.zhy.dz.com.tongxun.base.WebActivity;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.shipin.bean.ShipinfenleiBean;
import tongxun.zhy.dz.com.tongxun.shipin.bean.VideoDataBean;
import tongxun.zhy.dz.com.tongxun.tools.ACache;
import tongxun.zhy.dz.com.tongxun.tools.GetHeightUtil;
import tongxun.zhy.dz.com.tongxun.tools.GsonUtils;
import tongxun.zhy.dz.com.tongxun.tools.HttpUtils;
import tongxun.zhy.dz.com.tongxun.tools.LoadingDialog;
import tongxun.zhy.dz.com.tongxun.tools.TextCallBack;
import tongxun.zhy.dz.com.tongxun.tools.ToumingUtil;
import tongxun.zhy.dz.com.tongxun.wode.activity.XuanzeDialog;

public class AddShipin extends BaseActivity {

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
    @BindView(R.id.mIvaddvideo_video)
    ImageView mIvaddvideoVideo;
    @BindView(R.id.mEtaddvideo_name)
    EditText mEtaddvideoName;
    @BindView(R.id.mEtaddvideo_jianjie)
    EditText mEtaddvideoJianjie;
    @BindView(R.id.mGvaddvideo_grideview)
    GridView mGvaddvideoGrideview;
    @BindView(R.id.mTvaddvideo_shangchuan)
    TextView mTvaddvideoShangchuan;
    @BindView(R.id.mTvaddvideo_xieyi)
    TextView mTvaddvideoXieyi;
    @BindView(R.id.mTvaddvideo_mianfei)
    TextView mTvaddvideoMianfei;
    @BindView(R.id.mTvaddvideo_shoufei)
    TextView mTvaddvideoShoufei;
    @BindView(R.id.mTvaddvideo_mianfeicon)
    TextView mTvaddvideoMianfeicon;
    @BindView(R.id.mEtaddvideo_shoufei)
    EditText mEtaddvideoShoufei;
    @BindView(R.id.mLnaddvideo_soufeicon)
    LinearLayout mLnaddvideoSoufeicon;

    private Context context;
    private LoadingDialog loadingDialog;
    private ACache aCache;
    private String filPaths = "";
    UploadManager uploadManager;
    private String tokens = "";
    private int type, shangchuantype = 1, shangchuanqiniu = 1;
    private ShipinfenleiBean shipinfenleiBean;
    private List<ShipinfenleiBean.DataBean> fenleilist;
    private ShiPinFenleiAdapter fenleiAdapter;
    private String fenleiid = "";
    private String videoname = "";
    private Intent intent;
    String base = "";
    String key = "";
    private String time = "", money;
    private int moneytype = 1;
    private VideoDataBean videoDataBean;
private String TAG="Addshipin";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    shangchuantype = 2;
                    shangchuanqiniu = 2;
                    key = tokens + aCache.getAsString("uid");//<指定七牛服务上的文件名，或 null>
                    videoname = key;
                    shangchuanqiniu();
                    shangchuanxinxi();
                    break;
                case 2:
                    break;
                case 3:
                    if (shipinfenleiBean.getData().size() > 0) {
                        for (int i = 0; i < shipinfenleiBean.getData().size(); i++) {
                            fenleilist.add(shipinfenleiBean.getData().get(i));
                        }
                        mGvaddvideoGrideview.setNumColumns(fenleilist.size());
                        setFenleiAdapter();
                        if (getIntent().getStringExtra("type").equals("2")) {

                            for(int i=0;i<fenleilist.size();i++){
                                if(fenleilist.get(i).getId().indexOf(fenleiid)!=-1){
                                   fenleiAdapter.bian(i);
                                }
                            }

                        } else {
                            fenleiid = fenleilist.get(0).getId();
                        }

                    } else {
                        Toast.makeText(context, "暂无分类", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 4:
                    break;
                case 5:
                    fenleiid = videoDataBean.getData().get(0).getCid();
                    getfenlei();
                    Glide.with(context).load(Common.ImgUrl + videoDataBean.getData().get(0).getImg()).into(mIvaddvideoVideo);
                    mEtaddvideoName.setText(videoDataBean.getData().get(0).getTitle());
                    mEtaddvideoJianjie.setText(videoDataBean.getData().get(0).getIntroduce());
                    if (videoDataBean.getData().get(0).getCharge_status().equals("1")) {//不收费


                        moneytype = 1;
                        mTvaddvideoMianfei.setBackgroundResource(R.drawable.yjzhusequand);
                        mTvaddvideoMianfei.setTextColor(getResources().getColor(R.color.white));
                        mTvaddvideoMianfeicon.setVisibility(View.VISIBLE);
                        mTvaddvideoShoufei.setBackgroundResource(R.color.white);
                        mTvaddvideoShoufei.setTextColor(getResources().getColor(R.color.c333333));
                        mLnaddvideoSoufeicon.setVisibility(View.GONE);
                    } else {
                        moneytype = 2;
                        mTvaddvideoMianfei.setBackgroundResource(R.color.white);
                        mTvaddvideoMianfei.setTextColor(getResources().getColor(R.color.c333333));
                        mTvaddvideoMianfeicon.setVisibility(View.GONE);
                        mTvaddvideoShoufei.setBackgroundResource(R.drawable.yjzhusequand);
                        mTvaddvideoShoufei.setTextColor(getResources().getColor(R.color.white));
                        mLnaddvideoSoufeicon.setVisibility(View.VISIBLE);
                        mEtaddvideoShoufei.setText(videoDataBean.getData().get(0).getCharge());
                    }
                    break;
                case 6:
                    Toast.makeText(context, "视频信息获取失败，请重试", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }

        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_add_shipin);
        ButterKnife.bind(this);
        context = this;
        aCache = ACache.get(context);
        loadingDialog = new LoadingDialog(context);

    }

    @Override
    protected void initData() {
        titleqtop.setBackgroundResource(R.color.zhuse);
        titleqtop.getLayoutParams().height = GetHeightUtil.getztl(context);
        ToumingUtil.touming(this);
        title.setBackgroundResource(R.color.white);
        titleright.setText("视频规范");
        title.setText("上传视频");
        titleright.setVisibility(View.VISIBLE);
        titleleftimg.setVisibility(View.VISIBLE);
        titleback.setVisibility(View.VISIBLE);
        titleleftimg.setImageResource(R.mipmap.xiangyou);
        titleleftimg.setRotation(180);
        fenleilist = new ArrayList<>();
        mTvaddvideoMianfei.setBackgroundResource(R.drawable.yjzhusequand);
        mTvaddvideoShoufei.setBackgroundResource(R.color.white);
        mTvaddvideoMianfeicon.setVisibility(View.VISIBLE);
        mLnaddvideoSoufeicon.setVisibility(View.GONE);

        if (getIntent().getStringExtra("type").equals("2")) {//修改视频
            mTvaddvideoShangchuan.setText("确认修改");
            getdata();
        } else {//发布视频
            getfenlei();
        }
    }

    @Override
    protected void initListener() {
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
//                .recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
//                .recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
// 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);
    }

    @Override
    public void onClick(View view) {

    }

    private void setFenleiAdapter() {
        int size = fenleilist.size();
        int length = 100;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        mGvaddvideoGrideview.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        mGvaddvideoGrideview.setColumnWidth(itemWidth); // 设置列表项宽
        mGvaddvideoGrideview.setHorizontalSpacing(5); // 设置列表项水平间距
        mGvaddvideoGrideview.setStretchMode(GridView.NO_STRETCH);
        mGvaddvideoGrideview.setNumColumns(size); // 设置列数量=列表集合数

        if (fenleiAdapter == null) {
            fenleiAdapter = new ShiPinFenleiAdapter(context, fenleilist);
            mGvaddvideoGrideview.setAdapter(fenleiAdapter);
        } else {
            fenleiAdapter.notifyDataSetChanged();

        }
        mGvaddvideoGrideview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(context, "111", Toast.LENGTH_SHORT).show();
                fenleiAdapter.bian(i);
                fenleiid = fenleilist.get(i).getId();

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
    }

    @OnClick({R.id.titleback, R.id.titleright, R.id.mIvaddvideo_video, R.id.mTvaddvideo_shangchuan, R.id.mTvaddvideo_xieyi,
            R.id.mTvaddvideo_mianfei, R.id.mTvaddvideo_shoufei})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleback:
                finish();
                break;
            case R.id.titleright:
                intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", Common.VideoGuiFan);
                intent.putExtra("title", "视频规范");
                startActivity(intent);
                break;
            case R.id.mIvaddvideo_video:
                if (getIntent().getStringExtra("type").equals("2")) {//修改视频
                    Toast.makeText(context, "原视频不可以修改哦", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    xuanze();
                }
                break;
            case R.id.mTvaddvideo_shangchuan:
                if (getIntent().getStringExtra("type").equals("1")){
                if (filPaths.equals("")) {
                    Toast.makeText(context, "视频获取失败，请重新选择", Toast.LENGTH_SHORT).show();
                    return;
                }
                    if (base.equals("")) {
                        Toast.makeText(context, "请重新选择视频", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if (mEtaddvideoName.getText().toString().equals("")) {
                    Toast.makeText(context, "请输入视频标题", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (mEtaddvideoJianjie.getText().toString().equals("")) {
                    Toast.makeText(context, "请输入视频简介", Toast.LENGTH_SHORT).show();
                    break;
                }

//                if (time.equals("") || time.equals("0")) {
//                  //  Toast.makeText(context, "视频时长获取错误，请重新选择", Toast.LENGTH_SHORT).show();
//                    break;
//                }
                if (moneytype == 2) {
                    if (TextUtils.isEmpty(mEtaddvideoShoufei.getText().toString().trim())) {
                        Toast.makeText(context, "请输入收费价格", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        money = mEtaddvideoShoufei.getText().toString().trim();
                    }
                }
                loadingDialog.show();
                if (getIntent().getStringExtra("type")==null||getIntent().getStringExtra("type").equals("")||getIntent().getStringExtra("type").equals("2")){
                    shangchuanxinxi();
                }else {
                gettoken();
                }
                break;
            case R.id.mTvaddvideo_xieyi:
                intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", Common.VideoShangchuanXIeyi);
                intent.putExtra("title", "上传协议");
                startActivity(intent);
                break;
            case R.id.mTvaddvideo_mianfei:
                moneytype = 1;
                mTvaddvideoMianfei.setBackgroundResource(R.drawable.yjzhusequand);
                mTvaddvideoMianfei.setTextColor(getResources().getColor(R.color.white));
                mTvaddvideoMianfeicon.setVisibility(View.VISIBLE);
                mTvaddvideoShoufei.setBackgroundResource(R.color.white);
                mTvaddvideoShoufei.setTextColor(getResources().getColor(R.color.c333333));
                mLnaddvideoSoufeicon.setVisibility(View.GONE);
                break;
            case R.id.mTvaddvideo_shoufei:
                if (aCache.getAsString("shiming").equals("1")) {
                    moneytype = 2;
                    mTvaddvideoMianfei.setBackgroundResource(R.color.white);
                    mTvaddvideoMianfei.setTextColor(getResources().getColor(R.color.c333333));
                    mTvaddvideoMianfeicon.setVisibility(View.GONE);
                    mTvaddvideoShoufei.setBackgroundResource(R.drawable.yjzhusequand);
                    mTvaddvideoShoufei.setTextColor(getResources().getColor(R.color.white));
                    mLnaddvideoSoufeicon.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(context, "请先进行实名认证", Toast.LENGTH_SHORT).show();
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.Shiming + "?uid=" + aCache.getAsString("uid"));
                    startActivity(intent);
                }

                break;
        }
    }


    /**
     * shipin
     */
    public void xuanze() {
        final XuanzeDialog imageDialog = new XuanzeDialog(this);
        imageDialog.setDatas();
        imageDialog.setOnConfirmListener(new XuanzeDialog.OnConfirmListener() {
            @Override
            public void onConfirm() {
                int text = imageDialog.getText();
                type = text;
                if (text == 0) {
                    return;
                }
                if (text == 1) {
                    GalleryHelper.with(AddShipin.this).type(GalleryConfig.RECORD_VEDIO).requestCode(12)
                            .limitRecordTime(Integer.parseInt(aCache.getAsString("videotime")))//限制时长
                            .limitRecordSize(300)//限制大小
                            .execute();
//                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);// 创建一个请求视频的意图
//                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);// 设置视频的质量，值为0-1，
//                    intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 50);// 设置视频的录制长度，s为单位
////                    intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20 * 1024 * 1024L);// 设置视频文件大小，字节为单位
//                    startActivityForResult(intent, 1);// 设置请求码，在onActivityResult()方法中接收结果
                }
                if (text == 2) {

//                    switch (position) {
//                        case 0: /*** 选择单张图片 onActivityResult{@link GalleryActivity.PHOTOS}*/
//                            GalleryHelper.with(MainActivity.this).type(GalleryConfig.SELECT_PHOTO).requestCode(12).singlePhoto().execute();
//                            break;
//                        case 1:  /***选择单张图片并裁剪 onActivityResult{@link GalleryActivity.PHOTOS}*/
//                            outputPath = StorageUtil.getWritePath(StorageUtil.get32UUID() + ".jpg", StorageType.TYPE_TEMP);
//                            GalleryHelper.with(MainActivity.this).type(GalleryConfig.SELECT_PHOTO).requestCode(12).singlePhoto().isNeedCropWithPath(outputPath).execute();
//                            break;
//                        case 2:  /*** 选择多张图片 onActivityResult{@link GalleryActivity.PHOTOS}*/
//                            GalleryHelper.with(MainActivity.this).type(GalleryConfig.SELECT_PHOTO).requestCode(12).limitPickPhoto(9).execute();
//                            break;
//                        case 3:/***选择视频 onActivityResult{@link GalleryActivity.VIDEO}*/
//                            GalleryHelper.with(MainActivity.this).type(GalleryConfig.SELECT_VEDIO).requestCode(12).isSingleVedio().execute();
//                            break;
//                        case 4:/***拍摄视频 onActivityResult{@link GalleryActivity.VIDEO}*/
//
//                            GalleryHelper.with(MainActivity.this).type(GalleryConfig.RECORD_VEDIO).requestCode(12)
////                                .limitRecordTime(10)//限制时长
//                                    .limitRecordSize(1)//限制大小
//                                    .execute();
//                            break;
//                        case 5:/***拍照片onActivityResult {@link GalleryActivity.PHOTOS}*/
//                            GalleryHelper.with(MainActivity.this).type(GalleryConfig.TAKE_PHOTO).requestCode(12).execute();
//                            break;
//                        case 6: /***拍照片并裁剪 onActivityResult{@link GalleryActivity.CROP}*/
//                            outputPath = StorageUtil.getWritePath(StorageUtil.get32UUID() + ".jpg", StorageType.TYPE_TEMP);
//                            GalleryHelper.with(MainActivity.this).type(GalleryConfig.TAKE_PHOTO).isNeedCropWithPath(outputPath).requestCode(12).execute();
//                            break;
                    /***选择视频 onActivityResult{@link GalleryActivity.VIDEO}*/
                    GalleryHelper.with(AddShipin.this).type(GalleryConfig.SELECT_VEDIO).requestCode(12).isSingleVedio().limitRecordSize(1).execute();
                }

            }
        });
        imageDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data.getStringExtra(GalleryActivity.VIDEO) != null) {//选择和拍摄视频(目前支持单选)
                String path = data.getStringExtra(GalleryActivity.VIDEO);
                int duration = 0;
                if (type == 2) {

//                    try {
//                        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//                        mmr.setDataSource(path);
//                        duration = Integer.parseInt(mmr.extractMetadata
//                                (MediaMetadataRetriever.METADATA_KEY_DURATION));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Toast.makeText(context, "视频信息获取失败请重新选择", Toast.LENGTH_SHORT).show();
//                    }
//                    Log.e("视频time", duration + "");
//                    time = duration + "";
//                    if (duration > Integer.parseInt(aCache.getAsString("videotime")) * 1000) {
//                        Toast.makeText(context, "选择视频时间过长，请重新选择", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                    File f = new File(path);
                    if (f.exists() && f.isFile()) {
                        if (f.length() > 200 * 1024 * 1000) {
                            Toast.makeText(context, "选择视频过大，请处理后上传", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    } else {
                        Log.e("大小2222", "file doesn't exist or is not a file");
                        return;
                    }
                }
                if (type == 1) {
                    //   File f = new File(path);
                    try {
                        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                        mmr.setDataSource(path);
                        duration = Integer.parseInt(mmr.extractMetadata
                                (MediaMetadataRetriever.METADATA_KEY_DURATION));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "视频信息获取失败请重新选择", Toast.LENGTH_SHORT).show();
                    }
                    Log.e("视频time", duration + "");
                    time = duration + "";

//                    if (f.exists() && f.isFile()) {
//                        if (f.length() > 20 * 1024 * 1000) {
//                            Toast.makeText(context, "选择视频过大，请处理后上传", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//                    } else {
//                        Log.e("大小2222", "file doesn't exist or is not a file");
//                        return;
//                    }
                }

                // Toast.makeText(AddShipin.this, path.toString(), Toast.LENGTH_SHORT).show();
                tu(path);
                //     getLocalVideoDuration(context,path);
                filPaths = path;
//                loadingDialog.show();
//                gettoken();
            }

//            if (data.getStringArrayListExtra(GalleryActivity.PHOTOS) != null) {//选择图片返回
//
//                ArrayList<String> path = data.getStringArrayListExtra(GalleryActivity.PHOTOS);
//                Toast.makeText(MainActivity.this, path.toString(), Toast.LENGTH_SHORT).show();
//
//            } else if (data.getStringExtra(GalleryActivity.VIDEO) != null) {//选择和拍摄视频(目前支持单选)
//
//                String path = data.getStringExtra(GalleryActivity.VIDEO);
//                Toast.makeText(MainActivity.this, path.toString(), Toast.LENGTH_SHORT).show();
//
//            } else if (data.getStringExtra(GalleryActivity.DATA) != null) {//裁剪回来
//                if (outputPath == null) {//没有传入返回裁剪路径
//                    byte[] datas = data.getByteArrayExtra(GalleryActivity.DATA);
//                    Toast.makeText(MainActivity.this, datas.toString(), Toast.LENGTH_SHORT).show();
//
//                }
//            }
//

        }
    }

    private void shangchuanqiniu() {
        // showUploadVideoDialog();
        //指定zone的具体区域
//FixedZone.zone0   华东机房
//FixedZone.zone1   华北机房
//FixedZone.zone2   华南机房
//FixedZone.zoneNa0 北美机房
//自动识别上传区域
//AutoZone.autoZone
//Configuration config = new Configuration.Builder()
//.zone(Zone.autoZone)
//.build();
//UploadManager uploadManager = new UploadManager(config);
        // data = "";//<File对象、或 文件路径、或 字节数组>

        String token = tokens;//<从服务端SDK获取>
        uploadManager.put(filPaths, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            Log.e("qiniu", "Upload Success");
                            //loadingDialog.dismiss();
                            //  Toast.makeText(AddShipin.this, "上传成功", Toast.LENGTH_SHORT).show();
                            shangchuanqiniu = 1;
                            if (shangchuantype == 1 & shangchuanqiniu == 1) {
                                Toast.makeText(context, "视频发布成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } else {
                            Log.e("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                            Toast.makeText(AddShipin.this, "上传失败请重新上传", Toast.LENGTH_SHORT).show();
                            shangchuanqiniu = 1;
                        }
                        Log.e("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);


    }

    private void gettoken() {
        loadingDialog.show();
        JSONObject jsonObject = new JSONObject();
        HttpUtils.post(context, Common.Gettoken, jsonObject, new TextCallBack() {
            @Override
            protected void onSuccess(String text) {
                try {
                    JSONObject jsonObject1 = new JSONObject(text);
                    Message msg = new Message();
                    if (jsonObject1.getString("status").equals("1")) {
                        msg.what = 1;
                        tokens = jsonObject1.getString("data");
                    } else {
                        msg.what = 2;
                    }
                    Log.e("token", text);
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onFailure(ResponseException e) {

            }
        });
    }

    private void tu(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        Bitmap bitmap = media.getFrameAtTime();

        Matrix matrix = new Matrix();
        matrix.postRotate(readPictureDegree(path));
        int width = bitmap.getWidth();
        int height =bitmap.getHeight();
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

        mIvaddvideoVideo.setImageBitmap(bitmap);

        //  Bitmap bitmaps = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream baos;
        baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imgs;
        //                        bytes = baos.toByteArray();
        imgs = baos.toByteArray();
        base = Base64.encodeToString(imgs, Base64.DEFAULT);
    }

    private void getfenlei() {
        loadingDialog.show();
        JSONObject jsonObject = new JSONObject();
        HttpUtils.post(context, Common.ShipinFeenlei, jsonObject, new TextCallBack() {
            @Override
            protected void onSuccess(String text) {
                loadingDialog.dismiss();
                Message msg = new Message();
                shipinfenleiBean = GsonUtils.JsonToBean(text, ShipinfenleiBean.class);
                if (shipinfenleiBean.getStatus().equals("1")) {
                    msg.what = 3;
                } else {
                    msg.what = 4;
                }
                handler.sendMessage(msg);
            }

            @Override
            protected void onFailure(ResponseException e) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (shangchuantype == 2 || shangchuanqiniu == 2) {
            Toast.makeText(context, "视频正在上传，请稍后", Toast.LENGTH_SHORT).show();
            return;
        } else {
            loadingDialog.dismiss();
            finish();
        }
    }

    private void shangchuanxinxi() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (getIntent().getStringExtra("type").equals("2")){
                jsonObject.put("id",videoDataBean.getData().get(0).getId());
            }
            jsonObject.put("uid", aCache.getAsString("uid"));
            jsonObject.put("cid", fenleiid);
            jsonObject.put("title", mEtaddvideoName.getText().toString());
            jsonObject.put("introduce", mEtaddvideoJianjie.getText().toString());
            if (getIntent().getStringExtra("type").equals("1")){
                jsonObject.put("path", videoname);
                jsonObject.put("img", base);
                jsonObject.put("time", time);
            }
            jsonObject.put("charge_status", moneytype + "");
            if (moneytype == 2) {
                jsonObject.put("charge", money + "");
            }
            Log.e("上传视频信息json", jsonObject + "");
            HttpUtils.post(context, Common.AddVideo, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    Log.e("上传视频信息", text);

                    try {
                        JSONObject json = new JSONObject(text);
                        Toast.makeText(context, json.getString("msg"), Toast.LENGTH_SHORT).show();
                        if (json.getString("status").equals("1")) {
                            shangchuantype = 1;
                            if (shangchuantype == 1 & shangchuanqiniu == 1) {
                                Toast.makeText(context, "视频发布成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
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
    //获取视频总时长

    /**
     * get Local video duration
     *
     * @return
     */
    private void getLocalVideoDuration(Context context, String videoPath) {
        int duration = 0;
        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(videoPath);
            duration = Integer.parseInt(mmr.extractMetadata
                    (MediaMetadataRetriever.METADATA_KEY_DURATION));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "视频信息获取失败请重新选择", Toast.LENGTH_SHORT).show();
        }
        time = duration + "";
    }

    private void getdata() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getIntent().getStringExtra("id"));
            Log.e(TAG, "获取视频信息onSuccess:json "+jsonObject );
            HttpUtils.post(context, Common.VIdeodata, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    Log.e(TAG, "获取视频信息onSuccess: "+text );
                    videoDataBean = GsonUtils.JsonToBean(text, VideoDataBean.class);
                    Message msg = new Message();
                    if (videoDataBean.getStatus().equals("1")){
                        msg.what = 5;
                    } else {
                        msg.what = 6;
                    }
                    handler.sendMessage(msg);
                }

                @Override
                protected void onFailure(ResponseException e) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * 读取照片exif信息中的旋转角度
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
