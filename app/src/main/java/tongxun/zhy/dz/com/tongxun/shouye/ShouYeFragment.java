package tongxun.zhy.dz.com.tongxun.shouye;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mbg.library.IRefreshListener;
import com.mbg.library.RefreshRelativeLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tongxun.zhy.dz.com.tongxun.MainActivity;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.base.ShopWebActivity;
import tongxun.zhy.dz.com.tongxun.base.WebActivity;
import tongxun.zhy.dz.com.tongxun.gonggong.Apiall;
import tongxun.zhy.dz.com.tongxun.horhuadong.PagingScrollHelper;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.BannerBean;
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.FenLeiBean;
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.ShangpinBean;
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.SyYingXiaoBean;
import tongxun.zhy.dz.com.tongxun.shipin.AddShipin;
import tongxun.zhy.dz.com.tongxun.shipin.activity.ShiPinActivity;
import tongxun.zhy.dz.com.tongxun.shouye.adapter.ChanPinfenleiSyAdapter;
import tongxun.zhy.dz.com.tongxun.shouye.adapter.ChanpinSyAdapter;
import tongxun.zhy.dz.com.tongxun.shouye.adapter.CuxiaoShouyeAdapter;
import tongxun.zhy.dz.com.tongxun.shouye.adapter.FenLeiShouyeAdapter;
import tongxun.zhy.dz.com.tongxun.shouye.adapter.JiShuShouyeAdapter;
import tongxun.zhy.dz.com.tongxun.shouye.bean.GuanggaoBean;
import tongxun.zhy.dz.com.tongxun.shouye.bean.ShouyeShipingBean;
import tongxun.zhy.dz.com.tongxun.tools.ACache;
import tongxun.zhy.dz.com.tongxun.tools.GetHeightUtil;
import tongxun.zhy.dz.com.tongxun.tools.GsonUtils;
import tongxun.zhy.dz.com.tongxun.tools.HttpUtils;
import tongxun.zhy.dz.com.tongxun.tools.LoadImageView;
import tongxun.zhy.dz.com.tongxun.tools.LoadingDialog;
import tongxun.zhy.dz.com.tongxun.tools.MyGridview;
import tongxun.zhy.dz.com.tongxun.tools.ObservableScrollView;
import tongxun.zhy.dz.com.tongxun.tools.TextCallBack;
import tongxun.zhy.dz.com.tongxun.wode.activity.LoginActivity;

public class ShouYeFragment extends Fragment implements ObservableScrollView.ScrollViewListener, PagingScrollHelper.onPageChangeListener {
    @BindView(R.id.mBanshouyefr_banner)
    Banner mBanshouyefrBanner;
    Unbinder unbinder;
    @BindView(R.id.mGvshouyefr_fenlei)
    MyGridview mGvshouyefrFenlei;
    @BindView(R.id.mGvshouyefr_cuxiao)
    MyGridview mGvshouyefrCuxiao;
    @BindView(R.id.mGvshouyefr_jishu)
    MyGridview mGvshouyefrJishu;
    @BindView(R.id.mGvshouyefr_chanpinfenlei)
    GridView mGvshouyefrChanpinfenlei;
    @BindView(R.id.mGvshouyefr_chanpin)
    MyGridview mGvshouyefrChanpin;
    @BindView(R.id.mTvmain_top)
    TextView mTvmainTop;
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
    @BindView(R.id.mLnmain_top)
    LinearLayout mLnmainTop;
    @BindView(R.id.shouye_scroll)
    ObservableScrollView shouyeScroll;
    @BindView(R.id.shouye_shuaxin)
    RefreshRelativeLayout shouyeShuaxin;
    @BindView(R.id.mTvshouyefr_yinxiaoonename)
    TextView mTvshouyefrYinxiaoonename;
    @BindView(R.id.mTvshouyefr_yinxiaoonejianjie)
    TextView mTvshouyefrYinxiaoonejianjie;
    @BindView(R.id.mTvshouyefr_yinxiaooneimg)
    ImageView mTvshouyefrYinxiaooneimg;
    @BindView(R.id.mLnshouyefr_yinxiaoone)
    LinearLayout mLnshouyefrYinxiaoone;
    @BindView(R.id.mTvshouyefr_yinxiaotwoname)
    TextView mTvshouyefrYinxiaotwoname;
    @BindView(R.id.mTvshouyefr_yinxiaotwojianjie)
    TextView mTvshouyefrYinxiaotwojianjie;
    @BindView(R.id.mTvshouyefr_yinxiaotwoimg)
    ImageView mTvshouyefrYinxiaotwoimg;
    @BindView(R.id.mLnshouyefr_yinxiaotwo)
    LinearLayout mLnshouyefrYinxiaotwo;
    @BindView(R.id.mIvshouyefr_guanggao)
    ImageView mIvshouyefrGuanggao;
    @BindView(R.id.mLnshouyefr_gengduo)
    LinearLayout mLnshouyefrGengduo;
    @BindView(R.id.mLnshouyefr_huanyipi)
    LinearLayout mLnshouyefrHuanyipi;
    @BindView(R.id.toptext)
    TextView toptext;


