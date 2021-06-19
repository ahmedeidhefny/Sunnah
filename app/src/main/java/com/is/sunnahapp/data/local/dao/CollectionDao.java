package com.is.sunnahapp.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.is.sunnahapp.data.local.entity.Collections;

import java.util.List;

/**
 * @author Ahmed Eid Hefny
 * @date 4/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
@Dao
public interface CollectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Collections collection);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Collections> collectionList);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Collections collection);

    @Query("SELECT * FROM Collections")
    LiveData<List<Collections>> getCollectionList();

    @Query("SELECT * FROM Collections WHERE name = :name ")
    Collections getCollectionByName(String name);

    @Query("SELECT count(*) FROM  Collections")
    int getCollectionListCount();

    @Query("DELETE FROM Collections")
    void deleteCollectionsList();
}
