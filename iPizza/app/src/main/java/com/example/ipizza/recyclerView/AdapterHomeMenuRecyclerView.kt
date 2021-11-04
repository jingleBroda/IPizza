package com.example.ipizza.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ipizza.R
import com.example.ipizza.databinding.HomeMenuRowBinding
import com.example.ipizza.retrofit.PizzaModel


class AdapterHomeMenuRecyclerView(pizzaList:List<PizzaModel>?, context: Context ): RecyclerView.Adapter<AdapterHomeMenuRecyclerView.ViewHolder>() {

    private var listAdapter = pizzaList
    private val listContextRecycler = context
    private var onMylistener: ((item: PizzaModel) -> Unit)? = null

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        private val binding = HomeMenuRowBinding.bind(view)

        private var imagePizza : ImageView = binding.imgPizza
        private var namePizza : TextView = binding.namePizza
        private var descpirtPizza : TextView = binding.descriptPizza
        private var costPizza : TextView = binding.costPizza

        init {

            view.setOnClickListener{ onMylistener?.invoke(listAdapter!!.get(adapterPosition)) }
        }

        fun bind(listItem: PizzaModel){

            Glide.with(imagePizza)
                .load(listItem.imageUrls?.get(0))
                .centerCrop()
                .into(imagePizza)


            namePizza.text = listItem.name
            descpirtPizza.text = listItem.description?.substring(0,34) + "..."
            costPizza.text = listItem.price.toString()+"₽"


        }

    }

    fun update(pizzaList:List<PizzaModel>?){
        listAdapter = pizzaList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (item: PizzaModel) -> Unit) {
        this.onMylistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(listContextRecycler)
        return ViewHolder(inflater.inflate(R.layout.home_menu_row, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, ) {
        val listIteam = listAdapter!!.get(position)
        holder.bind(listIteam)
    }

    override fun getItemCount(): Int {
        return listAdapter!!.size
    }
}