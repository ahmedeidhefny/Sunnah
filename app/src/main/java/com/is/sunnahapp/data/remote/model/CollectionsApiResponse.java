package com.is.sunnahapp.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.is.sunnahapp.data.local.entity.Collections;

import java.util.List;

/**
 * @author Ahmed Eid Hefny
 * @date 4/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class CollectionsApiResponse {

    @Expose
    @SerializedName("data")
    private List<Collections> data;
    @Expose
    @SerializedName("next")
    private int next;
    @Expose
    @SerializedName("limit")
    private int limit;
    @Expose
    @SerializedName("total")
    private int total;


    public List<Collections> getData() {
        return data;
    }

    public void setData(List<Collections> data) {
        this.data = data;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
