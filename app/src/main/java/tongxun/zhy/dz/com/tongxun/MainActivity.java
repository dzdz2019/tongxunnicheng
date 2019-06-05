package tongxun.zhy.dz.com.tongxun;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.NetUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;
import tongxun.zhy.dz.com.tongxun.liaotian.LiaoTianFragment;
import tongxun.zhy.dz.com.tongxun.liaotian.activity.ECChatActivity;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.shangcheng.ShangChengFragment;
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.ShangpinBean;
import tongxun.zhy.dz.com.tongxun.shipin.AddShipin;
import tongxun.zhy.dz.com.tongxun.shipin.ShiPinFragment;
import tongxun.zhy.dz.com.tongxun.shipin.activity.ShiPinActivity;
import tongxun.zhy.dz.com.tongxun.shouye.ShouYeFragment;
import tongxun.zhy.dz.com.tongxun.shouye.bean.VideoTimeBean;
import tongxun.zhy.dz.com.tongxun.tools.ACache;
import tongxun.zhy.dz.com.tongxun.tools.GetHeightUtil;
import tongxun.zhy.dz.com.tongxun.tools.GsonUtils;
import tongxun.zhy.dz.com.tongxun.tools.HttpUtils;
import tongxun.zhy.dz.com.tongxun.tools.TextCallBack;
import tongxun.zhy.dz.com.tongxun.tools.ToumingUtil;
import tongxun.zhy.dz.com.tongxun.wode.WoDeFragment;
import tongxun.zhy.dz.com.tongxun.wode.activity.LoginActivity;

public class MainActivity extends FragmentActivity {
    @BindView(R.id.mFlmain_fragment)
    FrameLayout mFlmainFragment;
    @BindView(R.id.mIvmain_shouye)
    ImageView mIvmainShouye;
    @BindView(R.id.mTvmain_shouye)
    TextView mTvmainShouye;
    @BindView(R.id.mLnmain_shouye)
    LinearLayout mLnmainShouye;
    @BindView(R.id.mIvmain_shipin)
    ImageView mIvmainShipin;
    @BindView(R.id.mTvmain_shipin)
    TextView mTvmainShipin;
    @BindView(R.id.mLnmain_shipin)
    LinearLayout mLnmainShipin;
    @BindView(R.id.mIvmain_shangcheng)
    ImageView mIvmainShangcheng;
    @BindView(R.id.mTvmain_shangcheng)
    TextView mTvmainShangcheng;
    @BindView(R.id.mLnmain_shangcheng)
    LinearLayout mLnmainShangcheng;
    @BindView(R.id.mIvmain_liaotian)
    ImageView mIvmainLiaotian;
    @BindView(R.id.mTvmain_liaotian)
    TextView mTvmainLiaotian;
    @BindView(R.id.mLnmain_liaotian)
    LinearLayout mLnmainLiaotian;
    @BindView(R.id.mIvmain_wode)
    ImageView mIvmainWode;
    @BindView(R.id.mTvmain_wode)
    TextView mTvmainWode;
    @BindView(R.id.mLnmain_wode)
    LinearLayout mLnmainWode;
    @BindView(R.id.mTvmain_top)
    TextView mTvmainTop;
    @BindView(R.id.mLnmain_top)
    LinearLayout mLnmainTop;
    @BindView(R.id.mIvmain_jia)
    ImageView mIvmainJia;
    @BindView(R.id.mIvmain_sanjiao)
    ImageView mIvmainSanjiao;
    @BindView(R.id.mTvmain_tanshipin)
    TextView mTvmainTanshipin;
    @BindView(R.id.mTvmain_tanchanpin)
    TextView mTvmainTanchanpin;
    @BindView(R.id.mTvmain_tansan)
    TextView mTvmainTansan;
    @BindView(R.id.mTvmain_tansi)
    TextView mTvmainTansi;
    @BindView(R.id.mLnmain_tan)
    LinearLayout mLnmainTan;

