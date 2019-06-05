package tongxun.zhy.dz.com.tongxun.wode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.base.ShopWebActivity;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.tools.ACache;
import tongxun.zhy.dz.com.tongxun.tools.CircleImageView;
import tongxun.zhy.dz.com.tongxun.tools.GetHeightUtil;
import tongxun.zhy.dz.com.tongxun.tools.GsonUtils;
import tongxun.zhy.dz.com.tongxun.tools.HttpUtils;
import tongxun.zhy.dz.com.tongxun.tools.LoadingDialog;
import tongxun.zhy.dz.com.tongxun.tools.TextCallBack;
import tongxun.zhy.dz.com.tongxun.wode.activity.LoginActivity;
import tongxun.zhy.dz.com.tongxun.wode.activity.SettingActivity;
import tongxun.zhy.dz.com.tongxun.wode.bean.GerenxinxiBean;

public class WoDeFragment extends Fragment {

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
    Unbinder unbinder;
    @BindView(R.id.mLnmy_geren)
    LinearLayout mLnmyGeren;
    @BindView(R.id.mLnmy_guanzhu)
    LinearLayout mLnmyGuanzhu;
    @BindView(R.id.mLnmy_shoucang)
    LinearLayout mLnmyShoucang;
    @BindView(R.id.mLnmy_xihuan)
    LinearLayout mLnmyXihuan;
    @BindView(R.id.mLnmy_qianbao)
    LinearLayout mLnmyQianbao;
    @BindView(R.id.mLnmy_wodeshangpin)
    LinearLayout mLnmyWodeshangpin;
    @BindView(R.id.mIvmy_touxiang)
    CircleImageView mIvmyTouxiang;
    @BindView(R.id.mTvmy_name)
    TextView mTvmyName;
    @BindView(R.id.mLnmy_wodemaichu)
    LinearLayout mLnmyWodemaichu;
    @BindView(R.id.mLnmy_wodeshipin)
    LinearLayout mLnmyWodeshipin;
    @BindView(R.id.mLnmy_wodeguankan)
    LinearLayout mLnmyWodeguankan;
    @BindView(R.id.mLnmy_wodeyaoqing)
    LinearLayout mLnmyWodeyaoqing;
    @BindView(R.id.mLnmy_wodeshiming)
    LinearLayout mLnmyWodeshiming;
    @BindView(R.id.mLnmy_wodevip)
    LinearLayout mLnmyWodevip;
    @BindView(R.id.mLnmy_wodegaijin)
    LinearLayout mLnmyWodegaijin;
    @BindView(R.id.mLnmy_wodefuwu)
    LinearLayout mLnmyWodefuwu;
    @BindView(R.id.mLnmy_quanbudingdan)
    LinearLayout mLnmyQuanbudingdan;
    @BindView(R.id.mLnmy_daifukuan)
    LinearLayout mLnmyDaifukuan;
    @BindView(R.id.mLnmy_daifahuo)
    LinearLayout mLnmyDaifahuo;
    @BindView(R.id.mLnmy_daishouhuo)
    LinearLayout mLnmyDaishouhuo;
    @BindView(R.id.mLnmy_daipingjia)
    LinearLayout mLnmyDaipingjia;
    @BindView(R.id.mLnmy_shouhoutuikuan)
    LinearLayout mLnmyShouhoutuikuan;
    @BindView(R.id.mytuichu)
    TextView mytuichu;
    @BindView(R.id.mTvmy_daifukuan)
    TextView mTvmyDaifukuan;
    @BindView(R.id.mTvmy_daifahuo)
    TextView mTvmyDaifahuo;
    @BindView(R.id.mTvmy_daishouhuo)
    TextView mTvmyDaishouhuo;
    @BindView(R.id.mTvmy_daipingjia)
    TextView mTvmyDaipingjia;
    @BindView(R.id.mTvmy_tuikuan)
    TextView mTvmyTuikuan;
    private View view;
    private Intent intent;
    private Context context;
    private String TAG="Wodefragment";
    private LoadingDialog loadingDialog;
    private ACache aCache;
    private boolean login = false;
    private GerenxinxiBean gerenxinxiBean;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    aCache.put("uname", gerenxinxiBean.getData().getNickname());
                    mTvmyName.setText(aCache.getAsString("uname"));
                    aCache.put("shiming",gerenxinxiBean.getData().getShiming());//1已实名
                    if (gerenxinxiBean.getData().getType1() > 0) {
                        mTvmyDaifukuan.setVisibility(View.VISIBLE);
                        mTvmyDaifukuan.setText(gerenxinxiBean.getData().getType1() + "");
                    }
                    if (gerenxinxiBean.getData().getType1() <= 0) {
                        mTvmyDaifukuan.setVisibility(View.INVISIBLE);
                    }
                    if (gerenxinxiBean.getData().getType1()>99){
                    mTvmyDaifukuan.setVisibility(View.VISIBLE);
                    mTvmyDaifukuan.setText("...");
                }
                    if (gerenxinxiBean.getData().getType2() > 0) {
                        mTvmyDaifahuo.setVisibility(View.VISIBLE);
                        mTvmyDaifahuo.setText(gerenxinxiBean.getData().getType2() + "");
                    }
                    if (gerenxinxiBean.getData().getType2() <= 0) {
                        mTvmyDaifahuo.setVisibility(View.INVISIBLE);
                    }
                    if (gerenxinxiBean.getData().getType2()>99){
                        mTvmyDaifahuo.setVisibility(View.VISIBLE);
                        mTvmyDaifahuo.setText("...");
                    }

