package tongxun.zhy.dz.com.tongxun.liaotian.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.liaotian.bean.HuihuaBean;

public class HuiHuaJiLuAdapter extends BaseAdapter {
    private Context context;
    private List<HuihuaBean.DataBean> list;

    public HuiHuaJiLuAdapter(Context context, List<HuihuaBean.DataBean> list) {
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
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.hui_hua_ji_lu_adapter,null);
        }
        return convertView;
    }
}
