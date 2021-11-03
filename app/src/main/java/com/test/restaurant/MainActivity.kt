package com.test.restaurant;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.restaurant.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    List<FoodDetails> foodDetails;
    List<FoodDetails> foodDetailscard;
    MenuAdapter menuAdapter;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        foodDetails = new ArrayList<>();
        foodDetails.add(new FoodDetails("Guac de la costa","tortilas de mails ,fruit de la passion,mango",7,0));
        foodDetails.add(new FoodDetails("Chicharron y Cerveza","citron vert/ Corona sauce",7,0));
        foodDetails.add(new FoodDetails("Chilitos con","padrones tempura,games",7,0));

        menuAdapter = new MenuAdapter(foodDetails,MainActivity.this);
        activityMainBinding.recyerFood.setAdapter(menuAdapter);

        activityMainBinding.btnViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<FoodDetails> foodDetails =menuAdapter.getFoodDetails();
                foodDetailscard = new ArrayList<>();
                for (FoodDetails foodDetails1 : foodDetails){
                    if (foodDetails1.getCount()>0){
                        foodDetailscard.add(foodDetails1);
                    }
                }
                Log.e("tetst123",""+foodDetailscard.size());

                if (foodDetailscard.size()>=1){
                    Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                    intent.putExtra("cardview",gson.toJson(foodDetailscard));
                    activityResultLauncher.launch(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please Select Food Item", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateItem(){
        int count = 0;
        for (FoodDetails foodDetails : menuAdapter.getFoodDetails()){
            if (foodDetails.getCount()>0){
                    count++;
            }
        }
        activityMainBinding.btnViewOrder.setText("VIEW CART ("+count+" ITEMS)");

    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        String strvalue = data.getStringExtra("cardview");
                        List<FoodDetails> fooditems = gson.fromJson(strvalue, new TypeToken<List<FoodDetails>>(){}.getType());
                        if (!fooditems.isEmpty()){
                            for(FoodDetails foodDetailsvlaue : fooditems){
                                int index=0;
                                for(FoodDetails foodDetailsvlaue1 : foodDetails){
                                    if(foodDetailsvlaue1.food_title.equalsIgnoreCase(foodDetailsvlaue.food_title)){
                                        foodDetails.set(index, foodDetailsvlaue);
                                        break;
                                    }
                                    index++;
                                }
                            }
                            menuAdapter = new MenuAdapter(foodDetails,MainActivity.this);
                            activityMainBinding.recyerFood.setAdapter(menuAdapter);
                            updateItem();
                        }
                    }
                }
            });
}