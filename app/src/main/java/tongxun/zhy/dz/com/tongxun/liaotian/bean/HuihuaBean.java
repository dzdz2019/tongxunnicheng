package tongxun.zhy.dz.com.tongxun.liaotian.bean;

import java.util.List;

public class HuihuaBean {

    /**
     * data : [{"id":"6","uid":"3","f_uid":"1","content":"敲代码！","create_time":"2019-04-09 15:58:02","f_name":"wangyanting","f_img":"/Uploads/User/2019-04-01/201904011749113479.jpeg"},{"id":"10","uid":"12","f_uid":"1","content":"aaa","create_time":"2019-04-09 15:48:38","f_name":"一哈","f_img":"/Uploads/User/2019-04-01/201904011749113479.jpeg"},{"id":"11","uid":"14","f_uid":"1","content":"bbb","create_time":"2019-04-09 15:49:25","f_name":"五哈","f_img":"/Uploads/User/2019-04-01/201904011749113479.jpeg"}]
     * status : 1
     * msg : 查询成功
     */

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6
         * uid : 3
         * f_uid : 1
         * content : 敲代码！
         * create_time : 2019-04-09 15:58:02
         * f_name : wangyanting
         * f_img : /Uploads/User/2019-04-01/201904011749113479.jpeg
         */

        private String id;
        private String uid;
        private String f_uid;
        private String content;
        private String create_time;
        private String f_name;
        private String f_img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getF_uid() {
            return f_uid;
        }

        public void setF_uid(String f_uid) {
            this.f_uid = f_uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getF_name() {
            return f_name;
        }

        public void setF_name(String f_name) {
            this.f_name = f_name;
        }

        public String getF_img() {
            return f_img;
        }

        public void setF_img(String f_img) {
            this.f_img = f_img;
        }
    }
}
