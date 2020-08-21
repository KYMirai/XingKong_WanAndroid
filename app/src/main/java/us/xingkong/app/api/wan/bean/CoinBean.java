package us.xingkong.app.api.wan.bean;

import us.xingkong.app.api.wan.bean.base.BaseBean;
import us.xingkong.app.api.wan.bean.base.BaseDataBean;

public class CoinBean extends BaseBean<CoinBean.CoinDataBean> {
    public static class CoinDataBean implements BaseDataBean {
        //总积分
        public int coinCount;
        //当前排名
        public int rank;
        public int userId;
        public String username;
    }
}