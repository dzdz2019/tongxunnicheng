package tongxun.zhy.dz.com.tongxun.shangcheng;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mbg.library.IRefreshListener;
import com.mbg.library.RefreshRelativeLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.base.ShopWebActivity;
import tongxun.zhy.dz.com.tongxun.base.WebActivity;
import tongxun.zhy.dz.com.tongxun.horhuadong.PagingScrollHelper;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.shangcheng.adapter.ChanpinShangchengAdapter;
import tongxun.zhy.dz.com.tongxun.shangcheng.adapter.FenLeiShangchengAdapter;
import tongxun.zhy.dz.com.tongxun.shangcheng.adapter.FenLeiShangchengssAdapter;
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.BannerBean;
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.FenLeiBean;
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.ShangpinBean;
import tongxun.zhy.dz.com.tongxun.shipin.activity.ShiPinActivity;
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

public class ShangChengFragment extends Fragment implements ObservableScrollView.ScrollViewListener, PagingScrollHelper.onPageChangeListener {

    @BindView(R.id.mBnshangcheng_banner)
    Banner mBnshangchengBanner;
    Unbinder unbinder;
    @BindView(R.id.mGvshangcheng_fenlei)
    MyGridview mGvshangchengFenlei;
    @BindView(R.id.mGvshangcheng_fenleiss)
    GridView mGvshangchengFenleiss;
    @BindView(R.id.mGvshangcheng_shanpin)
    MyGridview mGvshangchengShanpin;
    @BindView(R.id.mRlshangcheng_shugxin)
    RefreshRelativeLayout mRlshangchengShugxin;

    @BindView(R.id.mTvmain_top)
    TextView mTvmainTop;
    @BindView(R.id.toptext)
    TextView toptext;
    @BindView(R.id.mIvmain_jia)
    ImageView mIvmainJia;
    @BindView(R.id.mLnmain_top)
    LinearLayout mLnmainTop;
    @BindView(R.id.mSvshangcheng_scroll)
    ObservableScrollView mSvshangchengScroll;
    @BindView(R.id.mTvshangcheng_carnum)
    TextView mTvshangchengCarnum;
    @BindView(R.id.mIvshangcheng_car)
    RelativeLayout mIvshangchengCar;
    private View view;
    private Context context;
    private List<String> bannerlist;
    private List<FenLeiBean.DataBean> fenleilist;
    private List<FenLeiBean.ShopBean> fenleilistss;
    private List<ShangpinBean.DataBean> chanpinlist;
    private FenLeiShangchengAdapter fenLeiShangchengAdapter;
    private FenLeiShangchengssAdapter fenLeiShangchengAdapterss;
    private ChanpinShangchengAdapter shangchengAdapter;
    private LoadingDialog loadingDialog;
    private int page = 1, size = 10;
    private ShangpinBean shangpinBean;
    private FenLeiBean fenLeiBean;
    private BannerBean bannerBean;
    private Intent intent;
    private ACache aCache;
    private int imageHeight;
    private String typeid = "";
    private String TAG = "ShangChengFragment";
    private boolean login = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    page++;
                    mRlshangchengShugxin.positiveRefreshComplete();
                    mRlshangchengShugxin.negativeRefreshComplete();
                    if (shangpinBean.getData().size() > 0) {
                        for (int i = 0; i < shangpinBean.getData().size(); i++) {
                            chanpinlist.add(shangpinBean.getData().get(i));
                        }

                        setchanpinada();
                    }
                    break;
                case 2:
                    mRlshangchengShugxin.positiveRefreshComplete();
                    mRlshangchengShugxin.negativeRefreshComplete();
                    break;
                case 3:
                    if (fenLeiBean.getData().size() > 0) {
                        typeid = fenLeiBean.getData().get(0).getId();
                        for (int i = 0; i < fenLeiBean.getData().size(); i++) {
                            fenleilist.add(fenLeiBean.getData().get(i));
                        }
                        FenLeiBean.DataBean data = new FenLeiBean.DataBean();
                        data.setName("全部");
                        data.setId("0");
                        fenleilist.add(data);
                        setfenleiada();
                        for (int i = 0; i < fenLeiBean.getShop().size(); i++) {
                            fenleilistss.add(fenLeiBean.getShop().get(i));
                        }
                        setfenleiss();
                    }
                    break;
                case 4:
                    break;
                case 5:
                    if (bannerBean.getData().size() > 0) {
                        for (int i = 0; i < bannerBean.getData().size(); i++) {
                            bannerlist.add(Common.ImgUrl + bannerBean.getData().get(i).getImg());
                        }
                    }
                    inibanner();
                    break;
                case 6:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.shang_cheng_fragment, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        initview();
        mTvmainTop.getLayoutParams().height = GetHeightUtil.getztl(context);
        // 获取顶部图片高度后，设置滚动监听
        ViewTreeObserver vto = mBnshangchengBanner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBnshangchengBanner.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                imageHeight = mBnshangchengBanner.getHeight();

