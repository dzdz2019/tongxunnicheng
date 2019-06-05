package tongxun.zhy.dz.com.tongxun.tools;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * Created by dingzhe on 2017/8/9.
 */

public class GsonUtils {
    public static <T> T json2Bean(String json, Class<T> classOfT) {
        Gson gson = new Gson();
        // json=json.substring(1, json.length()-1);
        //		System.out.println("----这个是我解析gson的方法->>");
        T bean = gson.fromJson(json, classOfT);
        return bean;
    }

    /**
     * @param jsonString
     *            这个是要解析的数据
     * @param cls
     *            这个是javabean对象类型
     * @return
     */
    public static <T> T JsonToBean(String jsonString, Class<T> cls) {
        T t = null;//
        try {
            Gson gson = new Gson();// 获得一个gson对象
            t = gson.fromJson(jsonString, cls);// 这个就是解析相应的jsonstring
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 方法: getListString <br>
     * 描述:
     * 作者: 961956@qq.com <br>
     * 时间: 2015年6月24日 下午9:58:26
     *
     * @param jsonString
     * @return
     */
    public static <T> List<String> getListString(String jsonString) {
        List<String> list = null;// 这里已经是String的类型了所以不要用到什么反射机制
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<String>>() {
            }.getType());// 这里不要用到什么反射机制
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }



    /**
     * 方法: json2List <br>
     * 描述: 把 json解析为list数据
     * 作者: 961956@qq.com <br>
     * 时间: 2015年6月24日 下午9:57:27
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> List<T> json2List(String jsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {}.getType());// 把T类型的数据一个一个取出来 赋值给list
        } catch (Exception e) {
            // handle exception
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 方法: getMaplist <br>
     * 描述: 解析出list<Map>数据<br>
     * 作者: 961956@qq.com <br>
     * 时间: 2015年6月24日 下午9:59:10
     *
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> getMaplist(String jsonString) {
        List<Map<String, Object>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString,
                    new TypeToken<List<Map<String, Object>>>() {
                    }.getType());
        } catch (Exception e) {
            // handle exception
            e.printStackTrace();
        }
        return list;
    }
}
