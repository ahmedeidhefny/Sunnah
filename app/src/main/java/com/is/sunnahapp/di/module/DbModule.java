package com.is.sunnahapp.di.module;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.is.sunnahapp.data.AppDatabase;
import com.is.sunnahapp.data.local.dao.BooksDao;
import com.is.sunnahapp.data.local.dao.ChaptersDao;
import com.is.sunnahapp.data.local.dao.CollectionDao;
import com.is.sunnahapp.data.local.dao.HadithsDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ahmed Eid Hefny
 * @date 4/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/

@Module
public class DbModule {

    /**
     * The method returns the Database object
     * */
    @Provides
    @Singleton
    AppDatabase provideDatabase(@NonNull Application application) {
        return Room.databaseBuilder(application,
                AppDatabase.class, "sunnah.db")
                .allowMainThreadQueries().build();
    }

    /**
     * We need the Dao module.
     * For this, We need the AppDatabase object
     * So we will define the providers for this here in this module.
     * */
    @Provides
    @Singleton
    CollectionDao provideCollectionDao(@NonNull AppDatabase appDatabase) { return appDatabase.collectionDao(); }

    @Provides
    @Singleton
    BooksDao provideBooksDao(@NonNull AppDatabase appDatabase) { return appDatabase.booksDao(); }

    @Provides
    @Singleton
    ChaptersDao provideCoronaDao(@NonNull AppDatabase appDatabase) { return appDatabase.chaptersDao(); }

    @Provides
    @Singleton
    HadithsDao provideHadithsDao(@NonNull AppDatabase appDatabase) { return appDatabase.hadithsDao(); }

}
