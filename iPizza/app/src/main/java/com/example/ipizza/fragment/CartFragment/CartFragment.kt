package com.example.ipizza.fragment.CartFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ipizza.R
import com.example.ipizza.contract.navigator
import com.example.ipizza.dataBase.CartModel
import com.example.ipizza.databinding.FragmentCartBinding
import com.example.ipizza.fragment.EndOrderFragment.EndOrderFragment
import com.example.ipizza.fragment.FragmentMainMenu.FragmentMainMenu
import com.example.ipizza.fragment.FragmentMainMenu.FragmentMainMenuViewModel
import com.example.ipizza.recyclerView.AdapterCartFragmentRecyclerView
import com.example.ipizza.recyclerView.AdapterHomeMenuRecyclerView
import java.util.ArrayList


class CartFragment : Fragment() {

    lateinit var placeOrderButton: Button
    private lateinit var cartRecView: RecyclerView
    private lateinit var adapter:AdapterCartFragmentRecyclerView
    private lateinit var deleteButton:ImageButton

    private var orderList:List<CartModel> = ArrayList()

    private lateinit var viewModel: FragmentMainMenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_cart, container, false)
        val binding:FragmentCartBinding = FragmentCartBinding.bind(root)
        viewModel = ViewModelProvider(this).get(FragmentMainMenuViewModel::class.java)

        placeOrderButton = binding.placeOrderButton
        cartRecView = binding.cartRecView
        cartRecView.hasFixedSize()
        cartRecView.layoutManager= LinearLayoutManager(activity)
        adapter = AdapterCartFragmentRecyclerView(orderList,requireContext())

        cartRecView.adapter = adapter

        deleteButton = binding.deleteButton

        viewModel.getOrderDataRoom()


        return root
    }

    fun showCostOrder(list:List<CartModel>):Int{
        var rez=0

        for(i in list.indices){
            rez +=list[i].price*list[i].quantity
        }
        return rez
    }

    override fun onStart() {
        super.onStart()

        viewModel.getAllOrder {
            orderList = it
            adapter.update(orderList)

            val costOrder = showCostOrder(it)
            placeOrderButton.text = "Place order                                                       "+ costOrder.toString()+"₽"
        }

        adapter.setOnVariationOrderCount {
            viewModel.updateOrder(it)

            val costOrder = showCostOrder(adapter.getCurrentList())
            placeOrderButton.text = "Place order                                                       "+ costOrder.toString()+"₽"
        }

        placeOrderButton.setOnClickListener(){
            viewModel.deleteOrder()

            //добавления главного меню БЕЗ кнопки перехода в корзину
            val fragmentReplaceMain = FragmentMainMenu.newInstance(false)
            navigator().replaceMainMenu(fragmentReplaceMain, false)

            val fragmentEnd = EndOrderFragment()
            navigator().showNewScreen(fragmentEnd)
        }

        deleteButton.setOnClickListener(){
            viewModel.deleteOrder()
            Toast.makeText(requireContext(), "Заказ был удален!", Toast.LENGTH_SHORT).show()


            val fragmentReplaceMain = FragmentMainMenu.newInstance(false)
            navigator().replaceMainMenu(fragmentReplaceMain, true)

        }

    }

    override fun onDestroy() {
        viewModel.clearComposite()
        super.onDestroy()
    }


}