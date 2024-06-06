package com.ai.ai_gen.bean;

import java.util.List;

public class ServiceBean {

    /**
     *{"total":10,
     * "rows":[{"id":1,"serviceName":"励志奋斗","imgUrl":"/profile/lizhi.png","link":"metro_query/index"},{"id":2,"serviceName":"温柔治愈","imgUrl":"/profile/wenrou.png","link":"metro_query/index"},{"id":3,"serviceName":"影视台词","imgUrl":"/profile/yinshi.png","link":"metro_query/index"},{"id":4,"serviceName":"书籍摘抄","imgUrl":"/profile/shuji.png","link":"metro_query/index"},{"id":5,"serviceName":"歌词摘录","imgUrl":"/profile/geci.png","link":"metro_query/index"},{"id":6,"serviceName":"广告文案","imgUrl":"/profile/guanggao.png","link":"metro_query/index"},{"id":7,"serviceName":"营销文案","imgUrl":"/profile/yinxiao.png","link":"metro_query/index"},{"id":8,"serviceName":"美食推广","imgUrl":"/profile/meishi.png","link":"metro_query/index"},{"id":9,"serviceName":"毒汤语录","imgUrl":"/profile/dutang.png","link":"metro_query/index"},{"id":10,"serviceName":"全部分类","imgUrl":"/profile/quanbu.png","link":"metro_query/index"}],
     * "code":200,
     * "msg":"查询成功"}
     */

    private int total;
    private int code;
    private String msg;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 2
         * serviceName : 励志奋斗
         * imgUrl : /profile/lizhi.png
         * link : metro_query/index
         */

        private int id;
        private String serviceName;
        private String imgUrl;
        private String link;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }



        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }



        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public static class ParamsBean {
        }

        public RowsBean(String serviceName, String imgUrl, String link) {
            this.serviceName = serviceName;
            this.imgUrl = imgUrl;
            this.link = link;
        }

        @Override
        public String toString() {
            return "MusicBean{" +
                    "serviceName='" + serviceName + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", link='" + link + '\'' +
                    '}';
        }
    }
}
