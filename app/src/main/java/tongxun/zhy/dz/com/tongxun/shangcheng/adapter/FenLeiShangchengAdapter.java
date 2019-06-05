package tongxun.zhy.dz.com.tongxun.shangcheng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.FenLeiBean;

public class FenLeiShangchengAdapter extends BaseAdapter {
    private Context context;
    private List<FenLeiBean.DataBean> list;
    private ViewHolder viewHolder;

    public FenLeiShangchengAdapter(Context context, List<FenLeiBean.DataBean> list) {
        this.context = context;
        this.list = list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.fen_lei_shouye_adapter, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTvsyfladaName.setText(list.get(position).getName());
        if (list.get(position).getId().equals("0")) {
            viewHolder.mIvsyfladaImg.setImageResource(R.drawable.quanbufenlei);
        } else {
            Glide.with(context).load(Common.ImgUrl + list.get(position).getImg()).into(viewHolder.mIvsyfladaImg);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.mIvsyflada_img)
        ImageView mIvsyfladaImg;
        @BindView(R.id.mTvsyflada_name)
        TextView mTvsyfladaName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
