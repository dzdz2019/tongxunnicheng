package tongxun.zhy.dz.com.tongxun.wode.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.base.BaseActivity;
import tongxun.zhy.dz.com.tongxun.base.ShopWebActivity;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.UpdateSmallshangpin;
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.UpdateshangpinBean;
import tongxun.zhy.dz.com.tongxun.tools.ACache;
import tongxun.zhy.dz.com.tongxun.tools.GetHeightUtil;
import tongxun.zhy.dz.com.tongxun.tools.GsonUtils;
import tongxun.zhy.dz.com.tongxun.tools.HttpUtils;
import tongxun.zhy.dz.com.tongxun.tools.LoadingDialog;
import tongxun.zhy.dz.com.tongxun.tools.TextCallBack;
import tongxun.zhy.dz.com.tongxun.tools.ToumingUtil;

public class AddSmallShangpinActivity extends BaseActivity {


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
    @BindView(R.id.mIvaddsmall_img)
    ImageView mIvaddsmallImg;
    @BindView(R.id.mEtaddsmall_name)
    EditText mEtaddsmallName;
    @BindView(R.id.mEtaddsmall_jiage)
    EditText mEtaddsmallJiage;
    @BindView(R.id.mEtaddsmall_kucun)
    EditText mEtaddsmallKucun;
    @BindView(R.id.mRladdsmall_queding)
    RelativeLayout mRladdsmallQueding;
    @BindView(R.id.mLnaddSp_img)
    LinearLayout mLnaddSpImg;
    @BindView(R.id.mTvaddSp_namenum)
    TextView mTvaddSpNamenum;
    private Context
            context;
    private String smallid;
    private ACache aCache;
    private String imgurl = "";

    private LoadingDialog loadingDialog;
    private UpdateSmallshangpin updateshangpinBean;
    /**
     * photo
     */
    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_add_small_shangpin);
        ButterKnife.bind(this);
        context = this;
        aCache = ACache.get(context);
        loadingDialog = new LoadingDialog(context);
        ToumingUtil.touming(this);
        titleqtop.getLayoutParams().height = GetHeightUtil.getztl(context);
        titleqtop.setBackgroundResource(R.color.zhuse);
        title.setText("添加分类");
        title.setTextColor(getResources().getColor(R.color.white));
        titleleftimg.setImageResource(R.mipmap.fanhuibai);
        titleleftimg.setVisibility(View.VISIBLE);
        title.setBackgroundResource(R.color.zhuse);
        if (getIntent().getStringExtra("type").equals("2")) {
            seachshangpin();
        }
    }

    @Override
    protected void initData() {
        mEtaddsmallName.addTextChangedListener(new TextWatcher() {
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

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
    }

    @OnClick(R.id.titleback)
    public void onViewClicked() {
        finish();
    }

    @OnClick({R.id.mIvaddsmall_img, R.id.mRladdsmall_queding})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mIvaddsmall_img:
                xuanze();
                break;
            case R.id.mRladdsmall_queding:
                if (imgurl.equals("")){
                    Toast.makeText(context, "请重新上传图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEtaddsmallName.getText().equals("")){
                    Toast.makeText(context, "请输入标题", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEtaddsmallJiage.getText().equals("")){
                    Toast.makeText(context, "请输入价格", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEtaddsmallKucun.getText().equals("")){
                    Toast.makeText(context, "请输入库存", Toast.LENGTH_SHORT).show();
                    return;
                }
                add();
                break;
        }
    }

    private void add() {
        loadingDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            if (getIntent().getStringExtra("type").equals("2")){
                jsonObject.put("id",getIntent().getStringExtra("id"));
            }
            jsonObject.put("uid", aCache.getAsString("uid"));
            jsonObject.put("pid", getIntent().getStringExtra("id"));
            jsonObject.put("picture", imgurl);
            jsonObject.put("name", mEtaddsmallName.getText().toString());
            jsonObject.put("price", mEtaddsmallJiage.getText().toString());
            jsonObject.put("stock", mEtaddsmallKucun.getText().toString());
            Log.e("添加小商品", "json" + jsonObject);
            HttpUtils.post(context, Common.AddSmallshop, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    loadingDialog.dismiss();
                    try {
                        JSONObject json = new JSONObject(text);
                        Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                        if (json.getString("status").equals("1")) {
                            Intent intent=new Intent(context, ShopWebActivity.class);
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

            Log.e("修改商品json",""+jsonObject);
            HttpUtils.post(context, Common.seachsmallshop, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    Log.e("修改商品text",text);
                    loadingDialog.dismiss();
                    updateshangpinBean = GsonUtils.JsonToBean(text, UpdateSmallshangpin.class);
                    Toast.makeText(context, updateshangpinBean.getMsg(), Toast.LENGTH_SHORT).show();
                    if (updateshangpinBean.getStatus().equals("1")) {
                        mEtaddsmallName.setText(updateshangpinBean.getData().getName());
                        mEtaddsmallJiage.setText(updateshangpinBean.getData().getPrice());
                        mEtaddsmallKucun.setText(updateshangpinBean.getData().getStock());
                        Glide.with(context).load(Common.ImgUrl+updateshangpinBean.getData().getPicture()).into(mIvaddsmallImg);
                        imgurl=updateshangpinBean.getData().getPicture()+"";
                        smallid=updateshangpinBean.getData().getId();
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




    /**
     * 相机
     */
    public void xuanze() {
        final XuanzeDialog imageDialog = new XuanzeDialog(this);
        imageDialog.setData();
        imageDialog.setOnConfirmListener(new XuanzeDialog.OnConfirmListener() {
            @Override
            public void onConfirm() {
                int text = imageDialog.getText();
                if (text == 0) {
                    return;
                }
                if (text == 1) {
                    takePhoto();
                }
                if (text == 2) {
                    choiseImg();
                }
                //     chuantouxiang();
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
                imageUri = FileProvider.getUriForFile(this, "tongxun.zhy.dz.com.tongxun.Fileprovider", outputImage);
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
                    Log.e("tupianimg", text + "");
                    try {
                        JSONObject json = new JSONObject(text);
                        if (json.getString("status").equals("1")) {
                            Glide.with(context).load(Common.ImgUrl + json.getString("data")).into(mIvaddsmallImg);
                            mLnaddSpImg.setVisibility(View.GONE);
                            imgurl = json.getString("data");
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
}
