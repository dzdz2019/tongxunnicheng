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

public class JiShuShouyeAdapter extends BaseAdapter {
    private Context context;
    private List<ShouyeShipingBean.DataBean> list;
    private ViewHolder viewHolder;

    public JiShuShouyeAdapter(Context context, List<ShouyeShipingBean.DataBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.ji_shu_shouye_adapter, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTvsyspadaCishu.setText(list.get(position).getFrequency() + "次播放");
        viewHolder.mTvsyspadaName
                .setText(list.get(position).getTitle());
        viewHolder.mTvsyspadaZuozhe.setText(list.get(position).getAuthor());
        if (list.get(position).getIs_recommend().equals("1")) {
            viewHolder.mTvsyspadaBiaoqian.setVisibility(View.VISIBLE);
            viewHolder.mTvsyspadaBiaoqian.setText("推荐");
        } else {
            viewHolder.mTvsyspadaBiaoqian.setVisibility(View.GONE);

        }
        Glide.with(context).load(Common.ImgUrl + list.get(position).getImg()).into(viewHolder.mIvsyspadaImg);
        viewHolder.mTvsyspadaTime.setText(list.get(position).getTime());
        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.mIvsyspada_img)
        ImageView mIvsyspadaImg;
        @BindView(R.id.mTvsyspada_biaoqian)
        TextView mTvsyspadaBiaoqian;
        @BindView(R.id.mTvsyspada_time)
        TextView mTvsyspadaTime;
        @BindView(R.id.mTvsyspada_cishu)
        TextView mTvsyspadaCishu;
        @BindView(R.id.mTvsyspada_name)
        TextView mTvsyspadaName;
        @BindView(R.id.mTvsyspada_zuozhe)
        TextView mTvsyspadaZuozhe;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
