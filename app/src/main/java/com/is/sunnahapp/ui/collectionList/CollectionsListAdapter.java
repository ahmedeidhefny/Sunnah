package com.is.sunnahapp.ui.collectionList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;

import com.is.sunnahapp.Constants;
import com.is.sunnahapp.R;
import com.is.sunnahapp.data.local.entity.Collections;
import com.is.sunnahapp.databinding.ItemCollectionListAdapterBinding;
import com.is.sunnahapp.ui.base.DataBoundListAdapter;
import com.is.sunnahapp.util.Objects;
import com.is.sunnahapp.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author Ahmed Eid Hefny
 * @date 4/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class CollectionsListAdapter extends DataBoundListAdapter<Collections, ItemCollectionListAdapterBinding> implements Filterable {

    private final androidx.databinding.DataBindingComponent dataBindingComponent;
    private BankClickCallback callback;
    private DataBoundListAdapter.DiffUtilDispatchedInterface diffUtilDispatchedInterface;
    private Context mContext;
    private List<Collections> contactList;
    private List<Collections> contactListFiltered;
    private SharedPreferences sharedPreferences;

    public CollectionsListAdapter(androidx.databinding.DataBindingComponent dataBindingComponent,
                                  BankClickCallback callback,
                                  DataBoundListAdapter.DiffUtilDispatchedInterface diffUtilDispatchedInterface) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = callback;
        this.diffUtilDispatchedInterface = diffUtilDispatchedInterface;
    }

    @Override
    protected ItemCollectionListAdapterBinding createBinding(ViewGroup parent) {

        ItemCollectionListAdapterBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_collection_list_adapter, parent, false, dataBindingComponent);
        mContext = parent.getContext();
        sharedPreferences = mContext.getSharedPreferences(Constants.SP_SETTINGS_NAME, mContext.MODE_PRIVATE);
        binding.getRoot().setOnClickListener(v -> {
            Collections notification = binding.getCollections();
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
    protected void bind(ItemCollectionListAdapterBinding binding, Collections item) {
        binding.setCollections(item);

        //String lang = sharedPreferences.getString(Constants.SP_LANGUAGE, "");
        String titleEn = item.getCollection().get(0).getTitle();
        String titleAr = item.getCollection().get(1).getTitle();

        //CharSequence charAr = Utils.spitTheFirstChar(titleAr);
        CharSequence charEn = Utils.spitTheFirstChar(titleEn);

        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));


        binding.tvTitle.setText(titleEn);
        binding.tvTitle.setTextColor(color);
        binding.tvTitleAr.setText(titleAr);
        binding.tvChar.setText(charEn);
        binding.tvChar.setTextColor(color);

        //binding.tvInfoEn.setText(item.getCollection().get(0).getShortintro());
        //binding.tvInfoAr.setText(item.getCollection().get(1).getShortintro());
        //binding.tvTotal.setText("Total Hadiths : " + item.getTotalhadith());

        // if (item.getCountryInfo().flag != null) {

        //     Glide.with(mContext)
        //             .load(item.CountryInfo.flag)
        //             .thumbnail(Glide.with(mContext)
        //                     .load(item.CountryInfo.flag)).into(binding.imgFlag);

        // } else {

        //     if (binding.imgFlag != null) {
        //         binding.imgFlag.setImageResource(R.drawable.placeholder_image);
        //     }

        // }

    }

    @Override
    protected boolean areItemsTheSame(Collections oldItem, Collections newItem) {
        return Objects.equals(oldItem.getName(), newItem.getName())
                && oldItem.getTotalhadith() == (newItem.getTotalhadith());
    }

    @Override
    protected boolean areContentsTheSame(Collections oldItem, Collections newItem) {
        return Objects.equals(oldItem.getName(), newItem.getName())
                && oldItem.getTotalhadith() == (newItem.getTotalhadith());
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
        void onClick(Collections notification);
    }
}
