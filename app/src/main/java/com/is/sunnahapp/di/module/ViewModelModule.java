package com.is.sunnahapp.di.module;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.is.sunnahapp.factory.ViewModelFactory;
import com.is.sunnahapp.viewModel.BooksViewModel;
import com.is.sunnahapp.viewModel.ChaptersViewModel;
import com.is.sunnahapp.viewModel.CollectionsViewModel;
import com.is.sunnahapp.viewModel.HadithsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


/**
 * @author mac
 *
 * The ViewModelModule is used to provide a map of view models through dagger that
 * is used by the ViewModelFactory class.
 *
 *  so basically
 *  We can use the ViewModelModule to define our ViewModels.
 *  We provide a key for each ViewModel using the ViewModelKey class.
 *  Then in our Activity/Fragment, we use the ViewModelFactory class to
 *  inject the corresponding ViewModel. (We will look into more detail at
 *  this when we are creating our Activity).
 */
@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);


    /*
     * This method basically says
     * inject this object into a Map using the @IntoMap annotation,
     * with the  MovieListViewModel.class as key,
     * and a Provider that will build a MovieListViewModel
     * object.
     *
     * */

    @Binds
    @IntoMap
    @ViewModelKey(CollectionsViewModel.class)
    protected abstract ViewModel collectionsViewModel(CollectionsViewModel collectionsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BooksViewModel.class)
    protected abstract ViewModel booksViewModel(BooksViewModel booksViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChaptersViewModel.class)
    protected abstract ViewModel chaptersViewModel(ChaptersViewModel chaptersViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HadithsViewModel.class)
    protected abstract ViewModel hadithsViewModel(HadithsViewModel hadithsViewModel);

}