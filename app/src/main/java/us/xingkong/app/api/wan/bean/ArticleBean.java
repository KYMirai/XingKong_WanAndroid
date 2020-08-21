package us.xingkong.app.api.wan.bean;

import us.xingkong.app.api.wan.bean.base.BaseBean;
import us.xingkong.app.api.wan.bean.base.BaseDataBean;

public class ArticleBean extends BaseBean<ArticleBean.ArticleDataBean> {
    public static class ArticleDataBean implements BaseDataBean {
        public int curPage;
        public int offset;
        public int pageCount;
        public int size;
        public int total;
        public boolean over;
        public Data[] datas;

        public static class Data {
            public String apkLink;
            public int audit;
            public String author;
            public boolean canEdit;
            public int chapterId;
            public String chapterName;
            public boolean collect;
            public int courseId;
            public String desc;
            public String descMd;
            public String envelopePic;
            public boolean fresh;
            public int id;
            public String link;
            public String niceDate;
            public String niceShareDate;
            public String origin;
            public String prefix;
            public String projectLink;
            public long publishTime;
            public int realSuperChapterId;
            public int selfVisible;
            public long shareDate;
            public String shareUser;
            public int superChapterId;
            public String superChapterName;
            public Tag[] tags;
            public String title;
            public int type;
            public int userId;
            public int visible;
            public int zan;

            public static class Tag {
                public String name;
                public String url;
            }
        }
    }
}