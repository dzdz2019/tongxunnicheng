package tongxun.zhy.dz.com.tongxun.shouye.bean;

public class VideoTimeBean {

    /**
     * data : {"duration":"160898"}
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
         * duration : 160898
         */

        private String duration;

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }
}
