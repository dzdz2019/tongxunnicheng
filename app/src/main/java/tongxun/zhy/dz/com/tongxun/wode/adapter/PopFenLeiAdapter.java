package tongxun.zhy.dz.com.tongxun.wode.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.liaotian.LiaoTianFragment;
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.FenLeiBean;
import tongxun.zhy.dz.com.tongxun.utils.Pop;

public class PopFenLeiAdapter extends BaseAdapter {
    private List<FenLeiBean.DataBean> list;
    private Context context;
    private ViewHolder viewHolder;

    public PopFenLeiAdapter(List<FenLeiBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
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
            view = LayoutInflater.from(context).inflate(R.layout.pop_fen_lei_adapter, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTvpopoFenlei.setText(list.get(i).getName());
        return view;
    }

    static
    class ViewHolder {
        @BindView(R.id.mTvpopo_fenlei)
        TextView mTvpopoFenlei;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
