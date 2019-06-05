package tongxun.zhy.dz.com.tongxun.gonggong;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import tongxun.zhy.dz.com.tongxun.net.Common;
import tongxun.zhy.dz.com.tongxun.tools.HttpUtils;
import tongxun.zhy.dz.com.tongxun.tools.LoadingDialog;
import tongxun.zhy.dz.com.tongxun.tools.TextCallBack;

public class Apiall {
    private LoadingDialog loadingDialog;

    public void getguanggao(Context context, String type, final apihui apihui) {
        loadingDialog = new LoadingDialog(context);
        loadingDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", type);
            HttpUtils.post(context, Common.Guanggao, jsonObject, new TextCallBack() {
                @Override
                protected void onSuccess(String text) {
                    Log.e("广告返回",text);
                    apihui.sucss(text);
                    loadingDialog.dismiss();
                }

                @Override
                protected void onFailure(ResponseException e) {
                    apihui.erry();
                    loadingDialog.dismiss();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface apihui {
        void sucss(String text);

        void erry();
    }
}
