package tongxun.zhy.dz.com.tongxun.shangcheng.adapter;

import android.content.Context;
import android.graphics.Paint;
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
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.ShangpinBean;

public class ChanpinShangchengAdapter extends BaseAdapter {

    private Context context;
    private List<ShangpinBean
            .DataBean> list;
    private ViewHolder viewHolder;

    public ChanpinShangchengAdapter(Context context, List<ShangpinBean.DataBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.chanpin_sy_adapter, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTvchanpinsyadaYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        //  setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰

        viewHolder.mTvshopadaName.setText(list.get(position).getName());
        viewHolder.mTvchanpinsyadaYuanjia.setText(list.get(position).getOld_price());
        viewHolder.mTvshopadaMoney.setText(list.get(position).getPrice());
        Glide.with(context).load(Common.ImgUrl+list.get(position).getPicture()).into(viewHolder.mIvshopadaImg);
        Glide.with(context).load(Common.ImgUrl+list.get(position).getPicture()).error(R.drawable.jiazaizong).into(viewHolder.mIvshopadaImg);
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.mIvshopada_img)
        ImageView mIvshopadaImg;
        @BindView(R.id.mTvshopada_name)
        TextView mTvshopadaName;
        @BindView(R.id.mTvshopada_jianjie)
        TextView mTvshopadaJianjie;
        @BindView(R.id.mTvshopada_money)
        TextView mTvshopadaMoney;
        @BindView(R.id.mTvchanpinsyada_yuanjia)
        TextView mTvchanpinsyadaYuanjia;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
