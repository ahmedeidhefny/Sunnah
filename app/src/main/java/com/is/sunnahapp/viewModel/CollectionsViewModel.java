package com.is.sunnahapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.is.sunnahapp.data.Resource;
import com.is.sunnahapp.data.local.entity.Collections;
import com.is.sunnahapp.data.repository.CollectionsRepository;
import com.is.sunnahapp.ui.base.BaseViewModel;
import com.is.sunnahapp.util.AbsentLiveData;
import com.is.sunnahapp.util.Utils;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Ahmed Eid Hefny
 * @date 4/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class CollectionsViewModel extends BaseViewModel {

    private CollectionsRepository collectionsRepository;

    private final LiveData<Resource<List<Collections>>> worldListData;
    private MutableLiveData<TmpDataHolder> worldListObj = new MutableLiveData<>();

    @Inject
    public CollectionsViewModel(CollectionsRepository repository) {

        this.collectionsRepository = repository;

        worldListData = Transformations.switchMap(worldListObj, obj -> {
            if (obj == null) {
                return AbsentLiveData.create();
            }
            Utils.log("Collections List.");
            return repository.getCollectionsList();
        });

    }

    //region WorldList

    public void setWorldListObj() {

        if (!isLoading) {
            TmpDataHolder tmpDataHolder = new TmpDataHolder();
            // tmpDataHolder.limit = limit;
            // tmpDataHolder.offset = offset;
            // tmpDataHolder.userId = userId;
            // tmpDataHolder.deviceToken = deviceToken;
            worldListObj.setValue(tmpDataHolder);

            // start loading
            setLoadingState(true);
        }
    }

    public LiveData<Resource<List<Collections>>> getWorldListData() {
        return worldListData;
    }

    //endregion

    // region By Country

    public Collections getDataByCountry(String country) {
        return collectionsRepository.getCountryDetailsFromDb(country);
    }

    //endregion

    /**
     * Temporary Data Holder Can contain params to be send to server
     */
    class TmpDataHolder {
        public String limit = "";
        public String offset = "";
        public Boolean isConnected = false;
        public String notificationId = "";
        public String userId = "";
        public String deviceToken = "";
    }
}
