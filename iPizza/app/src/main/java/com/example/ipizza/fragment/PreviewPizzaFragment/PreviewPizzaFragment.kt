package com.example.ipizza.fragment.PreviewPizzaFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.ipizza.R
import com.example.ipizza.contract.navigator
import com.example.ipizza.databinding.FragmentPreviewPizzaBinding
import com.example.ipizza.fragment.FragmentMainMenu.FragmentMainMenu
import com.example.ipizza.viewPager.AdapterViewPager


class PreviewPizzaFragment() : Fragment() {


    private var imgPizza:ArrayList<String> = ArrayList()
    private var namePizza = ""
    private var costPizza = ""

    private lateinit var nameDetailsTextView:TextView
    private lateinit var imgDetailsTextView:ImageView
    private lateinit var numberImg:TextView
    private lateinit var buttonGoCardDetails:Button
    private lateinit var backButton:ImageButton

    private lateinit var adapterVP: AdapterViewPager
    private lateinit var customeViewPager:ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imgPizza = requireArguments().getStringArrayList(fragmentArg1) as ArrayList<String>
        namePizza = requireArguments().getString(fragmentArg2, "")
        costPizza = requireArguments().getString(fragmentArg3, "")

    }

companion object {

    val fragmentArg1 = "imgPizza"
    val fragmentArg2 = "namePizza"
    val fragmentArg3 = "costPizza"

     fun newInstance(
         urlImgPizza: ArrayList<String>,
         namePizza: String,
         costPizza: String
    ): PreviewPizzaFragment {
        val detailPizzaFragment = PreviewPizzaFragment()
        val args = Bundle()
        args.putStringArrayList(fragmentArg1, urlImgPizza)
        args.putString(fragmentArg2, namePizza)
        args.putString(fragmentArg3, costPizza)

        detailPizzaFragment.arguments = args

        return detailPizzaFragment
    }
}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_preview_pizza, container, false)
        val binding:FragmentPreviewPizzaBinding = FragmentPreviewPizzaBinding.bind(root)

        nameDetailsTextView = binding.namePizzaDetailsTextView
        nameDetailsTextView.text = namePizza

        numberImg = binding.numberImg

        customeViewPager = binding.pager

        adapterVP = AdapterViewPager(imgPizza)


        customeViewPager.adapter = adapterVP

        customeViewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                numberImg.text = (position+1).toString()+"/"+adapterVP.itemCount.toString()
            }
        })


        //я буду исправлять это недоразумение, но пока у меня не получилось найти информацию, как сделать выравнивание
        //как в макете
        buttonGoCardDetails = binding.goCartDetailsTextView
        buttonGoCardDetails.text = "Add to cart                                   "+costPizza






        backButton = binding.backButton


        return root
    }

    override fun onStart() {
        super.onStart()

        backButton.setOnClickListener(){
            navigator().goBack()

        }

        buttonGoCardDetails.setOnClickListener(){
            val fragment = FragmentMainMenu.newInstance(true)
            navigator().replaceMainMenu(fragment, true)
        }

    }


}