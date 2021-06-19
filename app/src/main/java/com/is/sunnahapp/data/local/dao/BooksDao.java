package com.is.sunnahapp.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.is.sunnahapp.data.local.entity.Books;

import java.util.List;

/**
 * @author Ahmed Eid Hefny
 * @date 4/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
@Dao
public interface BooksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Books books);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Books> booksList);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Books books);

    @Query("SELECT * FROM Books")
    LiveData<List<Books>> getBooksList();

    @Query("SELECT * FROM Books WHERE hadithendnumber = :number ")
    Books getBooksBy(String number);

    @Query("SELECT count(*) FROM  Books")
    int getBooksListCount();

    @Query("DELETE FROM Books")
    void deleteBooksList();
}
