package com.is.sunnahapp.data.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

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
public class Books implements Parcelable {

    public Books() {
    }

    @PrimaryKey()
    @NonNull
    @Expose
    @SerializedName("bookNumber")
    private String booknumber;

    @Expose
    @SerializedName("book")// custom type object
    private List<BookEntity> book;
    @Expose
    @SerializedName("hadithStartNumber")
    private int hadithstartnumber;
    @Expose
    @SerializedName("hadithEndNumber")
    private int hadithendnumber;
    @Expose
    @SerializedName("numberOfHadith")
    private int numberofhadith;

    protected Books(Parcel in) {
        booknumber = in.readString();
        hadithstartnumber = in.readInt();
        hadithendnumber = in.readInt();
        numberofhadith = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(booknumber);
        dest.writeInt(hadithstartnumber);
        dest.writeInt(hadithendnumber);
        dest.writeInt(numberofhadith);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Books> CREATOR = new Creator<Books>() {
        @Override
        public Books createFromParcel(Parcel in) {
            return new Books(in);
        }

        @Override
        public Books[] newArray(int size) {
            return new Books[size];
        }
    };

    public int getNumberofhadith() {
        return numberofhadith;
    }

    public void setNumberofhadith(int numberofhadith) {
        this.numberofhadith = numberofhadith;
    }

    public int getHadithendnumber() {
        return hadithendnumber;
    }

    public void setHadithendnumber(int hadithendnumber) {
        this.hadithendnumber = hadithendnumber;
    }

    public int getHadithstartnumber() {
        return hadithstartnumber;
    }

    public void setHadithstartnumber(int hadithstartnumber) {
        this.hadithstartnumber = hadithstartnumber;
    }

    public List<BookEntity> getBook() {
        return book;
    }

    public void setBook(List<BookEntity> book) {
        this.book = book;
    }

    public String getBooknumber() {
        return booknumber;
    }

    public void setBooknumber(String booknumber) {
        this.booknumber = booknumber;
    }

    public static class BookEntity {
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("lang")
        private String lang;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

    }

}