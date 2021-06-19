package com.is.sunnahapp.data.local;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.is.sunnahapp.data.local.entity.Books;
import com.is.sunnahapp.data.local.entity.Chapters;
import com.is.sunnahapp.data.local.entity.Collections;
import com.is.sunnahapp.data.local.entity.Hadiths;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * @author Ahmed Eid Hefny
 * @date 4/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter // note this annotation
    public String fromCollectionList(List<Collections.CollectionEntity> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Collections.CollectionEntity>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Collections.CollectionEntity> toCollectionList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Collections.CollectionEntity>>() {
        }.getType();
        List<Collections.CollectionEntity> typeCollectionList = gson.fromJson(optionValuesString, type);
        return typeCollectionList;
    }

    @TypeConverter // note this annotation
    public String fromBooksList(List<Books.BookEntity> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Books.BookEntity>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Books.BookEntity> toBooksList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Books.BookEntity>>() {
        }.getType();
        List<Books.BookEntity> typeBooksList = gson.fromJson(optionValuesString, type);
        return typeBooksList;
    }

    @TypeConverter // note this annotation
    public String fromChaptersList(List<Chapters.ChapterEntity> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Chapters.ChapterEntity>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Chapters.ChapterEntity> toChaptersList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Chapters.ChapterEntity>>() {
        }.getType();
        List<Chapters.ChapterEntity> chaptersList = gson.fromJson(optionValuesString, type);
        return chaptersList;
    }

    @TypeConverter // note this annotation
    public String fromHadithsList(List<Hadiths.HadithEntity> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Hadiths.HadithEntity>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Hadiths.HadithEntity> toHadithsList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Hadiths.HadithEntity>>() {
        }.getType();
        List<Hadiths.HadithEntity> hadithsList = gson.fromJson(optionValuesString, type);
        return hadithsList;
    }

    @TypeConverter // note this annotation
    public String fromHadithsGradeList(List<Hadiths.GradesEntity> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Hadiths.GradesEntity>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Hadiths.GradesEntity> toHadithsGradesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Hadiths.GradesEntity>>() {
        }.getType();
        List<Hadiths.GradesEntity> hadithsgradeList = gson.fromJson(optionValuesString, type);
        return hadithsgradeList;
    }
}