    private View view;
    private List<String> bannerlist;
    private List<FenLeiBean.ShouyeBean> fenleilist;
    private List<ShouyeShipingBean.DataBean> cuxiaolist;
    private List<ShouyeShipingBean.DataBean> jishulist;
private String TAG="shouyefragment";
    private List<ShangpinBean.DataBean> chanpinlist;
    private FenLeiShouyeAdapter fenLeiShouyeAdapter;
    private CuxiaoShouyeAdapter cuxiaoShouyeAdapter;
    private JiShuShouyeAdapter jiShuShouyeAdapter;
    private ChanPinfenleiSyAdapter chanPinfenleiSyAdapter;
    private ChanpinSyAdapter chanpinSyAdapter;
    private Context context;
    private FenLeiBean fenLeiBean;
    private LoadingDialog loadingDialog;
    private ACache aCache;
    private int imageHeight;
    private Intent intent;
    private GuanggaoBean guanggaoBean;
    private BannerBean bannerBean;
    private SyYingXiaoBean syYingXiaoBean;
    private ShouyeShipingBean shipingBean;
    private ShouyeShipingBean shipingBeanjishu;
    private int page = 1, size = 10, jishupage = 1, jishusize = 4;
    private ShangpinBean shangpinBean;

    private boolean login = false;
    private Thread newThread; //声明一个子线程
    private String typeid = "";//分类id
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:    //banner
                    if (bannerBean.getData().size() > 0) {
                        for (int i = 0; i < bannerBean.getData().size(); i++) {
                            bannerlist.add(Common.ImgUrl + bannerBean.getData().get(i).getImg());
                        }
                    }
                    inibanner();
                    break;
                case 2:
                    break;
                case 3://分类
                    if (fenLeiBean.getData().size() > 0) {
                        for (int i = 0; i < 4; i++) {
                            fenleilist.add(fenLeiBean.getShouye().get(i));
                        }
                        FenLeiBean.ShouyeBean data = new FenLeiBean.ShouyeBean();
                        data.setName("全部");
                        data.setId("0");
                        fenleilist.add(data);
                        setfenleiada();
                        setchanpinflada();
                        typeid = fenLeiBean.getShouye().get(0).getId();
                        Log.e("fenleiid", typeid);
                        getshangpin("", typeid);
                    }

                    break;
                case 4:

                    break;
                case 5://广告
                    Glide.with(context).load(Common.ImgUrl + guanggaoBean.getData().getImg()).into(mIvshouyefrGuanggao);
                    break;
                case 6:
                    getguanggao();
                    break;
                case 7://商品
                    page++;
                    shouyeShuaxin.positiveRefreshComplete();
                    shouyeShuaxin.negativeRefreshComplete();
                    if (shangpinBean.getData().size() > 0) {
                        for (int i = 0; i < shangpinBean.getData().size(); i++) {
                            chanpinlist.add(shangpinBean.getData().get(i));
                        }

                        setchanpinada();
                    }
                    break;
                case 8:
                    shouyeShuaxin.positiveRefreshComplete();
                    shouyeShuaxin.negativeRefreshComplete();
                    break;
                case 9://首页营销
                    if (syYingXiaoBean.getData().size() > 1) {
                        mTvshouyefrYinxiaoonename.setText(syYingXiaoBean
                                .getData().get(0).getTitle());
                        mTvshouyefrYinxiaoonejianjie.setText(syYingXiaoBean.getData().get(0).getDetails());
                        Glide.with(context).load(Common.ImgUrl + syYingXiaoBean.getData().get(0).getImg()).into(mTvshouyefrYinxiaooneimg);
                        mTvshouyefrYinxiaotwoname.setText(syYingXiaoBean
                                .getData().get(1).getTitle());
                        mTvshouyefrYinxiaotwojianjie.setText(syYingXiaoBean.getData().get(1).getDetails());
                        Glide.with(context).load(Common.ImgUrl + syYingXiaoBean.getData().get(1).getImg()).into(mTvshouyefrYinxiaotwoimg);
                    }
                    break;
                case 10:
                    break;
                case 11:
                    if (shipingBean.getData().size() > 0) {
                        for (int i = 0; i < shipingBean.getData().size(); i++) {
                            cuxiaolist.add(shipingBean.getData().get(i));
                        }
                        setcuxiaoada();
                    }


