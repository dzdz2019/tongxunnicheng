package tongxun.zhy.dz.com.tongxun.shipin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.base.ShopWebActivity;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.shipin.activity.ShiPinActivity;
import tongxun.zhy.dz.com.tongxun.tools.ACache;
import tongxun.zhy.dz.com.tongxun.tools.GetHeightUtil;
import tongxun.zhy.dz.com.tongxun.tools.LoadingDialog;
import tongxun.zhy.dz.com.tongxun.wode.activity.AddShangpinActivity;
import tongxun.zhy.dz.com.tongxun.wode.activity.AddSmallShangpinActivity;
import tongxun.zhy.dz.com.tongxun.wode.activity.LoginActivity;

public class ShiPinFragment extends Fragment {
    @BindView(R.id.shipin_web)
    WebView shipinWeb;
    Unbinder unbinder;
    @BindView(R.id.shipin_top)
    TextView shipinTop;
    private View view;
    private Context context;
    private ACache aCache;
    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.shi_pin_fragment, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        context = getActivity();

        aCache = ACache.get(context);
        loadingDialog = new LoadingDialog(context);
        initview();
        return view;
    }

    private void initview() {
        shipinWeb.loadUrl(Common.VideoList + "?uid=" + aCache.getAsString("uid"));
    }

    public class StockJsInteration {
        @JavascriptInterface
        public String webShare(final String dataShare) {
            return "";
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onResume() {
        super.onResume();
        shipinTop.getLayoutParams().height = GetHeightUtil.getztl(context);
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
        Map extraHeaders = new HashMap();
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
                if (url.startsWith(Common.URL+"/Api.php/Video/video_order?video_id")) {//收费
                    Intent intent;
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                    return true;
                }


                if (url.startsWith("app:photo")) {//拍视频
                    if ("".equals(aCache.getAsString("uid")) || aCache.getAsString("uid") == null) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, AddShipin.class);
                        intent.putExtra("type","1");//1上传  2修改
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
                return false;
            }
        });

        shipinWeb.resumeTimers();
        shipinWeb.onResume();
    }


}
