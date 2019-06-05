package tongxun.zhy.dz.com.tongxun.liaotian;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.exceptions.HyphenateException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.liaotian.activity.ECChatActivity;
import tongxun.zhy.dz.com.tongxun.liaotian.activity.MyChatActivity;
import tongxun.zhy.dz.com.tongxun.liaotian.adapter.LiaotianViewPagerAdapter;
import tongxun.zhy.dz.com.tongxun.liaotian.bean.HaoyoulistBean;
import tongxun.zhy.dz.com.tongxun.liaotian.fragment.HuiHuanJiLuFragment;
import tongxun.zhy.dz.com.tongxun.liaotian.fragment.TongXunLuFragment;
import tongxun.zhy.dz.com.tongxun.liaotian.utils.APPConfig;
import tongxun.zhy.dz.com.tongxun.liaotian.utils.SharedPreferencesUtils;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.tools.ACache;
import tongxun.zhy.dz.com.tongxun.tools.GetHeightUtil;
import tongxun.zhy.dz.com.tongxun.tools.GsonUtils;
import tongxun.zhy.dz.com.tongxun.tools.HttpUtils;
import tongxun.zhy.dz.com.tongxun.tools.LoadingDialog;
import tongxun.zhy.dz.com.tongxun.tools.TextCallBack;

import static com.dhc.gallery.components.AnimatedFileDrawable.runOnUiThread;

public class LiaoTianFragment extends Fragment {
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
    @BindView(R.id.mTvliaotian_huihua)
    TextView mTvliaotianHuihua;
    @BindView(R.id.mVliaotian_huihua)
    View mVliaotianHuihua;
    @BindView(R.id.mTvliaotian_tongxun)
    TextView mTvliaotianTongxun;
    @BindView(R.id.mVliaotian_tongxun)
    View mVliaotianTongxun;
    @BindView(R.id.mVpliaotian_viewpage)
    ViewPager mVpliaotianViewpage;
    private View view;
    private Context context;
    private List<Fragment> list;
    private LoadingDialog loadingDialog;
    private ACache aCache;
    private HaoyoulistBean haoyoulistBean;

    private EaseConversationListFragment easeConversationListFragment;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
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
            view = inflater.inflate(R.layout.liao_tian_fragment, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        initView();
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
        return view;
    }



    private void initView() {
        context = getContext();
        loadingDialog = new LoadingDialog(context);
        aCache = ACache.get(context);
        title.setText("聊天");
        title.setBackgroundResource(R.color.zhuse);
        title.setTextColor(getResources().getColor(R.color.white));

        titleqtop.getLayoutParams().height = GetHeightUtil.getztl(context);
        list = new ArrayList<>();


//    ConversationListFragment contactListFragment = new ConversationListFragment();
//需要设置联系人列表才能启动fragment

//        try {
//            contactListFragment.setContactsMap(EMClient.getInstance().contactManager().getAllContactsFromServer());
//        } catch (HyphenateException e) {
//            e.printStackTrace();
//        }
//设置item点击事件
//        contactListFragment.setConversationListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {
//
//            @Override
//            public void onListItemClicked(EaseUser user) {
//                startActivity(new Intent(getContext(), ECChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername()));
//            }
//        });
      // EaseConversationListFragment conversationListFragment = new EaseConversationListFragment();

        easeConversationListFragment=new EaseConversationListFragment();
        easeConversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                //设置要发送出去的昵称
                SharedPreferencesUtils.setParam(context, APPConfig.USER_NAME, aCache.getAsString("uname"));
                //设置要发送出去的头像
                SharedPreferencesUtils.setParam(context,APPConfig.USER_HEAD_IMG, aCache.getAsString("uimg"));

                Intent intent=new Intent(getActivity(), MyChatActivity.class);
                //传入参数
                Bundle args=new Bundle();
                args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                args.putString(EaseConstant.EXTRA_USER_ID,conversation.conversationId());
                intent.putExtra("conversation",args);
                startActivity(intent);
            }
        });

//        list.add(new HuiHuanJiLuFragment());
//        list.add(new TongXunLuFragment());
      //  conversationListFragment
      list.add(easeConversationListFragment);//记录
      //  list.add(      conversationListFragment);//记录
        list.add(new TongXunLuFragment());//通讯录

        PagerAdapter adapter = new LiaotianViewPagerAdapter(context, getChildFragmentManager(), list);
        mVpliaotianViewpage.setAdapter(adapter);
        mVpliaotianViewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                // arg0是当前选中的页面的Position

                bian(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                //arg0 ==1的时表示正在滑动，arg0==2的时表示滑动完毕了，arg0==0的时表示什么都没做。

            }
        });

    }

    private void bian(int i) {
        switch (i) {
            case 0:
                mTvliaotianHuihua.setTextColor(getResources().getColor(R.color.zhuse));
                mVliaotianHuihua.setBackgroundColor(getResources().getColor(R.color.zhuse));
                mTvliaotianTongxun.setTextColor(getResources().getColor(R.color.c333333));
                mVliaotianTongxun.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 1:
                mTvliaotianHuihua.setTextColor(getResources().getColor(R.color.c333333));
                mVliaotianHuihua.setBackgroundColor(getResources().getColor(R.color.white));
                mTvliaotianTongxun.setTextColor(getResources().getColor(R.color.zhuse));
                mVliaotianTongxun.setBackgroundColor(getResources().getColor(R.color.zhuse));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.mTvliaotian_huihua, R.id.mTvliaotian_tongxun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mTvliaotian_huihua:
                bian(0);
                mVpliaotianViewpage.setCurrentItem(0);
                break;
            case R.id.mTvliaotian_tongxun:
                bian(1);
                mVpliaotianViewpage.setCurrentItem(1);
                break;
        }
    }
    EMMessageListener messageListener=new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            //接收到新的消息
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageRead(List<EMMessage> list) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {

        }
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                // refresh conversation list
                if (easeConversationListFragment != null) {
                    easeConversationListFragment.refresh();
                }
            }
        });
    }

}