                    if (gerenxinxiBean.getData().getType3() > 0) {
                        mTvmyDaishouhuo.setVisibility(View.VISIBLE);
                        mTvmyDaishouhuo.setText(gerenxinxiBean.getData().getType3() + "");
                    }
                    if (gerenxinxiBean.getData().getType3() <= 0) {
                        mTvmyDaishouhuo.setVisibility(View.INVISIBLE);
                    }
                    if (gerenxinxiBean.getData().getType3()>99){
                        mTvmyDaishouhuo.setVisibility(View.VISIBLE);
                        mTvmyDaishouhuo.setText("...");
                    }
                    if (gerenxinxiBean.getData().getType4() > 0) {
                        mTvmyDaipingjia.setVisibility(View.VISIBLE);
                        mTvmyDaipingjia.setText(gerenxinxiBean.getData().getType4() + "");
                    }
                    if (gerenxinxiBean.getData().getType4() <= 0) {
                        mTvmyDaipingjia.setVisibility(View.INVISIBLE);
                    }
                    if (gerenxinxiBean.getData().getType4()>99){
                        mTvmyDaipingjia.setVisibility(View.VISIBLE);
                        mTvmyDaipingjia.setText("...");
                    }
                    if (gerenxinxiBean.getData().getType6() > 0) {
                        mTvmyTuikuan.setVisibility(View.VISIBLE);
                        mTvmyTuikuan.setText(gerenxinxiBean.getData().getType6() + "");
                    }
                    if (gerenxinxiBean.getData().getType6() <= 0) {
                        mTvmyTuikuan.setVisibility(View.INVISIBLE);
                    }
                    if (gerenxinxiBean.getData().getType6()>99){
                       mTvmyTuikuan .setVisibility(View.VISIBLE);
                        mTvmyTuikuan.setText("...");
                    }
                    break;
                case 2:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.wo_de_fragment, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        context = getContext();
        title.setText("我的");
        loadingDialog = new LoadingDialog(context);
        aCache = ACache.get(context);
        title.setTextColor(getResources().getColor(R.color.white));
        titlerightimg.setImageResource(R.mipmap.settingbai);
        titlerightimg.setVisibility(View.VISIBLE);
        titleqtop.getLayoutParams().height = GetHeightUtil.getztl(getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        aCache = ACache.get(context);
        if ("".equals(aCache.getAsString("uid")) || aCache.getAsString("uid") == null) {
            login = false;
            mTvmyName.setText("请登陆");
            mIvmyTouxiang.setImageResource(R.mipmap.weidenglu);

        } else {
            //  mTvmyName.setText(aCache.getAsString("uname"));
            Glide.with(context).load(aCache.getAsString("uimg")).into(mIvmyTouxiang);
            login = true;
            getnickname();
            mTvmyName.setText(aCache.getAsString("uname"));

        }
    }

