package com.is.sunnahapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.is.sunnahapp.data.Resource;
import com.is.sunnahapp.data.local.entity.Chapters;
import com.is.sunnahapp.data.repository.ChaptersRepository;
import com.is.sunnahapp.ui.base.BaseViewModel;
import com.is.sunnahapp.util.AbsentLiveData;
import com.is.sunnahapp.util.Utils;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Ahmed Eid Hefny
 * @date 5/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class ChaptersViewModel extends BaseViewModel {

    private ChaptersRepository booksRepository;

    private final LiveData<Resource<List<Chapters>>> chaptersListData;
    private MutableLiveData<TmpDataHolder> chaptersListObj = new MutableLiveData<>();

    @Inject
    public ChaptersViewModel(ChaptersRepository repository) {

        this.booksRepository = repository;

        chaptersListData = Transformations.switchMap(chaptersListObj, obj -> {
            if (obj == null) {
                return AbsentLiveData.create();
            }
            Utils.log("Chapters List...");
            return repository.getChaptersList(obj.collectionName ,obj.bookNumber , obj.page , obj.limit);
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
            chaptersListObj.setValue(tmpDataHolder);

            // start loading
            setLoadingState(true);
        }
    }

    public LiveData<Resource<List<Chapters>>> getWorldListData() {
        return chaptersListData;
    }

    //endregion

    // region By Country

    public Chapters getDataByCountry(String country) {
        return booksRepository.getCountryDetailsFromDb(country);
    }

    //endregion

    /**
     * Temporary Data Holder Can contain params to be send to server
     */
    class TmpDataHolder {
        public int limit = 0;
        public int page = 0;
        public Boolean isConnected = false;
        public String collectionName = "";
        public String bookNumber = "";
        public String userId = "";
        public String deviceToken = "";
    }
}
