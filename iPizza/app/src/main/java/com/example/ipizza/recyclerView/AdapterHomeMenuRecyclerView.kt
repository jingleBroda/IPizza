package com.example.ipizza.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ipizza.R
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.ipizza.bottomFragment.BottomFragment
import com.example.ipizza.dataBase.PizzaEntity


class AdapterHomeMenuRecyclerView(pizzaList:List<PizzaEntity>?, context: Context, manag:FragmentManager, ): RecyclerView.Adapter<AdapterHomeMenuRecyclerView.ViewHolder>() {

    val listAdapter = pizzaList
    val listContextResicle = context
    val listManager = manag

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        private var imagePizza : ImageView = view.findViewById(R.id.imgPizza)
        private var namePizza : TextView = view.findViewById(R.id.namePizza)
        private var descpirtPizza : TextView = view.findViewById(R.id.descriptPizza)
        private var costPizza : TextView = view.findViewById(R.id.costPizza)

        fun bind(listItem:PizzaEntity, listManager:FragmentManager, listContextResicle:Context){

            Glide.with(imagePizza)
                .load(listItem.imageUrl)
                .centerCrop()
                .into(imagePizza)


            namePizza.text = listItem.name
            descpirtPizza.text = listItem.description.substring(0,34) + "..."
            costPizza.text = listItem.price.toString()+"₽"


            imagePizza.setOnClickListener() {

                /*
                //закрытие клавиатуры
                val imgr =
                    listContextResicle.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imgr.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                */

                val bottomFragment:BottomFragment = BottomFragment.myNewInstance(
                    listItem.id
                )
                bottomFragment.show(listManager, "tag")

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(listContextResicle)
        return ViewHolder(inflater.inflate(R.layout.home_menu_row, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, ) {
        val listIteam = listAdapter!!.get(position)
        holder.bind(listIteam,listManager, listContextResicle)
    }

    override fun getItemCount(): Int {
        return listAdapter!!.size
    }
}