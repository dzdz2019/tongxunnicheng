package tongxun.zhy.dz.com.tongxun.liaotian.fragment;

import android.content.Context;
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
import android.widget.ListView;
import android.widget.Toast;

import com.mbg.library.RefreshRelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.liaotian.adapter.HuiHuaJiLuAdapter;
import tongxun.zhy.dz.com.tongxun.liaotian.bean.HuihuaBean;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.tools.ACache;
import tongxun.zhy.dz.com.tongxun.tools.GsonUtils;
import tongxun.zhy.dz.com.tongxun.tools.HttpUtils;
import tongxun.zhy.dz.com.tongxun.tools.LoadingDialog;
import tongxun.zhy.dz.com.tongxun.tools.TextCallBack;

public class HuiHuanJiLuFragment extends Fragment {
    @BindView(R.id.mLvhuihuajilu_listview)
    ListView mLvhuihuajiluListview;
    Unbinder unbinder;
    @BindView(R.id.mRrhuihuajilu_listview)
    RefreshRelativeLayout mRrhuihuajiluListview;
    private View view;
    private Context context;
    private List<HuihuaBean.DataBean> list;
    private HuiHuaJiLuAdapter adapter;
    private LoadingDialog loadingDialog;
    private ACache aCache;
    private HuihuaBean huihuaBean;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mRrhuihuajiluListview.positiveRefreshComplete();
                    list.clear();
                    if (huihuaBean.getData().size() > 0) {
                        for (int i = 0; i < huihuaBean.getData().size(); i++) {
                            list.add(huihuaBean.getData().get(i));
                        }
                        setadapter();
                    }
                    break;
                case 2:
                    mRrhuihuajiluListview.positiveRefreshComplete();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.hui_huan_ji_lu_fragment, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        context = getContext();
        list = new ArrayList<>();
        loadingDialog = new LoadingDialog(context);
        aCache = ACache.get(context);
        getList();

    }

    private void setadapter() {
        if (adapter == null) {
            adapter = new HuiHuaJiLuAdapter(context, list);
            mLvhuihuajiluListview.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void getList() {
        loadingDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", aCache.getAsString("uid"));
            jsonObject.put("type", 1 + "");
            Log.e("聊天json", jsonObject + "");
            HttpUtils.post(context, Common.IMList, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    loadingDialog.dismiss();
                    Log.e("聊天text", text);
                    huihuaBean = GsonUtils.JsonToBean(text, HuihuaBean.class);
                    Message msg = new Message();
                    if (huihuaBean.getStatus() == 1) {
                        msg.what = 1;
                    } else {
                        msg.what = 2;
                    }
                    handler.sendMessage(msg);
                    Toast.makeText(context, huihuaBean.getMsg(), Toast.LENGTH_SHORT).show();

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
