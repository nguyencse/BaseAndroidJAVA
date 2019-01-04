package com.base.nguyencse.java.ui.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {
    private List<T> mData = new ArrayList<>();

    public BaseAdapter() {
    }

    public BaseAdapter(List<T> data) {
        this.mData = data;

    }

    @NonNull
    public abstract BaseViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<T> holder, int position) {
        holder.onBind(mData.get(position));
    }


    @Override
    public int getItemCount() {
        return mData == null || mData.isEmpty() ? 0 : mData.size();
    }

    public T getItemAtPosition(int position) {
        return mData == null
                || position < 0
                || mData.size() == 0
                || mData.size() < position
                ? null : mData.get(position);
    }

    public void resetData() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        mData.add(item);
        notifyItemInserted(mData.size() - 1);
    }

    public void removeItem(int position) {
        if (position >= 0 && position < mData.size()) {
            mData.remove(position);
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> items) {
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void setData(List<T> items) {
        mData = items;
        notifyDataSetChanged();
    }

    public void setDataAtPosition(T item, int position) {
        if (position >= 0 && position < mData.size()) {
            mData.set(position, item);
            notifyItemChanged(position);
        }
    }

    public void clearDataInRange(int start, int end) {
        if (start >= 0 && end >= 0 && start < mData.size() && end <= mData.size() && start < end) {
            mData.subList(start, end).clear();
            notifyItemChanged(start, end);
        }
    }

    public void addFirst(T item) {
        mData.add(0, item);
        notifyItemInserted(0);
    }

    public List<T> getData() {
        return mData;
    }
}
