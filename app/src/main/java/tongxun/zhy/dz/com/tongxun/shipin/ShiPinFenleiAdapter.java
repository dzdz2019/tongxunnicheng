package tongxun.zhy.dz.com.tongxun.shipin;

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
import tongxun.zhy.dz.com.tongxun.shipin.bean.ShipinfenleiBean;

public class ShiPinFenleiAdapter extends BaseAdapter {
    private Context context;
    private List<ShipinfenleiBean.DataBean> list;
    private ViewHolder viewHolder;
    private int pos;

    public ShiPinFenleiAdapter(Context context, List<ShipinfenleiBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }
public void bian(int pos){
        this.pos=pos;
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
            view = LayoutInflater.from(context).inflate(R.layout.shi_pin_fenlei_adapter, null);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.mTvSpFladaName.setText(list.get(i).getName());
        if (pos == i) {
            viewHolder.mTvSpFladaName.setBackgroundResource(R.drawable.yjzhusequand);
            viewHolder.mTvSpFladaName.setTextColor(view.getResources().getColor(R.color.white));
        }else {
            viewHolder.mTvSpFladaName.setBackgroundResource(R.color.white);
            viewHolder.mTvSpFladaName.setTextColor(view.getResources().getColor(R.color.c333333));
        }
        return view;
    }

    static
    class ViewHolder {
        @BindView(R.id.mTvSpFlada_name)
        TextView mTvSpFladaName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
