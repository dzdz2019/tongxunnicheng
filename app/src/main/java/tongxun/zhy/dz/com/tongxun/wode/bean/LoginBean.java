package tongxun.zhy.dz.com.tongxun.wode.bean;

public class LoginBean {

    /**
     * msg : 登录成功
     * status : 1
     * data : {"username":"13926063027","img":"/Uploads/banner/2019-02-27/1551257418893813128.png","id":"2","telephone":"13926063027"}
     */

    private String msg;
    private String status;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * username : 13926063027
         * img : /Uploads/banner/2019-02-27/1551257418893813128.png
         * id : 2
         * telephone : 13926063027
         */

        private String username;
        private String img;
        private String id;
        private String telephone;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }
    }
}
