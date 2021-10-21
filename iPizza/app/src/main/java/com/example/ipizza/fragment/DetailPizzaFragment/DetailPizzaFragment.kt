package com.example.ipizza.fragment.DetailPizzaFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.ipizza.R
import com.example.ipizza.contract.navigator
import com.example.ipizza.databinding.FragmentDetailPizzaBinding
import com.example.ipizza.fragment.FragmentMainMenu.FragmentMainMenu


class DetailPizzaFragment() : Fragment() {


    private var imgPizza = ""
    private var namePizza = ""
    private var costPizza = ""

    private lateinit var nameDetailsTextView:TextView
    private lateinit var imgDetailsTextView:ImageView
    private lateinit var buttonGoCardDetails:Button
    private lateinit var backButton:ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imgPizza = requireArguments().getString("imgPizza", "")
        namePizza = requireArguments().getString("namePizza", "")
        costPizza = requireArguments().getString("costPizza", "")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail_pizza, container, false)
        val binding:FragmentDetailPizzaBinding = FragmentDetailPizzaBinding.bind(root)

        nameDetailsTextView = binding.namePizzaDetailsTextView
        nameDetailsTextView.text = namePizza

        imgDetailsTextView = binding.imgPizzaDetailsTextView
        Glide.with(imgDetailsTextView)
            .load(imgPizza)
            .centerCrop()
            .into(imgDetailsTextView)

        buttonGoCardDetails = binding.goCartDetailsTextView
        buttonGoCardDetails.text = costPizza

        backButton = root.findViewById(R.id.backButton)


        return root
    }

companion object {
     fun newInstance(
         urlImgPizza: String,
         namePizza: String,
         costPizza: String
    ): DetailPizzaFragment {
        val detailPizzaFragment = DetailPizzaFragment()
        val args = Bundle()
        args.putString("imgPizza", urlImgPizza)
        args.putString("namePizza", namePizza)
        args.putString("costPizza", costPizza)

        detailPizzaFragment.arguments = args

        return detailPizzaFragment
    }
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