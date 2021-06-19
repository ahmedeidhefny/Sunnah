package com.is.sunnahapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.is.sunnahapp.Config;
import com.is.sunnahapp.data.Resource;
import com.is.sunnahapp.data.local.entity.Books;
import com.is.sunnahapp.data.repository.BooksRepository;
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
public class BooksViewModel extends BaseViewModel {

    private BooksRepository booksRepository;

    private final LiveData<Resource<List<Books>>> bookListData;
    private MutableLiveData<TmpDataHolder> bookListObj = new MutableLiveData<>();

    private final LiveData<Resource<Boolean>> nextPagebookListData;
    private final MutableLiveData<TmpDataHolder> nextPagebookListObj = new MutableLiveData<>();

    public String collectionName ;

    @Inject
    public BooksViewModel(BooksRepository repository) {

        this.booksRepository = repository;

        bookListData = Transformations.switchMap(bookListObj, obj -> {
            if (obj == null) {
                return AbsentLiveData.create();
            }
            Utils.log("Books List...");
            return repository.getBooksList(obj.collectionName , obj.page );
        });

        nextPagebookListData = Transformations.switchMap(nextPagebookListObj, obj -> {

            if (obj == null) {
                return AbsentLiveData.create();
            }

            return repository.getNextPageBooksListByKey(obj.collectionName,  obj.page);

        });
    }

    //region WorldList

    public void setBookListObj(String collectionName,int page) {

        if (!isLoading) {
            TmpDataHolder tmpDataHolder = new TmpDataHolder();
            tmpDataHolder.collectionName = collectionName;
            tmpDataHolder.page = page;
            tmpDataHolder.limit = Config.PAGING_LIMIT;
            // tmpDataHolder.deviceToken = deviceToken;
            bookListObj.setValue(tmpDataHolder);

            // start loading
            setLoadingState(true);
        }
    }

    public LiveData<Resource<List<Books>>> getBookListData() {

        return bookListData;
    }

    //endregion

    //region Getter And Setter for Next Page Books  List

    public void setNextPageBooksListObj(String collectionName, int page) {
        if (!isLoading) {
            TmpDataHolder tmpDataHolder = new TmpDataHolder();
            tmpDataHolder.collectionName = collectionName;
            tmpDataHolder.page = page;
            tmpDataHolder.limit = Config.PAGING_LIMIT;
            nextPagebookListObj.setValue(tmpDataHolder);

            // start loading
            setLoadingState(true);
        }
    }

    public LiveData<Resource<Boolean>> getNextPageBooksListData() {
        return nextPagebookListData;
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
        public String userId = "";
        public String deviceToken = "";
    }
}
