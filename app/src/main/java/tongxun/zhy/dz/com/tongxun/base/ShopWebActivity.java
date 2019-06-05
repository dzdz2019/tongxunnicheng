package tongxun.zhy.dz.com.tongxun.base;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tongxun.zhy.dz.com.tongxun.MainActivity;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.liaotian.activity.ECChatActivity;
import tongxun.zhy.dz.com.tongxun.liaotian.activity.MyChatActivity;
import tongxun.zhy.dz.com.tongxun.liaotian.utils.APPConfig;
import tongxun.zhy.dz.com.tongxun.liaotian.utils.SharedPreferencesUtils;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.shipin.AddShipin;
import tongxun.zhy.dz.com.tongxun.shipin.activity.ShiPinActivity;
import tongxun.zhy.dz.com.tongxun.tools.ACache;
import tongxun.zhy.dz.com.tongxun.tools.GetHeightUtil;
import tongxun.zhy.dz.com.tongxun.tools.LoadingDialog;
import tongxun.zhy.dz.com.tongxun.tools.ToumingUtil;
import tongxun.zhy.dz.com.tongxun.wode.activity.AddShangpinActivity;
import tongxun.zhy.dz.com.tongxun.wode.activity.AddSmallShangpinActivity;
import tongxun.zhy.dz.com.tongxun.wode.activity.LoginActivity;

public class ShopWebActivity extends BaseActivity {

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
    @BindView(R.id.shopweb)
    WebView shopweb;
    @BindView(R.id.shopwebtop)
    TextView shopwebtop;
    private Context context;
    private ACache aCache;
    private LoadingDialog loadingDialog;
    Handler myHandler = new Handler();
    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 2;

    @Override
    protected void initView() {

        setContentView(R.layout.activity_shop_web);
        ButterKnife.bind(this);
        context = this;
        loadingDialog = new LoadingDialog(context);
        ToumingUtil.touming(this);
        shopwebtop.getLayoutParams().height = GetHeightUtil.getztl(this);
        shopwebtop.setBackgroundResource(R.color.zhuse);
        title.setText(getIntent().getStringExtra("title"));
        aCache = ACache.get(context);
        Log.e("网址打印", getIntent().getStringExtra("url"));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

        shopweb.loadUrl(getIntent().getStringExtra("url"));
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
    }

