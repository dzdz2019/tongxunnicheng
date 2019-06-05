package tongxun.zhy.dz.com.tongxun.net;

public class Common {
    public static final String URL = "http://app.pangumeng.com";
    public static final String ImgUrl = "http://app.pangumeng.com";
    /**
     * 登陆注册
     */
    public static final String login = URL + "/api.php/mycenter/login";//登陆
    public static final String ZhuCe = URL + "/api.php/mycenter/zhuce";//注册
    public static final String ZhuCeCode = URL + "/api.php/mycenter/checkcode";//注册验证码
    public static final String WangJiMIma = URL + "/api.php/mycenter/forget";//忘记密码
    public static final String WangjiCode = URL + "/api.php/mycenter/forgetcode";//忘记密码验证码
    public static final String GetNIckname = URL + "/api.php/mycenter/index";//个人信息

    public static final String FriendList = URL + "/api.php/Mycenter/addList";//好友列表


    /**
     * 商城
     */
    public static final String ShangPin = URL + "/Api.php/Cate/goodslist";//商品列表
    public static final String ShangpinWeb = URL + "/api.php/goods/goods_details/goods_id/";//商品web
    public static final String Fenleishangpin = URL + "/api.php/goods/quanbu?cid=";//分类商品web
    public static final String FenLei = URL + "/Api.php/Cate/cate";//商城分类
    public static final String Banner = URL + "/Api.php/Cate/banner";//banner
    public static final String MyShangpin = URL + "/api.php/My/mygoods";//我的商品
    public static final String Carnum = URL + "/api.php/goods/carts";//购物车数量

    public static final String AddShangpin = URL + "/api.php/goods/addgoods";
    ;//添加商品
    public static final String SeachShangpin = URL + "/api.php/goods/goodsinfo";//查询用户商品
    public static final String AddSmallshop = URL + "/api.php/goods/addsmallgoods";//添加小商品
    public static final String seachsmallshop = URL + "/api.php/goods/smallgoods";//查询小商品
    public static final String Setimg = URL + "/api.php/goods/img";//上传图片


    public static final String Gettoken = URL + "/api.php/video/uploadsvideo";//上传视频token
    /**
     * 首页
     */
    public static final String Guanggao = URL + "/api.php/mycenter/advertising";//广告
    public static final String SyYingxiao = URL + "/api.php/Mycenter/plate";//首页营销
    public static final String TuiJianShipin = URL + "/api.php/vrecommend/isrecommend";//推荐视频
    public static final String JiShushipin = URL + "/api.php/vrecommend/videolist";//技术视频
    public static final String VideoTime = URL + "/api.php/mycenter/duration";//上传视频的时长
    public static final String YinDao = URL + "/api.php/Mycenter/page";//引导页


    /**
     * 视频
     */
    public static final String ShipinFeenlei = URL + "/api.php/Video/cate";//视频分类
    public static final String AddVideo = URL + "/api.php/Video/video";//上传视频信息
    public static final String VIdeodata = URL + "/api.php/Video/selVideo";//视频信息

    /**
     * H5
     */
    public static final String MyGuanZhu = URL + "/Api.php/my/concern";
    ;//我的关注
    public static final String MyQianbao = URL + "/Api.php/my/wallet";//我的钱包
    public static final String GaijinJianyi = URL + "/api.php/autonym/gaijinjianyi";//改进建议
    public static final String Fuwuzhongxin = URL + "/api.php/autonym/fuwuzhongxin";//服务中心
    public static final String Shiming = URL + "/api.php/autonym/weishiming";//实名认证
    public static final String Dingdan = URL + "/Api.php/order/order_list";//我的订单
    public static final String MaijiaDingDan = URL + "/Api.php/sell/sell_order_list";//买家订单
    public static final String YingXiao = URL + "/api.php/Goods/zhekou";//营销H5
    public static final String Address = URL + "/api.php/addr/myAdress";//我的地址
    public static final String AgentTuikuan = URL + "/Api.php/Order/refundOrder";
    ;//用户退款列表
    public static final String VideoList = URL + "/api.php/Video/video_index";//视频列表
    public static final String VideoXiangQing = URL + "/api.php/Video/video_details?video_id=";//视频详情
    public static final String UserXieyi = URL + "/api.php/video/useragree";//用户协议
    public static final String VideoShangchuanXIeyi = URL + "/api.php/video/uprotocol";//上传协议
    public static final String VideoGuiFan = URL + "/api.php/video/videospecification";//视频规范
    public static final String USerXinxi = Common.URL + "/Api.php/My/gerenzhongxin";//用户信息
    public static final String LookOld = Common.URL + "/Api.php/video/guankanlishi";//观看历史
    public static final String IMList = URL + "/api.php/Mycenter/conversation";//聊天列表
    public static final String GuanZhuDz = URL + "/Api.php/Personage/wodeguanzhu";//关注的店主
    public static final String Shoucang = URL + "/Api.php/My/my_collect";//收藏
    public static final String YaoqingTonghang = URL + "/api.php/video/peer";//邀请同行
    public static final String VIPTequan = URL + "/api.php/video/vip";//VIP特权
    public static final String ShopCar = URL + "/api.php/Goods/cart";//购物车

}
