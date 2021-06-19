package com.is.sunnahapp.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ahmed Eid Hefny
 * @date 4/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
@Entity
public class Hadiths {

    public Hadiths() {
    }

    @PrimaryKey()
    @Expose
    @NonNull
    @SerializedName("hadithNumber")
    private String hadithnumber;
    @Expose
    @SerializedName("hadith")
    private List<HadithEntity> hadith;
    @Expose
    @SerializedName("chapterId")
    private String chapterid;
    @Expose
    @SerializedName("bookNumber")
    private String booknumber;
    @Expose
    @SerializedName("collection")
    private String collection;

    public List<HadithEntity> getHadith() {
        return hadith;
    }

    public void setHadith(List<HadithEntity> hadith) {
        this.hadith = hadith;
    }

    public String getHadithnumber() {
        return hadithnumber;
    }

    public void setHadithnumber(String hadithnumber) {
        this.hadithnumber = hadithnumber;
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

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public static class HadithEntity {
        @Expose
        @SerializedName("grades")
        private List<GradesEntity> grades;
        @Expose
        @SerializedName("body")
        private String body;
        @Expose
        @SerializedName("urn")
        private int urn;
        @Expose
        @SerializedName("chapterTitle")
        private String chaptertitle;
        @Expose
        @SerializedName("chapterNumber")
        private String chapternumber;
        @Expose
        @SerializedName("lang")
        private String lang;

        public List<GradesEntity> getGrades() {
            return grades;
        }

        public void setGrades(List<GradesEntity> grades) {
            this.grades = grades;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public int getUrn() {
            return urn;
        }

        public void setUrn(int urn) {
            this.urn = urn;
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

    public static class GradesEntity {
        @Expose
        @SerializedName("grade")
        private String grade;
        @Expose
        @SerializedName("graded_by")
        private String gradedBy;

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }


        public String getGradedBy() {
            return gradedBy;
        }

        public void setGradedBy(String gradedBy) {
            this.gradedBy = gradedBy;
        }
    }

}