    @OnClick({R.id.mLnmy_geren, R.id.mLnmy_guanzhu, R.id.mLnmy_shoucang, R.id.mLnmy_xihuan, R.id.mLnmy_qianbao,
            R.id.mLnmy_wodeshangpin, R.id.mLnmy_wodemaichu, R.id.mLnmy_wodeshipin, R.id.mLnmy_wodeguankan, R.id.mLnmy_wodeyaoqing,
            R.id.mLnmy_wodeshiming, R.id.mLnmy_wodevip, R.id.mLnmy_wodegaijin, R.id.mLnmy_wodefuwu,
            R.id.mLnmy_quanbudingdan, R.id.mLnmy_daifukuan, R.id.mLnmy_daifahuo, R.id.mLnmy_daishouhuo, R.id.mLnmy_daipingjia,
            R.id.mLnmy_shouhoutuikuan, R.id.titlerightimg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mLnmy_geren:
                if ("".equals(aCache.getAsString("uid")) || aCache.getAsString("uid") == null) {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.USerXinxi + "?uid=" + aCache.getAsString("uid") + "&other_uid=" + aCache.getAsString("uid") + "&type=1");
                    startActivity(intent);
                }
                break;
            case R.id.mLnmy_guanzhu:
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.GuanZhuDz + "?uid=" + aCache.getAsString("uid"));
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mLnmy_shoucang:
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.Shoucang + "?uid=" + aCache.getAsString("uid"));
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mLnmy_xihuan://喜欢的视频
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.USerXinxi + "?uid=" + aCache.getAsString("uid") + "&other_uid=" + aCache.getAsString("uid") + "&type=3");
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mLnmy_qianbao:
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.MyQianbao + "?uid=" + aCache.getAsString("uid"));
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mLnmy_wodeshangpin://我的商品
                if (login) {
                    if (aCache.getAsString("shiming").equals("1")) {
                        intent = new Intent(context, ShopWebActivity.class);
                        intent.putExtra("url", Common.MyShangpin + "?uid=" + aCache.getAsString("uid"));
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "请先进行实名认证", Toast.LENGTH_SHORT).show();
                        intent = new Intent(context, ShopWebActivity.class);
                        intent.putExtra("url", Common.Shiming + "?uid=" + aCache.getAsString("uid"));
                        startActivity(intent);
                    }
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mLnmy_wodemaichu:
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.MaijiaDingDan + "?uid=" + aCache.getAsString("uid"));
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mLnmy_wodeshipin://我的视频
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.USerXinxi + "?uid=" + aCache.getAsString("uid") + "&other_uid=" + aCache.getAsString("uid") + "&type=1");
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mLnmy_wodeguankan:
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.LookOld + "?uid=" + aCache.getAsString("uid"));
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.mLnmy_wodeyaoqing:
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.YaoqingTonghang + "?uid=" + aCache.getAsString("uid"));
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.mLnmy_wodeshiming:
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.Shiming + "?uid=" + aCache.getAsString("uid"));
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.mLnmy_wodevip:
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.VIPTequan + "?uid=" + aCache.getAsString("uid"));
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mLnmy_wodegaijin:

                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.GaijinJianyi + "?uid=" + aCache.getAsString("uid"));
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mLnmy_wodefuwu:
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.Fuwuzhongxin + "?uid=" + aCache.getAsString("uid"));
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mLnmy_quanbudingdan:
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.Dingdan + "?uid=" + aCache.getAsString("uid"));
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mLnmy_daifukuan:
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.Dingdan + "?uid=" + aCache.getAsString("uid") + "&type=1");
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mLnmy_daifahuo:
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.Dingdan + "?uid=" + aCache.getAsString("uid") + "&type=2");
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mLnmy_daishouhuo:
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.Dingdan + "?uid=" + aCache.getAsString("uid") + "&type=3");
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mLnmy_daipingjia:
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.Dingdan + "?uid=" + aCache.getAsString("uid") + "&type=4");
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mLnmy_shouhoutuikuan:
                if (login) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.AgentTuikuan + "?uid=" + aCache.getAsString("uid"));
                    startActivity(intent);
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.titlerightimg:
                intent = new Intent(context, SettingActivity.class);
                startActivity(intent);
                break;
        }
    }


    @OnClick(R.id.mytuichu)
    public void onViewClicked() {
        aCache.clear();
    }

    private void getnickname() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", aCache.getAsString("uid"));
            HttpUtils.post(context, Common.GetNIckname, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    Log.e(TAG, "onSuccess: "+text );
                    gerenxinxiBean = GsonUtils.JsonToBean(text, GerenxinxiBean.class);
                    Message msg = new Message();
                    if (gerenxinxiBean.getStatus() == 1) {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
