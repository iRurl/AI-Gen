package com.ai.ai_gen.bean;

import java.util.ArrayList;
import java.util.List;

public class CommentBean {
    private int total;
    private int code;
    private String msg;
    public List<RowsBean> rows;
    public CommentBean(){
        rows=new ArrayList<>();
    }

    public CommentBean(int total, int code, String msg, List<RowsBean> rows) {
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
        private String comment_avatar;
        private String comment_username;
        private String comment_content;
        private String comment_from;


        public RowsBean(String comment_avatar, String comment_username, String comment_from, String comment_content) {
            this.comment_avatar = comment_avatar;
            this.comment_username = comment_username;
            this.comment_from = comment_from;
            this.comment_content = comment_content;
        }

        public String getComment_avatar() {
            return comment_avatar;
        }

        public void setComment_avatar(String comment_avatar) {
            this.comment_avatar = comment_avatar;
        }

        public String getComment_username() {
            return comment_username;
        }

        public void setComment_username(String comment_username) {
            this.comment_username = comment_username;
        }

        public String getComment_content() {
            return comment_content;
        }

        public void setComment_content(String comment_content) {
            this.comment_content = comment_content;
        }

        public String getComment_from() {
            return comment_from;
        }

    }
}
