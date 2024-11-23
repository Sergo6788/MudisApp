package com.example.mudisapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mudisapp.databinding.RecycleViewMenuBinding;
import com.example.mudisapp.model.MenuModel;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private List<MenuModel> list;
    private OnClickListener onClickListener;

    public FoodAdapter(List<MenuModel>data, OnClickListener onClickListener){
        list = data;
        this.onClickListener = onClickListener;
    }
    @Override
    public int getItemCount(){
        return list.size();
    }


    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleViewMenuBinding binding = RecycleViewMenuBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodAdapter.ViewHolder(binding);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
        holder.binding.getRoot().setOnClickListener(v -> {
            onClickListener.click(list.get(position));
        });
    }
    public interface OnClickListener{
        void click(MenuModel menuItem);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public RecycleViewMenuBinding binding;

        public ViewHolder(RecycleViewMenuBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(MenuModel menuItem){
            Glide.with(binding.ivMeal)
                    .load(menuItem.getImage())
                    .into(binding.ivMeal);
            binding.tvName.setText(menuItem.getName());
            binding.tvPrice.setText(menuItem.getPrice().toString());

        }
    }
}
