package com.ai.ai_gen.bean;

import java.util.ArrayList;
import java.util.List;

public class CommunityBean {
    private int total;
    private int code;
    private String msg;
    public List<RowsBean> rows;
    public CommunityBean(){
        rows=new ArrayList<>();
    }

    public CommunityBean(int total, int code, String msg, List<RowsBean> rows) {
        this.total = total;
        this.code = code;
        this.msg = msg;
        this.rows = rows;
    }


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
        private String name;
        private String imgUrl;
        private String content;
        private String likenum;
        private String colectionnum;
        private String commentsid;
        private int commentsnum;

        public RowsBean(String name, String imgUrl, String likenum, String colectionnum, String content, String comments, int commentsnum) {
            this.name = name;
            this.imgUrl = imgUrl;
            this.likenum = likenum;
            this.content = content;
            this.colectionnum = colectionnum;
            this.commentsid = comments;
            this.commentsnum = commentsnum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getColection() {
            return colectionnum;
        }
        public String getlikenum() {
            return likenum;
        }


        public String getCommentsid() {
            return commentsid;
        }

        public void setCommentsid(String commentsid) {
            this.commentsid = commentsid;
        }

        public int getCommentsnum() {
            return commentsnum;
        }

        public void setCommentsnum(int commentsnum) {
            this.commentsnum = commentsnum;
        }
    }
}
