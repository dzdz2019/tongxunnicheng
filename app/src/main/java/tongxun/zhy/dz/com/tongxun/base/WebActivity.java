package tongxun.zhy.dz.com.tongxun.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.tools.GetHeightUtil;
import tongxun.zhy.dz.com.tongxun.tools.LoadingDialog;
import tongxun.zhy.dz.com.tongxun.tools.ToumingUtil;
import tongxun.zhy.dz.com.tongxun.wode.activity.LoginActivity;

public class WebActivity extends BaseActivity {
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
    @BindView(R.id.mWvweb_web)
    WebView mWvwebWeb;

    private Context context;
    private LoadingDialog loadingDialog;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        context = this;
        loadingDialog = new LoadingDialog(context);
        title.setText(getIntent().getStringExtra("title"));
        ToumingUtil.touming(this);
        titleqtop.getLayoutParams().height= GetHeightUtil.getztl(context);
        titleback.setVisibility(View.VISIBLE);
        titleleftimg.setVisibility(View.VISIBLE);
        titleleftimg.setImageResource(R.mipmap.xiangyou);
        titleleftimg.setRotation(180);
        mWvwebWeb.loadUrl(getIntent().getStringExtra("url"));
    }

    @Override
    protected void initData() {

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
        if (mWvwebWeb.canGoBack()) {
            mWvwebWeb.goBack();//返回上个页面
        } else {
            finish();
            mWvwebWeb.clearHistory();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onResume() {
        super.onResume();
        mWvwebWeb.getSettings().setJavaScriptEnabled(true);
        mWvwebWeb.getSettings().setUseWideViewPort(true);
        mWvwebWeb.getSettings().setLoadWithOverviewMode(true);
        mWvwebWeb.addJavascriptInterface(new StockJsInteration(), "android");
        //     webView.setWebChromeClient(this.mOpenFileWebChromeClient);
        //    WebSettings webSettings = xieyiweb.getSettings();//启用支持javascript
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setLightTouchEnabled(true);
//        webSettings.setSupportZoom(true);
        mWvwebWeb.setHapticFeedbackEnabled(false);
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
        mWvwebWeb.getSettings().setDomStorageEnabled(true);
        //      this.webView.loadUrl("http://weibo.cn/");


        mWvwebWeb.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); 默认的处理方式，WebView变成空白页
                //                        //接受证书
                handler.proceed();
                //handleMessage(Message msg); 其他处理

            }
        });
        mWvwebWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

        });
        mWvwebWeb.setWebChromeClient(new WebChromeClient() {//加载进度
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

        mWvwebWeb.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("网址打印", url);
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                if (url.startsWith("app:back")) {

                    if (mWvwebWeb.canGoBack()) {
                        mWvwebWeb.goBack();//返回上个页面
                        return true;
                    } else {
                        finish();
                        mWvwebWeb.clearHistory();
                    }
                    return true;
                }
                if (url.startsWith("app:login")) {
                    Intent intent;
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (url.startsWith("weixin://wap/pay?")) {
                    Log.e("12222222222", url);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        mWvwebWeb.resumeTimers();
        mWvwebWeb.onResume();
    }

    public class StockJsInteration {
        @JavascriptInterface
        public String webShare(final String dataShare) {
            return "";
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWvwebWeb.canGoBack()) {
            mWvwebWeb.goBack();//返回上个页面
            return true;
        }else {
            finish();
            mWvwebWeb.clearHistory();

        }
        return  super.onKeyDown(keyCode, event) ;
    }
}
