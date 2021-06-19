package com.is.sunnahapp.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.is.sunnahapp.data.local.entity.Chapters;

import java.util.List;

/**
 * @author Ahmed Eid Hefny
 * @date 4/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
@Dao
public interface ChaptersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Chapters chapters);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Chapters> chaptersList);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Chapters chapters);

    @Query("SELECT * FROM Chapters")
    LiveData<List<Chapters>> getChaptersList();

    @Query("SELECT * FROM Chapters WHERE booknumber = :number ")
    Chapters  getChaptersByCountry(String number);

    @Query("SELECT count(*) FROM  Chapters")
    int getChaptersListCount();

    @Query("DELETE FROM Chapters")
    void deleteChaptersList();
}
