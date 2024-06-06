package com.ai.ai_gen.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class UtilViewBean implements Parcelable {
    private int total;
    private int code;
    private String msg;
    public List<RowsBean> rows;
    public UtilViewBean(){
        rows=new ArrayList<>();
    }

    public UtilViewBean(int total, int code, String msg, List<RowsBean> rows) {
        this.total = total;
        this.code = code;
        this.msg = msg;
        this.rows = rows;
    }

    protected UtilViewBean(Parcel in) {
        total = in.readInt();
        code = in.readInt();
        msg = in.readString();
        rows = in.createTypedArrayList(RowsBean.CREATOR);
    }

    public static final Creator<UtilViewBean> CREATOR = new Creator<UtilViewBean>() {
        @Override
        public UtilViewBean createFromParcel(Parcel in) {
            return new UtilViewBean(in);
        }

        @Override
        public UtilViewBean[] newArray(int size) {
            return new UtilViewBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(total);
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeTypedList(rows);
    }

    public static class RowsBean implements Parcelable {
        private String name;
        private String imgUrl;
        private String content;
        private boolean colection;

        public RowsBean(String name, String imgUrl, boolean colection, String content) {
            this.name = name;
            this.imgUrl = imgUrl;
            this.colection = colection;
            this.content = content;
        }

        protected RowsBean(Parcel in) {
            name = in.readString();
            imgUrl = in.readString();
            content = in.readString();
            colection = in.readByte() != 0;
        }
        public static final Creator<RowsBean> CREATOR = new Creator<RowsBean>() {
            @Override
            public RowsBean createFromParcel(Parcel in) {
                return new RowsBean(in);
            }
            @Override
            public RowsBean[] newArray(int size) {
                return new RowsBean[size];
            }
        };

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

        public boolean isColection() {
            return colection;
        }

        public void setColection(boolean colection) {
            this.colection = colection;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(imgUrl);
            dest.writeString(content);
            dest.writeByte((byte) (colection ? 1 : 0));
        }
    }
}
