package tongxun.zhy.dz.com.tongxun.shipin.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.base.ShopWebActivity;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.shipin.AddShipin;
import tongxun.zhy.dz.com.tongxun.tools.ACache;
import tongxun.zhy.dz.com.tongxun.tools.LoadingDialog;
import tongxun.zhy.dz.com.tongxun.wode.activity.LoginActivity;

public class ShiPinActivity extends AppCompatActivity {


    @BindView(R.id.jc_video)
    JCVideoPlayerStandard jcVideo;
    @BindView(R.id.shipin_jianjieweb)
    WebView shipinWeb;
    private Context context;
    private ACache aCache;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//这句代码必须写在setContentView()前面
        setContentView(R.layout.activity_shi_pin);
        ButterKnife.bind(this);
        context=this;
        aCache=ACache.get(context);
        loadingDialog=new LoadingDialog(context);
        jcVideo.setUp(getIntent().getStringExtra("videourl")
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");

        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
        JCVideoPlayer.releaseAllVideos();
        jcVideo.startButton.performClick();

        shipinWeb.loadUrl(getIntent().getStringExtra("url"));
    }



    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onResume() {
        super.onResume();

        shipinWeb.getSettings().setJavaScriptEnabled(true);
        shipinWeb.getSettings().setUseWideViewPort(true);
        shipinWeb.getSettings().setLoadWithOverviewMode(true);
        shipinWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        shipinWeb.addJavascriptInterface(new StockJsInteration(), "android");
        //     webView.setWebChromeClient(this.mOpenFileWebChromeClient);
        //    WebSettings webSettings = xieyiweb.getSettings();//启用支持javascript
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setLightTouchEnabled(true);
//        webSettings.setSupportZoom(true);
        shipinWeb.setHapticFeedbackEnabled(false);
        final Map extraHeaders = new HashMap();
        extraHeaders.put("Referer", "http://app.pangumeng.com");//例如 http://www.baidu.com ))

        shipinWeb.getSettings().setDomStorageEnabled(true);
        shipinWeb.getSettings().setDomStorageEnabled(true);
        //      this.webView.loadUrl("http://weibo.cn/");


        shipinWeb.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); 默认的处理方式，WebView变成空白页
                //                        //接受证书
                handler.proceed();
                //handleMessage(Message msg); 其他处理

            }
        });
        shipinWeb.setWebViewClient(new WebViewClient() {
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

        shipinWeb.setWebChromeClient(new WebChromeClient() {//加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    loadingDialog.dismiss();

                } else {
                    loadingDialog.show();

                }

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {//网站标题
                super.onReceivedTitle(view, title);

            }
        });

        shipinWeb.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("网址打印", url);
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }

                if (url.startsWith("app:login")) {//登陆
                    Intent intent;
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (url.startsWith("app:photo")) {//拍视频
                    if ("".equals(aCache.getAsString("uid")) || aCache.getAsString("uid") == null) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, AddShipin.class);
                        startActivity(intent);
                    }
                    return true;
                }
                if (url.startsWith("app:ziliaouser?id=")) {//资料页
                    if ("".equals(aCache.getAsString("uid")) || aCache.getAsString("uid") == null) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        String ids = url.substring(18);
                      Intent  intent = new Intent(context, ShopWebActivity.class);
                        intent.putExtra("url", Common.USerXinxi + "?uid=" + aCache.getAsString("uid")+"&other_uid="+ids+"&type=1");
                        startActivity(intent);
                    }
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
                      //  finish();
                        Intent intent = getIntent();
                        finish();
                        intent.putExtra("type", "2");
                        intent.putExtra("videourl", urls);
                        intent.putExtra("url", Common.VideoXiangQing + ids + "&uid=" +aCache.getAsString("uid"));
                        startActivityForResult(intent,3);
//                        Intent intent;
//                        intent = new Intent(context, ShiPinActivity.class);
//                        intent.putExtra("type", "2");
//                        intent.putExtra("videourl", urls);
//                        intent.putExtra("url", Common.VideoXiangQing + ids + "&uid=" +aCache.getAsString("uid"));
//                        // Toast.makeText(context, ids, Toast.LENGTH_SHORT).show();
//                        startActivity(intent);
                    }
                    return true;

                }
                return false;
            }
        });

        shipinWeb.resumeTimers();
        shipinWeb.onResume();
    }
    public class StockJsInteration {
        @JavascriptInterface
        public String webShare(final String dataShare) {
            return "";
        }

    }


    class MyUserActionStandard implements JCUserActionStandard {

        @Override
        public void onEvent(int type, String url, int screen, Object... objects) {
            switch (type) {
                case JCUserAction.ON_CLICK_START_ICON:
                    Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_ERROR:
                    Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_PAUSE:
                    Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_RESUME:
                    Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;

                case JCUserActionStandard.ON_CLICK_START_THUMB:
                    Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserActionStandard.ON_CLICK_BLANK:
                    Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                default:
                    Log.i("USER_EVENT", "unknow");
                    break;
            }
        }
    }

}
