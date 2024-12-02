package com.example.mudisapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mudisapp.databinding.RecycleViewCardCartBinding;
import com.example.mudisapp.model.MenuModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<MenuModel> list;
    private OnClickListener onClickListener;
    private Context context;

    public CartAdapter(List<MenuModel> data, CartAdapter.OnClickListener onClickListener, Context context){
        list = data;
        this.onClickListener = onClickListener;
        this.context = context;
    }

    @Override
    public int getItemCount() {return list.size();}

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleViewCardCartBinding binding = RecycleViewCardCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position), context);

        holder.binding.getRoot().setOnClickListener(v -> {
            onClickListener.click(list.get(position));
        });
    }

    public interface OnClickListener{
        void click(MenuModel menuItem);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public RecycleViewCardCartBinding binding;

        public ViewHolder(RecycleViewCardCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(MenuModel menuItem, Context context){
            Glide.with(binding.ivDish)
                    .load(menuItem.getImage())
                    .into(binding.ivDish);
            binding.tvName.setText(menuItem.getName());
            binding.tvPrice.setText(menuItem.getPrice() + "₪");

        }
    }

}
