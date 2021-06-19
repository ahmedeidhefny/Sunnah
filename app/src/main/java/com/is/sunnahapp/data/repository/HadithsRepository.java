package com.is.sunnahapp.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.is.sunnahapp.AppExecutors;
import com.is.sunnahapp.data.AppDatabase;
import com.is.sunnahapp.data.NetworkBoundResource;
import com.is.sunnahapp.data.Resource;
import com.is.sunnahapp.data.local.dao.HadithsDao;
import com.is.sunnahapp.data.local.entity.Hadiths;
import com.is.sunnahapp.data.remote.api.ApiResponse;
import com.is.sunnahapp.data.remote.api.ApiService;
import com.is.sunnahapp.data.remote.model.HadithsApiResponse;
import com.is.sunnahapp.util.Utils;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Islam Elshnawey
 * @date 4/10/20
 * <p>
 * is.elshnawey@gmail.com
 **/
public class HadithsRepository extends BaseRepository {

    // region Variables

    private HadithsDao hadithsDao;
    private ApiService apiService;

    // endregion

    // region Constructor

    @Inject
    HadithsRepository(ApiService apiService, AppExecutors appExecutors, AppDatabase db, HadithsDao hadithsDao) {
        super(apiService, appExecutors, db);
        this.apiService = apiService;
        this.hadithsDao = hadithsDao;
    }

    //endregion

    // region Methods

    /**
     * Get Corona list
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
    public LiveData<Resource<List<Hadiths>>> getHadithsList(String collectionName, String bookNumber,int page, int limit) {

        return new NetworkBoundResource<List<Hadiths>, HadithsApiResponse>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull HadithsApiResponse jsonElement) {
                Utils.log("SaveCallResult of getHadithsList.");

                try {
                    db.runInTransaction(() -> {
                        hadithsDao.deleteHadithsList();

                        hadithsDao.insert(jsonElement.getData());
                    });
                } catch (Exception ex) {
                    Utils.errorLog("Error at ", ex);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Hadiths> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Hadiths>> loadFromDb() {
                Utils.log("Load Recent Hadiths List From Db");
                return hadithsDao.getHadithsList(collectionName , bookNumber);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<HadithsApiResponse>> createCall() {
                return apiService.getHadithListOfBook(collectionName,bookNumber ,page, limit);

            }

            @Override
            protected void onFetchFailed(String message) {
                Utils.log("Fetch Failed (getRecentCollectionsList) : " + message);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Boolean>> getNextPageHadithsListByKey(String collectionName, String bookNumber ,int page , int limit) {

        Log.d("HadithsRepository", "here");
        final MediatorLiveData<Resource<Boolean>> statusLiveData = new MediatorLiveData<>();

        LiveData<ApiResponse<HadithsApiResponse>>  apiResponse = apiService.getHadithListOfBook(collectionName, bookNumber, page, limit);
        Log.d("HadithsRepository", "apiRes"+apiResponse);

        statusLiveData.addSource(apiResponse, response -> {
            Log.d("HadithsRepository", "addsource");

            statusLiveData.removeSource(apiResponse);

            //noinspection Constant Conditions
            if (response.isSuccessful()) {
                Log.d("HadithsRepository", "success");
                if (response.body != null) {
                    appExecutors.diskIO().execute(() -> {

                        try {
                            db.runInTransaction(() -> {
                                Log.d("HadithsRepository", "insert"+response.body.getData());
                                hadithsDao.insert(response.body.getData());
                            });
                        } catch (Exception ex) {
                            Log.d("Error at ", ex.toString());
                        }

                        if (response.body != null) {
                            if (response.body.getNext() ==0) {
                                statusLiveData.postValue(Resource.success(false));
                                Log.d("HadithsRepository", "Resource.success(false)");
                            } else {
                                statusLiveData.postValue(Resource.success(true));
                                Log.d("HadithsRepository", "Resource.success(true)");
                            }
                        }
                    });
                } else {
                    statusLiveData.postValue(Resource.error(response.errorMessage, null));
                }

            } else {
                Log.d("HadithsRepository", "not success");
                statusLiveData.postValue(Resource.error(response.errorMessage, null));
            }
        });

        return statusLiveData;

    }


    public int getCount() {
        return hadithsDao.getHadithsListCount();
    }

    /**
     * Load Country Details
     */
    public Hadiths getCountryDetailsFromDb(String country) {
        return hadithsDao.getHadithsByCollection(country);
    }

    //endregion
}
