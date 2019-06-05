package tongxun.zhy.dz.com.tongxun.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.shangcheng.bean.FenLeiBean;
import tongxun.zhy.dz.com.tongxun.wode.adapter.PopFenLeiAdapter;

import static tongxun.zhy.dz.com.tongxun.tools.UIHelper.setListViewHeightBasedOnChildren;

public class Pop extends PopupWindow {
    private View view;
    WindowManager.LayoutParams params;
    public ListView fenleilistview;
    public static PopFenLeiAdapter popFenLeiAdapter;

    public TextView baoyou,daofu;

    public Pop(Activity context, int type,View views,List<FenLeiBean.DataBean>list) {
        switch (type) {
            case 1:
                poptianjia(context,views,list);
                break;
            case 2:
                popkuaidi(context,views);
                break;
        }
    }
    /**
     * 添加商品分类
     */
    public void poptianjia(final Activity context, View views, List<FenLeiBean.DataBean> list) {
        view = View.inflate(context, R.layout.fenleipop, null);
        fenleilistview = view.findViewById(R.id.poplist_fenlei);
        popFenLeiAdapter = new PopFenLeiAdapter(list, context);
        fenleilistview.setAdapter(popFenLeiAdapter);
        setListViewHeightBasedOnChildren(fenleilistview);
        setting(context, views);
    }
/**
 * 快递方式
 * */
public void popkuaidi(Activity context,View views){
    view = View.inflate(context, R.layout.popkuaidi, null);
    daofu=view.findViewById(R.id.pop_daofu);
    baoyou=view.findViewById(R.id.pop_baoyou);
    setting(context, views);
}

    private void setting(final Activity activity, View views) {
        this.setOutsideTouchable(true);
        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0ffffff);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        this.setTouchable(true); // 设置PopupWindow可触摸
        this.setFocusable(true);
        this.setBackgroundDrawable(new PaintDrawable());
        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);
        //        设置Popupwindow显示位置（从底部弹出）
        this.showAtLocation(views, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        params = activity.getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
        params.alpha = 0.7f;
        activity.getWindow().setAttributes(params);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        this.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = activity.getWindow().getAttributes();
                params.alpha = 1f;
                activity.getWindow().setAttributes(params);
            }
        });
    }
}
