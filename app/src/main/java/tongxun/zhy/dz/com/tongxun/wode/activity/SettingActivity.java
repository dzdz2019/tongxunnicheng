package tongxun.zhy.dz.com.tongxun.wode.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tongxun.zhy.dz.com.tongxun.MainActivity;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.base.BaseActivity;
import tongxun.zhy.dz.com.tongxun.base.ShopWebActivity;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.tools.ACache;
import tongxun.zhy.dz.com.tongxun.tools.DialogCallBack;
import tongxun.zhy.dz.com.tongxun.tools.DialogImpl;
import tongxun.zhy.dz.com.tongxun.tools.GetHeightUtil;
import tongxun.zhy.dz.com.tongxun.tools.IDialog;
import tongxun.zhy.dz.com.tongxun.tools.ToumingUtil;
import tongxun.zhy.dz.com.tongxun.utils.DataCleanManager;

public class SettingActivity extends BaseActivity {

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
    @BindView(R.id.mLnset_dizhi)
    LinearLayout mLnsetDizhi;
    @BindView(R.id.mLnset_huancun)
    LinearLayout mLnsetHuancun;
    @BindView(R.id.mStset_tuisong)
    Switch mStsetTuisong;
    @BindView(R.id.mLnset_liaotian)
    LinearLayout mLnsetLiaotian;
    @BindView(R.id.mLnset_tuichu)
    TextView mLnsetTuichu;
    @BindView(R.id.mTvset_huancun)
    TextView mTvsetHuancun;
    private Context context;
    private ACache aCache;
    private Intent intent;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        context = this;
        aCache = ACache.get(context);
    }

    @Override
    protected void initData() {
        title.setText("设置");
        title.setTextColor(getResources().getColor(R.color.c333333));
        titleleftimg.setVisibility(View.VISIBLE);
        titleleftimg.setRotation(180);
        titleleftimg.setImageResource(R.mipmap.xiangyou);
        title.setBackgroundResource(R.color.white);
        titleqtop.setBackgroundResource(R.color.zhuse);
        ToumingUtil.touming(this);
        titleqtop.getLayoutParams().height = GetHeightUtil.getztl(context);
        mTvsetHuancun.setText(getCacheSize());

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

    @OnClick({R.id.titleback, R.id.mLnset_dizhi, R.id.mLnset_huancun, R.id.mLnset_liaotian, R.id.mLnset_tuichu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleback:
                finish();
                break;
            case R.id.mLnset_dizhi:
                intent = new Intent(context, ShopWebActivity.class);
                intent.putExtra("url", Common.Address + "?uid=" + aCache.getAsString("uid"));
                startActivity(intent);
                break;
            case R.id.mLnset_huancun:
                show();
                break;
            case R.id.mLnset_liaotian:
                break;
            case R.id.mLnset_tuichu:

                signOut();

                aCache.clear();
                finish();
                break;
        }
    }
    /**
     * 退出登录
     */
    private void signOut() {
        // 调用sdk的退出登录方法，第一个参数表示是否解绑推送的token，没有使用推送或者被踢都要传false
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override public void onSuccess() {
                Log.i("lzan13", "logout success");
                // 调用退出成功，结束app
                aCache.clear();
                finish();
            }

            @Override public void onError(int i, String s) {
                Log.i("lzan13", "logout error " + i + " - " + s);
            }

            @Override public void onProgress(int i, String s) {

            }
        });
    }
    private void show() {
        IDialog iDialog = new DialogImpl();
        iDialog.showDialog(context, "清除缓存", "清除缓存会导致下载的内容删除，是否确定？", "确认", "取消", new DialogCallBack() {
            @Override
            public void onClick(int what) {
                if (what == IDialog.YES) {
                 cleanCache();
                    Toast.makeText(context, "缓存已清除", Toast.LENGTH_SHORT).show();
                    mTvsetHuancun.setText("0.0B");
//                    Intent intent = getBaseContext().getPackageManager()
//                            .getLaunchIntentForPackage(getBaseContext().getPackageName());
//                    PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
//                    AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
//
//
//// 退出程序
//                    android.os.Process.killProcess(android.os.Process.myPid());
//                    System.exit(0);




                }
            }
        }).show();

    }

    public static void reStartApp() {



    }

    //获取缓存大小
    private String getCacheSize() {
        String str = "";
        try {
            str = DataCleanManager.getTotalCacheSize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    //清空缓存
    private void cleanCache() {
        DataCleanManager.cleanDatabases(context);
        DataCleanManager.cleanFiles(context);
        DataCleanManager.cleanSharedPreference(context);
    }
}
