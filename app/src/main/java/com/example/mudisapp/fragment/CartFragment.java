package com.example.mudisapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.mudisapp.R;
import com.example.mudisapp.adapter.CartAdapter;
import com.example.mudisapp.app.App;
import com.example.mudisapp.databinding.FragmentCartBinding;
import com.example.mudisapp.databinding.PaymentDialogBinding;
import com.example.mudisapp.model.MenuModel;
import com.example.mudisapp.model.OrderModel;
import com.example.mudisapp.repository.FirebaseRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class CartFragment extends Fragment implements CartAdapter.OnClickListener {
    public FragmentCartBinding binding;
    private AlertDialog materialDialog;
    private MenuModel currentDish;
    private FirebaseRepository firebaseDataBase;
    private ArrayList<MenuModel> list = new ArrayList<>();
    private AlertDialog dialog;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater);
        firebaseDataBase = new ViewModelProvider(this).get(FirebaseRepository.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObservers();
        firebaseDataBase.getMenu();
        materialDialog = createMaterialDialog();
        applyClick();
    }

    public void applyClick() {
        binding.btPayNow.setOnClickListener(v -> {
            makePayment();
        });
    }

    private void setAdapter(){
        list.removeIf(it -> !it.getReady());
        binding.rvCart.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false));
        binding.rvCart.setAdapter(new CartAdapter(list,this, requireContext()));
        changeTotalAmount();
    }

    private void setObservers(){
        firebaseDataBase.isTaskReady.observe(getViewLifecycleOwner(), data -> {
            if (data) {
                list = firebaseDataBase.getMenuList();
                setAdapter();
                firebaseDataBase.isTaskReady.setValue(false);
            }
        });
        firebaseDataBase.isOrderCreated.observe(getViewLifecycleOwner(), data -> {
            if(data){
                App.sharedManager.cleanCart();
                binding.rvCart.setAdapter(new CartAdapter(list, this, requireContext()));
                firebaseDataBase.isOrderCreated.setValue(false);
            }
        });
    }


    @Override
    public void decrease(MenuModel menuItem) {
        currentDish = menuItem;
        showMaterialDialog();
    }

    public void showMaterialDialog(){
        materialDialog.show();
    }
    private AlertDialog createMaterialDialog(){
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(requireActivity())
                .setTitle(R.string.delete_dish)
                .setMessage(R.string.do_you_want_to_delete_this_dish_from_the_cart)
                .setPositiveButton("Yes", (d, which)->{
                    list.remove(currentDish);
                    App.sharedManager.saveToCart(currentDish, 0);
                    binding.rvCart.getAdapter().notifyDataSetChanged();
                    changeTotalAmount();
                })
                .setNegativeButton("No",(d, which)->{})
                .setCancelable(false);
        return dialog.create();
    }

    private void makePayment(){
        showAnimation();
        createOrder();
    }
    private void showAnimation() {
        if (dialog != null && dialog.isShowing()) {
            return;
        }

        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(requireContext());
        PaymentDialogBinding bindingDialog = PaymentDialogBinding.inflate(LayoutInflater.from(requireContext()));
        dialogBuilder.setView(bindingDialog.getRoot());
        dialog = dialogBuilder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog);
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (!isAdded() || getView() == null) return;

            bindingDialog.pbLoad.animate()
                    .alpha(0f)
                    .setDuration(500)
                    .withEndAction(() -> {
                        bindingDialog.pbLoad.setVisibility(View.GONE);
                        bindingDialog.cvDone.setVisibility(View.VISIBLE);

                        Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.appearence_animation);
                        animation.setDuration(800);

                        bindingDialog.cvDone.startAnimation(animation);

                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {}

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    if (dialog != null && dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                }, 500);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {}
                        });
                    })
                    .start();

        }, 2000);

        dialog.show();
    }
    private void changeTotalAmount(){
        int amount = 0;
        for(MenuModel menuModel : list){
            for(int i = 0; i < App.sharedManager.getAmountOfDishInCart(menuModel); i++){
                amount += menuModel.getPrice();
            }
        }
        binding.tvPrice.setText(amount + " ₪");
    }
    private void createOrder(){
        ArrayList<MenuModel> localList = new ArrayList<>();
        for(int index = 0; index < list.size(); index++) {
            for(int i = 0; i < App.sharedManager.getAmountOfDishInCart(list.get(index)) ; i++){
                localList.add(list.get(index));
            }
        }
        OrderModel order = new OrderModel(localList, timeFormat.format(new Date()), App.sharedManager.getPaymentMethod(), App.sharedManager.getUID());
        firebaseDataBase.createOrder(order);
        binding.tvPrice.setText("0₪");
    }


}