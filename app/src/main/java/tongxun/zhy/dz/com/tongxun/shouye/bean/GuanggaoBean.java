package tongxun.zhy.dz.com.tongxun.shouye.bean;

public class GuanggaoBean {

    /**
     * data : {"id":"1","title":"大幅度发大幅度发","img":"/Uploads/banner/2019-02-28/1551337188215460128.jpg","url":"/api.php/mycenter/flashview"}
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
         * id : 1
         * title : 大幅度发大幅度发
         * img : /Uploads/banner/2019-02-28/1551337188215460128.jpg
         * url : /api.php/mycenter/flashview
         */

        private String id;
        private String title;
        private String img;
        private String url;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
