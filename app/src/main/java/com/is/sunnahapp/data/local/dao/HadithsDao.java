package com.is.sunnahapp.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.is.sunnahapp.data.local.entity.Hadiths;

import java.util.List;

/**
 * @author Ahmed Eid Hefny
 * @date 4/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
@Dao
public interface HadithsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Hadiths hadiths);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Hadiths> hadithsList);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Hadiths hadiths);

    @Query("SELECT * FROM Hadiths WHERE collection = :collectionName AND booknumber =:bookNumber ")
    LiveData<List<Hadiths>> getHadithsList(String collectionName, String bookNumber);

    @Query("SELECT * FROM Hadiths WHERE collection = :collection ")
    Hadiths getHadithsByCollection(String collection);

    @Query("SELECT count(*) FROM  Hadiths")
    int getHadithsListCount();

    @Query("DELETE FROM Hadiths")
    void deleteHadithsList();

}
