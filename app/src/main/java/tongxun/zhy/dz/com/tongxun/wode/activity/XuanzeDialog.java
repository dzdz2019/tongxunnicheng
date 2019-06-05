package tongxun.zhy.dz.com.tongxun.wode.activity;

import android.app.Activity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import tongxun.zhy.dz.com.tongxun.R;
import tongxun.zhy.dz.com.tongxun.tools.ShareDialogBase;

/**
 * Created by Administrator on 2016/10/24.
 */
public class XuanzeDialog extends ShareDialogBase {
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
//    private DialogListener dialogListener;
    private TextView sex_select;
    private OnConfirmListener mConfrimListener;

    public XuanzeDialog(Activity activity) {
        super(activity);
//        spUtils = SPUtils.getInstance(activity);
        initView();
    }
//    public XuanzeDialog(Context activity) {
//        super(activity);
//        spUtils = SPUtils.getInstance(activity);
//        initView();
//    }

//    public void setonDialogListener (DialogListener dialogListener){
//        this.dialogListener = dialogListener;
//    }

    @Override
    public View onCreateStubView() {
        View view = View.inflate(getContext(), R.layout.paizhaodialog, null);

        return view;
    }

    public void initView()
    {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.group_dialog);
        sex_select = (TextView)findViewById(R.id.sex_select);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeFragment(checkedId);
            }
        });

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConfrimListener != null) {
                    mConfrimListener.onConfirm();
                }
                onConfirm();
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConfrimListener != null) {
                    mConfrimListener.onConfirm();
                }
                onConfirm();
            }
        });
        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConfrimListener != null) {
                    mConfrimListener.onConfirm();
                }
                onConfirm();
            }
        });
    }

    private void changeFragment(int checkedId) {
        switch (checkedId) {
            case R.id.rb1:
                rb1.setChecked(true);
                dismiss();
                break;
            case R.id.rb2:
                rb2.setChecked(true);
                dismiss();
                break;
            case R.id.rb3:
                rb3.setChecked(true);
                dismiss();
                break;
        }
    }
    public void setData()
    {
        sex_select.setText("请选择");
        rb1.setText("拍照");
        rb2.setText("图库");
    }
    public void setDatas()
    {
        sex_select.setText("请选择");
        rb1.setText("录制视频");
        rb2.setText("选择视频");
    }
    public void onConfirm()
    {
        dismiss();
    }

    public XuanzeDialog setOnConfirmListener(OnConfirmListener l)
    {
        this.mConfrimListener = l;
        return this;
    }

    public interface OnConfirmListener
    {
        void onConfirm();
    }

    public int getText()
    {
        if(rb1.isChecked() == true)
        {
            return 1;
        }
        if(rb2.isChecked() == true)
        {
            return 2;
        }
        if(rb3.isChecked() == true)
        {
            return 3;
        }
        return 0;
    }



}
