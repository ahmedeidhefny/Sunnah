package com.is.sunnahapp.data.repository;


import com.is.sunnahapp.AppExecutors;
import com.is.sunnahapp.data.AppDatabase;
import com.is.sunnahapp.data.remote.api.ApiService;
import com.is.sunnahapp.util.Connectivity;
import com.is.sunnahapp.util.Utils;

import javax.inject.Inject;

/**
 * Parent Class of All Repository Class in this project
 * @author Islam Elshnawey
 * @date 2020-03-25
 * <p>
 * is.elshnawey@gmail.com
 **/

public abstract class BaseRepository {


    //region Variables

    protected final ApiService apiService;
    protected final AppExecutors appExecutors;
    protected final AppDatabase db;

    @Inject
    protected Connectivity connectivity;

    //endregion

    //region Constructor

    /**
     * Constructor of PSRepository
     * @param apiService  API Service Instance
     * @param appExecutors Executors Instance
     * @param db Panacea-Soft DB
     */
    protected BaseRepository(ApiService apiService, AppExecutors appExecutors, AppDatabase db) {
        Utils.log("Inside Base repository");

        this.apiService = apiService;
        this.appExecutors = appExecutors;
        this.db = db;
    }

    //endregion


}
