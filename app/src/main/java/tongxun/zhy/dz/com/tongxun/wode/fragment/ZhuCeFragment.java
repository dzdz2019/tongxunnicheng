package tongxun.zhy.dz.com.tongxun.wode.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.base.WebActivity;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.tools.ACache;
import tongxun.zhy.dz.com.tongxun.tools.HttpUtils;
import tongxun.zhy.dz.com.tongxun.tools.LoadingDialog;
import tongxun.zhy.dz.com.tongxun.tools.MD5Util;
import tongxun.zhy.dz.com.tongxun.tools.TextCallBack;
import tongxun.zhy.dz.com.tongxun.wode.activity.LoginActivity;

import static com.dhc.gallery.components.AnimatedFileDrawable.runOnUiThread;

public class ZhuCeFragment extends Fragment {
    @BindView(R.id.mEvzhuce_shoujihao)
    EditText mEvzhuceShoujihao;
    @BindView(R.id.mEvzhuce_mima)
    EditText mEvzhuceMima;
    @BindView(R.id.mEvzhuce_yanzhengma)
    EditText mEvzhuceYanzhengma;
    @BindView(R.id.mTvzhuce_yanzhengma)
    TextView mTvzhuceYanzhengma;
    @BindView(R.id.mLnzhuce_zhuce)
    RelativeLayout mLnzhuceZhuce;
    @BindView(R.id.mTvzhuce_zhucexieyi)
    TextView mTvzhuceZhucexieyi;
    Unbinder unbinder;
    @BindView(R.id.mEvzhuce_mimas)
    EditText mEvzhuceMimas;
    private View view;
    private LoadingDialog loadingDialog;
    private ACache aCache;
    private Context context;
    private TimeCount time;
    private Intent intent;
    // 弹出框
    private ProgressDialog mDialog;
    private String TAG = "zhuce";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    time = new TimeCount(30000, 1000);
                    time.start();// 开始计时
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.zhu_ce_fragment, container, false);
        }
        context = getContext();
        aCache = ACache.get(context);
        loadingDialog = new LoadingDialog(context);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void zhuce() {
        loadingDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("telephone", mEvzhuceShoujihao.getText().toString());
            jsonObject.put("code", mEvzhuceYanzhengma.getText().toString());
            jsonObject.put("password", MD5Util.getMD5Str(mEvzhuceMima.getText().toString()));
            jsonObject.put("passwords", mEvzhuceMima.getText().toString());
            Log.e(TAG, "zhuce: json" + jsonObject);
            HttpUtils.post(context, Common.ZhuCe, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    loadingDialog.dismiss();
                    try {
                        JSONObject json = new JSONObject(text);
                        if (json.getString("status").equals("1")) {
//                            aCache.put("uid",json.getJSONObject("data").getString("id"));
//
//                            aCache.put("uimg", json.getJSONObject("data").getString("img"));
//                            aCache.put("uname", json.getJSONObject("data").getString("nickname"));
//                            aCache.put("uphone", json.getJSONObject("data").getString("telephone"));
//                            time.onFinish();
//
//                           // Log.e("uidddd",json.getJSONObject("data").getString("id"));
//                          //  Toast.makeText(context,json.getJSONObject("data").getString("id") , Toast.LENGTH_SHORT).show();
//                            getActivity().finish();
                            LoginActivity loginActivity = (LoginActivity) getActivity();
                            loginActivity.setdenglufr();
                        }
                        Toast.makeText(context, json.getString("msg"), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.mLnzhuce_zhuce, R.id.mTvzhuce_zhucexieyi, R.id.mTvzhuce_yanzhengma})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mLnzhuce_zhuce:
                if (mEvzhuceShoujihao.length() < 11) {
                    Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEvzhuceMima.length() < 6) {
                    Toast.makeText(context, "请按照提示输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEvzhuceMima.getText().toString().equals(mEvzhuceMimas.getText().toString())) {

                } else {
                    Toast.makeText(context, "确认密码和密码不同请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEvzhuceYanzhengma.getText().equals("")) {
                    Toast.makeText(context, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
//                signUp();
                zhuce();
                break;
            case R.id.mTvzhuce_zhucexieyi:
                intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", Common.UserXieyi);
                intent.putExtra("title", "用户协议");
                startActivity(intent);
                break;
            case R.id.mTvzhuce_yanzhengma:
                if (mEvzhuceShoujihao.length() < 11) {
                    Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                getcode();
                break;
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            mTvzhuceYanzhengma.setClickable(false);
            mTvzhuceYanzhengma.setText(millisUntilFinished / 1000 + "秒");

        }

        @Override
        public void onFinish() {
            mTvzhuceYanzhengma.setText("重新获取");
            mTvzhuceYanzhengma.setClickable(true);
        }
    }

    private void getcode() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("telephone", mEvzhuceShoujihao.getText().toString());
            HttpUtils.post(context, Common.ZhuCeCode, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    try {
                        Message msg = new Message();
                        JSONObject jsonObject1 = new JSONObject(text);
                        if (jsonObject1.getInt("status") == 1) {
                            msg.what = 1;
                        } else {
                            Toast.makeText(context, jsonObject1.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        handler.sendMessage(msg);
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

//
//    /**
//     * 注册方法
//     */
//    private void signUp() {
//        // 注册是耗时过程，所以要显示一个dialog来提示下用户
//        mDialog = new ProgressDialog(getActivity());
//        mDialog.setMessage("注册中，请稍后...");
//        mDialog.show();
//
//        new Thread(new Runnable() {
//            @Override public void run() {
//                try {
//                    String username = mEvzhuceShoujihao.getText().toString().trim();
//                    String password =MD5Util.getMD5Str( mEvzhuceMima.getText().toString().trim());
//                    EMClient.getInstance().createAccount(username, password);
//                    runOnUiThread(new Runnable() {
//                        @Override public void run() {
//                            if (!getActivity().isFinishing()) {
//                                mDialog.dismiss();
//                            }
//                            Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_LONG).show();
//                            //aCache.put("huanxinpass",MD5Util.getMD5Str( mEvzhuceMima.getText().toString().trim()));
//
//                        }
//                    });
//                } catch (final HyphenateException e) {
//                    e.printStackTrace();
//                    runOnUiThread(new Runnable() {
//                        @Override public void run() {
//                            if (!getActivity().isFinishing()) {
//                                mDialog.dismiss();
//                            }
//                            /**
//                             * 关于错误码可以参考官方api详细说明
//                             * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
//                             */
//                            int errorCode = e.getErrorCode();
//                            String message = e.getMessage();
//                            Log.d("lzan13",
//                                    String.format("sign up - errorCode:%d, errorMsg:%s", errorCode,
//                                            e.getMessage()));
//                            switch (errorCode) {
//                                // 网络错误
//                                case EMError.NETWORK_ERROR:
//                                    Toast.makeText(getActivity(),
//                                            "网络错误 code: " + errorCode + ", message:" + message,
//                                            Toast.LENGTH_LONG).show();
//                                    break;
//                                // 用户已存在
//                                case EMError.USER_ALREADY_EXIST:
//                                    Toast.makeText(getActivity(),
//                                            "用户已存在 code: " + errorCode + ", message:" + message,
//                                            Toast.LENGTH_LONG).show();
//                                    break;
//                                // 参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册
//                                case EMError.USER_ILLEGAL_ARGUMENT:
//                                    Toast.makeText(getActivity(),
//                                            "参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册 code: "
//                                                    + errorCode
//                                                    + ", message:"
//                                                    + message, Toast.LENGTH_LONG).show();
//                                    break;
//                                // 服务器未知错误
//                                case EMError.SERVER_UNKNOWN_ERROR:
//                                    Toast.makeText(getActivity(),
//                                            "服务器未知错误 code: " + errorCode + ", message:" + message,
//                                            Toast.LENGTH_LONG).show();
//                                    break;
//                                case EMError.USER_REG_FAILED:
//                                    Toast.makeText(getActivity(),
//                                            "账户注册失败 code: " + errorCode + ", message:" + message,
//                                            Toast.LENGTH_LONG).show();
//                                    break;
//                                default:
//                                    Toast.makeText(getActivity(),
//                                            "ml_sign_up_failed code: " + errorCode + ", message:" + message,
//                                            Toast.LENGTH_LONG).show();
//                                    break;
//                            }
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

}
