package com.is.sunnahapp.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ahmed Eid Hefny
 * @date 1/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
@Entity
public class Chapters {

    public Chapters() {
    }

    @PrimaryKey
    @NonNull
    @Expose
    @SerializedName("chapterId")
    private String chapterid;
    @Expose
    @SerializedName("bookNumber")
    private String booknumber;
    @Expose
    @SerializedName("chapter")
    private List<ChapterEntity> chapter;

    public List<ChapterEntity> getChapter() {
        return chapter;
    }

    public void setChapter(List<ChapterEntity> chapter) {
        this.chapter = chapter;
    }

    public String getChapterid() {
        return chapterid;
    }

    public void setChapterid(String chapterid) {
        this.chapterid = chapterid;
    }

    public String getBooknumber() {
        return booknumber;
    }

    public void setBooknumber(String booknumber) {
        this.booknumber = booknumber;
    }


    public static class ChapterEntity {
        @Expose
        @SerializedName("ending")
        private String ending;
        @Expose
        @SerializedName("intro")
        private String intro;
        @Expose
        @SerializedName("chapterTitle")
        private String chaptertitle;
        @Expose
        @SerializedName("chapterNumber")
        private String chapternumber;
        @Expose
        @SerializedName("lang")
        private String lang;

        public String getEnding() {
            return ending;
        }

        public void setEnding(String ending) {
            this.ending = ending;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getChaptertitle() {
            return chaptertitle;
        }

        public void setChaptertitle(String chaptertitle) {
            this.chaptertitle = chaptertitle;
        }

        public String getChapternumber() {
            return chapternumber;
        }

        public void setChapternumber(String chapternumber) {
            this.chapternumber = chapternumber;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }
    }

}
