package tongxun.zhy.dz.com.tongxun.wode.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.base.BaseActivity;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.tools.ACache;
import tongxun.zhy.dz.com.tongxun.tools.GetHeightUtil;
import tongxun.zhy.dz.com.tongxun.tools.HttpUtils;
import tongxun.zhy.dz.com.tongxun.tools.LoadingDialog;
import tongxun.zhy.dz.com.tongxun.tools.MD5Util;
import tongxun.zhy.dz.com.tongxun.tools.TextCallBack;
import tongxun.zhy.dz.com.tongxun.tools.ToumingUtil;
import tongxun.zhy.dz.com.tongxun.wode.fragment.ZhuCeFragment;

public class WangjiMiMaActivity extends BaseActivity {
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
    @BindView(R.id.mEtwangji_shoujihao)
    EditText mEtwangjiShoujihao;
    @BindView(R.id.mEtwangji_yanzhengma)
    EditText mEtwangjiYanzhengma;
    @BindView(R.id.mTvwangji_yanzhengma)
    TextView mTvwangjiYanzhengma;
    @BindView(R.id.mEtwangji_xinmima)
    EditText mEtwangjiXinmima;
    @BindView(R.id.mEtwangji_querenxinmima)
    EditText mEtwangjiQuerenxinmima;
    @BindView(R.id.mTvwangji_queding)
    RelativeLayout mTvwangjiQueding;
    private Context context;
    private LoadingDialog loadingDialog;
    private ACache aCache;
    private TimeCount time;



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    time = new TimeCount(60000, 1000);
                    time.start();// 开始计时

                    break;
            }
        }
    };
    @Override
    protected void initView() {
        setContentView(R.layout.activity_wangji_mi_ma);
        ButterKnife.bind(this);
        context = this;
    }

    @Override
    protected void initData() {
        ToumingUtil.touming(this);
        titleqtop.getLayoutParams().height = GetHeightUtil.getztl(context);
        title.setTextColor(getResources().getColor(R.color.white));
        title.setText("忘记密码");
        titleleftimg.setVisibility(View.VISIBLE);
        loadingDialog=new LoadingDialog(context);
        aCache=ACache.get(context);
        titleleftimg.setImageResource(R.mipmap.loginfanhui);
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

    @OnClick(R.id.titleback)
    public void onViewClicked() {
        finish();
    }

    @OnClick({R.id.mTvwangji_yanzhengma, R.id.mTvwangji_queding})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mTvwangji_yanzhengma:
                if (mEtwangjiShoujihao.length() < 11) {
                    Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                getcode();
                break;
            case R.id.mTvwangji_queding:
                if (mEtwangjiShoujihao.getText().length() < 11) {
                    Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEtwangjiYanzhengma.getText().length() < 4) {
                    Toast.makeText(context, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEtwangjiXinmima.getText().length() <6) {
                    Toast.makeText(context, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEtwangjiQuerenxinmima.getText().length() < 4) {
                    Toast.makeText(context, "请检查确认密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                wangjimima();
                break;
        }
    }
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
    }

        @Override
        public void onTick(long millisUntilFinished) {
            mTvwangjiYanzhengma.setClickable(false);
            mTvwangjiYanzhengma.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            mTvwangjiYanzhengma.setText("重新获取");
            mTvwangjiYanzhengma.setClickable(true);
        }
    }

    private void getcode() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("telephone", mEtwangjiShoujihao.getText().toString());
            HttpUtils.post(context, Common.WangjiCode, jsonObject, new TextCallBack() {
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
    private void wangjimima(){
        loadingDialog.show();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("telephone",mEtwangjiShoujihao.getText().toString());
            jsonObject.put("code",mEtwangjiYanzhengma.getText().toString());
            jsonObject.put("password1", MD5Util.getMD5Str(mEtwangjiXinmima.getText().toString()));
            jsonObject.put("password2",MD5Util.getMD5Str(mEtwangjiQuerenxinmima.getText().toString()));
            jsonObject.put("passwords",mEtwangjiQuerenxinmima.getText().toString());
            Log.e("忘记密码","js" +jsonObject);
            HttpUtils.post(context, Common.WangJiMIma, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    loadingDialog.dismiss();
                    try {
                        JSONObject json=new JSONObject(text);
                        if (json.getString("status").equals("1")){
                            finish();
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
}
