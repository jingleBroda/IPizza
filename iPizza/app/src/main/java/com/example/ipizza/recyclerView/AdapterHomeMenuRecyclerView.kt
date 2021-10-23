package com.example.ipizza.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ipizza.R
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.ipizza.bottomFragment.BottomFragment
import com.example.ipizza.dataBase.PizzaEntity
import com.example.ipizza.databinding.FragmentCartBinding
import com.example.ipizza.databinding.HomeMenuRowBinding


class AdapterHomeMenuRecyclerView(pizzaList:List<PizzaEntity>?, context: Context ): RecyclerView.Adapter<AdapterHomeMenuRecyclerView.ViewHolder>() {

    private var listAdapter = pizzaList
    private val listContextResicle = context
    private var onMylistener: ((item: PizzaEntity) -> Unit)? = null

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        //нужно убрать findViewById и переделать на ViewBinding
        private val binding = HomeMenuRowBinding.bind(view)

        private var imagePizza : ImageView = binding.imgPizza
        private var namePizza : TextView = binding.namePizza
        private var descpirtPizza : TextView = binding.descriptPizza
        private var costPizza : TextView = binding.costPizza

        init {
            imagePizza.setOnClickListener{ onMylistener?.invoke(listAdapter!!.get(adapterPosition)) }
        }

        fun bind(listItem: PizzaEntity){

            Glide.with(imagePizza)
                .load(listItem.imageUrl)
                .centerCrop()
                .into(imagePizza)


            namePizza.text = listItem.name
            descpirtPizza.text = listItem.description.substring(0,34) + "..."
            costPizza.text = listItem.price.toString()+"₽"


        }

    }

    fun update(pizzaList:List<PizzaEntity>?){
        listAdapter = pizzaList
        notifyDataSetChanged()
    }

    fun setOnImgItemClickListener(listener: (item: PizzaEntity) -> Unit) {
        this.onMylistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(listContextResicle)
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