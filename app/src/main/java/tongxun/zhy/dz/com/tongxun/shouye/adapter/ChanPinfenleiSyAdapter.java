package tongxun.zhy.dz.com.tongxun.shouye.adapter;

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
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.FenLeiBean;

public class ChanPinfenleiSyAdapter extends BaseAdapter {
    private Context context;
    private List<FenLeiBean.ShouyeBean> list;
    private ViewHolder viewHolder;
    private int ps;

    public ChanPinfenleiSyAdapter(Context context, List<FenLeiBean.ShouyeBean> list) {
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
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chan_pinfenlei_sy_adapter, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTvfenleiName.setText(list.get(position).getName());
        if (ps == position) {
            viewHolder.mTvfenleiName.setTextColor(convertView.getResources().getColor(R.color.zhuse));
            viewHolder.mVfenleiName.setBackgroundColor(convertView.getResources().getColor(R.color.zhuse));
        }else {
            viewHolder.mTvfenleiName.setTextColor(convertView.getResources().getColor(R.color.c999999));
            viewHolder.mVfenleiName.setBackgroundColor(convertView.getResources().getColor(R.color.white));
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.mTvfenlei_name)
        TextView mTvfenleiName;
        @BindView(R.id.mVfenlei_name)
        View mVfenleiName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
