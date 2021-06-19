package com.is.sunnahapp.data.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ahmed Eid Hefny
 * @date 4/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
@Entity
public class Collections implements Parcelable {

    /*
    *
    * {
    "data": [
        {
            "name": "bukhari",
            "hasBooks": true,
            "hasChapters": true,
            "collection": [
                {
                    "lang": "en",
                    "title": "Sahih al-Bukhari",
                    "shortIntro": "Sahih al-Bukhari is a collection of hadith compiled by Imam Muhammad al-Bukhari (d. 256 AH/870 AD) (rahimahullah).\r\n\r\nHis collection is recognized by the overwhelming majority of the Muslim world to be the most authentic collection of reports of the <i>Sunnah</i> of the Prophet Muhammad (ﷺ). It contains over 7500 hadith (with repetitions) in 97 books.\r\n\r\nThe translation provided here is by Dr. M. Muhsin Khan."
                },
                {
                    "lang": "ar",
                    "title": "صحيح البخاري",
                    "shortIntro": "Sahih al-Bukhari is a collection of hadith compiled by Imam Muhammad al-Bukhari (d. 256 AH/870 AD) (rahimahullah).\r\n\r\nHis collection is recognized by the overwhelming majority of the Muslim world to be the most authentic collection of reports of the <i>Sunnah</i> of the Prophet Muhammad (ﷺ). It contains over 7500 hadith (with repetitions) in 97 books.\r\n\r\nThe translation provided here is by Dr. M. Muhsin Khan."
                }
            ],
            "totalHadith": 7291,
            "totalAvailableHadith": 7291
        }
    ],
    "total": 17,
    "limit": 1,
    "previous": null,
    "next": 2
   }*/

    public Collections() {
    }

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private int id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("hasBooks")
    private boolean hasbooks;
    @Expose
    @SerializedName("hasChapters")
    private boolean haschapters;
    @Expose
    @SerializedName("collection") // custom type object
    private List<CollectionEntity> collection;
    @Expose
    @SerializedName("totalHadith")
    private int totalhadith;
    @Expose
    @SerializedName("totalAvailableHadith")
    private int totalavailablehadith;

    protected Collections(Parcel in) {
        totalavailablehadith = in.readInt();
        totalhadith = in.readInt();
        haschapters = in.readByte() != 0;
        hasbooks = in.readByte() != 0;
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalavailablehadith);
        dest.writeInt(totalhadith);
        dest.writeByte((byte) (haschapters ? 1 : 0));
        dest.writeByte((byte) (hasbooks ? 1 : 0));
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Collections> CREATOR = new Creator<Collections>() {
        @Override
        public Collections createFromParcel(Parcel in) {
            return new Collections(in);
        }

        @Override
        public Collections[] newArray(int size) {
            return new Collections[size];
        }
    };

    public int getTotalavailablehadith() {
        return totalavailablehadith;
    }

    public void setTotalavailablehadith(int totalavailablehadith) {
        this.totalavailablehadith = totalavailablehadith;
    }

    public int getTotalhadith() {
        return totalhadith;
    }

    public void setTotalhadith(int totalhadith) {
        this.totalhadith = totalhadith;
    }

    public List<CollectionEntity> getCollection() {
        return collection;
    }

    public void setCollection(List<CollectionEntity> collection) {
        this.collection = collection;
    }

    public boolean getHaschapters() {
        return haschapters;
    }

    public void setHaschapters(boolean haschapters) {
        this.haschapters = haschapters;
    }

    public boolean getHasbooks() {
        return hasbooks;
    }

    public void setHasbooks(boolean hasbooks) {
        this.hasbooks = hasbooks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class CollectionEntity implements Serializable {
        @Expose
        @SerializedName("shortIntro")
        private String shortintro;
        @Expose
        @SerializedName("title")
        private String title;
        @Expose
        @SerializedName("lang")
        private String lang;

        public String getShortintro() {
            return shortintro;
        }

        public void setShortintro(String shortintro) {
            this.shortintro = shortintro;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }
    }


}
