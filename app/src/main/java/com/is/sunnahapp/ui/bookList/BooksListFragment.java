package com.is.sunnahapp.ui.bookList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.is.sunnahapp.Config;
import com.is.sunnahapp.Constants;
import com.is.sunnahapp.R;
import com.is.sunnahapp.data.Resource;
import com.is.sunnahapp.data.Status;
import com.is.sunnahapp.data.binding.FragmentDataBindingComponent;
import com.is.sunnahapp.data.local.entity.Books;
import com.is.sunnahapp.databinding.FragmentBookListBinding;
import com.is.sunnahapp.ui.base.BaseFragment;
import com.is.sunnahapp.ui.base.DataBoundListAdapter;
import com.is.sunnahapp.util.AutoClearedValue;
import com.is.sunnahapp.util.Utils;
import com.is.sunnahapp.viewModel.BooksViewModel;

import java.util.List;

/**
 * @author Ahmed Eid Hefny
 * @date 8/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class BooksListFragment extends BaseFragment implements DataBoundListAdapter.DiffUtilDispatchedInterface {

    private final androidx.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    private BooksViewModel booksViewModel;

    public BooksListAdapter nvAdapter;

    private SearchView searchView;

    private String collectionName ;

    @VisibleForTesting
    private AutoClearedValue<FragmentBookListBinding> binding;
    private AutoClearedValue<BooksListAdapter> adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentBookListBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_list, container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);

        binding.get().setLoadingMore(connectivity.isConnected());
        setHasOptionsMenu(true);
        return binding.get().getRoot();

    }

    @Override
    public void onDispatched() {
        if (booksViewModel.loadingDirection == Utils.LoadingDirection.top) {

            if (binding.get().notificationList != null) {

                LinearLayoutManager layoutManager = (LinearLayoutManager)
                        binding.get().notificationList.getLayoutManager();

                if (layoutManager != null) {
                    layoutManager.scrollToPosition(0);
                }
            }
        }
    }

    @Override
    protected void initUIAndActions() {

        binding.get().notificationList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)
                           recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int lastPosition = layoutManager
                            .findLastVisibleItemPosition();
                    if (lastPosition == adapter.get().getItemCount() - 1) {

                        if (!binding.get().getLoadingMore() && !booksViewModel.forceEndLoading) {

                            if (connectivity.isConnected()) {

                                booksViewModel.loadingDirection = Utils.LoadingDirection.bottom;

                                int limit = Config.NOTI_LIST_COUNT;
                                booksViewModel.offset = booksViewModel.offset + limit;
                                booksViewModel.collectionName = collectionName;


                                booksViewModel.page++;
                                booksViewModel.setNextPageBooksListObj(booksViewModel.collectionName, booksViewModel.page);

                            }
                        }
                    }
                }
            }
        });

        binding.get().swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        binding.get().swipeRefresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorPrimary));
        binding.get().swipeRefresh.setOnRefreshListener(() -> {

            booksViewModel.loadingDirection = Utils.LoadingDirection.top;

            // reset productViewModel.offset
            booksViewModel.offset = 0;
            booksViewModel.page = 1;

            // reset productViewModel.forceEndLoading
            booksViewModel.forceEndLoading = false;


            // update live data
            booksViewModel.setBookListObj(booksViewModel.collectionName, booksViewModel.page);

        });
    }

    @Override
    protected void initViewModels() {
        booksViewModel = new ViewModelProvider(this, viewModelFactory).get(BooksViewModel.class);
    }

    @Override
    protected void initAdapters() {

        nvAdapter = new BooksListAdapter(dataBindingComponent,
                book ->
                navigationController.navigateToHadithsActivity(getActivity(),collectionName,book.getBooknumber()),
                this, pref);

        this.adapter = new AutoClearedValue<>(this, nvAdapter);
        binding.get().notificationList.setAdapter(nvAdapter);
    }

    @Override
    protected void initData() {

        try {
            if (getActivity() != null) {
                if (getActivity().getIntent().getExtras() != null) {
                    collectionName = getActivity().getIntent().getExtras().getString(Constants.COLLECTION_NAME);
                }
            }
        } catch (Exception e) {
            Utils.errorLog("", e);
        }


        LoadData();

        try {
            Utils.log(">>>> On initData.");

            //coronaViewModel.token = dataManager.getUser().getToken();

        } catch (NullPointerException ne) {
            Utils.errorLog("Null Pointer Exception.", ne);
        } catch (Exception e) {
            Utils.errorLog("Error in getting notification flag data.", e);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView.setSearchableInfo(searchManager
//                .getSearchableInfo(getActivity().getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//
//        // listening to search query text change
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // filter recycler view when query submitted
//                adapter.get().getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                // filter recycler view when text is changed
//                adapter.get().getFilter().filter(query);
//                return false;
//            }
//        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            //navigationController.navigateToSettingsActivity(getActivity());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void LoadData() {

        this.booksViewModel.collectionName = this.collectionName;

        booksViewModel.setBookListObj(collectionName , booksViewModel.page);

        LiveData<Resource<List<Books>>> news = booksViewModel.getBookListData();

        if (news != null) {
            news.observe(this, listResource -> {
                if (listResource != null) {

                    switch (listResource.status) {
                        case LOADING:
                            // Loading State
                            // Data are from Local DB
                            if (listResource.data != null) {
                                //fadeIn Animation
                                fadeIn(binding.get().getRoot());

                                booksViewModel.setLoadingState(true);

                                if (booksViewModel.forceEndLoading) {
                                    booksViewModel.forceEndLoading = false;
                                }
                                // Update the data
                                replaceData(listResource.data);
                                onDispatched();

                            }

                            break;

                        case SUCCESS:
                            // Success State
                            // Data are from Server

                            if (listResource.data != null) {
                                // Update the data
                                replaceData(listResource.data);

                                if(listResource.data.size()< Config.PAGING_LIMIT){
                                    booksViewModel.forceEndLoading = true ;
                                }
                            }

                            booksViewModel.setLoadingState(false);

                            break;

                        case ERROR:

                            // Error State
                            booksViewModel.setLoadingState(false);
                            booksViewModel.forceEndLoading = true;

                            break;
                        default:
                            // Default

                            break;
                    }

                } else {

                    // Init Object or Empty Data
                    Utils.log("Empty Data");

                    if (booksViewModel.offset > 1) {
                        // No more data for this list
                        // So, Block all future loading
                        booksViewModel.forceEndLoading = true;
                    }

                }

            });
        }

        booksViewModel.getNextPageBooksListData().observe(this, state -> {
            if (state.data != null) {
                if (!state.data) {
                    Utils.log("Next Page State : " + state.data);

                    booksViewModel.setLoadingState(false);//hide
                    booksViewModel.forceEndLoading = true;//stop
                }

                if (state.status == Status.ERROR) {
                    Utils.log("Next Page State : " + state.data);

                }
            }
        });

        booksViewModel.getLoadingState().observe(this, loadingState -> {

            binding.get().setLoadingMore(booksViewModel.isLoading);

            if (loadingState != null && !loadingState) {
                binding.get().swipeRefresh.setRefreshing(false);
            }

        });


    }

    private void replaceData(List<Books> list) {

        adapter.get().replace(list);
        binding.get().executePendingBindings();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Utils.log("Request code " + requestCode);
        Utils.log("Result code " + resultCode);

    }
}
