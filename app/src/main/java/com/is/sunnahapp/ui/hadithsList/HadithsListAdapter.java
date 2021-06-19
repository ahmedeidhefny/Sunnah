package com.is.sunnahapp.ui.hadithsList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;

import com.is.sunnahapp.R;
import com.is.sunnahapp.data.local.entity.Collections;
import com.is.sunnahapp.data.local.entity.Hadiths;
import com.is.sunnahapp.data.local.shardPref.PreferencesHelper;
import com.is.sunnahapp.databinding.ItemHadithListAdapterBinding;
import com.is.sunnahapp.ui.base.DataBoundListAdapter;
import com.is.sunnahapp.util.Objects;
import com.is.sunnahapp.util.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Ahmed Eid Hefny
 * @date 5/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class HadithsListAdapter extends DataBoundListAdapter<Hadiths, ItemHadithListAdapterBinding> implements Filterable {

    private final androidx.databinding.DataBindingComponent dataBindingComponent;
    private BankClickCallback callback;
    private DiffUtilDispatchedInterface diffUtilDispatchedInterface;
    private Context mContext;
    private List<Collections> contactList;
    private List<Collections> contactListFiltered;
    //private SharedPreferences sharedPreferences;



    protected PreferencesHelper pref;

    public HadithsListAdapter(androidx.databinding.DataBindingComponent dataBindingComponent,
                              BankClickCallback callback,
                              DiffUtilDispatchedInterface diffUtilDispatchedInterface,
                              PreferencesHelper pref) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = callback;
        this.diffUtilDispatchedInterface = diffUtilDispatchedInterface;
        this.pref = pref;
    }

    @Override
    protected ItemHadithListAdapterBinding createBinding(ViewGroup parent) {

        ItemHadithListAdapterBinding binding = (ItemHadithListAdapterBinding) DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_hadith_list_adapter, parent, false, dataBindingComponent);
        mContext = parent.getContext();
        //sharedPreferences = mContext.getSharedPreferences(SP_SETTINGS_NAME, mContext.MODE_PRIVATE);
        binding.getRoot().setOnClickListener(v -> {
            Hadiths notification = binding.getHadiths();
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
    protected void bind(ItemHadithListAdapterBinding binding, Hadiths item) {
        binding.setHadiths(item);

        //String lang = sharedPreferences.getString(SP_LANGUAGE, "both");
        //int  fontSize = sharedPreferences.getInt(SP_FONT_SIZE, 16);

        int fontSize = pref.getFontSize();
        String lang = pref.getLang();

        if (lang.equalsIgnoreCase("ar")) {
            binding.tvTitle.setVisibility(View.GONE);
            binding.tvInfoEn.setVisibility(View.GONE);
        } else if (lang.equalsIgnoreCase("en")) {
            binding.tvTitleAr.setVisibility(View.GONE);
            binding.tvInfoAr.setVisibility(View.GONE);
        }

        binding.tvTitle.setText(item.getHadith().get(0).getChaptertitle());
        //Utils.ParseHtml(item.getHadith().get(0).getChaptertitle(), binding.tvTitle);
        binding.tvTitle.setTextSize(fontSize);

        binding.tvTitleAr.setText(item.getHadith().get(1).getChaptertitle());
        //Utils.ParseHtml(item.getHadith().get(1).getChaptertitle(), binding.tvTitleAr);
        binding.tvTitleAr.setTextSize(fontSize);

        Utils.ParseHtml(item.getHadith().get(0).getBody(), binding.tvInfoEn);
        binding.tvInfoEn.setTextSize(fontSize);

        Utils.ParseHtml(item.getHadith().get(1).getBody(), binding.tvInfoAr);
        binding.tvInfoAr.setTextSize(fontSize);

        binding.tvHadithNumber.setText("" + item.getHadithnumber());
        binding.tvTotal.setText("In-book: Book " + item.getBooknumber() + ", " + "Hadith " + item.getHadithnumber());
        binding.tvTotal.setTextSize(fontSize - 4);
        binding.tvData.setText("Chapter Number: " + item.getChapterid());
        binding.tvData.setTextSize(fontSize - 4);
    }

    @Override
    protected boolean areItemsTheSame(Hadiths oldItem, Hadiths newItem) {
        return Objects.equals(oldItem.getBooknumber(), newItem.getBooknumber())
                && oldItem.getCollection() .equals (newItem.getCollection())
                && oldItem.getHadithnumber() .equals (newItem.getHadithnumber());
    }

    @Override
    protected boolean areContentsTheSame(Hadiths oldItem, Hadiths newItem) {
        return Objects.equals(oldItem.getBooknumber(), newItem.getBooknumber())
                && oldItem.getCollection() .equals (newItem.getCollection())
                && oldItem.getHadithnumber() .equals (newItem.getHadithnumber());
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
                    List<Collections> filteredList = new ArrayList<>();
                    for (Collections row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<Collections>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public interface BankClickCallback {
        void onClick(Hadiths notification);
    }
}
