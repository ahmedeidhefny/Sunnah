package com.is.sunnahapp.data.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.is.sunnahapp.AppExecutors;
import com.is.sunnahapp.data.AppDatabase;
import com.is.sunnahapp.data.NetworkBoundResource;
import com.is.sunnahapp.data.Resource;
import com.is.sunnahapp.data.local.dao.CollectionDao;
import com.is.sunnahapp.data.local.entity.Collections;
import com.is.sunnahapp.data.remote.api.ApiResponse;
import com.is.sunnahapp.data.remote.api.ApiService;
import com.is.sunnahapp.data.remote.model.CollectionsApiResponse;
import com.is.sunnahapp.util.Utils;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Islam Elshnawey
 * @date 4/10/20
 * <p>
 * is.elshnawey@gmail.com
 **/
public class CollectionsRepository extends BaseRepository {

    // region Variables

    private CollectionDao collectionDao;
    private ApiService apiService;

    //endregion

    // region Constructor

    @Inject
    CollectionsRepository(ApiService apiService, AppExecutors appExecutors, AppDatabase db, CollectionDao coronaDao) {
        super(apiService, appExecutors, db);
        this.apiService = apiService;
        this.collectionDao = coronaDao;
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
    public LiveData<Resource<List<Collections>>> getCollectionsList() {

        return new NetworkBoundResource<List<Collections>, CollectionsApiResponse>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull CollectionsApiResponse jsonElement) {
                Utils.log("SaveCallResult of getCollectionsList.");

                try {
                    db.runInTransaction(() -> {
                        collectionDao.deleteCollectionsList();

                       /* List<Corona> coronaList = new ArrayList<>();

                        JSONArray jsonArray = new JSONArray(jsonElement);

                        for(int i=0; i<jsonArray.length(); i++){

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject imageObject = jsonObject.getJSONObject("countryInfo");

                            String imageUrl = imageObject.getString("flag");
                            String country   = jsonObject.getString("country");
                            String cases     = "Confirmed" + jsonObject.getInt("cases");
                            String deaths    = "Deaths"+ jsonObject.getInt("deaths");
                            String recovered = "Recoverd" + jsonObject.getInt("recovered");

                            coronaList.add(new Corona(imageUrl, country, cases, deaths, recovered));
                        }*/

                        collectionDao.insert(jsonElement.getData());
                    });
                } catch (Exception ex) {
                    Utils.errorLog("Error at ", ex);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Collections> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Collections>> loadFromDb() {
                Utils.log("Load Recent Collections List From Db");
                return collectionDao.getCollectionList();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CollectionsApiResponse>> createCall() {
                return apiService.getCollectionsList();

            }

            @Override
            protected void onFetchFailed(String message) {
                Utils.log("Fetch Failed (getRecentCollectionsList) : " + message);
            }
        }.asLiveData();
    }

    public int getCount() {
        return collectionDao.getCollectionListCount();
    }

    /**
     * Load Country Details
     */
    public Collections getCountryDetailsFromDb(String name) {
        return collectionDao.getCollectionByName(name);
    }

    //endregion
}
