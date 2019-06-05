package tongxun.zhy.dz.com.tongxun.shangcheng.bean;

import java.util.List;

public class UpdateshangpinBean {

    /**
     * status : 1
     * data : {"id":"145","name":"测试2","cid":"1","pinkage":"1","postage":"0.00","detail":"手机介绍","slideshow":["/Public/Api/images/ceXv5fWE8sIg.jpg","/Public/Api/images/ceXC1VajAQYQ.jpg","/Public/Api/images/ceW9esZnvUIRs.jp"],"small":[{"small_id":"146","small_name":"11","small_price":"22.00","small_stock":"33"}],"cname":""}
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
         * id : 145
         * name : 测试2
         * cid : 1
         * pinkage : 1
         * postage : 0.00
         * detail : 手机介绍
         * slideshow : ["/Public/Api/images/ceXv5fWE8sIg.jpg","/Public/Api/images/ceXC1VajAQYQ.jpg","/Public/Api/images/ceW9esZnvUIRs.jp"]
         * small : [{"small_id":"146","small_name":"11","small_price":"22.00","small_stock":"33"}]
         * cname :
         */

        private String id;
        private String name;
        private String cid;
        private String pinkage;
        private String postage;
        private String detail;
        private String cname;
        private String picture;
        private List<String> slideshow;
        private List<SmallBean> small;

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
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

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getPinkage() {
            return pinkage;
        }

        public void setPinkage(String pinkage) {
            this.pinkage = pinkage;
        }

        public String getPostage() {
            return postage;
        }

        public void setPostage(String postage) {
            this.postage = postage;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public List<String> getSlideshow() {
            return slideshow;
        }

        public void setSlideshow(List<String> slideshow) {
            this.slideshow = slideshow;
        }

        public List<SmallBean> getSmall() {
            return small;
        }

        public void setSmall(List<SmallBean> small) {
            this.small = small;
        }

        public static class SmallBean {
            /**
             * small_id : 146
             * small_name : 11
             * small_price : 22.00
             * small_stock : 33
             */

            private String small_id;
            private String small_name;
            private String small_price;
            private String small_stock;



            public String getSmall_id() {
                return small_id;
            }

            public void setSmall_id(String small_id) {
                this.small_id = small_id;
            }

            public String getSmall_name() {
                return small_name;
            }

            public void setSmall_name(String small_name) {
                this.small_name = small_name;
            }

            public String getSmall_price() {
                return small_price;
            }

            public void setSmall_price(String small_price) {
                this.small_price = small_price;
            }

            public String getSmall_stock() {
                return small_stock;
            }

            public void setSmall_stock(String small_stock) {
                this.small_stock = small_stock;
            }
        }
    }
}