                    break;
                case 12:
                    break;
                case 13:
                    if (shipingBeanjishu.getData().size() > 1) {
                        jishupage++;
                        jishulist.clear();
                        for (int i = 0; i < shipingBeanjishu.getData().size(); i++) {
                            jishulist.add(shipingBeanjishu.getData().get(i));
                        }
                        setjishuada();
                    } else {
                        jishulist.clear();
                        jishupage = 1;
                        getjishu();
                    }

                    break;
                case 14:
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.shou_ye_fragment, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        context = getActivity();
        mTvmainTop.getLayoutParams().height = GetHeightUtil.getztl(context);
        loadingDialog = new LoadingDialog(context);
        aCache = ACache.get(context);
        bannerlist = new ArrayList<>();
        fenleilist = new ArrayList<>();
        cuxiaolist = new ArrayList<>();
        jishulist = new ArrayList<>();
        chanpinlist = new ArrayList<>();
        getbanner();
        getFenlei();
        getYingxiao();
        getguanggao();
        tuijianshipin();
        getjishu();


        shouyeShuaxin.addRefreshListener(new IRefreshListener() {
            @Override
            public void onPositiveRefresh() {
                //刷新
                if (bannerlist.size() < 0) {
                    bannerlist.clear();
                    getbanner();
                }
                if (fenleilist.size() < 0) {
                    fenleilist.clear();
                    getFenlei();
                }
                if (syYingXiaoBean
                        .getData().size() < 1) {
                    getYingxiao();
                }
                getshangpin("", typeid);
            }

            @Override
            public void onNegativeRefresh() {
                //加载
                getshangpin("", typeid);
            }
        });
        // 获取顶部图片高度后，设置滚动监听
        ViewTreeObserver vto = mBanshouyefrBanner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBanshouyefrBanner.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                imageHeight = mBanshouyefrBanner.getHeight();

