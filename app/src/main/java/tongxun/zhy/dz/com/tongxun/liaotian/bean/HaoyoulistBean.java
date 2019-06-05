package tongxun.zhy.dz.com.tongxun.liaotian.bean;

import java.util.List;

public class HaoyoulistBean {


    /**
     * data : [{"id":"75","uid":"1","g_uid":"22","create_time":"2019-04-15 09:37:16","username":"13770502801","img":"/Uploads/img.jpg"},{"id":"78","uid":"1","g_uid":"23","create_time":"2019-04-15 09:40:16","username":"13770502802","img":"/Uploads/img.jpg"},{"id":"79","uid":"1","g_uid":"26","create_time":"2019-04-15 09:40:22","username":"13852223057","img":"/Uploads/img.jpg"}]
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
         * id : 75
         * uid : 1
         * g_uid : 22
         * create_time : 2019-04-15 09:37:16
         * username : 13770502801
         * img : /Uploads/img.jpg
         */

        private String id;
        private String uid;
        private String g_uid;
        private String create_time;
        private String username;
        private String img;
        private String phone;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

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

        public String getG_uid() {
            return g_uid;
        }

        public void setG_uid(String g_uid) {
            this.g_uid = g_uid;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
