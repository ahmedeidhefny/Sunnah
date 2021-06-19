package com.is.sunnahapp.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.is.sunnahapp.AppExecutors;
import com.is.sunnahapp.Config;
import com.is.sunnahapp.data.AppDatabase;
import com.is.sunnahapp.data.NetworkBoundResource;
import com.is.sunnahapp.data.Resource;
import com.is.sunnahapp.data.local.dao.BooksDao;
import com.is.sunnahapp.data.local.entity.Books;
import com.is.sunnahapp.data.remote.api.ApiResponse;
import com.is.sunnahapp.data.remote.api.ApiService;
import com.is.sunnahapp.data.remote.model.BooksApiResponse;
import com.is.sunnahapp.util.Utils;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Islam Elshnawey
 * @date 4/10/20
 * <p>
 * is.elshnawey@gmail.com
 **/
public class BooksRepository extends BaseRepository {

    // region Variables

    private BooksDao booksDao;
    private ApiService apiService;

    //endregion

    // region Constructor

    @Inject
    BooksRepository(ApiService apiService, AppExecutors appExecutors, AppDatabase db, BooksDao booksDao) {
        super(apiService, appExecutors, db);
        this.apiService = apiService;
        this.booksDao = booksDao;
    }

    //endregion

    // region Methods

    /**
     * Get Books list
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
     * @return ...
     */
    public LiveData<Resource<List<Books>>> getBooksList(String collectionName, int page) {

        return new NetworkBoundResource<List<Books>, BooksApiResponse>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull BooksApiResponse jsonElement) {
                Utils.log("SaveCallResult of getBooksList...");

                try {
                    db.runInTransaction(() -> {
                        booksDao.deleteBooksList();
                        booksDao.insert(jsonElement.getData());
                    });
                } catch (Exception ex) {
                    Utils.errorLog("Error at ", ex);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Books> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Books>> loadFromDb() {
                Utils.log("Load Recent Books List From Db");
                return booksDao.getBooksList();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<BooksApiResponse>> createCall() {
                return apiService.getListOfBook(collectionName, page, Config.PAGING_LIMIT);

            }

            @Override
            protected void onFetchFailed(String message) {
                Utils.log("Fetch Failed (get Recent Books List) : " + message);
            }

        }.asLiveData();
    }

    public LiveData<Resource<Boolean>> getNextPageBooksListByKey(String collectionName, int page) {

        final MediatorLiveData<Resource<Boolean>> statusLiveData = new MediatorLiveData<>();

        LiveData<ApiResponse<BooksApiResponse>> apiResponse = apiService.getListOfBook(collectionName, page, Config.PAGING_LIMIT);


        statusLiveData.addSource(apiResponse, response -> {

            statusLiveData.removeSource(apiResponse);

            //noinspection Constant Conditions
            if (response.isSuccessful()) {

                if (response.body != null) {
                    appExecutors.diskIO().execute(() -> {

                        try {
                            db.runInTransaction(() -> {
                                booksDao.insert(response.body.getData());
                            });
                        } catch (Exception ex) {
                            Log.d("Error at ", ex.toString());
                        }

                        if (response.body != null) {
                            if (response.body.getNext() == 0) {
                                statusLiveData.postValue(Resource.success(false));
                                Log.d("BooksRepository", "Resource.success(false)");
                            } else {
                                statusLiveData.postValue(Resource.success(true));
                                Log.d("BooksRepository","Resource.success(true)");
                            }
                        }
                    });
                } else {
                    statusLiveData.postValue(Resource.error(response.errorMessage, null));
                }

            } else {
                statusLiveData.postValue(Resource.error(response.errorMessage, null));
            }
        });

        return statusLiveData;

    }

    public int getCount() {
        return booksDao.getBooksListCount();
    }

    //endregion
}