                mSvshangchengScroll.setScrollViewListener(ShangChengFragment.this);
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
            mTvshangchengCarnum.setVisibility(View.GONE);

        } else {
            //  mTvmyName.setText(aCache.getAsString("uname"));
            login = true;
            getcarnum();
            mTvshangchengCarnum.setVisibility(View.VISIBLE);

        }
    }

    private void initview() {
        context = getActivity();

        loadingDialog = new LoadingDialog(context);
        bannerlist = new ArrayList<>();
        fenleilist = new ArrayList<>();
        fenleilistss = new ArrayList<>();
        chanpinlist = new ArrayList<>();


        getshangpin("", "");
        getFenlei();
        getbanner();
        mRlshangchengShugxin.addRefreshListener(new IRefreshListener() {
            @Override
            public void onPositiveRefresh() {
                page = 1;
                chanpinlist.clear();
                getshangpin("", typeid);
            }

            @Override
            public void onNegativeRefresh() {
                getshangpin("", typeid);
            }
        });
    }

    private void setfenleiss() {
        if (fenLeiShangchengAdapterss == null) {
            fenLeiShangchengAdapterss = new FenLeiShangchengssAdapter(context, fenleilistss);
            mGvshangchengFenleiss.setAdapter(fenLeiShangchengAdapterss);
        } else {
            fenLeiShangchengAdapterss.notifyDataSetChanged();
        }
        mGvshangchengFenleiss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fenLeiShangchengAdapterss.bian(i);
                page = 1;
                chanpinlist.clear();
                getshangpin("", fenleilistss.get(i).getId());
                typeid = fenleilistss.get(i).getId();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setfenleiada() {
        if (fenLeiShangchengAdapter == null) {
            fenLeiShangchengAdapter = new FenLeiShangchengAdapter(getContext(), fenleilist);
            mGvshangchengFenlei.setAdapter(fenLeiShangchengAdapter);
        } else {
            fenLeiShangchengAdapter.notifyDataSetChanged();
        }
        mGvshangchengFenlei.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent = new Intent(context, ShopWebActivity.class);
                intent.putExtra("url", Common.Fenleishangpin + fenleilist.get(i).getId() + "&uid=" + aCache.getAsString("uid"));
                startActivity(intent);
                // Log.e("uid", Common.Fenleishangpin + fenleilist.get(i).getId() + "&uid=" + aCache.getAsString("uid"));
            }
        });
    }


    private void setchanpinada() {
        if (shangchengAdapter == null) {
            shangchengAdapter = new ChanpinShangchengAdapter(getContext(), chanpinlist);
            mGvshangchengShanpin.setAdapter(shangchengAdapter);
        } else {
            shangchengAdapter.notifyDataSetChanged();
        }
        mGvshangchengShanpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent = new Intent(context, ShopWebActivity.class);
                intent.putExtra("url", Common.ShangpinWeb + chanpinlist.get(i).getId() + "/uid/" + aCache.getAsString("uid") + "/type/list");
                startActivity(intent);
                // Log.e("uid", Common.ShangpinWeb + chanpinlist.get(i).getId() + "/uid/" + aCache.getAsString("uid")+"/typy/list");
            }
        });
    }

    private void inibanner() {


        //设置banner样式
        mBnshangchengBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBnshangchengBanner.setImageLoader(new LoadImageView());
        //设置banner动画效果
        mBnshangchengBanner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        mBnshangchengBanner.isAutoPlay(true);
        //设置轮播时间
        mBnshangchengBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        mBnshangchengBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片集合
        mBnshangchengBanner.setImages(bannerlist);
        //banner设置方法全部调用完毕时最后调用
        mBnshangchengBanner.start();

        mBnshangchengBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

                if (bannerBean.getData().get(position).getType().equals("1")) {//1:h5页面  2:商品   3:视频
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

            Log.e("shangpinjson", jsonObject + "");
            HttpUtils.post(context, Common.ShangPin, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    loadingDialog.dismiss();
                    Log.e("shangpintext", text);
                    shangpinBean = GsonUtils.JsonToBean(text, ShangpinBean.class);

                    Message msg = new Message();
                    if (shangpinBean.getStatus() == 1) {
                        msg.what = 1;
                    } else {
                        msg.what = 2;
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

    private void getFenlei() {
        JSONObject jsonObject = new JSONObject();
        HttpUtils.post(context, Common.FenLei, jsonObject, new TextCallBack() {
            @Override
            protected void onSuccess(String text) {
                fenLeiBean = GsonUtils.JsonToBean(text, FenLeiBean.class);
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

    private void getbanner() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cate", "2");
            HttpUtils.post(context, Common.Banner, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    Log.e(TAG, "onSuccess: banner" + text);
                    bannerBean = GsonUtils.JsonToBean(text, BannerBean.class);
                    Message msg = new Message();
                    if (bannerBean.getStatus() == 1) {
                        msg.what = 5;
                    } else {
                        msg.what = 6;
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

    @OnClick(R.id.mIvshangcheng_car)
    public void onViewClicked() {
        if ("".equals(aCache.getAsString("uid")) || aCache.getAsString("uid") == null) {
            Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();

        } else {
            Intent intent = new Intent(context, ShopWebActivity.class);
            intent.putExtra("url", Common.ShopCar + "?uid=" + aCache.getAsString("uid") + "&type=1");
            startActivity(intent);
        }
    }

    @OnClick({R.id.toptext, R.id.mIvmain_jia})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toptext:
                intent = new Intent(context, ShopWebActivity.class);
                intent.putExtra("url", Common.Fenleishangpin + "0" + "&uid=" + aCache.getAsString("uid"));
                startActivity(intent);
                break;
            case R.id.mIvmain_jia:
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
                break;
        }
    }

    @Override
    public void onPageChange(int index) {

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
    private void getcarnum(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("uid",aCache.getAsString("uid"));
            HttpUtils.post(context, Common.Carnum, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    Log.e(TAG, "onSuccess: "+text );
                    try {
                        JSONObject json=new JSONObject(text);
                        if (json.getInt("status")==1){
                            mTvshangchengCarnum.setText(json.getString("data"));
                            if (mTvshangchengCarnum.length()>2){
                                mTvshangchengCarnum.setTextSize(9);
                            }
                            if (mTvshangchengCarnum.length()>3){
                                mTvshangchengCarnum.setTextSize(7);
                                mTvshangchengCarnum.setText("999+");
                            }
                        }else {

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
