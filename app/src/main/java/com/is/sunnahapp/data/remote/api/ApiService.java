package com.is.sunnahapp.data.remote.api;

import androidx.lifecycle.LiveData;

import com.is.sunnahapp.data.local.entity.Collections;
import com.is.sunnahapp.data.local.entity.HadithPojo;
import com.is.sunnahapp.data.remote.model.BooksApiResponse;
import com.is.sunnahapp.data.remote.model.ChaptersApiResponse;
import com.is.sunnahapp.data.remote.model.CollectionsApiResponse;
import com.is.sunnahapp.data.remote.model.HadithsApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * @author Ahmed Eid Hefny
 * @date 1/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public interface ApiService {

    //region Hadiths

    /**
     * Get a randomly selected hadith
     * @return ...
     */
    @GET("hadiths/random")
    Observable<HadithPojo> getHadith();

    /**
     * Get a list of hadiths
     * @param page Page Number
     * @param limit Number of Rows
     * @return ...
     */
    @GET("hadiths?page=1&limit=50")
    Observable<HadithPojo> getHadithList(@Query("page") String page,
                                         @Query("limit") String limit);

    /**
     * Get a hadith by its URN
     * @return
     */
    @GET("hadiths/{urn}")
    Observable<HadithPojo> getHadithsURN();

    //endregion

    //region Collections

    /**
     * Get the list of collections
     * @return ...
     */
    @GET("collections?limit=50&page=1")
    LiveData<ApiResponse<CollectionsApiResponse>> getCollectionsList();

    /**
     * Get collection by name
     * @return ...
     */
    @GET("collections/{collectionName}")
    Observable<Collections> getCollectionByName(@Path("collectionName") String collectionName);

    /**
     * Get the list of books for a collection
     * @return ...
     */
    @GET("collections/{collectionName}/books")
    LiveData<ApiResponse<BooksApiResponse>> getListOfBook(@Path("collectionName") String collectionName,
                                                          @Query("page") int page,
                                                          @Query("limit") int limit);

    /**
     * Get the list of chapters of a book for a collection
     * @return ...
     */
    @GET("collections/{collectionName}/books/{bookNumber}/chapters?limit=50&page=1")
    LiveData<ApiResponse<ChaptersApiResponse>> getListOfChapters(@Path("collectionName") String collectionName,
                                                                 @Path("bookNumber") String bookNumber,
                                                                 @Query("page") int page,
                                                                 @Query("limit") int limit);

    /**
     * Get the list of hadith of a book for a collection
     * @return ...
     */
    @GET("collections/{collectionName}/books/{bookNumber}/hadiths")
    LiveData<ApiResponse<HadithsApiResponse>> getHadithListOfBook(@Path("collectionName") String collectionName,
                                                                  @Path("bookNumber") String bookNumber,
                                                                  @Query("page") int page,
                                                                  @Query("limit") int limit);

    /**
     * Get a book of a collection
     * @return ...
     */
    @GET("collections/{collectionName}/books/{bookNumber}")
    Observable<HadithPojo> getBookCollection();


    /**
     * Get a Hadith of a collection
     * @return ...
     */
    @GET("collections/{collectionName}/hadiths/{hadithNumber}")
    Observable<HadithPojo> getHadithOfCollection();

    //endregion

}
