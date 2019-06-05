package tongxun.zhy.dz.com.tongxun.shangcheng.bean;

import java.util.List;

public class FenLeiBean {

    /**
     * msg : 获取信息成功
     * status : 1
     * data : [{"id":"1","name":"手机","img":"/Uploads/banner/2019-02-27/15512362591276882045.jpg"},{"id":"2","name":"平板","img":"/Uploads/banner/2019-02-27/1551236381551021958.jpg"},{"id":"3","name":"外配","img":"/Uploads/banner/2019-02-27/1551236405416934928.jpg"},{"id":"4","name":"华为手机","img":"/Uploads/banner/2019-02-27/1551236466597108996.jpg"},{"id":"5","name":"小米手机","img":"/Uploads/banner/2019-02-27/15512364861345948053.png"},{"id":"6","name":"苹果平板","img":"/Uploads/banner/2019-02-27/155123651418116379.png"},{"id":"8","name":"智慧云外配","img":"/Uploads/banner/2019-02-27/1551236706193599983.png"},{"id":"11","name":"摩托","img":"/Uploads/banner/2019-02-27/1551250829983878646.jpg"},{"id":"12","name":"奔驰","img":"/Uploads/banner/2019-02-27/1551255146974712877.jpg"}]
     * shouye : [{"id":"8","name":"智慧云外配"},{"id":"11","name":"摩托"}]
     * shop : [{"id":"5","name":"小米手机"}]
     */

    private String msg;
    private int status;
    private List<DataBean> data;
    private List<ShouyeBean> shouye;
    private List<ShopBean> shop;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<ShouyeBean> getShouye() {
        return shouye;
    }

    public void setShouye(List<ShouyeBean> shouye) {
        this.shouye = shouye;
    }

    public List<ShopBean> getShop() {
        return shop;
    }

    public void setShop(List<ShopBean> shop) {
        this.shop = shop;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 手机
         * img : /Uploads/banner/2019-02-27/15512362591276882045.jpg
         */

        private String id;
        private String name;
        private String img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public static class ShouyeBean {
        /**
         * id : 8
         * name : 智慧云外配
         */

        private String id;
        private String name;
        private String img;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ShopBean {
        /**
         * id : 5
         * name : 小米手机
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
