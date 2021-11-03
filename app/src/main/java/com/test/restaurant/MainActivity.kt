package com.test.restaurant

import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import android.os.Bundle
import java.util.ArrayList
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.google.gson.reflect.TypeToken
import com.test.restaurant.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var activityMainBinding: ActivityMainBinding? = null
    var foodDetails: MutableList<FoodDetails> = ArrayList()
    var foodDetailscard: MutableList<FoodDetails?>? = null
    var menuAdapter: MenuAdapter? = null
    var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding!!.root)
        foodDetails.add(FoodDetails("Guac de la costa", "tortilas de mails ,fruit de la passion,mango", 7, 0))
        foodDetails.add(FoodDetails("Chicharron y Cerveza", "citron vert/ Corona sauce", 7, 0))
        foodDetails.add(FoodDetails("Chilitos con", "padrones tempura,games", 7, 0))
        menuAdapter = MenuAdapter(foodDetails, this@MainActivity)
        activityMainBinding!!.recyerFood.adapter = menuAdapter
        activityMainBinding!!.btnViewOrder.setOnClickListener {
            val foodDetails = menuAdapter!!.foodDetails
            foodDetailscard = ArrayList()
            for (foodDetails1 in foodDetails!!) {
                if (foodDetails1.count > 0) {
                    foodDetailscard?.add(foodDetails1)
                }
            }
            if (foodDetailscard!!.size >= 1) {
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtra("cardview", gson.toJson(foodDetailscard))
                activityResultLauncher.launch(intent)
            } else {
                Toast.makeText(this@MainActivity, "Please Select Food Item", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateItem() {
        var count = 0
        for (foodDetails in menuAdapter?.foodDetails!!) {
            if (foodDetails.count > 0) {
                count++
            }
        }
        activityMainBinding!!.btnViewOrder.text = "VIEW CART ($count ITEMS)"
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    var activityResultLauncher = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // There are no request codes
            val data = result.data
            val strvalue = data!!.getStringExtra("cardview")
            val fooditems = gson.fromJson<List<FoodDetails>>(
                strvalue,
                object : TypeToken<List<FoodDetails?>?>() {}.type
            )
            if (!fooditems.isEmpty()) {
                for (foodDetailsvlaue in fooditems) {
                    var index = 0
                    for (foodDetailsvlaue1 in foodDetails) {
                        if (foodDetailsvlaue1.food_title.equals(
                                foodDetailsvlaue.food_title,
                                ignoreCase = true
                            )
                        ) {
                            foodDetails[index] = foodDetailsvlaue
                            break
                        }
                        index++
                    }
                }
                menuAdapter = MenuAdapter(foodDetails, this@MainActivity)
                activityMainBinding!!.recyerFood.adapter = menuAdapter
                updateItem()
            }
        }
    }
}