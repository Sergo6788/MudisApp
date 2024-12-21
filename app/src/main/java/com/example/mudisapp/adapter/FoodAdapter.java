package com.example.mudisapp.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mudisapp.R;
import com.example.mudisapp.app.App;
import com.example.mudisapp.databinding.RecycleViewCardBinding;
import com.example.mudisapp.model.MenuModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private List<MenuModel> list;
    private OnClickListener onClickListener;
    private Context context;

    public FoodAdapter(List<MenuModel> dishes, OnClickListener onClickListener, Context context){
        list = dishes;
        this.onClickListener = onClickListener;
        this.context = context;
    }
    @Override
    public int getItemCount(){
        return list.size();
    }


    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleViewCardBinding binding = RecycleViewCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodAdapter.ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position), context);


        holder.binding.getRoot().setOnClickListener(v -> {
            onClickListener.click(list.get(position));
        });
        holder.binding.ivHeart.setOnClickListener(v -> {
            holder.changeHeartColor(context, list.get(position), true);
        });
        holder.binding.btAdd.setOnClickListener(v -> {
            holder.addDishToCart(list.get(position), 1);
        });

        holder.binding.tvPlus.setOnClickListener(v -> {
            int count = Integer.parseInt(holder.binding.tvCount.getText().toString());
            count++;
            holder.addDishToCart(list.get(position), count);
            holder.binding.tvCount.setText(Integer.toString(count));
        });
        holder.binding.tvMinus.setOnClickListener(v -> {
            int count = Integer.parseInt(holder.binding.tvCount.getText().toString());
            if(count <= 1){onClickListener.decrease(list.get(position));}
            else {
                count--;
                holder.addDishToCart(list.get(position), count);
                holder.binding.tvCount.setText(Integer.toString(count));
            }
        });
    }
    public interface OnClickListener{
        void click(MenuModel menuItem);
        void decrease(MenuModel menuItem);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public RecycleViewCardBinding binding;

        public ViewHolder(RecycleViewCardBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MenuModel menuItem, Context context){
            Glide.with(binding.ivDish)
                    .load(menuItem.getImage())
                    .into(binding.ivDish);
            binding.tvName.setText(menuItem.getName());
            binding.tvPrice.setText(menuItem.getPrice() + "₪");
            changeHeartColor(context, menuItem);
            if(App.sharedManager.isItemInCart(menuItem)){
                binding.btAdd.setVisibility(View.GONE);
                binding.countLayout.setVisibility(View.VISIBLE);
                binding.tvCount.setText("" + App.sharedManager.getCartList().get(menuItem.getId()));
            }
        }

        public void changeHeartColor(Context context, MenuModel menuItem){
            if(App.sharedManager.isMealInFavoriteList(menuItem)){
                binding.ivHeart.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_read));
            }
            else
                binding.ivHeart.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_svgrepo_com));
        }

        public void changeHeartColor(Context context, MenuModel menuItem, boolean marker){
            if(App.sharedManager.isMealInFavoriteList(menuItem)){
                App.sharedManager.saveFavorite(menuItem, true);
                binding.ivHeart.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_svgrepo_com));
            }
            else{
                App.sharedManager.saveFavorite(menuItem, false);
                binding.ivHeart.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_read));
            }

        }
        public void addDishToCart(MenuModel menuItem, Integer count){
            App.sharedManager.saveToCart(menuItem, count);
            binding.btAdd.setVisibility(View.GONE);
            binding.countLayout.setVisibility(View.VISIBLE);
        }
    }
}
