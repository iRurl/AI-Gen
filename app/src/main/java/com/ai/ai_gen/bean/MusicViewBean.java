package com.ai.ai_gen.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MusicViewBean implements Parcelable {
    private int total;
    private int code;
    private String msg;
    private List<MusicBean> rows;

    protected MusicViewBean(Parcel in) {
        total = in.readInt();
        code = in.readInt();
        msg = in.readString();
        rows = new ArrayList<>();
        in.readList(rows, MusicBean.class.getClassLoader());
    }

    public static final Creator<MusicViewBean> CREATOR = new Creator<MusicViewBean>() {
        @Override
        public MusicViewBean createFromParcel(Parcel in) {
            return new MusicViewBean(in);
        }

        @Override
        public MusicViewBean[] newArray(int size) {
            return new MusicViewBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(total);
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeList(rows);
    }

    // Getters and Setters
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

    public List<MusicBean> getRows() {
        return rows;
    }

    public void setRows(List<MusicBean> rows) {
        this.rows = rows;
    }

    public static class MusicBean implements Parcelable {
        private String album_img;
        private String song_url;
        private String musicname;

        public MusicBean() {
        }

        public MusicBean(String albumImg, String songUrl, String musicname) {
            this.album_img = albumImg;
            this.song_url = songUrl;
            this.musicname = musicname;
        }

        protected MusicBean(Parcel in) {
            album_img = in.readString();
            song_url = in.readString();
            musicname = in.readString();
        }

        public static final Creator<MusicBean> CREATOR = new Creator<MusicBean>() {
            @Override
            public MusicBean createFromParcel(Parcel in) {
                return new MusicBean(in);
            }

            @Override
            public MusicBean[] newArray(int size) {
                return new MusicBean[size];
            }
        };

        public String getAlbum_img() {
            return album_img;
        }

        public void setAlbum_img(String album_img) {
            this.album_img = album_img;
        }

        public String getSong_url() {
            return song_url;
        }

        public void setSong_url(String song_url) {
            this.song_url = song_url;
        }

        public String getMusicname() {
            return musicname;
        }

        public void setMusicname(String musicname) {
            this.musicname = musicname;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(album_img);
            dest.writeString(song_url);
            dest.writeString(musicname);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public String toString() {
            return "MusicBean{" +
                    "musicname='" + musicname + '\'' +
                    ", album_img='" + album_img + '\'' +
                    ", song_url='" + song_url + '\'' +
                    '}';
        }
    }
}