    @BindView(R.id.fragment_container)
    RelativeLayout fragmentContainer;
    private Context context;
    private ShouYeFragment shouYeFragment;
    private ShiPinFragment shiPinFragment;
    private ShangChengFragment shangChengFragment;
    private LiaoTianFragment liaoTianFragment;
   // ConversationListFragment conversationListFragment;
    private WoDeFragment woDeFragment;
    private FragmentManager manager;
    private ACache aCache;
    Intent intent;
    public TextView mainXiaoqinum;
    //记录用户首次点击返回键的时间
    private long firstTime = 0;
    private static final String SHARE_APP_TAG = "app_run_status";
    private VideoTimeBean videoTimeBean;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    aCache.put("videotime", videoTimeBean.getData().getDuration());
                    break;
                case 2:
                    getVideotime();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        ToumingUtil.touming(this);
        context = this;
        aCache = ACache.get(context);
        aCache.put("videotime", "0");

        mainXiaoqinum = findViewById(R.id.main_xiaoqinum);
        if (Build.VERSION.SDK_INT >= 23 && isFirstStart(this)) {
            //
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission. RECORD_AUDIO,
                    Manifest.permission. MODIFY_AUDIO_SETTINGS
            };
            if (EasyPermissions.hasPermissions(this, perms)) {//检查是否获取该权限
                Log.e("首页", "上面");

            } else {
                Log.e("首页", "下面");
//                    ActivityCompat.requestPermissions(getActivity(), perms, 10);

                this.requestPermissions(perms, 0);
//                    EasyPermissions.requestPermissions(getActivity(), "本程序运行所需要的权限", 0, perms);
                //第二个参数是被拒绝后再次申请该权限的解释
                //第三个参数是请求码
                //第四个参数是要申请的权限
            }
        }

        setFragment(1);
        mTvmainTop.getLayoutParams().height = GetHeightUtil.getztl(this);
        getVideotime();
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());

        // EMClient.getInstance().chatManager().addMessageListener(msgListener);


    }


    /**
     * get unread message count
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMessageCount();
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        aCache.clear();
                        intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(context, "账号在其他设备登录", Toast.LENGTH_SHORT).show();
                        signOut();
                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)) {
                            //连接不到聊天服务器
                        } else { //当前网络不可用，请检查网络设置
                        }

                    }
                }
            });
        }
    }

    /**
     * 退出登录
     */
    private void signOut() {
        // 调用sdk的退出登录方法，第一个参数表示是否解绑推送的token，没有使用推送或者被踢都要传false
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.i("lzan13", "logout success");
                // 调用退出成功，结束app
                aCache.clear();
                finish();
            }

            @Override
            public void onError(int i, String s) {
                Log.i("lzan13", "logout error " + i + " - " + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    private void setFragment(int i) {
        manager = getSupportFragmentManager();
        FragmentTransaction fragmenttra = manager.beginTransaction();//初始化事务（）
        mIvmainShouye.setImageResource(R.mipmap.mainzhuyehui);
        mIvmainShipin.setImageResource(R.mipmap.mainshipin);
        mIvmainShangcheng.setImageResource(R.mipmap.mainshangcheng);
        mIvmainLiaotian.setImageResource(R.mipmap.mainliaotian);
        mIvmainWode.setImageResource(R.mipmap.mainwode);
        mTvmainShouye.setTextColor(getResources().getColor(R.color.c666666));
        mTvmainShipin.setTextColor(getResources().getColor(R.color.c666666));
        mTvmainShangcheng.setTextColor(getResources().getColor(R.color.c666666));
        mTvmainLiaotian.setTextColor(getResources().getColor(R.color.c666666));
        mTvmainWode.setTextColor(getResources().getColor(R.color.c666666));
        dianji(fragmenttra);
        switch (i) {
            case 1:
                //   Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                if (shouYeFragment == null) {
                    shouYeFragment = new ShouYeFragment();
                    fragmenttra.add(R.id.mFlmain_fragment, shouYeFragment);
                } else {
                    fragmenttra.show(shouYeFragment);
                }
                mLnmainTop.setVisibility(View.INVISIBLE);
                mIvmainShouye.setImageResource(R.mipmap.mainshouye);
                mTvmainShouye.setTextColor(getResources().getColor(R.color.zhuse));
                break;
            case 2:
                if (shiPinFragment == null) {
                    shiPinFragment = new ShiPinFragment();
                    fragmenttra.add(R.id.mFlmain_fragment, shiPinFragment);
                } else {
                    fragmenttra.show(shiPinFragment);
                }
                mTvmainShipin.setTextColor(getResources().getColor(R.color.zhuse));
                mIvmainShipin.setImageResource(R.mipmap.mainshipinzhu);
                break;
            case 3:
                if (shangChengFragment == null) {
                    shangChengFragment = new ShangChengFragment();
                    fragmenttra.add(R.id.mFlmain_fragment, shangChengFragment);
                } else {
                    fragmenttra.show(shangChengFragment);
                }
                mLnmainTop.setVisibility(View.GONE);
                mTvmainShangcheng.setTextColor(getResources().getColor(R.color.zhuse));
                mIvmainShangcheng.setImageResource(R.mipmap.mainshangchengzhu);
                break;
            case 4:
                EMClient.getInstance().chatManager().loadAllConversations();
                EMClient.getInstance().groupManager().loadAllGroups();
                if (liaoTianFragment == null) {
                    liaoTianFragment = new LiaoTianFragment();
                    fragmenttra.add(R.id.mFlmain_fragment, liaoTianFragment);
                } else {
                    fragmenttra.show(liaoTianFragment);
                }
                mLnmainTop.setVisibility(View.GONE);
                mIvmainLiaotian.setImageResource(R.mipmap.mainliaotianzhu);
                mTvmainLiaotian.setTextColor(getResources().getColor(R.color.zhuse));
                break;
//            case 4:
//                EMClient.getInstance().chatManager().loadAllConversations();
//                EMClient.getInstance().groupManager().loadAllGroups();
//              if (conversationListFragment == null) {
//                conversationListFragment = new ConversationListFragment();
//                fragmenttra.add(R.id.mFlmain_fragment, conversationListFragment);
//                  } else {
//                   fragmenttra.show(conversationListFragment);
//                  }
//                mLnmainTop.setVisibility(View.GONE);
//                mIvmainLiaotian.setImageResource(R.mipmap.mainliaotianzhu);
//                mTvmainLiaotian.setTextColor(getResources().getColor(R.color.zhuse));
//                break;
            case 5:
                if (woDeFragment == null) {
                    woDeFragment = new WoDeFragment();
                    fragmenttra.add(R.id.mFlmain_fragment, woDeFragment);
                } else {
                    fragmenttra.show(woDeFragment);
                }
                mLnmainTop.setVisibility(View.GONE);
                mIvmainWode.setImageResource(R.mipmap.minewodezhu);
                mTvmainWode.setTextColor(getResources().getColor(R.color.zhuse));
                break;
        }
        fragmenttra.commit();
    }

    private void dianji(FragmentTransaction i) {
        if (shouYeFragment != null) {
            i.hide(shouYeFragment);
        }
        if (shiPinFragment != null) {
            i.hide(shiPinFragment);
        }
        if (shangChengFragment != null) {
            i.hide(shangChengFragment);
        }
        if (liaoTianFragment != null) {
            i.hide(liaoTianFragment);
        }
//        if (conversationListFragment != null) {
//            i.hide(conversationListFragment);
//        }
        if (woDeFragment != null) {
            i.hide(woDeFragment);
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            long secondTime = System.currentTimeMillis();
//            if (secondTime - firstTime > 2000) {
//                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                firstTime = secondTime;
//                return true;
//            } else {
//                finish();
//                super.onDestroy();
//                System.exit(0);
//                // 或者下面这种方式
//                //           android.os.Process.killProcess(android.os.Process.myPid());
//            }
            exitBy2Click();      //调用双击退出函数
        }
        return false;
    }

    /**
     * 调用双击退出函数
     */

    private boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true; // 准备退出
            Toast.makeText(getApplicationContext(), "再次点击退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

    /**
     * @param @param  context
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     * @Title: isFirstStart
     * @Description:
     */
    public static boolean isFirstStart(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_APP_TAG, 0);
        Boolean isFirst = preferences.getBoolean("FIRSTStart", true);
        if (isFirst) {// 第一次
            preferences.edit().putBoolean("FIRSTStart", false).commit();
            return true;
        } else {
            return false;
        }
    }

    @OnClick({R.id.mLnmain_shouye, R.id.mLnmain_shipin, R.id.mLnmain_shangcheng, R.id.mLnmain_liaotian, R.id.mLnmain_wode, R.id.mIvmain_jia
            , R.id.mTvmain_tanshipin, R.id.mTvmain_tanchanpin, R.id.mTvmain_tansan, R.id.mTvmain_tansi, R.id.mLnmain_tan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mLnmain_shouye:
                setFragment(1);
                break;
            case R.id.mLnmain_shipin:
                setFragment(2);
                break;
            case R.id.mLnmain_shangcheng:
                setFragment(3);
                break;
            case R.id.mLnmain_liaotian:
                if ("".equals(aCache.getAsString("uid")) || aCache.getAsString("uid") == null) {
                    Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();

                } else {
                    setFragment(4);
                    //    mainXiaoqinum.setVisibility(View.GONE);
                }

                break;
            case R.id.mLnmain_wode:
                setFragment(5);
                break;
            case R.id.mIvmain_jia:
                if (mLnmainTan.getVisibility() == View.GONE) {
                    mIvmainSanjiao.setVisibility(View.VISIBLE);
                    mLnmainTan.setVisibility(View.VISIBLE);
                } else {
                    mIvmainSanjiao.setVisibility(View.GONE);
                    mLnmainTan.setVisibility(View.GONE);
                }
                break;
            case R.id.mTvmain_tanshipin:
                if ("".equals(aCache.getAsString("uid")) || aCache.getAsString("uid") == null) {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(context, AddShipin.class);
//                    intent.putExtra("type","1");//1上传  2修改
                    startActivity(intent);
                }
                mLnmainTan.setVisibility(View.GONE);
                mIvmainSanjiao.setVisibility(View.GONE);
                break;
            case R.id.mTvmain_tanchanpin:
                break;
            case R.id.mTvmain_tansan:
                break;
            case R.id.mTvmain_tansi:
                break;
            case R.id.mLnmain_tan:
                break;
        }
    }

    public void selectFragmentmQuotes(int i) {
        setFragment(i);
    }

    private void getVideotime() {
        JSONObject jsonObject = new JSONObject();
        HttpUtils.post(context, Common.VideoTime, jsonObject, new TextCallBack() {
            @Override
            protected void onSuccess(String text) {
                videoTimeBean = GsonUtils.JsonToBean(text, VideoTimeBean.class);
                Message msg = new Message();
                if (videoTimeBean.getStatus() == 1) {
                    msg.what = 1;
                } else {
                    msg.what = 2;
                }
                handler.sendMessage(msg);
            }

            @Override
            protected void onFailure(ResponseException e) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);
        updateUnreadLabel();

    }

    EMMessageListener emMessageListener =new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            Log.e("111", "onMessageReceived: 接收到消息main");
            // refresh();
            updateUnreadLabel();
            refreshUIWithMessage();
            //   mainXiaoqinum.setVisibility(View.VISIBLE);
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {

        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> messages) {

        }

//        @Override
//        public void onMessageRecalled(List<EMMessage> messages) {
//
//        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {

        }
    } ;

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                // updateUnreadLabel();
                //  refresh();
                // refresh conversation list
//                    if (conversationListFragment != null) {
//                        conversationListFragment.refresh();
//
//                }

            }
        });
    }
    /**
     * update unread message count
     */
    public void updateUnreadLabel() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int count = getUnreadMsgCountTotal();
                if (count > 0) {
                    if (count>99){
                        Log.e("xaoxi++", "++" + count);
                        mainXiaoqinum.setVisibility(View.VISIBLE);
                        mainXiaoqinum.setTextSize(3);
                        mainXiaoqinum.setText("99+");
                    }else {
                    Log.e("xaoxi++", "++" + count);
                    mainXiaoqinum.setVisibility(View.VISIBLE);
                    mainXiaoqinum.setText(String.valueOf(count));
                    }
                } else {
                    mainXiaoqinum.setVisibility(View.GONE);
                }
            }
        });
    }
}
