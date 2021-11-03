package com.test.restaurant;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.restaurant.databinding.ActivitySecondBinding;

import java.util.List;

public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding activitySecondBinding;
    List<FoodDetails> foodDetails;
    Gson gson = new Gson();
    MenuAdapter menuAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySecondBinding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(activitySecondBinding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String strfood = bundle.getString("cardview");
            foodDetails = gson.fromJson(strfood, new TypeToken<List<FoodDetails>>(){}.getType());
            if(foodDetails.size()>2) {
                menuAdapter = new MenuAdapter(foodDetails.subList(0, 2), SecondActivity.this);
            }else{
                menuAdapter = new MenuAdapter(foodDetails, SecondActivity.this);
                activitySecondBinding.txtShowmore.setVisibility(View.GONE);
            }
            activitySecondBinding.recyerCard.setAdapter(menuAdapter);
            updateprice();
        }

        activitySecondBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        activitySecondBinding.txtShowmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activitySecondBinding.txtShowmore.setVisibility(View.GONE);
                menuAdapter = new MenuAdapter(foodDetails,SecondActivity.this);
                activitySecondBinding.recyerCard.setAdapter(menuAdapter);

            }
        });

        activitySecondBinding.btnViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateprice(){
        int price = 0;
        for (FoodDetails foodDetails : menuAdapter.getFoodDetails()){
             price += foodDetails.getPrice() * foodDetails.getCount();
        }
        activitySecondBinding.txtTotal.setText("€ "+price);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("cardview", gson.toJson(menuAdapter.getFoodDetails()));
        setResult(RESULT_OK, intent);
        finish();
    }
}