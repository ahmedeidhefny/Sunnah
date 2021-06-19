package com.is.sunnahapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.is.sunnahapp.Config;
import com.is.sunnahapp.data.Resource;
import com.is.sunnahapp.data.local.entity.Hadiths;
import com.is.sunnahapp.data.repository.HadithsRepository;
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
public class HadithsViewModel extends BaseViewModel {

    private HadithsRepository booksRepository;

    private final LiveData<Resource<List<Hadiths>>> hadithsListData;
    private MutableLiveData<TmpDataHolder> hadithsListObj = new MutableLiveData<>();

    private final LiveData<Resource<Boolean>> nextPagebookListData;
    private final MutableLiveData<TmpDataHolder> nextPagebookListObj =
            new MutableLiveData<>();

    public String collectionName;
    public String bookNumber;

    @Inject
    public HadithsViewModel(HadithsRepository repository) {

        this.booksRepository = repository;

        hadithsListData = Transformations.switchMap(hadithsListObj, obj -> {
            if (obj == null) {
                return AbsentLiveData.create();
            }
            Utils.log("Hadiths List...");
            return repository.getHadithsList(obj.collectionName, obj.bookNumber, obj.page, obj.limit);
        });


        nextPagebookListData = Transformations.switchMap(nextPagebookListObj, obj -> {

            if (obj == null) {
                return AbsentLiveData.create();
            }
            Utils.log("get next hadiths List...");
            return repository.getNextPageHadithsListByKey(obj.collectionName, obj.bookNumber, obj.page, obj.limit);

        });

    }

    //region HadithsList

    public void setHadithsListObj(String collectionName, String bookNumber, int page) {

        if (!isLoading) {
            TmpDataHolder tmpDataHolder = new TmpDataHolder();
            tmpDataHolder.limit = Config.PAGING_LIMIT;
            tmpDataHolder.page = page;
            tmpDataHolder.collectionName = collectionName;
            tmpDataHolder.bookNumber = bookNumber;
            hadithsListObj.setValue(tmpDataHolder);

            // start loading
            setLoadingState(true);
        }
    }

    public LiveData<Resource<List<Hadiths>>> getHadithsListData() {
        return hadithsListData;
    }


    public void setNextPageHadithListObj(String collectionName, String bookNumber, int page) {

        if (!isLoading) {
            TmpDataHolder tmpDataHolder = new TmpDataHolder();
            tmpDataHolder.collectionName = collectionName;
            tmpDataHolder.page = page;
            tmpDataHolder.limit = Config.PAGING_LIMIT;
            tmpDataHolder.bookNumber = bookNumber;
            nextPagebookListObj.setValue(tmpDataHolder);

            // start loading
            setLoadingState(true);
        }
    }

    public LiveData<Resource<Boolean>> getNextPageHadithListData() {
        return nextPagebookListData;
    }


    //endregion

    // region By Country

    public Hadiths getDataByCountry(String country) {
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
