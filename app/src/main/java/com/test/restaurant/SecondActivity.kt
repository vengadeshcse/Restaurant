package com.test.restaurant

import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import android.os.Bundle
import android.view.View
import android.util.Log
import android.content.Intent
import android.widget.Toast
import android.app.Activity
import com.google.gson.reflect.TypeToken
import com.test.restaurant.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    var activitySecondBinding: ActivitySecondBinding? = null
    var foodDetails: List<FoodDetails>? = null
    var gson = Gson()
    var menuAdapter: MenuAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySecondBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(activitySecondBinding!!.root)
        val bundle = intent.extras
        if (bundle != null) {
            val strfood = bundle.getString("cardview")
            foodDetails = gson.fromJson(strfood, object : TypeToken<List<FoodDetails?>?>() {}.type)
            if (foodDetails!!.size > 2) {
                menuAdapter = MenuAdapter(foodDetails?.subList(0, 2), this@SecondActivity)
            } else {
                menuAdapter = MenuAdapter(foodDetails, this@SecondActivity)
                activitySecondBinding!!.txtShowmore.visibility = View.GONE
            }
            activitySecondBinding!!.recyerCard.adapter = menuAdapter
            updateprice()
        }
        activitySecondBinding!!.imgBack.setOnClickListener { onBackPressed() }
        activitySecondBinding!!.txtShowmore.setOnClickListener {
            activitySecondBinding!!.txtShowmore.visibility = View.GONE
            menuAdapter = MenuAdapter(foodDetails, this@SecondActivity)
            activitySecondBinding!!.recyerCard.adapter = menuAdapter
        }
        activitySecondBinding!!.btnViewOrder.setOnClickListener {
            Toast.makeText(
                this@SecondActivity,
                "Order Placed Successfully",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun updateprice() {
        var price = 0
        for (foodDetails in menuAdapter?.foodDetails!!) {
            price += foodDetails.price * foodDetails.count
        }
        activitySecondBinding!!.txtTotal.text = "€ $price"
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("cardview", gson.toJson(menuAdapter?.foodDetails))
        setResult(RESULT_OK, intent)
        finish()
    }
}