package com.is.sunnahapp.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.is.sunnahapp.data.local.entity.Books;

import java.util.List;

/**
 * @author Ahmed Eid Hefny
 * @date 7/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class BooksApiResponse {

    @Expose
    @SerializedName("data")
    private List<Books> data;
    @Expose
    @SerializedName("next")
    private int next;
    @Expose
    @SerializedName("limit")
    private int limit;
    @Expose
    @SerializedName("total")
    private int total;

    public List<Books> getData() {
        return data;
    }

    public void setData(List<Books> data) {
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
