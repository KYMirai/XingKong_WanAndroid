package us.xingkong.app.api.wan.bean.data;

import java.util.List;

import us.xingkong.app.api.wan.bean.base.BaseDataBean;

public class UserDataBean implements BaseDataBean {
    public boolean admin;
    public List<Object> chapterTops;
    public List<Object> collectIds;
    public String
            email,
            icon,
            nickname,
            password,
            publicName,
            token,
            username;
    public int
            id,
            type;
}