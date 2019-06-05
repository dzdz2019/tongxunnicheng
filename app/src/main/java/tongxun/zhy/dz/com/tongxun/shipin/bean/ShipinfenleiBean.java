package tongxun.zhy.dz.com.tongxun.shipin.bean;

import java.util.List;

public class ShipinfenleiBean {

    /**
     * status : 1
     * data : [{"id":"1","name":"屏幕维修"},{"id":"2","name":"主板维修"},{"id":"3","name":"软件维修"},{"id":"4","name":"电池维修"},{"id":"5","name":"碎屏"}]
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
         * id : 1
         * name : 屏幕维修
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
