package us.xingkong.app.api.wan.bean.base;

public abstract class BaseBean<T extends BaseDataBean> {
    public T data;
    public int errorCode;
    public String errorMsg;
}