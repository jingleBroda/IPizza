package com.example.ipizza.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ipizza.R
import com.example.ipizza.dataBase.CartModel
import com.example.ipizza.databinding.CartRecviewRowBinding
import com.example.ipizza.retrofit.PizzaModel

class AdapterCartFragmentRecyclerView(orderList:List<CartModel>, context: Context): RecyclerView.Adapter<AdapterCartFragmentRecyclerView.ViewHolder>() {

    private var listAdapter = orderList
    private val listContextRecycler = context

    private var onMylistenerVariationOrder: ((orderCount: CartModel) -> Unit)? = null
    //private var onMylistenerButtonMinus: ((orderCount: CartModel) -> Unit)? = null

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val binding = CartRecviewRowBinding.bind(view)

        val imageOrder = binding.imageOrder
        val namePizza = binding.namePizzaOrder
        val priceOrder = binding.pricePizzaOrder
        val countPizza = binding.countOrder
        val increaseOrder = binding.buttonPlus
        val decreaseOrder = binding.buttonMinus

        fun bind(listIteam:CartModel){
            namePizza.text = listIteam.name
            priceOrder.text = listIteam.price.toString()+"₽"
            countPizza.text = listIteam.quantity.toString()

            Glide.with(imageOrder)
                .load(listIteam.imgUrl)
                .centerCrop()
                .into(imageOrder)

            increaseOrder.setOnClickListener(){
                countPizza.text = (countPizza.text.toString().toInt() + 1).toString()

                val updateListIteam = listIteam
                updateListIteam.quantity = countPizza.text.toString().toInt()

                onMylistenerVariationOrder?.invoke(updateListIteam)

            }

            decreaseOrder.setOnClickListener(){
                if(countPizza.text.toString().toInt() != 0) {
                    countPizza.text = (countPizza.text.toString().toInt() - 1).toString()

                    val updateListIteam = listIteam
                    updateListIteam.quantity = countPizza.text.toString().toInt()

                    onMylistenerVariationOrder?.invoke(updateListIteam)
                }
            }


        }


    }

    fun setOnVariationOrderCount(item:(orderCount: CartModel) -> Unit){
        onMylistenerVariationOrder = item
    }

    fun update(orderList:List<CartModel>){
        listAdapter = orderList
        notifyDataSetChanged()
    }

    fun getCurrentList():List<CartModel>{
        return listAdapter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(listContextRecycler)
        return ViewHolder(inflater.inflate(R.layout.cart_recview_row, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listIteam = listAdapter.get(position)
        holder.bind(listIteam)
    }

    override fun getItemCount(): Int {
        return listAdapter.size
    }
}