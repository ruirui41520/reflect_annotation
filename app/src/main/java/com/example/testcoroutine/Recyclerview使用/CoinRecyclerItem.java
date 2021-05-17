package com.example.testcoroutine.Recyclerview使用;

import android.view.View;
import android.widget.TextView;

import com.example.testcoroutine.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
@RecyclerMarket(layoutId = R.layout.item_coins,viewHolderClass = CoinRecyclerItem.LocalViewHolder.class)
public class CoinRecyclerItem implements BaseRecyclerItemView<CoinRecyclerItem.LocalViewHolder> {
    private TextView view;

    @Override
    public void bindViewHolder(LocalViewHolder viewHolder) {
        view = viewHolder.itemView.findViewById(R.id.item_name);
        view.setText("CoinRecyclerItem");
    }

    public static class LocalViewHolder extends RecyclerView.ViewHolder{

        public LocalViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
