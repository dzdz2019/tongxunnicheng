package tongxun.zhy.dz.com.tongxun;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.mob.MobApplication;

import tongxun.zhy.dz.com.tongxun.liaotian.HxEaseuiHelper;

import static com.dhc.gallery.utils.Gallery.applicationContext;

/**
 * Created by Administrator on 2017/3/10.
 */

public class MyApplication extends MobApplication {
    private static Context context;

    private static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        instance=this;
        MultiDex.install(this);
        huanxin();
        initHuanXin();
    }

    public static Context getContext() {
        return context;
    }

    private void huanxin() {
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

        EaseUI.getInstance().init(context, options);
    }


    public static MyApplication getInstance() {
        return instance;
    }

    private void initHuanXin(){

        HxEaseuiHelper.getInstance().init(this.getApplicationContext());
        //设置全局监听
       // setGlobalListeners();
    }

}
