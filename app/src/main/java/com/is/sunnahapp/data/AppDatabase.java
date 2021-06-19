package com.is.sunnahapp.data;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.is.sunnahapp.data.local.Converters;
import com.is.sunnahapp.data.local.dao.BooksDao;
import com.is.sunnahapp.data.local.dao.ChaptersDao;
import com.is.sunnahapp.data.local.dao.CollectionDao;
import com.is.sunnahapp.data.local.dao.HadithsDao;
import com.is.sunnahapp.data.local.entity.Books;
import com.is.sunnahapp.data.local.entity.Chapters;
import com.is.sunnahapp.data.local.entity.Collections;
import com.is.sunnahapp.data.local.entity.Hadiths;

/**
 * @author Ahmed Eid Hefny
 * @date 4/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/

@Database(entities = {
        Collections.class,
        Books.class,
        Chapters.class,
        Hadiths.class
}, version = 1, exportSchema = false)

@TypeConverters({Converters.class})

public abstract class AppDatabase extends RoomDatabase {

    public abstract CollectionDao collectionDao();

    public abstract BooksDao booksDao();

    public abstract ChaptersDao chaptersDao();

    public abstract HadithsDao hadithsDao();

}