package com.is.sunnahapp.data.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.is.sunnahapp.AppExecutors;
import com.is.sunnahapp.data.AppDatabase;
import com.is.sunnahapp.data.NetworkBoundResource;
import com.is.sunnahapp.data.Resource;
import com.is.sunnahapp.data.local.dao.ChaptersDao;
import com.is.sunnahapp.data.local.entity.Chapters;
import com.is.sunnahapp.data.remote.api.ApiResponse;
import com.is.sunnahapp.data.remote.api.ApiService;
import com.is.sunnahapp.data.remote.model.ChaptersApiResponse;
import com.is.sunnahapp.util.Utils;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Islam Elshnawey
 * @date 4/10/20
 * <p>
 * is.elshnawey@gmail.com
 **/
public class ChaptersRepository extends BaseRepository {

    // region Variables

    private ChaptersDao chaptersDao;
    private ApiService apiService;

    // endregion

    // region Constructor

    @Inject
    ChaptersRepository(ApiService apiService, AppExecutors appExecutors, AppDatabase db, ChaptersDao chaptersDao) {
        super(apiService, appExecutors, db);
        this.apiService = apiService;
        this.chaptersDao = chaptersDao;
    }

    //endregion

    // region Methods

    /**
     * Get Chapters list
     * <p>
     * We are using this method to fetch the movies list
     * NetworkBoundResource is part of the Android architecture
     * components. You will notice that this is a modified version of
     * that class. That class is based on LiveData but here we are
     * using Observable from RxJava.
     * <p>
     * There are three methods called:
     * a. fetch data from server
     * b. fetch data from local
     * c. save data from api in local
     * <p>
     * So basically we fetch data from server, store it locally
     * and then fetch data from local and update the UI with
     * this data.
     *
     * @return
     */
    public LiveData<Resource<List<Chapters>>> getChaptersList(String collectionName, String bookNumber, int page, int limit) {

        return new NetworkBoundResource<List<Chapters>, ChaptersApiResponse>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull ChaptersApiResponse jsonElement) {
                Utils.log("SaveCallResult of getChaptersList.");

                try {
                    db.runInTransaction(() -> {
                        chaptersDao.deleteChaptersList();

                        chaptersDao.insert(jsonElement.getData());
                    });
                } catch (Exception ex) {
                    Utils.errorLog("Error at ", ex);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Chapters> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Chapters>> loadFromDb() {
                Utils.log("Load Recent Chapters List From Db");
                return chaptersDao.getChaptersList();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ChaptersApiResponse>> createCall() {
                return apiService.getListOfChapters(collectionName,bookNumber ,page, limit);

            }

            @Override
            protected void onFetchFailed(String message) {
                Utils.log("Fetch Failed (getRecentCollectionsList) : " + message);
            }
        }.asLiveData();
    }

    public int getCount() {
        return chaptersDao.getChaptersListCount();
    }

    /**
     * Load Country Details
     */
    public Chapters getCountryDetailsFromDb(String country) {
        return chaptersDao.getChaptersByCountry(country);
    }

    //endregion
}
