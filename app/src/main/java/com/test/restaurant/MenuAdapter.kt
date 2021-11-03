package com.test.restaurant
import android.view.View
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.test.restaurant.MenuAdapter.MyViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.LinearLayout
import com.test.restaurant.databinding.MenuListAdapterBinding


class MenuAdapter(val foodDetails: List<FoodDetails>?, var context: Context) : RecyclerView.Adapter<MyViewHolder>() {

    private var binding: MenuListAdapterBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = MenuListAdapterBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding!!.root)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val food = foodDetails!![position]
        holder.txt_food.text = foodDetails[position].food_title
        holder.txt_desc.text = foodDetails[position].food_desc
        holder.txt_price.text = "€ " + foodDetails[position].price
        holder.txt_count.text = "" + foodDetails[position].count
        if (food.count > 0) {
            holder.btnadd.visibility = View.GONE
            holder.quan.visibility = View.VISIBLE
        }
        holder.txt_mins.setOnClickListener {
            if (food.count > 1) {
                food.count = food.count - 1
            } else {
                food.count = 0
                holder.quan.visibility = View.GONE
                holder.btnadd.visibility = View.VISIBLE
            }
            holder.txt_count.text = "" + food.count
            if (context is SecondActivity) {
                (context as SecondActivity).updateprice()
            } else if (context is MainActivity) {
                (context as MainActivity).updateItem()
            }
        }
        holder.txt_plus.setOnClickListener {
            if (food.count < 20) {
                food.count = food.count + 1
                holder.txt_count.text = "" + food.count
            }
            if (context is SecondActivity) {
                (context as SecondActivity).updateprice()
            } else if (context is MainActivity) {
                (context as MainActivity).updateItem()
            }
        }
        holder.btnadd.setOnClickListener { v: View ->
            food.count = 1
            holder.txt_count.text = "" + food.count
            v.visibility = View.GONE
            holder.quan.visibility = View.VISIBLE
            if (context is SecondActivity) {
                (context as SecondActivity).updateprice()
            } else if (context is MainActivity) {
                (context as MainActivity).updateItem()
            }
        }
    }

    override fun getItemCount(): Int {
        return foodDetails?.size ?: 0
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt_food: TextView
        var txt_desc: TextView
        var txt_price: TextView
        var txt_count: TextView
        var txt_mins: TextView
        var txt_plus: TextView
        var btnadd: TextView
        var quan: LinearLayout

        init {
            txt_food = binding!!.txtFood
            txt_desc = binding!!.txtDesc
            txt_price = binding!!.txtPrice
            txt_count = binding!!.txtCount
            txt_mins = binding!!.txtMius
            txt_plus = binding!!.txtPlus
            btnadd = binding!!.btnAdd
            quan = binding!!.quan
        }
    }
}

