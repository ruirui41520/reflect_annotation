package com.example.testcoroutine.Recyclerview使用;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

interface BaseRecyclerItemView<T extends RecyclerView.ViewHolder> {
    void bindViewHolder(T viewHolder);
}