                shouyeScroll.setScrollViewListener(ShouYeFragment.this);
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        aCache = ACache.get(context);
        if ("".equals(aCache.getAsString("uid")) || aCache.getAsString("uid") == null) {
            login = false;
        } else {
            //  mTvmyName.setText(aCache.getAsString("uname"));
            login = true;
        }
    }


    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            mLnmainTop.setBackgroundColor(Color.argb((int) 0, 227, 29, 26));//AGB由相关工具获得，或者美工提供
        } else if (y > 0 && y <= imageHeight) {
            float scale = (float) y / imageHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            mLnmainTop.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        } else {
            mLnmainTop.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
        }

    }

    private void setchanpinflada() {
        if (chanPinfenleiSyAdapter == null) {
            chanPinfenleiSyAdapter = new ChanPinfenleiSyAdapter(getContext(), fenleilist);
            mGvshouyefrChanpinfenlei.setAdapter(chanPinfenleiSyAdapter);
        } else {
            chanPinfenleiSyAdapter.notifyDataSetChanged();
        }
        mGvshouyefrChanpinfenlei.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (fenleilist.get(position).getId().equals("0")) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.Fenleishangpin + "0" + "&uid=" + aCache.getAsString("uid"));
                    startActivity(intent);
                } else {
                    chanPinfenleiSyAdapter.bian(position);
                    page = 1;
                    chanpinlist.clear();
                    setchanpinada();
                    getshangpin("", fenleilist.get(position).getId());
                    typeid = fenleilist.get(position).getId();
                }
            }
        });

    }

    private void setchanpinada() {
        if (chanpinSyAdapter == null) {
            chanpinSyAdapter = new ChanpinSyAdapter(getContext(), chanpinlist);
            mGvshouyefrChanpin.setAdapter(chanpinSyAdapter);
        } else {
            chanpinSyAdapter.notifyDataSetChanged();
        }
        mGvshouyefrChanpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent = new Intent(context, ShopWebActivity.class);
                intent.putExtra("url", Common.ShangpinWeb + chanpinlist.get(i).getId() + "/uid/" + aCache.getAsString("uid") + "/type/list");
                startActivity(intent);
                // Log.e("uid", Common.ShangpinWeb + chanpinlist.get(i).getId() + "/uid/" + aCache.getAsString("uid")+"/typy/list");
            }
        });
    }

    private void setjishuada() {
        if (jiShuShouyeAdapter == null) {
            jiShuShouyeAdapter = new JiShuShouyeAdapter(getContext(), jishulist);
            mGvshouyefrJishu.setAdapter(jiShuShouyeAdapter);
        } else {
            jiShuShouyeAdapter.notifyDataSetChanged();
        }
        mGvshouyefrJishu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                intent = new Intent(context, ShiPinActivity.class);
                intent.putExtra("type", "2");
                intent.putExtra("videourl", jishulist.get(i).getPath());
                intent.putExtra("url", Common.VideoXiangQing + jishulist.get(i).getId() + "&uid=" + aCache.getAsString("uid"));
                // Toast.makeText(context, ids, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    private void setcuxiaoada() {
        if (cuxiaoShouyeAdapter == null) {
            cuxiaoShouyeAdapter = new CuxiaoShouyeAdapter(getContext(), cuxiaolist);
            mGvshouyefrCuxiao.setAdapter(cuxiaoShouyeAdapter);
        } else {
            cuxiaoShouyeAdapter.notifyDataSetChanged();
        }
        mGvshouyefrCuxiao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                intent = new Intent(context, ShiPinActivity.class);
                intent.putExtra("type", "2");
                intent.putExtra("videourl", cuxiaolist.get(i).getPath());
                intent.putExtra("url", Common.VideoXiangQing + cuxiaolist.get(i).getId() + "&uid=" + aCache.getAsString("uid"));
                // Toast.makeText(context, ids, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    private void setfenleiada() {
        if (fenLeiShouyeAdapter == null) {
            fenLeiShouyeAdapter = new FenLeiShouyeAdapter(getContext(), fenleilist);
            mGvshouyefrFenlei.setAdapter(fenLeiShouyeAdapter);
        } else {
            fenLeiShouyeAdapter.notifyDataSetChanged();
        }
        mGvshouyefrFenlei.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent = new Intent(context, ShopWebActivity.class);
                intent.putExtra("url", Common.Fenleishangpin + fenleilist.get(i).getId() + "&uid=" + aCache.getAsString("uid"));
                startActivity(intent);
                // Log.e("uid", Common.Fenleishangpin + fenleilist.get(i).getId() + "&uid=" + aCache.getAsString("uid"));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void inibanner() {

        //设置banner样式
        mBanshouyefrBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanshouyefrBanner.setImageLoader(new LoadImageView());
        //设置banner动画效果
        mBanshouyefrBanner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        mBanshouyefrBanner.isAutoPlay(true);
        //设置轮播时间
        mBanshouyefrBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanshouyefrBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片集合
        mBanshouyefrBanner.setImages(bannerlist);
        //banner设置方法全部调用完毕时最后调用
        mBanshouyefrBanner.start();
        mBanshouyefrBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

                if (bannerBean.getData().get(position).getType().equals("1")) {//1:h5页面  2:商品   3:视频   4:活动
                    intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url", Common.URL + bannerBean.getData().get(position).getUrl());
                    intent.putExtra("title", Common.URL + bannerBean.getData().get(position).getTitle());
                    startActivity(intent);

                }
                if (bannerBean.getData().get(position).getType().equals("2")) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.ShangpinWeb + bannerBean.getData().get(position).getGoods() + "/uid/" + aCache.getAsString("uid"));
                    startActivity(intent);
                }
                if (bannerBean.getData().get(position).getType().equals("3")) {
                    Intent intent;
                    intent = new Intent(context, ShiPinActivity.class);
                    intent.putExtra("type", "2");
                    intent.putExtra("videourl", bannerBean.getData().get(position).getVideo());
                    intent.putExtra("url", Common.VideoXiangQing + bannerBean.getData().get(position).getUrl() + "&uid=" + aCache.getAsString("uid"));
                    // Toast.makeText(context, ids, Toast.LENGTH_SHORT).show();
                    startActivityForResult(intent, 1);
                }
                if (bannerBean.getData().get(position).getType().equals("4")) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.URL + bannerBean.getData().get(position).getUrl() + "&uid=" + aCache.getAsString("uid"));
                    startActivity(intent);
                }
            }

        });
    }

    private void getbanner() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cate", "1");
            HttpUtils.post(context, Common.Banner, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    bannerBean = GsonUtils.JsonToBean(text, BannerBean.class);
                    Message msg = new Message();
                    if (bannerBean.getStatus() == 1) {
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

    private void getFenlei() {
        JSONObject jsonObject = new JSONObject();
        HttpUtils.post(context, Common.FenLei, jsonObject, new TextCallBack() {
            @Override
            protected void onSuccess(String text) {


           //     Gson gson =new GsonBuilder().registerTypeAdapter(String.class, new StringConverter()).create();
             //   String a="{\"msg\":\"\\u83b7\\u53d6\\u4fe1\\u606f\\u6210\\u529f\",\"status\":1,\"shouye\":[{\"id\":null,\"name\":null,\"img\":\"\\/Uploads\\/banner\\/2019-03-21\\/1553134363956983783.jpg\"},{\"id\":\"8\",\"name\":\"vivo\",\"img\":\"\\/Uploads\\/banner\\/2019-03-21\\/15531344381142579855.jpg\"},{\"id\":\"13\",\"name\":\"\\u8054\\u60f3\",\"img\":\"\\/Uploads\\/banner\\/2019-03-21\\/15531346381145721175.jpg\"},{\"id\":\"12\",\"name\":\"IQOO\",\"img\":\"\\/Uploads\\/banner\\/2019-03-21\\/15531345921047264460.jpg\"}]}";

             // fenLeiBean = gson.fromJson(a, FenLeiBean.class);
               fenLeiBean = GsonUtils.JsonToBean(text, FenLeiBean.class);
          //      Log.e(TAG, "onSuccess: "+fenLeiBean.getData().get(0).getId() );
                Log.e("首页分类text", text);
                Message msg = new Message();
                if (fenLeiBean.getStatus() == 1) {
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




    private void getguanggao() {
        Apiall apiall = new Apiall();
        apiall.getguanggao(context, "1", new Apiall.apihui() {
            @Override
            public void sucss(String text) {
                Message msg = new Message();
                guanggaoBean = GsonUtils.JsonToBean(text, GuanggaoBean.class);
                if (guanggaoBean.getStatus() == 1) {
                    msg.what = 5;
                } else {
                    msg.what = 6;
                }
                handler.sendMessage(msg);
            }

            @Override
            public void erry() {
                Toast.makeText(context, "信息获取失败请刷新", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getshangpin(String sousuo, String fenlei) {
        loadingDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", sousuo);
            jsonObject.put("id", fenlei);
            jsonObject.put("index_page", page);
            jsonObject.put("index_size", size);
            Log.e("fenleiid", fenlei);
            Log.e("shangpinjson", jsonObject + "");
            HttpUtils.post(context, Common.ShangPin, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    loadingDialog.dismiss();
                    Log.e("shangpintext", text);
                    shangpinBean = GsonUtils.JsonToBean(text, ShangpinBean.class);
                    Message msg = new Message();
                    if (shangpinBean.getStatus() == 1) {
                        msg.what = 7;
                    } else {
                        msg.what = 8;
                    }
                    handler.sendMessage(msg);
                }

                @Override
                protected void onFailure(ResponseException e) {
                    loadingDialog.dismiss();
                    Toast.makeText(context, "失败", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getYingxiao() {
        JSONObject jsonObject = new JSONObject();
        HttpUtils.post(context, Common.SyYingxiao, jsonObject, new TextCallBack() {
            @Override
            protected void onSuccess(String text) {
                Log.e("营销text", text);
                Message msg = new Message();
             Gson gson =new GsonBuilder().registerTypeAdapter(String.class, new tongxun.zhy.dz.com.tongxun.utils.HttpUtils.StringConverter()).create();

          //      syYingXiaoBean = gson.fromJson(a, SyYingXiaoBean.class);



                syYingXiaoBean =  gson.fromJson(text, SyYingXiaoBean.class);
                if (syYingXiaoBean.getStatus() == 1) {
                    msg.what = 9;
                } else {
                    msg.what = 10;
                }
                handler.sendMessage(msg);
            }

            @Override
            protected void onFailure(ResponseException e) {

            }
        });
    }

    private void tuijianshipin() {
        JSONObject jsonObject = new JSONObject();
        HttpUtils.post(context, Common.TuiJianShipin, jsonObject, new TextCallBack() {
            @Override
            protected void onSuccess(String text) {
                shipingBean = GsonUtils.JsonToBean(text, ShouyeShipingBean.class);
                Message msg = new Message();
                if (shipingBean.getStatus() == 1) {
                    msg.what = 11;
                } else {
                    msg.what = 12;
                }
                handler.sendMessage(msg);
            }

            @Override
            protected void onFailure(ResponseException e) {

            }
        });
    }

    /**
     * 技术视频
     */
    private void getjishu() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("index_page", jishupage);
            jsonObject.put("index_size", jishusize);
            HttpUtils.post(context, Common.JiShushipin, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    shipingBeanjishu = GsonUtils.JsonToBean(text, ShouyeShipingBean.class);
                    Message msg = new Message();
                    if (shipingBeanjishu.getStatus() == 1) {
                        msg.what = 13;
                    } else {
                        msg.what = 14;
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

    @Override
    public void onPageChange(int index) {

    }

    @OnClick({R.id.mLnshouyefr_yinxiaoone, R.id.mLnshouyefr_yinxiaotwo, R.id.mIvshouyefr_guanggao, R.id.mLnshouyefr_gengduo, R.id.mLnshouyefr_huanyipi,
            R.id.toptext, R.id.mIvmain_jia, R.id.mTvmain_tanshipin, R.id.mTvmain_tanchanpin, R.id.mTvmain_tansan, R.id.mTvmain_tansi, R.id.mLnmain_tan, R.id.mLnmain_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mLnshouyefr_yinxiaoone:
                if (syYingXiaoBean.getData().size() > 1) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.URL + syYingXiaoBean.getData().get(0).getUrl() + "&uid=" + aCache.getAsString("uid"));
                    startActivity(intent);
                }
                break;
            case R.id.mLnshouyefr_yinxiaotwo:
                if (syYingXiaoBean.getData().size() > 1) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.URL + syYingXiaoBean.getData().get(1).getUrl());
                    startActivity(intent);
                }
                break;
            case R.id.mIvshouyefr_guanggao:
                if (guanggaoBean.getData().getType().equals("1")) {//1:商品  2:活动   3:视频   4:H5

                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.URL + guanggaoBean.getData().getUrl() + "&uid=" + aCache.getAsString("uid") + "&type=list");
                    startActivity(intent);
                }
                if (guanggaoBean.getData().getType().equals("2")) {
                    intent = new Intent(context, ShopWebActivity.class);
                    intent.putExtra("url", Common.URL + guanggaoBean.getData().getUrl() + "&uid=" + aCache.getAsString("uid"));
                    startActivity(intent);

                }
                if (guanggaoBean.getData().getType().equals("3")) {
                    Toast.makeText(context, "视频", Toast.LENGTH_SHORT).show();
                }
                if (guanggaoBean.getData().getType().equals("4")) {
                    intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url", Common.URL + guanggaoBean.getData().getUrl() + "/uid/" + aCache.getAsString("uid"));
                    intent.putExtra("title", Common.URL + guanggaoBean.getData().getTitle());
                    startActivity(intent);
                }


                break;
            case R.id.mLnshouyefr_gengduo:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.selectFragmentmQuotes(2);
                break;
            case R.id.mLnshouyefr_huanyipi:
                getjishu();
                break;

            case R.id.toptext:
                intent = new Intent(context, ShopWebActivity.class);
                intent.putExtra("url", Common.Fenleishangpin + "0" + "&uid=" + aCache.getAsString("uid"));
                startActivity(intent);
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
                    intent = new Intent(getContext(), AddShipin.class);
                    intent.putExtra("type", "1");//1上传  2修改
                    startActivity(intent);
                }
                mLnmainTan.setVisibility(View.GONE);
                mIvmainSanjiao.setVisibility(View.GONE);
                break;
            case R.id.mTvmain_tanchanpin:
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
                mLnmainTan.setVisibility(View.GONE);
                mIvmainSanjiao.setVisibility(View.GONE);
                break;
            case R.id.mTvmain_tansan:
                intent = new Intent(context, ShopWebActivity.class);
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
                mLnmainTan.setVisibility(View.GONE);
                mIvmainSanjiao.setVisibility(View.GONE);
                break;
            case R.id.mTvmain_tansi:
                intent = new Intent(context, ShopWebActivity.class);
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
                mLnmainTan.setVisibility(View.GONE);
                mIvmainSanjiao.setVisibility(View.GONE);
                break;
            case R.id.mLnmain_tan:
                break;
            case R.id.mLnmain_top:
                break;
        }
    }

}
