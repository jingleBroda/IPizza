package com.example.ipizza.bottomFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.ipizza.R
import com.example.ipizza.contract.navigator
import com.example.ipizza.databinding.BottomSheetLayoutBinding
import com.example.ipizza.fragment.DetailPizzaFragment.DetailPizzaFragment
import com.example.ipizza.fragment.FragmentMainMenu.FragmentMainMenu
import com.example.ipizza.fragment.FragmentMainMenu.FragmentMainMenuViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomFragment() : BottomSheetDialogFragment() {


    private lateinit var binding: BottomSheetLayoutBinding
    private lateinit var viewModel: FragmentMainMenuViewModel

    private var urlImgPizza:String = ""
    private var namePizza:String = ""
    private var descriptPizza:String = ""
    private var costPizza:String = ""
    private var idPizza:Int = 0

    private lateinit var namePosPizzaView:TextView
    private lateinit var descriptPizzaView:TextView
    private lateinit var imgPizzaView:ImageView
    private lateinit var goPizzaCartButton:Button

    //ДОБАВИТЬ КОНСТАНТЫ ДЛЯ КЛЮЧЕЙ В NEWINSTANCE

    // Переопределим тему, чтобы использовать нашу с закруглёнными углами
    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(FragmentMainMenuViewModel::class.java)
        //получаем id пиццы
        idPizza = requireArguments().getInt(fragmentArg1, 0)

        //вытаскиваем из бд данную пиццу
        val selectedItemPizza = viewModel.concretItemLiveData(idPizza)


       urlImgPizza = selectedItemPizza.imageUrl
       namePizza = selectedItemPizza.name
       descriptPizza = selectedItemPizza.description
       costPizza = selectedItemPizza.price.toString()+"₽"
        
    }

    companion object {

        val fragmentArg1 = "idPizza"

        fun myNewInstance(
            idPizza:Int
        ):BottomFragment{
            val bottomFragment = BottomFragment()
            val args = Bundle()
            args.putInt(fragmentArg1, idPizza)
            bottomFragment.setArguments(args)
            return bottomFragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetLayoutBinding.bind(inflater.inflate(R.layout.bottom_sheet_layout, container))

        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        namePosPizzaView = binding.nameRowPizza
        namePosPizzaView.text = namePizza

        descriptPizzaView = binding.descriptRowPizza
        descriptPizzaView.text = descriptPizza

        imgPizzaView = binding.previewPizza
        Glide.with(imgPizzaView)
            .load(urlImgPizza)
            .centerCrop()
            .into(imgPizzaView)

        goPizzaCartButton= binding.addCartButton
        goPizzaCartButton.text = costPizza



        return binding.root
    }

    override fun onStart() {
        super.onStart()

        goPizzaCartButton.setOnClickListener(){

            // ПО НАЖАТИЮ НА ЭТОМУ КНОПКУ ДОЛЖНО ПРОИЗОЙТИ ДОБАВЛЕНИЕ ПИЦЦЫ В КОРИЗНУ, А ТАКЖЕ ДОЛЖЕН ОТКРЫТЬСЯ
            // ФРАГМЕНТ ГЛАВНОГО МЕНЮ С КНОПКОЙ ВНИЗУ ДЛЯ ПЕРЕХОДА В КОРЗИНУ

            val fragment = FragmentMainMenu.newInstance(true)

            this.dismiss()

            navigator().replaceMainMenu(fragment, true)

        }



        imgPizzaView.setOnClickListener(){

            this.dismiss()

            val fragment = DetailPizzaFragment.newInstance(urlImgPizza,namePizza, costPizza)

            navigator().showNewScreen(fragment)

        }



    }


}