package com.example.mudisapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mudisapp.databinding.RecycleViewCardBinding;
import com.example.mudisapp.databinding.RecycleViewCardCartBinding;
import com.example.mudisapp.fragment.CartFragment;
import com.example.mudisapp.model.MenuModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<MenuModel> list;
    private OnClickListener onClickListener;

    public CartAdapter(List<MenuModel> data, CartAdapter.OnClickListener onClickListener){
        list = data;
        this.onClickListener = onClickListener;
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
        holder.binding.ivHeart.setOnClickListener(v -> {
            onClickListener.toFavorite(list.get(position));

        });
    }

    public interface OnClickListener{
        void click(MenuModel menuItem);
        void toFavorite(MenuModel menuItem);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public RecycleViewCardCartBinding binding;

        public ViewHolder(RecycleViewCardCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(){

        }
    }

}
