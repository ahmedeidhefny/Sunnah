package com.is.sunnahapp.ui.hadithsList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.is.sunnahapp.data.local.entity.Hadiths;
import com.is.sunnahapp.databinding.FragmentHadithListBinding;
import com.is.sunnahapp.ui.base.BaseFragment;
import com.is.sunnahapp.ui.base.DataBoundListAdapter;
import com.is.sunnahapp.util.AutoClearedValue;
import com.is.sunnahapp.util.Utils;
import com.is.sunnahapp.viewModel.HadithsViewModel;

import java.util.List;

/**
 * @author Ahmed Eid Hefny
 * @date 5/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class HadithsListFragment extends BaseFragment implements DataBoundListAdapter.DiffUtilDispatchedInterface {

    private final androidx.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    private HadithsViewModel hadithsViewModel;
    public HadithsListAdapter nvAdapter;

    private SearchView searchView;

    @VisibleForTesting
    private AutoClearedValue<FragmentHadithListBinding> binding;
    private AutoClearedValue<HadithsListAdapter> adapter;

    private String collectionName;
    private String bookNumber;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHadithListBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hadith_list, container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);

        binding.get().setLoadingMore(connectivity.isConnected());
        setHasOptionsMenu(true);
        return binding.get().getRoot();

    }

    @Override
    public void onDispatched() {
        if (hadithsViewModel.loadingDirection == Utils.LoadingDirection.top) {

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
                        //Log.d("fragment", "lastPage");

                        if (!binding.get().getLoadingMore() && !hadithsViewModel.forceEndLoading) {
                            if (connectivity.isConnected()) {

                                hadithsViewModel.loadingDirection = Utils.LoadingDirection.bottom;

                                int limit = Config.NOTI_LIST_COUNT;
                                hadithsViewModel.offset = hadithsViewModel.offset + limit;
                                hadithsViewModel.collectionName = collectionName;
                                hadithsViewModel.bookNumber = bookNumber;

                                hadithsViewModel.page++;
                                Log.d("fragment", "page"+ hadithsViewModel.page);
                                hadithsViewModel.setNextPageHadithListObj(hadithsViewModel.collectionName,
                                        hadithsViewModel.bookNumber, hadithsViewModel.page);

                            }
                        }
                    }
                }
            }
        });

        binding.get().swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        binding.get().swipeRefresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorPrimary));
        binding.get().swipeRefresh.setOnRefreshListener(() -> {

            hadithsViewModel.loadingDirection = Utils.LoadingDirection.top;

            // reset productViewModel.offset
            hadithsViewModel.offset = 0;
            hadithsViewModel.page = 1;

            hadithsViewModel.collectionName = collectionName;
            hadithsViewModel.bookNumber = bookNumber;

            // reset productViewModel.forceEndLoading
            hadithsViewModel.forceEndLoading = false;

            // update live data
            hadithsViewModel.setHadithsListObj(collectionName, bookNumber, hadithsViewModel.page);


        });
    }

    @Override
    protected void initViewModels() {
        hadithsViewModel = new ViewModelProvider(this, viewModelFactory).get(HadithsViewModel.class);
    }

    @Override
    protected void initAdapters() {

        nvAdapter = new HadithsListAdapter(dataBindingComponent,
                corona -> {
                },
                this,
                pref);

        this.adapter = new AutoClearedValue<>(this, nvAdapter);
        binding.get().notificationList.setAdapter(nvAdapter);
    }

    @Override
    protected void initData() {


        try {
            if (getActivity() != null) {
                if (getActivity().getIntent().getExtras() != null) {
                    collectionName = getActivity().getIntent().getExtras().getString(Constants.COLLECTION_NAME);
                    bookNumber = getActivity().getIntent().getExtras().getString(Constants.BOOK_NUMBER);
                    hadithsViewModel.bookNumber = this.bookNumber;
                    hadithsViewModel.collectionName = this.collectionName;
                }
            }
        } catch (Exception e) {
            Utils.errorLog("", e);
        }


        LoadData();

        try {
            Utils.log(">>>> On initData.");

        } catch (NullPointerException ne) {
            Utils.errorLog("Null Pointer Exception.", ne);
        } catch (Exception e) {
            Utils.errorLog("Error in getting notification flag data.", e);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.hadith_menu, menu);

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
        int fontSize = pref.getFontSize();
        int id = item.getItemId();

        if (id == R.id.action_increase) {
            if (fontSize < 20) {
                fontSize++;
                pref.setFontSize(fontSize);
                nvAdapter.notifyDataSetChanged();
            }
            return true;

        } else if (id == R.id.action_decrease) {
            if (fontSize > 10) {
                fontSize--;
                pref.setFontSize(fontSize);
                nvAdapter.notifyDataSetChanged();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void LoadData() {

        Log.d("fragment", "load Data");
        this.collectionName = this.hadithsViewModel.collectionName;
        this.bookNumber = this.hadithsViewModel.bookNumber;

        hadithsViewModel.setHadithsListObj(collectionName, bookNumber, hadithsViewModel.page);

        LiveData<Resource<List<Hadiths>>> hadiths = hadithsViewModel.getHadithsListData();

        if (hadiths != null) {
            hadiths.observe(this, listResource -> {
                if (listResource != null) {

                    switch (listResource.status) {
                        case LOADING:
                            // Loading State
                            // Data are from Local DB
                            if (listResource.data != null) {
                                //fadeIn Animation
                                fadeIn(binding.get().getRoot());

                                hadithsViewModel.setLoadingState(true);

                                if (hadithsViewModel.forceEndLoading) {
                                    hadithsViewModel.forceEndLoading = false;
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
                                    hadithsViewModel.forceEndLoading = true ;
                                }
                            }

                            hadithsViewModel.setLoadingState(false);

                            break;

                        case ERROR:
                            // Error State
                            // Error State
                            hadithsViewModel.setLoadingState(false);
                            hadithsViewModel.forceEndLoading = true;

                            break;
                        default:
                            // Default

                            break;
                    }

                } else {

                    // Init Object or Empty Data
                    Utils.log("Empty Data");

                    if (hadithsViewModel.offset > 1) {
                        // No more data for this list
                        // So, Block all future loading
                        hadithsViewModel.forceEndLoading = true;
                    }

                }

            });
        }


        hadithsViewModel.getNextPageHadithListData().observe(this, state -> {
            if (state.data != null) {
                if (!state.data) {
                    Utils.log("Next Page State : " + state.data);

                    hadithsViewModel.setLoadingState(false);//hide
                    hadithsViewModel.forceEndLoading = true;//stop
                }

            }


            if (state.status == Status.ERROR) {
                Utils.log("Next Page State : " + state.data);
                hadithsViewModel.setLoadingState(false);
            }
        });

        hadithsViewModel.getLoadingState().observe(this, loadingState -> {

            binding.get().setLoadingMore(hadithsViewModel.isLoading);

            if (loadingState != null && !loadingState) {
                binding.get().swipeRefresh.setRefreshing(false);
            }

        });


    }

    private void replaceData(List<Hadiths> list) {

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
