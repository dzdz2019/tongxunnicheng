package tongxun.zhy.dz.com.tongxun.tools;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import tongxun.zhy.dz.com.tongxun.R;


/**
 * Created by Administrator on 2016/10/27.
 */
public class ShareDialogBase extends Dialog {

    private FrameLayout container_content;//要显示视图的容器，要显示的东西是装在这个容器里的
    private View stubView;//要显示的view
    private Button button3;
    private OnConfirmListener mConfrimListener;
    private OnCancleListener mCancleListener;

    public ShareDialogBase(Context context) {
        super(context, R.style.custom_dialog);//设置已经自定义好的样式

        initView();
        View view = onCreateStubView();//通过调用onCreateStubView 获取要显示的视图
        if(view != null)
        {
            setStubView(view);//添加视图
        }

        //点击到弹出窗之外，true为消失。 false不消失
        setCanceledOnTouchOutside(false);
        //如果为true，点击back键,会自动消失。默认为true
       // setCancelable(false);

//        initSize();//设置一下窗口
    }

    //找到子组件
    private void initView() {
        //设置Dialog显示的组件
        setContentView(R.layout.share_dialog_base);
        container_content = (FrameLayout) this.findViewById(R.id.container_content);
        button3 = (Button) findViewById(R.id.button_share_dialog);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        initSize();
    }


    public View onCreateStubView()
    {
        return null;
    }

    //给dialog里设置内容
    public ShareDialogBase setStubView(View view)
    {
        if(view.getParent() == container_content)
        {
            return this;
        }

        if(stubView != null)
        {
            throw new RuntimeException("stubView is setted");
        }

        stubView = view;
        container_content.addView(view);

        return this;
    }

    /**
     * 设置一下Dialog的大小
     */
    public void initSize()
    {
        Window window = this.getWindow();//拿到Dialog的窗口
        WindowManager.LayoutParams p = window.getAttributes();//获取Dialog窗口的属性
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getContext().getResources().getDimensionPixelSize(resourceId);
        }
        int height = wm.getDefaultDisplay().getHeight()-statusBarHeight1;
        p.width = (int) (width * 0.9); // 宽度设置为屏幕的0.9
        p.height = height;//WindowManager.LayoutParams.WRAP_CONTENT;
        p.gravity = Gravity.BOTTOM;//设置dialog 在布局中的位置
        window.setAttributes(p);
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.button3:
//                if (this.mConfrimListener != null) {
//                    this.mConfrimListener.onConfirm();
//                }
//                onConfirm();
//                break;
//        }
//    }

        public void onConfirm()
        {
            dismiss();
        }

        public ShareDialogBase setOnConfirmListener(OnConfirmListener l)
        {
            this.mConfrimListener = l;
            return this;
        }

    public interface OnConfirmListener
    {
        void onConfirm();
    }

    public interface OnCancleListener
    {
        void onCancel();
    }

}
