package tongxun.zhy.dz.com.tongxun.liaotian.adapter;

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
import tongxun.zhy.dz.com.tongxun.liaotian.bean.HaoyoulistBean;
import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.tools.CircleImageView;

public class TongXunLuAdapter extends BaseAdapter {
    private Context context;
    private String num = "0";
    private List<HaoyoulistBean.DataBean> list;
    private ViewHolder viewHolder;

    public TongXunLuAdapter(Context context, List<HaoyoulistBean.DataBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.hui_hua_ji_lu_adapter, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        if (position==0){
//            viewHolder.mIvliaotianadaImg.setImageResource(R.mipmap.yanzheng);
//            viewHolder.mTvliaotianadaName.setText("验证消息");
//            viewHolder.mTvliaotianadaNeirong.setVisibility(View.GONE);
//            viewHolder.mTvliaotianadanum.setText(num);
//            viewHolder.mTvliaotianadanum.setVisibility(View.VISIBLE);
//
//        }else if (position==1){
//            viewHolder.mIvliaotianadaImg.setImageResource(R.mipmap.tianjiahaoyou);
//            viewHolder.mTvliaotianadaName.setText("添加好友");
//
//            viewHolder.mTvliaotianadaNeirong.setVisibility(View.GONE);
//        }else if (position==2){
//            viewHolder.mIvliaotianadaImg.setImageResource(R.mipmap.heimingdan);
//            viewHolder.mTvliaotianadaName.setText("黑名单");
//            viewHolder.mTvliaotianadaNeirong.setVisibility(View.GONE);
//        }else {
        viewHolder.mTvliaotianadaName.setText(list.get(position).getUsername());
        Glide.with(context).load(Common.ImgUrl + list.get(position).getImg()).into(viewHolder.mIvliaotianadaImg);
        viewHolder.mTvliaotianadaNeirong.setVisibility(View.GONE);
        // }

        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.mIvliaotianada_img)
        CircleImageView mIvliaotianadaImg;
        @BindView(R.id.mTvliaotianada_name)
        TextView mTvliaotianadaName;
        @BindView(R.id.mTvliaotianada_neirong)
        TextView mTvliaotianadaNeirong;
        @BindView(R.id.mTvliaotianada_num)
        TextView mTvliaotianadaNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
