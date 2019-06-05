package tongxun.zhy.dz.com.tongxun.tools;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import tongxun.zhy.dz.com.tongxun.R;

/***
 * IDialog 基本实现
 */
public class DialogImpl implements IDialog {
    /**
     * 显示带有单个按钮且无法点击返回键取消的对话框
     */
    public Dialog showDialogSingleBtn(Context context, String title, String msg, String btnConfirm, final DialogCallBack dialogCallBack) {
        Builder builder = new Builder(context).setTitle(title).setMessage(msg).setNegativeButton(btnConfirm, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogCallBack != null) {
                    dialogCallBack.onClick(IDialog.YES);
                }
            }
        });
        builder.setCancelable(false);
        return builder.show();
    }

    /**
     * isCancel-是否取消
     */
    public Dialog showDialog(Context context, String title, String msg, String btnConfirm, String btnCancel, boolean isCancel, final DialogCallBack dialogCallBack) {
        return isCancel ? showDialog(context, title, msg, btnConfirm, btnCancel, dialogCallBack) : showDialogNoCancel(context, title, msg, btnConfirm, btnCancel, dialogCallBack);
    }

    /**
     * 显示自定义确定和取消按钮的对话框，对话框无法点击返回键取消
     */
    public Dialog showDialogNoCancel(Context context, String title, String msg, String btnConfirm, String btnCancel, final DialogCallBack dialogCallBack) {
        Builder builder = new Builder(context).setTitle(title).setMessage(msg).setNegativeButton(btnCancel, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogCallBack != null) {
                    dialogCallBack.onClick(IDialog.CANCLE);
                }
            }
        });
        if (dialogCallBack != null) {
            builder.setPositiveButton(btnConfirm, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (dialogCallBack != null) {
                        dialogCallBack.onClick(IDialog.YES);
                    }
                }
            });
        }
        builder.setCancelable(false);
        return builder.show();
    }

    public Dialog showDialog(Context context, String title, String msg, String btnConfirm, String btnCancel, final DialogCallBack dialogCallBack) {
        Builder builder = new Builder(context).setTitle(title).setMessage(msg).setNegativeButton(btnCancel, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogCallBack != null) {
                    dialogCallBack.onClick(IDialog.CANCLE);
                }
            }
        });
        if (!DataValidation.isEmpty(btnCancel)) {
            builder.setPositiveButton(btnConfirm, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (dialogCallBack != null) {
                        dialogCallBack.onClick(IDialog.YES);
                    }
                }
            });
        }
        return builder.show();
    }

    public Dialog showDialog(Context context, String title, String msg, final DialogCallBack dialogCallBack) {
        Builder builder = new Builder(context).setTitle(title).setMessage(msg).setNegativeButton(context.getString(R.string.cancel), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogCallBack != null) {
                    dialogCallBack.onClick(IDialog.CANCLE);
                }
            }
        });
        builder.setPositiveButton(context.getString(R.string.confirm), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogCallBack != null) {
                    dialogCallBack.onClick(IDialog.YES);
                }
            }
        });
        return builder.show();
    }

    public Dialog showItemDialog(Context context, String title, CharSequence[] items, final DialogCallBack callback) {
        Dialog dialog = new Builder(context).setTitle(title).setItems(items, new OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    callback.onClick(which);
                }
            }
        }).show();
        return dialog;

    }

    class DialogOnItemClickListener implements OnItemClickListener {
        Dialog dialog;

        public void setDialog(Dialog dialog) {
            this.dialog = dialog;
        }

        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        }

    }

    public Dialog showProgressDialog(Context context, String title, String msg, boolean cancel) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msg);
        progressDialog.show();
        progressDialog.setCancelable(cancel);
        return progressDialog;
    }

    public Dialog showProgressDialog(Context context, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.show();
        progressDialog.setCancelable(true);
        return progressDialog;
    }

    public Dialog showProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setCancelable(true);
        return progressDialog;
    }

    public void showToastLong(Context context, String msg) {
        // 使用同一个toast避免 toast重复显示
        Toast toast = IocContainer.getIocContainer().get(Toast.class);
        toast.setDuration(Toast.LENGTH_LONG);
        TextView textView = new TextView(context);
        textView.setText(msg);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(15, 10, 15, 10);
        textView.setBackgroundResource(android.R.drawable.toast_frame);
        toast.setView(textView);
        toast.show();
    }

    public void showToastShort(Context context, String msg) {
        // 使用同一个toast避免 toast重复显示
        Toast toast = IocContainer.getIocContainer().get(Toast.class);
        toast.setDuration(Toast.LENGTH_SHORT);
        TextView textView = new TextView(context);
        textView.setText(msg);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(15, 10, 15, 10);
        textView.setBackgroundResource(android.R.drawable.toast_frame);
        toast.setView(textView);
        toast.show();
    }

    public void showToastType(Context context, String msg, String type) {
        showToastLong(context, msg);
    }

    public Dialog showDialog(Context context, int icon, String title, String msg, DialogCallBack callback) {
        return showDialog(context, title, msg, callback);
    }

    @Override
    public Dialog showAdapterDialoge(Context context, String title, android.widget.ListAdapter adapter, OnItemClickListener itemClickListener) {
        return null;
    }

    @Override
    public DialogPlus showListDialogPlus(Context context, List<String> list, com.orhanobut.dialogplus.OnItemClickListener onItemClickListener) {
        ListHolder viewHolder = new ListHolder();
        ListAdapter adapter = new ListAdapter(context, list,true);

        return DialogPlus.newDialog(context)
                .setContentHolder(viewHolder)
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setAdapter(adapter)
                .setOnItemClickListener(onItemClickListener)
                .setExpanded(false)
                .create();
    }

    @Override
    public DialogPlus showListCenterDialogPlus(Context context, List<String> list, com.orhanobut.dialogplus.OnItemClickListener onItemClickListener) {
        ListHolder viewHolder = new ListHolder();
        ListAdapter adapter = new ListAdapter(context, list,false);

        return DialogPlus.newDialog(context)
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setAdapter(adapter)
                .setOnItemClickListener(onItemClickListener)
                .setExpanded(false)
                .setContentBackgroundResource(R.drawable.shape_corner_trans)
                .setMargin(UIHelper.dip2px(context,40),0,UIHelper.dip2px(context,40),0)
                .create();
    }

    class ListAdapter extends BaseAdapter {

        private List<String> list = new ArrayList<>();
        private Context mContext;
        private boolean isBottom;

        public ListAdapter(Context context, List<String> list,boolean isBottom) {
            this.list = list;
            this.mContext = context;
            this.isBottom = isBottom;
        }

        public ListAdapter() {
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
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_list_item, null);
                viewHolder.mTextContent = (TextView) convertView.findViewById(R.id.mTextContent);
                viewHolder.mLine = convertView.findViewById(R.id.mLine);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.mTextContent.setText(list.get(position));

            if(isBottom){
                viewHolder.mTextContent.setGravity(Gravity.CENTER);
            }else{
                viewHolder.mTextContent.setGravity(Gravity.CENTER_VERTICAL);
            }
            if(position == (list.size() - 1)){
                viewHolder.mLine.setVisibility(View.GONE);
            }else {
                viewHolder.mLine.setVisibility(View.VISIBLE);
            }
            return convertView;
        }

        class ViewHolder {
            private TextView mTextContent;
            private View mLine;
        }
    }

    @Override
    public DialogPlus showEditDialogPlus(Context context, View ResView) {
        return DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(ResView))
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setExpanded(false)
                .create();
    }

    @Override
    public DialogPlus showListHeightDialogPlus(Context context, List<String> list, int height, com.orhanobut.dialogplus.OnItemClickListener onItemClickListener) {
        ListHolder viewHolder = new ListHolder();
        ListAdapter adapter = new ListAdapter(context, list,true);
        if (list.size() <= 5){
            height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        return DialogPlus.newDialog(context)
                .setContentHolder(viewHolder)
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setAdapter(adapter)
                .setOnItemClickListener(onItemClickListener)
                .setExpanded(false)
                .setContentHeight(height)
                .create();
    }
}
