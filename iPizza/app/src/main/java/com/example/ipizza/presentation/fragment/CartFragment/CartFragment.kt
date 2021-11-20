package com.example.ipizza.presentation.fragment.CartFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ipizza.R
import com.example.ipizza.presentation.contract.navigator
import com.example.ipizza.domain.model.CartModel
import com.example.ipizza.databinding.FragmentCartBinding
import com.example.ipizza.domain.model.ServerCartModel
import com.example.ipizza.presentation.fragment.EndOrderFragment.EndOrderFragment
import com.example.ipizza.presentation.fragment.FragmentMainMenu.FragmentMainMenu
import com.example.ipizza.presentation.fragment.FragmentMainMenu.FragmentMainMenuViewModel
import com.example.ipizza.presentation.adapterRecyclerView.AdapterCartFragmentRecyclerView
import java.util.ArrayList


class CartFragment : Fragment() {

    private lateinit var adapter: AdapterCartFragmentRecyclerView

    private var orderList:List<CartModel> = ArrayList()

    private lateinit var viewModel: FragmentMainMenuViewModel
    private lateinit var binding:FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_cart, container, false)
        binding = FragmentCartBinding.bind(root)
        viewModel = ViewModelProvider(this).get(FragmentMainMenuViewModel::class.java)

        binding.cartRecView.hasFixedSize()
        binding.cartRecView.layoutManager= LinearLayoutManager(activity)
        adapter = AdapterCartFragmentRecyclerView(orderList,requireContext())

        binding.cartRecView.adapter = adapter

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
            binding.placeOrderButton.text = "Place order                                                       "+ costOrder.toString()+"₽"
        }

        adapter.setOnVariationOrderCount {
            viewModel.updateOrder(it)

            val costOrder = showCostOrder(adapter.getCurrentList())
            binding.placeOrderButton.text = "Place order                                                       "+ costOrder.toString()+"₽"
        }

        binding.placeOrderButton.setOnClickListener(){


            val serverListOrder:MutableList<ServerCartModel> = ArrayList()

            for(i in 0 until adapter.itemCount){
                val tmpServerIteam:ServerCartModel = ServerCartModel(i, orderList[i].quantity)
                serverListOrder.add(i, tmpServerIteam)
            }

            viewModel.postOrderPizzaListServer(serverListOrder)

            viewModel.deleteOrder()

            //добавления главного меню БЕЗ кнопки перехода в корзину
            val fragmentReplaceMain = FragmentMainMenu.newInstance(false)
            navigator().replaceMainMenu(fragmentReplaceMain, false)

            val fragmentEnd = EndOrderFragment()
            navigator().showNewScreen(fragmentEnd)
        }

        binding.deleteButton.setOnClickListener(){
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