package com.example.ipizza.fragment.CartFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.ipizza.R
import com.example.ipizza.contract.navigator
import com.example.ipizza.fragment.EndOrderFragment.EndOrderFragment
import com.example.ipizza.fragment.FragmentMainMenu.FragmentMainMenu


class CartFragment : Fragment() {

    lateinit var placeOrderButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_cart, container, false)

        placeOrderButton = root.findViewById(R.id.placeOrderButton)


        placeOrderButton.setOnClickListener(){
            //при переходе на финальный экран, необходимо сделать переход по нажатию на клавиши системной назад в главное меню,
            //без кнопки перехода в корзину
            // поэтому, необходимо указать, что в стеке будет находится только финальный фрагмент

            //добавления главного меню БЕЗ кнопки перехода в корзину
            val fragmentReplaceMain = FragmentMainMenu.newInstance(false)

            navigator().replaceMainMenu(fragmentReplaceMain, false)

            //добавляем в стек финальный экран и показываем его
            val fragmentEnd = EndOrderFragment()

            navigator().showNewScreen(fragmentEnd)
        }

        return root
    }


}