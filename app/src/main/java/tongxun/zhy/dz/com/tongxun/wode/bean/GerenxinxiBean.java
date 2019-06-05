package tongxun.zhy.dz.com.tongxun.wode.bean;

public class GerenxinxiBean {

    /**
     * data : {"id":"23","img":"/Uploads/User/2019-04-24/201904241545430491.jpeg","telephone":"13770502802","nickname":"2802123","type1":1,"type2":0,"type3":0,"type4":0,"type5":0,"type6":0}
     * status : 1
     * msg : 查询成功
     */

    private DataBean data;
    private int status;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * id : 23
         * img : /Uploads/User/2019-04-24/201904241545430491.jpeg
         * telephone : 13770502802
         * nickname : 2802123
         * type1 : 1
         * type2 : 0
         * type3 : 0
         * type4 : 0
         * type5 : 0
         * type6 : 0
         */

        private String id;
        private String img;
        private String telephone;
        private String nickname;
        private int type1;
        private int type2;
        private int type3;
        private int type4;
        private int type5;
        private int type6;
        private String shiming;

        public String getShiming() {
            return shiming;
        }

        public void setShiming(String shiming) {
            this.shiming = shiming;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getType1() {
            return type1;
        }

        public void setType1(int type1) {
            this.type1 = type1;
        }

        public int getType2() {
            return type2;
        }

        public void setType2(int type2) {
            this.type2 = type2;
        }

        public int getType3() {
            return type3;
        }

        public void setType3(int type3) {
            this.type3 = type3;
        }

        public int getType4() {
            return type4;
        }

        public void setType4(int type4) {
            this.type4 = type4;
        }

        public int getType5() {
            return type5;
        }

        public void setType5(int type5) {
            this.type5 = type5;
        }

        public int getType6() {
            return type6;
        }

        public void setType6(int type6) {
            this.type6 = type6;
        }
    }
}
