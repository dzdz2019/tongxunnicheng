package tongxun.zhy.dz.com.tongxun.tools;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;

import com.orhanobut.dialogplus.DialogPlus;

import java.util.List;

/**
 * 对话框接口
 */
public interface IDialog {

    public static int YES = -1;

    public static int CANCLE = -2;

    /**
     * 根据isCancel判断对话框是否能取消
     *
     * @param context
     * @param title
     * @param msg
     * @param btnConfirm
     * @param btnCancel
     * @param isCancel       --是否可以取消
     * @param dialogCallBack
     * @return Dialog
     * @Description: TODO
     * @throw
     */
    Dialog showDialog(Context context, String title, String msg, String btnConfirm, String btnCancel, boolean isCancel, final DialogCallBack dialogCallBack);

    /**
     * 不能取消的对话框
     *
     * @param context
     * @param title
     * @param msg
     * @param btnConfirm
     * @param btnCancel
     * @param dialogCallBack
     * @return Dialog
     * @Description: TODO
     * @throw
     */
    Dialog showDialogNoCancel(Context context, String title, String msg, String btnConfirm, String btnCancel, final DialogCallBack dialogCallBack);

    /**
     * 普通对话框
     *
     * @param context
     * @param title
     * @param msg
     * @param btnConfirm
     * @param btnCancel
     * @param dialogCallBack
     * @return Dialog
     * @Description: TODO
     * @throw
     */
    Dialog showDialog(Context context, String title, String msg, String btnConfirm, String btnCancel, final DialogCallBack dialogCallBack);

    /**
     * 显示带有单个按钮且无法点击返回键取消的对话框
     *
     * @param context
     * @param title
     * @param msg
     * @param btnConfirm
     * @param dialogCallBack
     * @return Dialog
     * @Description: TODO
     * @throw
     */
    Dialog showDialogSingleBtn(Context context, String title, String msg, String btnConfirm, final DialogCallBack dialogCallBack);

    /**
     * 显示短的toast
     *
     * @param context
     * @param msg
     */
    void showToastLong(Context context, String msg);

    /**
     * 显示长的toast
     *
     * @param context
     * @param msg
     */
    void showToastShort(Context context, String msg);

    /**
     * 根据type显示toast
     *
     * @param context
     * @param type
     */
    void showToastType(Context context, String msg, String type);

    /**
     * 显示确定对话框 (确定,取消)
     *
     * @param context
     * @param title
     * @param msg
     * @param callback
     */
    Dialog showDialog(Context context, String title, String msg, DialogCallBack callback);

    /**
     * 显示确定对话框 (确定,取消) 可指定icon
     *
     * @param context
     * @param title
     * @param msg
     * @param callback
     */
    Dialog showDialog(Context context, int icon, String title, String msg, DialogCallBack callback);

    /**
     * item选择对话框
     *
     * @param context
     * @param title
     * @param items
     * @param callback
     */
    Dialog showItemDialog(Context context, String title, CharSequence[] items, DialogCallBack callback);

    /**
     * 显示进度框,可以返回
     *
     * @param context
     * @param title
     * @param msg
     * @return
     */
    Dialog showProgressDialog(Context context, String title, String msg, boolean cancel);

    /**
     * 显示进度框,不可返回
     *
     * @param context
     * @param msg
     * @return
     */
    Dialog showProgressDialog(Context context, String msg);

    /**
     * 显示进度框
     *
     * @param context
     * @return
     */
    Dialog showProgressDialog(Context context);

    /**
     * 用adapter 的dialoger
     *
     * @param adapter
     * @param itemClickListener
     * @return
     */
    Dialog showAdapterDialoge(Context context, String title, ListAdapter adapter, OnItemClickListener itemClickListener);

   /* *
     * 获取dialog list样式*/

    DialogPlus showListDialogPlus(Context context, List<String> list, com.orhanobut.dialogplus.OnItemClickListener onItemClickListener);

  /*  *
     * 获取dialog list样式
     */
    DialogPlus showListCenterDialogPlus(Context context, List<String> list, com.orhanobut.dialogplus.OnItemClickListener onItemClickListener);

    DialogPlus showEditDialogPlus(Context context, View ResView);
    DialogPlus showListHeightDialogPlus(Context context, List<String> list, int height, com.orhanobut.dialogplus.OnItemClickListener onItemClickListener);
}
