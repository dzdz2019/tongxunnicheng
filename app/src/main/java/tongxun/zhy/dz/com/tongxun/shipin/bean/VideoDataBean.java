package tongxun.zhy.dz.com.tongxun.shipin.bean;

import java.util.List;

public class VideoDataBean {

    /**
     * data : [{"id":"32","uid":"1243234","cid":"23423","title":"3333","introduce":"4324324","path":"32523","frequency":"5","addtime":"2019-04-22 14:36:45","is_recommend":"2","img":"/Uploads/video/2019-04-22/201904221431174350.jpg","like":"0","time":"","charge_status":"0","charge":"0.00"}]
     * status : 1
     * msg : 查询成功
     */

    private String status;
    private String msg;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
         * id : 32
         * uid : 1243234
         * cid : 23423
         * title : 3333
         * introduce : 4324324
         * path : 32523
         * frequency : 5
         * addtime : 2019-04-22 14:36:45
         * is_recommend : 2
         * img : /Uploads/video/2019-04-22/201904221431174350.jpg
         * like : 0
         * time :
         * charge_status : 0
         * charge : 0.00
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
        private String img;
        private String like;
        private String time;
        private String charge_status;
        private String charge;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCharge_status() {
            return charge_status;
        }

        public void setCharge_status(String charge_status) {
            this.charge_status = charge_status;
        }

        public String getCharge() {
            return charge;
        }

        public void setCharge(String charge) {
            this.charge = charge;
        }
    }
}
