package tongxun.zhy.dz.com.tongxun.shangcheng.bean;

public class UpdateSmallshangpin {
    /**
     * status : 1
     * data : {"id":"32","name":"iqoo","picture":"/Uploads/Shop/2019-03-25/201903251513383054.jpeg","detail":"","price":"0.00","stock":"2"}
     * msg : 查询成功
     */

    private String status;
    private DataBean data;
    private String msg;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * id : 32
         * name : iqoo
         * picture : /Uploads/Shop/2019-03-25/201903251513383054.jpeg
         * detail :
         * price : 0.00
         * stock : 2
         */

        private String id;
        private String name;
        private String picture;
        private String detail;
        private String price;
        private String stock;

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

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }
    }
}