    @OnClick({R.id.titleback, R.id.shopweb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleback:
                if (shopweb.canGoBack()) {
                    shopweb.goBack();//返回上个页面
                } else {
                    finish();
                    shopweb.clearHistory();
                }
                break;
            case R.id.shopweb:
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onResume() {
        super.onResume();
        shopweb.getSettings().setJavaScriptEnabled(true);
        shopweb.getSettings().setUseWideViewPort(true);
        shopweb.getSettings().setLoadWithOverviewMode(true);
        shopweb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        shopweb.addJavascriptInterface(new StockJsInteration(), "android");
        shopweb.getSettings().setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        shopweb.getSettings().setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        shopweb.getSettings().setAllowFileAccessFromFileURLs(false);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        shopweb.getSettings().setAllowUniversalAccessFromFileURLs(false);



        //     webView.setWebChromeClient(this.mOpenFileWebChromeClient);
        //    WebSettings webSettings = xieyiweb.getSettings();//启用支持javascript
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setLightTouchEnabled(true);
//        webSettings.setSupportZoom(true);
        shopweb.setHapticFeedbackEnabled(false);
        Map extraHeaders = new HashMap();
        extraHeaders.put("Referer", "http://app.pangumeng.com");//例如 http://www.baidu.com ))
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }*/
        //   webSettings.setJavaScriptEnabled(true);//启用支持javascript
      /*  webSettings.setSupportZoom(true);  //支持缩放
        webSettings.setBuiltInZoomControls(true); //设置支持缩放*/

     /*   webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");*/
//        webSettings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
//        webSettings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        //   webSettings.setAllowFileAccessFromFileURLs(false);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
//        //开启JavaScript支持
        //  webSettings.setJavaScriptEnabled(true);
        // 支持缩放
        //    webSettings.setSupportZoom(true);


//WebView屏幕自适应
//        webSettings = xieyiweb.getSettings();
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
        //   loadingDialog.show();
        shopweb.getSettings().setDomStorageEnabled(true);
        //      this.webView.loadUrl("http://weibo.cn/");


        shopweb.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); 默认的处理方式，WebView变成空白页
                //                        //接受证书
                handler.proceed();
                //handleMessage(Message msg); 其他处理

            }
        });
        shopweb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);

                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }



        });

        shopweb.setWebChromeClient(new WebChromeClient() {//加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                // TODO Auto-generated method stub
                if (newProgress == 100) {
                  //  loadingDialog.dismiss();

                } else {
                   // loadingDialog.show();

                }

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {//网站标题
                super.onReceivedTitle(view, title);

            }

            // For 3.0+ Devices (Start)
            // onActivityResult attached before constructor
            protected void openFileChooser(ValueCallback uploadMsg, String acceptType)
            {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            }


            // For Lollipop 5.0+ Devices
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
            {
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }

                uploadMessage = filePathCallback;

                Intent intent = fileChooserParams.createIntent();
                try
                {
                    startActivityForResult(intent, REQUEST_SELECT_FILE);
                } catch (ActivityNotFoundException e)
                {
                    uploadMessage = null;
                    Toast.makeText(getBaseContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

            //For Android 4.1 only
            protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
            {
                mUploadMessage = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            protected void openFileChooser(ValueCallback<Uri> uploadMsg)
            {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }



        });

        shopweb.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("网址打印", url);
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                if (url.startsWith("alipays:") || url.startsWith("alipay")) {
                    try {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    } catch (Exception e) {
                        new AlertDialog.Builder(context)
                                .setMessage("未检测到支付宝客户端，请安装后重试。")
                                .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                        startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                                    }
                                }).setNegativeButton("取消", null).show();
                    }
                    return true;
                }




                if (url.startsWith("app:back")) {
                    if ( shopweb.canGoBack()) {
                        Log.e("111","QQQQQQQQ");
                        shopweb.goBack();//返回上个页面
                    } else {
                        Log.e("111","2222222");
                        finish();
                     //   shopweb.clearHistory();
                    }
                    return true;
                }
                if (url.startsWith("app:updatevideo?id=")) {//编辑视频
                    String ids = url.substring(url.indexOf("app:updatevideo?id=") + "app:updatevideo?id=".length());

                    Intent intent = new Intent(context, AddShipin.class);
                    intent.putExtra("type","2");//1上传  2修改
                    intent.putExtra("id",ids);
                    startActivity(intent);
                    return true;
                }
                if (url.startsWith("app:login")) {//登录
                    Intent intent;
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (url.startsWith("weixin://wap/pay?")) {//微信支付
                    Log.e("12222222222", url);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }

                if (url.startsWith("app:addmyshangpin")) {//上传商品
                    Intent intent;
                    intent = new Intent(context, AddShangpinActivity.class);
                    intent.putExtra("type", "1");
                    startActivityForResult(intent, 1);
                    return true;
                }
                if (url.startsWith("app:huanxinid=")) {//客服
                    String Id = url;
                    String ids = Id.substring(Id.indexOf("app:huanxinid=") + "app:huanxinid=".length());
                   // String urls = Id.substring(Id.indexOf("?url=") + "?url=".length(), Id.indexOf("&id="));
                  //  Log.e("shipingurl", urls);
                    Log.e("环信id", ids);
                    if (ids.equals("")) {
                        Toast.makeText(context, "聊天室获取失败，请重试", Toast.LENGTH_SHORT).show();

                    } else {
                        //设置要发送出去的昵称
                        SharedPreferencesUtils.setParam(context, APPConfig.USER_NAME, aCache.getAsString("uname"));
                        //设置要发送出去的头像
                        SharedPreferencesUtils.setParam(context,APPConfig.USER_HEAD_IMG, aCache.getAsString("uimg"));

                        Intent intent=new Intent(context, MyChatActivity.class);
                        //传入参数
                        Bundle args=new Bundle();
                        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                        args.putString(EaseConstant.EXTRA_USER_ID,ids);
                        intent.putExtra("conversation",args);
                        startActivity(intent);
                    }
                    return true;
                }
                if (url.startsWith("app:video?url=")) {//启动视频页
                    String Id = url;
                    String ids = Id.substring(Id.indexOf("id=") + "id=".length());
                    String urls = Id.substring(Id.indexOf("?url=") + "?url=".length(), Id.indexOf("&id="));
                    Log.e("shipingurl", urls);
                    Log.e("shipingid", ids);
                    if (ids.equals("")) {
                        Toast.makeText(context, "视频信息获取失败，请重试", Toast.LENGTH_SHORT).show();

                    } else {
                        Intent intent;
                        intent = new Intent(context, ShiPinActivity.class);
                        intent.putExtra("type", "2");
                        intent.putExtra("videourl", urls);
                        intent.putExtra("url", Common.VideoXiangQing + ids + "&uid=" + aCache.getAsString("uid"));
                        // Toast.makeText(context, ids, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                    return true;
                }
                if (url.startsWith("app:userimg=")) {//头像,昵称
                    String Id = url;
                    String img = Id.substring(Id.indexOf("userimg=") + "userimg=".length(),Id.indexOf("&username="));
                 //   String name = Id.substring(Id.indexOf("&username=") + "&username=".length());
                    aCache.put("uimg", Common.ImgUrl + img);
                 //   aCache.put("uname",name);
                    shopweb.goBack();//返回上个页面
                    return true;
                }

                if (url.startsWith("app:updatemyshangpin?id=")) {
                    String Id = url;
                    String ids = Id.substring(24);
                    Intent intent;
                    intent = new Intent(context, AddShangpinActivity.class);
                    intent.putExtra("type", "2");
                    intent.putExtra("id", ids);
                    // Toast.makeText(context, ids, Toast.LENGTH_SHORT).show();
                    startActivityForResult(intent, 1);
                    return true;
                }

                if (url.startsWith("app:addsmallshangpin?id=")) {
                    String Id = url;
                    String ids = Id.substring(24);
                    Intent intent;
                    intent = new Intent(context, AddSmallShangpinActivity.class);
                    intent.putExtra("id", ids);
                    intent.putExtra("type", "1");
                    startActivityForResult(intent, 1);
                    return true;
                }
                if (url.startsWith("app:updatesmallshangpin?id=")) {
                    String Id = url;
                    String ids = Id.substring(27);
                    Intent intent;
                    intent = new Intent(context, AddSmallShangpinActivity.class);
                    intent.putExtra("id", ids);
                    intent.putExtra("type", "2");
                    startActivityForResult(intent, 1);
                    return true;
                }
                if (url.startsWith("app:finish")) {
                    finish();
                    return true;
                }

                if (url.startsWith("app:video?url=")) {//启动视频页
                    String Id = url;
                    String ids = Id.substring(Id.indexOf("id=")+"id=".length());
                    String urls = Id.substring(Id.indexOf("?url=") + "?url=".length(), Id.indexOf("&id="));
                    Log.e("shipingurl", urls);
                    Log.e("shipingid", ids);
                    if (ids.equals("")) {
                        Toast.makeText(context, "视频信息获取失败，请重试", Toast.LENGTH_SHORT).show();

                    } else {
                        Intent intent;
                        intent = new Intent(context, ShiPinActivity.class);
                        intent.putExtra("type", "2");
                        intent.putExtra("videourl", urls);
                        intent.putExtra("url", Common.VideoXiangQing + ids + "&uid=" +aCache.getAsString("uid"));
                        // Toast.makeText(context, ids, Toast.LENGTH_SHORT).show();
                        startActivityForResult(intent, 1);
                    }
                    return true;
                }
//                if (url.startsWith("app:ziliaouser?id=")) {//资料页
//                    if ("".equals(aCache.getAsString("uid")) || aCache.getAsString("uid") == null) {
//                        Intent intent = new Intent(context, LoginActivity.class);
//                        startActivity(intent);
//                    } else {
//                        String ids = url.substring(18);
//                        Intent  intent = new Intent(context, ShopWebActivity.class);
//                        intent.putExtra("url", Common.USerXinxi + "?uid=" + aCache.getAsString("uid")+"&other_uid="+ids+"&type=1");
//                        startActivity(intent);
//                    }
//                    return true;
//                }
                return false;
            }
        });

        shopweb.resumeTimers();
        shopweb.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            shopweb.reload();
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (requestCode == REQUEST_SELECT_FILE)
            {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                uploadMessage = null;
            }
        }
        else if (requestCode == FILECHOOSER_RESULTCODE)
        {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = data == null || resultCode != MainActivity.RESULT_OK ? null : data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
        else
            Toast.makeText(getBaseContext(), "Failed to Upload Image", Toast.LENGTH_LONG).show();
    }

    public class StockJsInteration {
        @JavascriptInterface
        public String webShare(final String dataShare) {
            return "";
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && shopweb.canGoBack()) {
            Log.e("111","QQQQQQQQ");
            shopweb.goBack();//返回上个页面
          return true;
        } else {
            finish();

        }
        return super.onKeyDown(keyCode, event);
    }



}
