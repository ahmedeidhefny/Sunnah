package com.is.sunnahapp.ui.chapterList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;

import com.is.sunnahapp.R;
import com.is.sunnahapp.data.local.entity.Chapters;
import com.is.sunnahapp.data.local.entity.Collections;
import com.is.sunnahapp.databinding.ItemChapterListAdapterBinding;
import com.is.sunnahapp.ui.base.DataBoundListAdapter;
import com.is.sunnahapp.util.Objects;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Ahmed Eid Hefny
 * @date 9/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class ChaptersListAdapter extends DataBoundListAdapter<Chapters, ItemChapterListAdapterBinding> implements Filterable {

    private final androidx.databinding.DataBindingComponent dataBindingComponent;
    private BankClickCallback callback;
    private DiffUtilDispatchedInterface diffUtilDispatchedInterface;
    private Context mContext;
    private List<Collections> contactList;
    private List<Collections> contactListFiltered;

    public ChaptersListAdapter(androidx.databinding.DataBindingComponent dataBindingComponent,
                               BankClickCallback callback,
                               DiffUtilDispatchedInterface diffUtilDispatchedInterface) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = callback;
        this.diffUtilDispatchedInterface = diffUtilDispatchedInterface;
    }

    @Override
    protected ItemChapterListAdapterBinding createBinding(ViewGroup parent) {

        ItemChapterListAdapterBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_chapter_list_adapter, parent, false, dataBindingComponent);
        mContext = parent.getContext();
        binding.getRoot().setOnClickListener(v -> {
            Chapters notification = binding.getChapters();
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
    protected void bind(ItemChapterListAdapterBinding binding, Chapters item) {
        binding.setChapters(item);

       // binding.tvTitle.setText(item.getCollection().get(0).getTitle());
       // binding.tvTitleAr.setText(item.getCollection().get(1).getTitle());
       // binding.tvInfoEn.setText(item.getCollection().get(1).getShortintro());
       // binding.tvTotal.setText("Total Hadiths : " + item.getTotalhadith());

    }

    @Override
    protected boolean areItemsTheSame(Chapters oldItem, Chapters newItem) {
        return Objects.equals(oldItem.getBooknumber(), newItem.getBooknumber())
                && oldItem.getChapterid() == (newItem.getChapterid());
    }

    @Override
    protected boolean areContentsTheSame(Chapters oldItem, Chapters newItem) {
        return Objects.equals(oldItem.getBooknumber(), newItem.getBooknumber())
                && oldItem.getChapterid() == (newItem.getChapterid());
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
        void onClick(Chapters notification);
    }
}
