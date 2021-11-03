package com.test.restaurant;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.restaurant.databinding.MenuListAdapterBinding;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private final List<FoodDetails> foodDetails;
    Context context;
    private MenuListAdapterBinding binding;

    public MenuAdapter(List<FoodDetails> foodDetails, Context context1) {
        this.foodDetails = foodDetails;
        this.context = context1;
    }

    @NonNull
    @Override
    public MenuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = MenuListAdapterBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.MyViewHolder holder, int position) {
        FoodDetails food = foodDetails.get(position);
        holder.txt_food.setText(foodDetails.get(position).getFood_title());
        holder.txt_desc.setText(foodDetails.get(position).getFood_desc());
        holder.txt_price.setText("€ " + foodDetails.get(position).getPrice());
        holder.txt_count.setText("" + foodDetails.get(position).getCount());

        if (food.count>0){
            holder.btnadd.setVisibility(View.GONE);
            holder.quan.setVisibility(View.VISIBLE);
        }

        holder.txt_mins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (food.count > 1) {
                    food.setCount(food.count-1);
                } else {
                    food.setCount(0);
                    holder.quan.setVisibility(View.GONE);
                    holder.btnadd.setVisibility(View.VISIBLE);
                }
                holder.txt_count.setText("" + food.getCount());

                if (context instanceof SecondActivity){
                    ((SecondActivity)context).updateprice();
                } else if (context instanceof MainActivity){
                    ((MainActivity)context).updateItem();
                }

            }
        });

        holder.txt_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (food.count < 20) {
                    food.setCount(food.count+1);
                    holder.txt_count.setText("" + food.getCount());
                }

                if (context instanceof SecondActivity){
                    ((SecondActivity)context).updateprice();
                } else if (context instanceof MainActivity){
                    ((MainActivity)context).updateItem();
                }
            }
        });

        holder.btnadd.setOnClickListener(v -> {
            food.setCount(1);
            holder.txt_count.setText("" + food.getCount());
            v.setVisibility(View.GONE);
            holder.quan.setVisibility(View.VISIBLE);

            if (context instanceof SecondActivity){
                ((SecondActivity)context).updateprice();
            } else if (context instanceof MainActivity){
                ((MainActivity)context).updateItem();
            }
        });
    }

    public List<FoodDetails> getFoodDetails() {
        return foodDetails;
    }

    @Override
    public int getItemCount() {
        if (foodDetails != null)
            return foodDetails.size();
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_food, txt_desc, txt_price, txt_count, txt_mins, txt_plus, btnadd;
        LinearLayout quan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_food = binding.txtFood;
            txt_desc = binding.txtDesc;
            txt_price = binding.txtPrice;
            txt_count = binding.txtCount;
            txt_mins = binding.txtMius;
            txt_plus = binding.txtPlus;
            btnadd = binding.btnAdd;
            quan = binding.quan;
        }
    }
}
