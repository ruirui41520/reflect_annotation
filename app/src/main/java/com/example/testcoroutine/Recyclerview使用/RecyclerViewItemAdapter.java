package com.example.testcoroutine.Recyclerview使用;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecyclerViewItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<BaseRecyclerItemView> items = new ArrayList<>();
    HashMap<Integer,Class<? extends RecyclerView.ViewHolder>> holderMap = new HashMap<>();
    public RecyclerViewItemAdapter() {
    }

    public void addItem(BaseRecyclerItemView itemView){
        items.add(itemView);
        notifyDataSetChanged();
    }

    public void addAll(List<BaseRecyclerItemView> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (holderMap.containsKey(viewType)){
            Class cls = holderMap.get(viewType);
            return createHolder(parent,viewType,cls);
        }
        throw new RuntimeException("Add RecyclerItemView annotation to item view. layoutId= $viewType");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        items.get(position).bindViewHolder(holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        BaseRecyclerItemView itemView = items.get(position);
        RecyclerMarket annotation = itemView.getClass().getAnnotation(RecyclerMarket.class);
        if (annotation != null){
            int layoutId = annotation.layoutId();
            if (!holderMap.containsKey(layoutId)){
                holderMap.put(layoutId,annotation.viewHolderClass());
            }
            return layoutId;
        }
        throw new RuntimeException("Add RecyclerItemViewMaker annotation to item view = ");
    }

    private RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType, Class cls){
        RecyclerView.ViewHolder viewHolder = null;
        try {
            Constructor construct = cls.getConstructor(View.class);
            View view = LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);
            return (RecyclerView.ViewHolder) construct.newInstance(view);
        }catch (Exception e){
            e.printStackTrace();
        }
        return viewHolder;
    }
}
