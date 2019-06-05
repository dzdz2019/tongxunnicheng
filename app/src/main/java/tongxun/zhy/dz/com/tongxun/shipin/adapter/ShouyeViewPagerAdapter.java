package tongxun.zhy.dz.com.tongxun.shipin.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TextView;

import java.util.List;

import tongxun.zhy.dz.com.tongxun.shipin.bean.ShipinfenleiBean;

/**
 * Created by Administrator on 2016/6/23.
 */
public class ShouyeViewPagerAdapter extends FragmentPagerAdapter {

    private List<ShipinfenleiBean.DataBean> mTitles;
    private List<Fragment> mFragments;
    private Context context;
    private TextView tv;


    public ShouyeViewPagerAdapter(Context context, FragmentManager fm, List<ShipinfenleiBean.DataBean> mTitles) {
        super(fm);
        this.context = context;
        this.mTitles = mTitles;
       this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).getName();
    }
}
