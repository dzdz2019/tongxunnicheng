package tongxun.zhy.dz.com.tongxun.liaotian.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.mbg.library.IRefreshListener;
import com.mbg.library.RefreshRelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.liaotian.activity.ECChatActivity;
import tongxun.zhy.dz.com.tongxun.liaotian.activity.MyChatActivity;
import tongxun.zhy.dz.com.tongxun.liaotian.adapter.TongXunLuAdapter;
import tongxun.zhy.dz.com.tongxun.liaotian.bean.HaoyoulistBean;
import tongxun.zhy.dz.com.tongxun.liaotian.utils.APPConfig;
import tongxun.zhy.dz.com.tongxun.liaotian.utils.SharedPreferencesUtils;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.tools.ACache;
import tongxun.zhy.dz.com.tongxun.tools.GsonUtils;
import tongxun.zhy.dz.com.tongxun.tools.HttpUtils;
import tongxun.zhy.dz.com.tongxun.tools.LoadingDialog;
import tongxun.zhy.dz.com.tongxun.tools.TextCallBack;

public class TongXunLuFragment extends Fragment {
    private static final String TAG = "TongXunLuFragment";
    @BindView(R.id.mLvtongxunlu_listview)
    EaseConversationList mLvtongxunluListview;
    Unbinder unbinder;
    @BindView(R.id.mRrtongxunlu_shuaxin)
    RefreshRelativeLayout mRrtongxunluShuaxin;
    @BindView(R.id.query)
    EditText query;
    @BindView(R.id.search_clear)
    ImageButton searchClear;
    private View view;
    private Context context;
    private List<HaoyoulistBean.DataBean> list;
    private TongXunLuAdapter adapter;
    private LoadingDialog loadingDialog;
    private ACache aCache;
    private HaoyoulistBean haoyoulistBean;
    private List<HaoyoulistBean.DataBean>haoyoulist;
    protected InputMethodManager inputMethodManager;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mRrtongxunluShuaxin.positiveRefreshComplete();
                    list.clear();
                    HaoyoulistBean.DataBean data = new HaoyoulistBean.DataBean();
//                    for (int i = 0; i < 3; i++) {
//                        data.setId("a");
//                        list.add(data);
//                    }
                    if (haoyoulistBean.getData().size() > 0) {
                        for (int i = 0; i < haoyoulistBean.getData().size(); i++) {
                            list.add(haoyoulistBean.getData().get(i));
                        }
                        haoyoulist=list;
                        setadapter();
                    }
                    break;
                case 2:
                    mRrtongxunluShuaxin.positiveRefreshComplete();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.tong_xun_lu_fragment, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        initView();

        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        return view;
    }

    private void initView() {
        context = getContext();
        list = new ArrayList<>();
        haoyoulist=new ArrayList<>();
        loadingDialog = new LoadingDialog(context);
        aCache = ACache.get(context);
        getList(2);
        mRrtongxunluShuaxin.addRefreshListener(new IRefreshListener() {
            @Override
            public void onPositiveRefresh() {
                getList(2);
            }

            @Override
            public void onNegativeRefresh() {

            }
        });

        query.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                Log.e(TAG, "afterTextChanged: "+"213" );
                if (s.length() > 0) {
                    List<HaoyoulistBean.DataBean> duanzanlist = new ArrayList<>();
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).getUsername().indexOf(query.getText().toString())!=-1){
                            duanzanlist.add(list.get(i));
                        }
                    }
                    haoyoulist=duanzanlist;
                    for (int i = 0; i < haoyoulist.size(); i++) {

                        Log.e(TAG, "afterTextChanged: "+haoyoulist.get(i).getPhone() );
                    }
                }else {
                    haoyoulist=list;
                }
                setadapter();
            }
        });
        searchClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mLvtongxunluListview.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });
    }

    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void setadapter() {
        Log.e(TAG, "setadapter: " + list.size());
     //  if (adapter == null) {
            adapter = new TongXunLuAdapter(context, haoyoulist);
            mLvtongxunluListview.setAdapter(adapter);
    //   } else {
     //      adapter.notifyDataSetChanged();
     //   }
        mLvtongxunluListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 获取我们发起聊天的者的username
                String chatId = list.get(i).getPhone();
                if (!TextUtils.isEmpty(chatId)) {
                    // 获取当前登录用户的 username
                    String currUsername = EMClient.getInstance().getCurrentUser();
                    if (chatId.equals(currUsername)) {
                        Toast.makeText(context, "不能和自己聊天", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 跳转到聊天界面，开始聊天

                    //设置要发送出去的昵称
                    SharedPreferencesUtils.setParam(context, APPConfig.USER_NAME, aCache.getAsString("uname"));
                    //设置要发送出去的头像
                    SharedPreferencesUtils.setParam(context,APPConfig.USER_HEAD_IMG, aCache.getAsString("uimg"));

                    Intent intent=new Intent(context, MyChatActivity.class);
                    //传入参数
                    Bundle args=new Bundle();
                    args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                    args.putString(EaseConstant.EXTRA_USER_ID,chatId);
                    intent.putExtra("conversation",args);
                    startActivity(intent);


                } else {
                    Toast.makeText(context, "Username 不能为空", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getList(int i) {
        loadingDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", aCache.getAsString("uid"));
            Log.e("聊天json", jsonObject + "");
            HttpUtils.post(context, Common.FriendList, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    loadingDialog.dismiss();
                    Log.e("聊天text", text);
                    haoyoulistBean = GsonUtils.JsonToBean(text, HaoyoulistBean.class);
                    Message msg = new Message();
                    if (haoyoulistBean.getStatus() == 1) {
                        msg.what = 1;
                    } else {
                        msg.what = 2;
                    }
                    handler.sendMessage(msg);
                    Toast.makeText(context, haoyoulistBean.getMsg(), Toast.LENGTH_SHORT).show();

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
