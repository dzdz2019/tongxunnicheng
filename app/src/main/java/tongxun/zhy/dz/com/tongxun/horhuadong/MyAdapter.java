package tongxun.zhy.dz.com.tongxun.horhuadong;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tongxun.zhy.dz.com.tongxun.R;


/**
 * Created by zhuguohui on 2016/11/8.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<String> data;
    private Context context;
//    private ACache aCache;
//    private DaohangBean.DataEntity dataEntity;
    private Intent intent;

    public MyAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_myadapters, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        final int index = position;

    }

    @Override
    public int getItemCount() {
        return null == data ? 0 : data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView tupian;
        private ImageView hot;
        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }
    }

