package com.example.mudisapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mudisapp.databinding.RecycleViewMenu1Binding;
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
        RecycleViewMenu1Binding binding = RecycleViewMenu1Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
        public RecycleViewMenu1Binding binding;

        public ViewHolder(RecycleViewMenu1Binding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
        

        public void bind(MenuModel menuItem){
            Glide.with(binding.ivDish)
                    .load(menuItem.getImage())
                    .into(binding.ivDish);
            binding.tvName.setText(menuItem.getName());
            binding.tvPrice.setText(menuItem.getPrice() + "₪");

        }
    }
}
