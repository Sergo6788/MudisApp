package com.example.mudisapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mudisapp.R;
import com.example.mudisapp.databinding.RecycleViewOrderBinding;
import com.example.mudisapp.enums.OrderStatus;
import com.example.mudisapp.enums.PaymentMethod;
import com.example.mudisapp.model.OrderModel;

import java.util.List;
import java.util.Objects;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private List<OrderModel> list;

    public OrderAdapter(List<OrderModel> data) {
        list = data;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleViewOrderBinding binding = RecycleViewOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private RecycleViewOrderBinding binding;

        public ViewHolder(RecycleViewOrderBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(OrderModel orderItem){
            Glide.with(binding.ivOrder)
                    .load(orderItem.getOrderMenu().get(0).getImage())
                    .into(binding.ivOrder);
            binding.tvTotalAmount.setText(orderItem.getTotalAmount() + "₪");
            binding.tvOrderDate.setText(orderItem.getOrderDate());

            if(orderItem.getOrderStatus() == OrderStatus.COMPLETED){binding.tvStatusOrder.setText(R.string.completed_order);}
            else if(orderItem.getOrderStatus() == OrderStatus.InPROCESS){binding.tvStatusOrder.setText(R.string.in_process);}
            else if(orderItem.getOrderStatus() == OrderStatus.CANCELED){binding.tvStatusOrder.setText(R.string.cancelled_order);}

            if(Objects.equals(orderItem.getPaymentMethod(), PaymentMethod.APPLE_PAY.toString())){binding.tvPaymentMethod.setText(R.string.payment_method_card);}
            else if(Objects.equals(orderItem.getPaymentMethod(), PaymentMethod.GOOGLE_PAY.toString())){binding.tvPaymentMethod.setText(R.string.payment_method_card);}
            else if(Objects.equals(orderItem.getPaymentMethod(), PaymentMethod.BANK_CARD.toString())){binding.tvPaymentMethod.setText(R.string.payment_method_card);}
            else if(Objects.equals(orderItem.getPaymentMethod(), PaymentMethod.CASH.toString())){binding.tvPaymentMethod.setText(R.string.payment_method_cash);}
        }
    }
}
