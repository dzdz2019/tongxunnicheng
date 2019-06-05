package tongxun.zhy.dz.com.tongxun.wode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.net.Common;

public class ImgAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private ViewHolder viewHolder;
    private int ps;

    public ImgAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void bian(int ps) {
        this.ps = ps;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.img_adapter, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(context).load(Common.ImgUrl + list.get(i)).into(viewHolder.mIvimgImg);
        if (ps == i) {
            viewHolder.mIvimgFengmianxuan.setImageResource(R.mipmap.xuanzhong);
        } else {
            viewHolder.mIvimgFengmianxuan.setImageResource(R.mipmap.weixuanzhong);
        }
        return view;
    }

    static
    class ViewHolder {
        @BindView(R.id.mIvimg_img)
        ImageView mIvimgImg;
        @BindView(R.id.mIvimg_fengmianxuan)
        ImageView mIvimgFengmianxuan;
        @BindView(R.id.mLnimg_fengmianxuan)
        LinearLayout mLnimgFengmianxuan;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
