package com.example.ipizza.presentation.fragment.PreviewPizzaFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.ipizza.R
import com.example.ipizza.presentation.contract.navigator
import com.example.ipizza.databinding.FragmentPreviewPizzaBinding
import com.example.ipizza.domain.model.CartModel
import com.example.ipizza.presentation.fragment.FragmentMainMenu.FragmentMainMenu
import com.example.ipizza.presentation.fragment.FragmentMainMenu.FragmentMainMenuViewModel
import com.example.ipizza.presentation.viewPager.AdapterViewPager
import kotlin.properties.Delegates


class PreviewPizzaFragment() : Fragment() {

    private var imgPizza:ArrayList<String> = ArrayList()
    private var namePizza = ""
    private var costPizza = ""
    var pizzaId by Delegates.notNull<Int>()
    private lateinit var binding:FragmentPreviewPizzaBinding

    private lateinit var adapterVP: AdapterViewPager

    private lateinit var viewModel: FragmentMainMenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentMainMenuViewModel::class.java)
        pizzaId = requireArguments().getInt(fragmentArg1, 0)
        //imgPizza = requireArguments().getStringArrayList(fragmentArg1) as ArrayList<String>
        //namePizza = requireArguments().getString(fragmentArg2, "")
        //costPizza = requireArguments().getString(fragmentArg3, "")
        viewModel.searchSpecificPizza(pizzaId)
    }

companion object {

    val fragmentArg1 = "idPizza"
    //val fragmentArg1 = "imgPizza"
    //val fragmentArg2 = "namePizza"
    //val fragmentArg3 = "costPizza"

     fun newInstance(
         //urlImgPizza: ArrayList<String>,
         //namePizza: String,
         //costPizza: String
        id:Int
    ): PreviewPizzaFragment {
        val detailPizzaFragment = PreviewPizzaFragment()
        val args = Bundle()
         args.putInt(fragmentArg1, id)
        //args.putStringArrayList(fragmentArg1, urlImgPizza)
        //args.putString(fragmentArg2, namePizza)
        //args.putString(fragmentArg3, costPizza)

        detailPizzaFragment.arguments = args

        return detailPizzaFragment
    }
}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_preview_pizza, container, false)
        binding = FragmentPreviewPizzaBinding.bind(root)


        viewModel.getSpecificPizza {
            namePizza = it.name
            costPizza = it.price.toString()
            imgPizza = it.imageUrls!!

            binding.namePizzaDetailsTextView.text = namePizza

            adapterVP = AdapterViewPager(imgPizza)

            binding.pager.adapter = adapterVP

            binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    binding.numberImg.text =
                        (position + 1).toString() + "/" + adapterVP.itemCount.toString()
                }
            })

            //я буду исправлять это недоразумение, но пока у меня не получилось найти информацию, как сделать выравнивание
            //как в макете
            binding.goCartDetailsButton.text =
                "Add to cart                                   " + costPizza + "₽"



            viewModel.searchSpecificOrderPizza(it.name)

            binding.goCartDetailsButton.setOnClickListener() {

                val tmpOrder = CartModel()
                tmpOrder.imgUrl = imgPizza[0]
                tmpOrder.price = costPizza.toInt()
                tmpOrder.quantity =1
                tmpOrder.name = namePizza
                viewModel.insertOrderDataRoom(tmpOrder)

                val fragment = FragmentMainMenu.newInstance(true)
                navigator().replaceMainMenu(fragment, true)

            }

        }
        return root
    }

    override fun onStart() {
        super.onStart()

        viewModel.searchSpecificOrderPizza(namePizza)

        binding.backButton.setOnClickListener(){
            navigator().goBack()

        }

        viewModel.getSpecificOrderPizza{ specificOrderPizza->

            binding.goCartDetailsButton.setOnClickListener(){

                specificOrderPizza.quantity++
                viewModel.insertOrderDataRoom(specificOrderPizza)

                val fragment = FragmentMainMenu.newInstance(true)
                navigator().replaceMainMenu(fragment, true)

            }
        }

    }

    override fun onDestroy() {
        viewModel.clearComposite()
        super.onDestroy()
    }


}