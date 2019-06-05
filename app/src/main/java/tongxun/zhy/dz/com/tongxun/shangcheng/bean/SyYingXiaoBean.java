package tongxun.zhy.dz.com.tongxun.shangcheng.bean;

import java.util.List;

public class SyYingXiaoBean {

    /**
     * data : [{"id":"2","title":"清仓","details":"大甩卖","img":"/Uploads/banner/2019-03-22/155323645519149142.png","status":"1","url":"/app.pangumeng.com/api.php/Goods/zhekou?cid=2&type=1"},{"id":"3","title":"大甩卖","details":"清仓","img":"/Uploads/banner/2019-03-29/1553822273248637632.jpg","status":"2","url":"/app.pangumeng.com/api.php/Goods/zhekou?cid=3&type=1"}]
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
         * id : 2
         * title : 清仓
         * details : 大甩卖
         * img : /Uploads/banner/2019-03-22/155323645519149142.png
         * status : 1
         * url : /app.pangumeng.com/api.php/Goods/zhekou?cid=2&type=1
         */

        private String id;
        private String title;
        private String details;
        private String img;
        private String status;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
