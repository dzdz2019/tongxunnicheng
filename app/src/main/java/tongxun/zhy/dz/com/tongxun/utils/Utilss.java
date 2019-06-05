package tongxun.zhy.dz.com.tongxun.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2019-04-20 13:55.
 */

public class Utilss {
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    public static RequestBody toRequest(JSONObject jsonObject) {
        return RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
    }

    static int LENGTH = 4000;

    public static void e(String tag, String msg) {
        if (msg.length() > LENGTH) {
            for (int i = 0; i < msg.length(); i += LENGTH) {
                if (i + LENGTH < msg.length()) {
                    android.util.Log.e(tag, msg.substring(i, i + LENGTH));
                } else {
                    android.util.Log.e(tag, msg.substring(i, msg.length()));
                }
            }
        } else {
            android.util.Log.e(tag, msg);
        }

    }

    //判断String类型的返回值
    public static void JudgeEmptyString(String content, TextView tv) {
        if (!TextUtils.isEmpty(content)) {
            tv.setText(content);
        } else {
            tv.setText("暂无");
        }
    }

    //判断int类型的返回值
    public static void JudgeEmptyInt(int content, TextView tv) {
        if (!TextUtils.isEmpty(String.valueOf(content))) {
            tv.setText(String.valueOf(content));
        } else {
            tv.setText("暂无");
        }
    }

}
