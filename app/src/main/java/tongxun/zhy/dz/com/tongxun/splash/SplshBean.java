package tongxun.zhy.dz.com.tongxun.splash;

import java.util.List;

public class SplshBean {


    /**
     * data : [{"img":"/Uploads/banner/2019-04-17/155546353293723219.jpg"},{"img":"/Uploads/banner/2019-04-17/1555463536256512448.jpg"},{"img":"/Uploads/banner/2019-04-17/15554635411019508961.jpg"}]
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
         * img : /Uploads/banner/2019-04-17/155546353293723219.jpg
         */

        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
