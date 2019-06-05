package tongxun.zhy.dz.com.tongxun.liaotian.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class LiaotianViewPagerAdapter extends FragmentPagerAdapter {

    private List<String> mTitles;
    private List<Fragment> mFragments;
    private Context context;
    private TextView tv;


    public LiaotianViewPagerAdapter(Context context, FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.context = context;
     //   this.mTitles = mTitles;
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mTitles.get(position);
//    }
}