package com.example.mudisapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mudisapp.app.App;
import com.example.mudisapp.databinding.RecycleViewCardCartBinding;
import com.example.mudisapp.model.MenuModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ArrayList<MenuModel> list;
    private OnClickListener onClickListener;
    private Context context;

    public CartAdapter(ArrayList<MenuModel> data, CartAdapter.OnClickListener onClickListener, Context context){
        list = data;
        list.removeIf(dish -> !App.sharedManager.isItemInCart(dish));

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


        holder.binding.tvPlus.setOnClickListener(v -> {
            int count = Integer.parseInt(holder.binding.tvCount.getText().toString());
            if(count >= 9){
                Toast.makeText(context, "Max. quantity: 9", Toast.LENGTH_SHORT).show();
                holder.binding.tvPlus.setEnabled(false);
            }
            else{
                count++;
                App.sharedManager.saveToCart(list.get(position), count);
                holder.binding.tvCount.setText(Integer.toString(count));
            }

        });
        holder.binding.tvMinus.setOnClickListener(v -> {
            int count = Integer.parseInt(holder.binding.tvCount.getText().toString());
            if(count <= 1){onClickListener.decrease(list.get(position));}
            else {
                count--;
                holder.binding.tvCount.setText(Integer.toString(count));
                App.sharedManager.saveToCart(list.get(position), count);
            }
            if(count < 9) {
                holder.binding.tvPlus.setEnabled(true);
            }
        });

    }

    public interface OnClickListener{
        void decrease(MenuModel menuItem);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public RecycleViewCardCartBinding binding;
        private HashMap<String, Integer> cart = App.sharedManager.getCartList();

        public ViewHolder(RecycleViewCardCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MenuModel menuItem, Context context){
            Integer count = cart.get(menuItem.getId());
            Glide.with(binding.ivDish)
                    .load(menuItem.getImage())
                    .into(binding.ivDish);
            binding.tvName.setText(menuItem.getName());
            binding.tvPrice.setText(menuItem.getPrice() + "₪");

            if(count != 0)
            {
                binding.tvCount.setText("" + count);
            }

        }

    }

}
