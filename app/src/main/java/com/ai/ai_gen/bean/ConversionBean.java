package com.ai.ai_gen.bean;

import java.util.ArrayList;
import java.util.List;

public class ConversionBean {

    private int total;
    public List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private String content;
        private int type;

        public RowsBean(){}


        public RowsBean(String content, int type) {
            this.content = content;
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int gettype() {
            return type;
        }

        @Override
        public String toString() {
            return "";
        }

    }
    public ConversionBean(){
        this.total=0;
        rows=new ArrayList<>();
    }
}
