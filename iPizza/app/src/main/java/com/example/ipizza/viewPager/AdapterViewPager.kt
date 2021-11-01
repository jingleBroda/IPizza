package com.example.ipizza.viewPager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ipizza.R
import kotlinx.android.synthetic.main.viewpager_item.view.*


class AdapterViewPager(imgPizza:ArrayList<String>):RecyclerView.Adapter<AdapterViewPager.PagerVH>() {

    private val pizzaImgList = imgPizza


    class PagerVH(view: View):RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
       return PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.viewpager_item, parent, false))
    }

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
        holder.itemView.run {
            Glide.with(pizzaPhoto)
                .load(pizzaImgList[position])
                .centerCrop()
                .into(pizzaPhoto)
        }
    }

    override fun getItemCount(): Int {
       return pizzaImgList.size
    }
}

