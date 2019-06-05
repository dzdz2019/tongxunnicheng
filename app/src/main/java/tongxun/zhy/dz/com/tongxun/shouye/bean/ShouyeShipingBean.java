package tongxun.zhy.dz.com.tongxun.shouye.bean;

import java.util.List;

public class ShouyeShipingBean {

    /**
     * msg : 获取信息成功
     * status : 1
     * data : [{"id":"281","uid":"0","cid":"2","title":"必看！维修视频","introduce":"必看！","path":"/Uploads/video/2019-02-27/1551249962285429805.mp4","frequency":"2","addtime":"2019-02-27 14:46:04","is_recommend":"1","catename":"主板维修"},{"id":"280","uid":"2","cid":"1","title":"维修视频","introduce":"维修视频","path":"/Uploads/video/2019-02-27/1551249962285429805.mp4","frequency":"1","addtime":"2019-02-27 14:46:04","is_recommend":"1","catename":"屏幕维修12"}]
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
         * id : 281
         * uid : 0
         * cid : 2
         * title : 必看！维修视频
         * introduce : 必看！
         * path : /Uploads/video/2019-02-27/1551249962285429805.mp4
         * frequency : 2
         * addtime : 2019-02-27 14:46:04
         * is_recommend : 1
         * catename : 主板维修
         */

        private String id;
        private String uid;
        private String cid;
        private String title;
        private String introduce;
        private String path;
        private String frequency;
        private String addtime;
        private String is_recommend;
        private String catename;
        private String img;
        private String author;
        private String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
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

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getFrequency() {
            return frequency;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getIs_recommend() {
            return is_recommend;
        }

        public void setIs_recommend(String is_recommend) {
            this.is_recommend = is_recommend;
        }

        public String getCatename() {
            return catename;
        }

        public void setCatename(String catename) {
            this.catename = catename;
        }
    }
}
