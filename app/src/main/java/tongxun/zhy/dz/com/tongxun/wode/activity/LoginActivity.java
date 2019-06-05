package tongxun.zhy.dz.com.tongxun.wode.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.tools.GetHeightUtil;
import tongxun.zhy.dz.com.tongxun.tools.ToumingUtil;
import tongxun.zhy.dz.com.tongxun.wode.fragment.DengluFragment;
import tongxun.zhy.dz.com.tongxun.wode.fragment.ZhuCeFragment;

public class LoginActivity extends FragmentActivity {
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
    @BindView(R.id.mTvlogin_denglu)
    TextView mTvloginDenglu;
    @BindView(R.id.mVlogin_denglu)
    View mVloginDenglu;
    @BindView(R.id.mLnlogin_denglu)
    LinearLayout mLnloginDenglu;
    @BindView(R.id.mTvlogin_zhuce)
    TextView mTvloginZhuce;
    @BindView(R.id.mVlogin_zhuce)
    View mVloginZhuce;
    @BindView(R.id.mLnlogin_zhuce)
    LinearLayout mLnloginZhuce;
    @BindView(R.id.mFllogin_fragment)
    FrameLayout mFlloginFragment;
    private Context context;
    private DengluFragment dengluFragment;
    private ZhuCeFragment zhuCeFragment;
    private FragmentManager manager;
    public LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context = this;
        loginActivity=new LoginActivity();
        initView();
    }

    private void initView() {
        title.setText("登陆");
        title.setTextColor(getResources().getColor(R.color.white));
        titleqtop.getLayoutParams().height = GetHeightUtil.getztl(this);
        ToumingUtil.touming(this);
        titleleftimg.setImageResource(R.mipmap.loginfanhui);
        titleleftimg.setVisibility(View.VISIBLE);
        setFragment(1);

    }

    @OnClick({R.id.mLnlogin_denglu, R.id.mLnlogin_zhuce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mLnlogin_denglu:
                setFragment(1);
                break;
            case R.id.mLnlogin_zhuce:
                setFragment(2);
                break;
        }
    }

    private void setFragment(int i) {
        manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        mTvloginDenglu.setTextColor(getResources().getColor(R.color.cFFD4D4D4));
        mVloginDenglu.setBackgroundResource(R.color.cFFD4D4D4);
        mTvloginZhuce.setTextColor(getResources().getColor(R.color.cFFD4D4D4));
        mVloginZhuce.setBackgroundResource(R.color.cFFD4D4D4);
        dianji(fragmentTransaction);
        switch (i) {
            case 1:
                mTvloginDenglu.setTextColor(getResources().getColor(R.color.zhuse));
                mVloginDenglu.setBackgroundResource(R.color.zhuse);
                if (dengluFragment == null) {
                    dengluFragment = new DengluFragment();
                    fragmentTransaction.add(R.id.mFllogin_fragment, dengluFragment);
                } else {
                    fragmentTransaction.show(dengluFragment);
                }
                break;
            case 2:

                mTvloginZhuce.setTextColor(getResources().getColor(R.color.zhuse));
                mVloginZhuce.setBackgroundResource(R.color.zhuse);
                if (zhuCeFragment == null) {
                    zhuCeFragment = new ZhuCeFragment();
                    fragmentTransaction.add(R.id.mFllogin_fragment, zhuCeFragment);
                } else {
                    fragmentTransaction.show(zhuCeFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    private void dianji(FragmentTransaction i) {
        if (dengluFragment != null) {
            i.hide(dengluFragment);
        }
        if (zhuCeFragment != null) {
            i.hide(zhuCeFragment);
        }
    }

    @OnClick(R.id.titleback)
    public void onViewClicked() {
        finish();
    }

    public void setdenglufr(){
        setFragment(1);
    }
}
