package tongxun.zhy.dz.com.tongxun.wode.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tongxun.zhy.dz.com.tongxun.MainActivity;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.cache.UserCacheManager;
import tongxun.zhy.dz.com.tongxun.liaotian.utils.APPConfig;
import tongxun.zhy.dz.com.tongxun.liaotian.utils.SharedPreferencesUtils;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.tools.ACache;
import tongxun.zhy.dz.com.tongxun.tools.GsonUtils;
import tongxun.zhy.dz.com.tongxun.tools.HttpUtils;
import tongxun.zhy.dz.com.tongxun.tools.LoadingDialog;
import tongxun.zhy.dz.com.tongxun.tools.MD5Util;
import tongxun.zhy.dz.com.tongxun.tools.TextCallBack;
import tongxun.zhy.dz.com.tongxun.wode.activity.LoginActivity;
import tongxun.zhy.dz.com.tongxun.wode.activity.WangjiMiMaActivity;
import tongxun.zhy.dz.com.tongxun.wode.bean.GerenxinxiBean;
import tongxun.zhy.dz.com.tongxun.wode.bean.LoginBean;

import static com.dhc.gallery.components.AnimatedFileDrawable.runOnUiThread;

public class DengluFragment extends Fragment {
    @BindView(R.id.mTvdenglu_queding)
    RelativeLayout mTvdengluQueding;
    Unbinder unbinder;
    @BindView(R.id.mTvdenglu_wangjimima)
    TextView mTvdengluWangjimima;
    @BindView(R.id.mEvdenglu_shoujihao)
    EditText mEvdengluShoujihao;
    @BindView(R.id.mEvdenglu_mima)
    EditText mEvdengluMima;
    private View view;
    private Context context;
    private LoadingDialog loadingDialog;
    private ACache aCache;
    private LoginBean loginBean;

    private GerenxinxiBean gerenxinxiBean;
    // 弹出框
    private ProgressDialog mDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //                    Log.e("登陆text",loginBean.getData().getId());
                    aCache.put("uid", loginBean.getData().getId());
                    aCache.put("uimg", Common.ImgUrl + loginBean.getData().getImg());
                    aCache.put("uname", loginBean.getData().getUsername());
                    aCache.put("uphone", loginBean.getData().getTelephone());
                    SharedPreferencesUtils.setParam(context, APPConfig.USER_NAME, aCache.getAsString("uname"));
                    SharedPreferencesUtils.setParam(context, APPConfig.PASS_WORD, mEvdengluMima.getText().toString());
                    getActivity().finish();
                   getnickname();
                    break;
                case 2:
                    //  Toast.makeText(context, "123333", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    aCache.put("shiming", gerenxinxiBean.getData().getShiming());//1已实名
                    break;
                case 4:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.activity_denglu_fragment, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        context = getActivity();
        aCache = ACache.get(context);
        loadingDialog = new LoadingDialog(context);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.mTvdenglu_queding, R.id.mTvdenglu_wangjimima})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mTvdenglu_queding:
//                Log.e("1111111111","11111");
                //  getActivity().finish();
                // login();
                signIn();
                break;
            case R.id.mTvdenglu_wangjimima:
                Intent intent = new Intent(getActivity(), WangjiMiMaActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void login() {
        loadingDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("telephone", mEvdengluShoujihao.getText().toString());
            jsonObject.put("password", MD5Util.getMD5Str(mEvdengluMima.getText().toString()));
            HttpUtils.post(context, Common.login, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    loadingDialog.dismiss();
                    loginBean = GsonUtils.JsonToBean(text, LoginBean.class);
                    Toast.makeText(context, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Log.e("登陆text", text);
                    Message msg = new Message();
                    if (loginBean.getStatus().equals("1")) {
                        msg.what = 1;
                    } else {
                        msg.what = 2;
                    }
                    handler.sendMessage(msg);
                }

                @Override
                protected void onFailure(ResponseException e) {
                    loadingDialog.dismiss();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录方法
     */
    private void signIn() {
        mDialog = new ProgressDialog(context);
        mDialog.setMessage("正在登陆，请稍后...");
      mDialog.show();

      //  mDialog.setCancelable(true);//是否可以被取消

//        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//圆环风格
//        mDialog.show();
//
//       mDialog.setContentView(R.layout.view_progress);//自定义布局
//TextView title;
//title=mDialog.findViewById(R.id.dolig_title);
//title.setText("hahahhah");



        String username = mEvdengluShoujihao.getText().toString().trim();
        String password = mEvdengluMima.getText().toString().trim();
        Log.e("mima", password);
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(context, "用户名和密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        EMClient.getInstance().login(username, password, new EMCallBack() {
            /**
             * 登陆成功的回调
             */
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                        // 加载所有会话到内存
                        //  EMClient.getInstance().chatManager().loadAllConversations();
                        // 加载所有群组到内存，如果使用了群组的话
                        // EMClient.getInstance().groupManager().loadAllGroups();
                        // 登录成功跳转界面
                        //   Toast.makeText(context, "11111", Toast.LENGTH_SHORT).show();
                        login();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        EMClient.getInstance().groupManager().loadAllGroups();

                    }
                });
            }

            /**
             * 登陆错误的回调
             * @param i
             * @param s
             */
            @Override
            public void onError(final int i, final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                        Log.d("lzan13", "登录失败 Error code:" + i + ", message:" + s);
                        /**
                         * 关于错误码可以参考官方api详细说明
                         * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                         */
                        switch (i) {
                            // 网络异常 2
                            case EMError.NETWORK_ERROR:
                                Toast.makeText(context,
                                        "网络错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无效的用户名 101
                            case EMError.INVALID_USER_NAME:
                                Toast.makeText(context,
                                        "无效的用户名 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无效的密码 102
                            case EMError.INVALID_PASSWORD:
                                Toast.makeText(context,
                                        "无效的密码 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 用户认证失败，用户名或密码错误 202
                            case EMError.USER_AUTHENTICATION_FAILED:
                                Toast.makeText(context,
                                        "用户认证失败，用户名或密码错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG)
                                        .show();
                                break;
                            // 用户不存在 204
                            case EMError.USER_NOT_FOUND:
                                Toast.makeText(context,
                                        "用户不存在 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无法访问到服务器 300
                            case EMError.SERVER_NOT_REACHABLE:
                                Toast.makeText(context,
                                        "无法访问到服务器 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 等待服务器响应超时 301
                            case EMError.SERVER_TIMEOUT:
                                Toast.makeText(context,
                                        "等待服务器响应超时 code: " + i + ", message:" + s, Toast.LENGTH_LONG)
                                        .show();
                                break;
                            // 服务器繁忙 302
                            case EMError.SERVER_BUSY:
                                Toast.makeText(context,
                                        "服务器繁忙 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 未知 Server 异常 303 一般断网会出现这个错误
                            case EMError.SERVER_UNKNOWN_ERROR:
                                Toast.makeText(context,
                                        "未知的服务器异常 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(context,
                                        "ml_sign_in_failed code: " + i + ", message:" + s,
                                        Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    private void getnickname() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", aCache.getAsString("uid"));
            HttpUtils.post(context, Common.GetNIckname, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {

                    gerenxinxiBean = GsonUtils.JsonToBean(text, GerenxinxiBean.class);
                    Message msg = new Message();
                    if (gerenxinxiBean.getStatus() == 1) {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
