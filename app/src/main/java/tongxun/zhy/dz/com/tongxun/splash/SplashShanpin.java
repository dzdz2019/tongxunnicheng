package tongxun.zhy.dz.com.tongxun.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.dhc.gallery.utils.ImageLoader;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tongxun.zhy.dz.com.tongxun.MainActivity;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.tools.GsonUtils;
import tongxun.zhy.dz.com.tongxun.tools.HttpUtils;
import tongxun.zhy.dz.com.tongxun.tools.TextCallBack;


public class SplashShanpin extends FragmentActivity implements View.OnClickListener {
    private Context context;
    private TextView time, timetex;
    private LinearLayout timelin, di, one, two, three, four;
    private TimeCount times ,shijian;
    private ArrayList<Fragment> fragmentsList;
    private static final String SHARE_APP_TAG = "app_run_status";
    private ImageView img, xian;
    int i = 1;
    private int currIndex = 0;
    private int s;
    private RollPagerView banner;
    private SplshBean dataBean;
    private List<SplshBean.DataBean> bannerlist;
    private int flag = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                   for (int i = 0; i < dataBean.getData().size(); i++) {
                        bannerlist.add(dataBean.getData().get(i));
                   }
                    flag = 1;
                    String is;
                    is = dataBean.getData().get(0).getImg();
                    if (s==0){
                        Glide.with(context).load(Common.ImgUrl+is).into(img);
                    }
                    banner.setAdapter(new MyPagerAdapter(bannerlist));
                    break;
                case 4:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splshyindao);
        context = this;
        touming();
        img = (ImageView) findViewById(R.id.yindao_img);
        time = (TextView) findViewById(R.id.yinddao_time);
        timetex = (TextView) findViewById(R.id.yindao_tex);
        timelin = (LinearLayout) findViewById(R.id.yindao_lin);
        //viewPager = (ViewPager) findViewById(R.id.yindao_view);
        xian = (ImageView) findViewById(R.id.yindaoxian);
        banner = (RollPagerView) findViewById(R.id.yindaoyi);
        bannerlist = new ArrayList<>();

        fragmentsList = new ArrayList<>();
        if (isFirstStart(context)) {
            s = 1;
            banner.setPlayDelay(400000);
            banner.setVisibility(View.VISIBLE);
            getbanner2();
            //      di.setVisibility(View.GONE);

            timetex.setVisibility(View.GONE);
            banner.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            banner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (position == bannerlist.size() - 1) {
                        Intent m = new Intent(context, MainActivity.class);
                        startActivity(m);
                        finish();
                    }
                }
            });

            img.setVisibility(View.GONE);
            timelin.setVisibility(View.GONE);


        } else {
            s = 0;
            //xian.setVisibility(View.VISIBLE);

            banner.setVisibility(View.GONE);
            img.setVisibility(View.VISIBLE);
            times = new TimeCount(5000, 1000);
            times.start();// 开始计时
            getbanner1();

        }
        initData();

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent m = new Intent(context, MainActivity.class);
                startActivity(m);
                finish();
            }
        }, 4000);*/
        super.onCreate(savedInstanceState);

    }


    private void initData() {
        time.setOnClickListener(this);
        timelin.setOnClickListener(this);
        timetex.setOnClickListener(this);





    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yinddao_time:
                i = 0;
                time.clearFocus();
                Intent m = new Intent(context, MainActivity.class);
                startActivity(m);
                finish();
                return;
            case R.id.yindao_lin:
                i = 0;
                Intent ms = new Intent(context, MainActivity.class);
                startActivity(ms);
                finish();
                return;
            case R.id.yindao_tex:
                i = 0;
                Intent mss = new Intent(context, MainActivity.class);
                startActivity(mss);
                finish();
                return;
        }
    }

    private void touming() {
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        // getActivity().  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            time.setClickable(false);
            time.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            if (i == 1) {
                Intent m = new Intent(context, MainActivity.class);
                startActivity(m);
                finish();
            }

        }
    }


    public static boolean isFirstStart(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_APP_TAG, 0);
        Boolean isFirst = preferences.getBoolean("FIRSTStart", true);
        if (isFirst) {// 第一次
            //            preferences.edit().putBoolean("FIRSTStart", false).commit();
            return true;
        } else {
            return false;
        }
    }


    class MyPagerAdapter extends StaticPagerAdapter {
        private static final String TAG ="MyPagerAdapter" ;
        List<SplshBean.DataBean> image;

        public MyPagerAdapter(List<SplshBean.DataBean> image) {
            this.image = image;
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            Log.e(TAG, "getView: " +image.get(position));
            Glide.with(context).load(Common.ImgUrl+image.get(position).getImg()).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;
        }

        @Override
        public int getCount() {
            return image.size();
        }

    }

    private void getbanner1() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", "1");

            Log.e("indextwo", jsonObject+"");
            HttpUtils.post(context, Common.YinDao, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    Log.e("indextwo", text);
                    dataBean = GsonUtils.JsonToBean(text, SplshBean.class);
                    if (dataBean.getStatus()== 1) {
                        Message msg = new Message();
                        msg.what = 3;
                        xian.setVisibility(View.GONE);
                        handler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = 4;
                        handler.sendMessage(msg);
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

    private void getbanner2() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", "2");
            HttpUtils.post(context, Common.YinDao, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    Log.e("indextwo", text);
                    dataBean = GsonUtils.JsonToBean(text, SplshBean.class);
                    if (dataBean.getStatus() == 1) {
                        Message msg = new Message();
                        msg.what = 3;
                        handler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = 4;
                        handler.sendMessage(msg);
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
