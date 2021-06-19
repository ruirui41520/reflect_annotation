package com.example.testcoroutine.Recyclerview使用;
import androidx.recyclerview.widget.RecyclerView;

public interface BaseRecyclerItemView<T extends RecyclerView.ViewHolder> {
       void bindViewHolder(T viewHolder);
}
