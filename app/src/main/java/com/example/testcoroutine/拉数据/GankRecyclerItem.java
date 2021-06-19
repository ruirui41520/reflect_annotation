package com.example.testcoroutine.拉数据;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.testcoroutine.ApiClient.Model.GankModel.NewsResultEntity;
import com.example.testcoroutine.R;
import com.example.testcoroutine.Recyclerview使用.BaseRecyclerItemView;
import com.example.testcoroutine.Recyclerview使用.RecyclerMarket;

@RecyclerMarket(layoutId = R.layout.recycler_item_gank,viewHolderClass = GankRecyclerItem.LocalViewHolder.class)
public class GankRecyclerItem implements BaseRecyclerItemView<GankRecyclerItem.LocalViewHolder> {
    private NewsResultEntity resultEntity;
    public GankRecyclerItem(NewsResultEntity newsEntity){
        resultEntity = newsEntity;
    }

    @Override
    public void bindViewHolder(LocalViewHolder viewHolder) {
        if (resultEntity == null)return;
        ((TextView)viewHolder.itemView.findViewById(R.id.header)).setText(resultEntity.getWho());
        ((TextView)viewHolder.itemView.findViewById(R.id.content)).setText(resultEntity.getDesc());
    }

    public static class LocalViewHolder extends RecyclerView.ViewHolder{
        public LocalViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
