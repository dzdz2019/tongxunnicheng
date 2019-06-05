package tongxun.zhy.dz.com.tongxun.shangcheng.bean;

import java.util.List;

public class BannerBean {

    /**
     * msg : 获取信息成功
     * status : 1
     * data : [{"id":"20","img":"/Uploads/banner/2019-02-27/1551257418893813128.png","title":"第一张","type":"2","goods":"11","video":"","url":""},{"id":"22","img":"/Uploads/banner/2019-02-27/15512579611019250770.jpg","title":"第三张","type":"1","goods":"","video":"","url":"/Api.php/Cate/one?id=22"},{"id":"23","img":"/Uploads/banner/2019-02-28/1551338037862572055.jpg","title":"第四张","type":"2","goods":"12","video":"","url":""}]
     */

    private String msg;
    private int status;
    private List<DataBean> data;

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

    public static class DataBean {
        /**
         * id : 20
         * img : /Uploads/banner/2019-02-27/1551257418893813128.png
         * title : 第一张
         * type : 2
         * goods : 11
         * video :
         * url :
         */

        private String id;
        private String img;
        private String title;
        private String type;
        private String goods;
        private String video;
        private String url;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getGoods() {
            return goods;
        }

        public void setGoods(String goods) {
            this.goods = goods;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
