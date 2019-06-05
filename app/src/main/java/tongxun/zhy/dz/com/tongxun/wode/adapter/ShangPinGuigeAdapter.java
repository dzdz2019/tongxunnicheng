package tongxun.zhy.dz.com.tongxun.wode.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.platform.comapi.map.E;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.UpdateshangpinBean;
import tongxun.zhy.dz.com.tongxun.wode.activity.AddShangpinActivity;
import tongxun.zhy.dz.com.tongxun.wode.bean.ShangpinGuigeBean;

public class ShangPinGuigeAdapter extends BaseAdapter {

    private AddShangpinActivity context;
    private List<UpdateshangpinBean.DataBean.SmallBean> list;
    private ViewHolder viewHolder;
    private Callback callback;
    private static final String TAG = "ShangPinGuigeAdapter";
    private EditText name,money,kucun;
    private ImageView del;

    public ShangPinGuigeAdapter(AddShangpinActivity context, List<UpdateshangpinBean.DataBean.SmallBean> list) {
        this.context = context;
        this.list = list;
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(context, R.layout.shang_pin_guige_adapter, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        money=view.findViewById(R.id.mIvshangpinguigeada_money);
        name=view.findViewById(R.id.mIvshangpinguigeada_name);
        kucun=view.findViewById(R.id.mIvshangpinguigeada_kucun);
        del=view.findViewById(R.id.mIvshangpinguigeada_del);
        money.setText(list.get(position).getSmall_price());
        name.setText(list.get(position).getSmall_name());
        kucun.setText(list.get(position).getSmall_stock());
        final int index = position;
        final ImageView delete = del;
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   context.deilist(context, index);
            }
        });
//        final EditText names = name;
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                list.get(index).setSmall_name(charSequence.toString().trim());
//                callback.success(1, index, list);

//               callback.success(1, index, charSequence.toString().trim());
//                Log.e(TAG, "onTextChangeda: "+charSequence.toString().trim());
              //  notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                callback.success(1, index, editable.toString().trim());
                Log.e(TAG, "onTextChangeda: "+editable.toString().trim());
            }
        });
//        final EditText moneys = money;
        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                list.get(index).setSmall_price(charSequence.toString().trim());
//                callback.success(1, index, list);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                callback.success(2, index, editable.toString().trim());
                //  notifyDataSetChanged();
                Log.e(TAG, "onTextChangedb: "+editable.toString().trim());
            }
        });
      //  final EditText stocks = kucun;
        kucun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                list.get(index).setSmall_stock(charSequence.toString().trim());
//                callback.success(1, index, list);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                callback.success(3, index, editable.toString().trim());
                //  notifyDataSetChanged();
                Log.e(TAG, "onTextChangedc: "+editable.toString().trim());
            }
        });
        return view;
    }

    public void Click(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void success(int type, int position,String msg);
    }

    static class ViewHolder {
//        @BindView(R.id.mIvshangpinguigeada_name)
//        EditText mIvshangpinguigeadaName;
//        @BindView(R.id.mIvshangpinguigeada_money)
//        EditText mIvshangpinguigeadaMoney;
//        @BindView(R.id.mIvshangpinguigeada_kucun)
//        EditText mIvshangpinguigeadaKucun;
//        @BindView(R.id.mIvshangpinguigeada_del)
//        ImageView mIvshangpinguigeadaDel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
