package com.is.sunnahapp.ui.bookList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;

import com.is.sunnahapp.R;
import com.is.sunnahapp.data.local.entity.Books;
import com.is.sunnahapp.data.local.shardPref.PreferencesHelper;
import com.is.sunnahapp.databinding.ItemBookListAdapterBinding;
import com.is.sunnahapp.ui.base.DataBoundListAdapter;
import com.is.sunnahapp.util.Objects;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Ahmed Eid Hefny
 * @date 8/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class BooksListAdapter extends DataBoundListAdapter<Books, ItemBookListAdapterBinding> implements Filterable {

    private final androidx.databinding.DataBindingComponent dataBindingComponent;
    private BankClickCallback callback;
    private DiffUtilDispatchedInterface diffUtilDispatchedInterface;
    private Context mContext;
    private List<Books> contactList;
    private List<Books> contactListFiltered;
    //private SharedPreferences sharedPreferences;

    protected PreferencesHelper pref;




    public BooksListAdapter(androidx.databinding.DataBindingComponent dataBindingComponent,
                            BankClickCallback callback,
                            DiffUtilDispatchedInterface diffUtilDispatchedInterface,
                            PreferencesHelper pref) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = callback;
        this.diffUtilDispatchedInterface = diffUtilDispatchedInterface;
        this.pref = pref;
    }




    @Override
    protected ItemBookListAdapterBinding createBinding(ViewGroup parent) {

        ItemBookListAdapterBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_book_list_adapter, parent, false, dataBindingComponent);
        mContext = parent.getContext();
        //sharedPreferences  = mContext.getSharedPreferences(SP_SETTINGS_NAME, mContext.MODE_PRIVATE);
        binding.getRoot().setOnClickListener(v -> {
            Books notification = binding.getBooks();
            if (notification != null && callback != null) {
                callback.onClick(notification);
            }
        });
        return binding;
    }

    @Override
    protected void dispatched() {
        if (diffUtilDispatchedInterface != null) {
            diffUtilDispatchedInterface.onDispatched();
        }
    }

    @Override
    protected void bind(ItemBookListAdapterBinding binding, Books item) {

        binding.setBooks(item);

        //int  fontSize = sharedPreferences.getInt(SP_FONT_SIZE, 16);
        int fontSize = pref.getFontSize();

        binding.tvTitle.setText(item.getBook().get(0).getName());
        binding.tvTitle.setTextSize(fontSize);
        binding.tvTitleAr.setText(item.getBook().get(1).getName());
        binding.tvTitleAr.setTextSize(fontSize);
        binding.tvInfoEn.setText(""+item.getBooknumber());//"Book Number : " +
        binding.tvInfoEn.setTextSize(fontSize);
        binding.tvTotal.setText(item.getHadithendnumber() +" Hadith: [" +item.getHadithstartnumber()+ " to " +item.getHadithendnumber()+"]");//"Total Hadiths : " +
        binding.tvTotal.setTextSize(fontSize-2);
    }

    @Override
    protected boolean areItemsTheSame(Books oldItem, Books newItem) {
        return Objects.equals(oldItem.getBooknumber(), newItem.getBooknumber())
                && oldItem.getHadithendnumber() == (newItem.getHadithendnumber());
    }

    @Override
    protected boolean areContentsTheSame(Books oldItem, Books newItem) {
        return Objects.equals(oldItem.getBooknumber(), newItem.getBooknumber())
                && oldItem.getHadithendnumber() == (newItem.getHadithendnumber());
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<Books> filteredList = new ArrayList<>();
                    for (Books row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getBooknumber().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<Books>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public interface BankClickCallback {
        void onClick(Books notification);
    }
}
