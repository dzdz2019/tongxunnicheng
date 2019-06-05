package tongxun.zhy.dz.com.tongxun.shouye.adapter;

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
import tongxun.zhy.dz.com.tongxun.shouye.bean.ShouyeShipingBean;

public class CuxiaoShouyeAdapter extends BaseAdapter {

    private Context context;
    private List<ShouyeShipingBean.DataBean> list;
    private ViewHolder viewHolder;

    public CuxiaoShouyeAdapter(Context context, List<ShouyeShipingBean.DataBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.cuxiao_shouye_adapter, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(Common.ImgUrl+list.get(position).getImg()).into(viewHolder.mIvcuxiaoadaImg);
        viewHolder.mTvcuxiaoadaName.setText(list.get(position).getTitle());
        viewHolder.mTvcuxiaoadaJianjie.setText(list.get(position).getIntroduce());
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.mIvcuxiaoada_img)
        ImageView mIvcuxiaoadaImg;
        @BindView(R.id.mTvcuxiaoada_name)
        TextView mTvcuxiaoadaName;
        @BindView(R.id.mTvcuxiaoada_jianjie)
        TextView mTvcuxiaoadaJianjie;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
