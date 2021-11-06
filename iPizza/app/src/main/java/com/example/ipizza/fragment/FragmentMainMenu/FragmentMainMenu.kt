package com.example.ipizza.fragment.FragmentMainMenu

import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ipizza.R
import com.example.ipizza.bottomFragment.BottomFragment
import com.example.ipizza.contract.navigator
import com.example.ipizza.dataBase.CartModel
import com.example.ipizza.databinding.FragmentMainMenuBinding
import com.example.ipizza.fragment.CartFragment.CartFragment
import com.example.ipizza.recyclerView.AdapterHomeMenuRecyclerView
import com.example.ipizza.retrofit.PizzaModel


class FragmentMainMenu() : Fragment(), TextView.OnEditorActionListener{


    private var tmpList:List<PizzaModel> = ArrayList()
    private lateinit var binding:FragmentMainMenuBinding
    private lateinit var adapter:AdapterHomeMenuRecyclerView

    private lateinit var mainRecView:RecyclerView
    private lateinit var searchButton:Button
    private lateinit var searchTextPizza:EditText
    private lateinit var textMenu:TextView
    private lateinit var goCartButton:Button
    private lateinit var layoutButtonCart:LinearLayout

    private lateinit var root:View

    private lateinit var viewModel:FragmentMainMenuViewModel

    private var cartNoNull:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartNoNull = requireArguments().getBoolean(fragmentArg1, false)

    }

    companion object {

            val  fragmentArg1 = "cartNoNull"

            fun newInstance(
                cartNoNull: Boolean
            ): FragmentMainMenu {
                val args = Bundle()

                args.putBoolean(fragmentArg1, cartNoNull)
                val fragment = FragmentMainMenu()
                fragment.arguments = args
                return fragment
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_main_menu, container, false)

        initUI()

        return root
    }

    fun initUI(){
        viewModel = ViewModelProvider(this).get(FragmentMainMenuViewModel::class.java)
        binding = FragmentMainMenuBinding.bind(root)

        //проверка на наличие товаров в корзине
        if(cartNoNull) {

            layoutButtonCart = binding.layoutButtonCart
            layoutButtonCart.visibility = View.VISIBLE

            goCartButton = binding.goToCartinMenu

            viewModel.getAllOrder {
                val costOrder = showCostOrder(it)
                goCartButton.text =
                    "Checkout                                                       " + costOrder.toString() + "₽"
            }

            goCartButton.setOnClickListener(){
                val fragment = CartFragment()

                navigator().showNewScreen(fragment)
            }

        }

        searchButton = binding.searchButton

        searchTextPizza = binding.searchPizza

        textMenu = binding.menuText

        mainRecView = binding.assortiPizza
        mainRecView.hasFixedSize()
        mainRecView.layoutManager= LinearLayoutManager(activity)


        adapter = AdapterHomeMenuRecyclerView(tmpList,requireContext())

        adapter.setOnItemClickListener{ listItem ->
            val  bottomFragment: BottomFragment =BottomFragment.myNewInstance(listItem.id)

            bottomFragment.show(requireActivity().supportFragmentManager, "tag")
        }

        viewModel.insertDataRoom()

        viewModel.getAllPizza {
            tmpList = it
            adapter.update(tmpList)
        }

        viewModel.makeApiCallPizza()

        mainRecView.adapter =  adapter

    }

    fun showCostOrder(list:List<CartModel>):Int{
        var rez=0

        for(i in list.indices){
            rez +=list[i].price*list[i].quantity
        }
        return rez
    }


    override fun onResume() {
        super.onResume()
        viewModel.getOrderDataRoom()
    }

    override fun onStart() {
        super.onStart()


        if(cartNoNull) {
            viewModel.getAllOrder {
                val costOrder = showCostOrder(it)
                goCartButton.text =
                    "Checkout                                                                     " + costOrder.toString() + "₽"
            }
        }

        searchButton.setOnClickListener(){
            //скрытие кнопки и надписи
            textMenu.isVisible = false
            searchButton.isVisible = false

            searchTextPizza.isVisible = true
            searchTextPizza.requestFocus()
            searchTextPizza.setOnEditorActionListener(this)

            searchTextPizza.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {

                    val newPizzaList:MutableList<PizzaModel> = arrayListOf()
                    var j=0
                    for(i in tmpList) {
                        if (searchTextPizza.text in i.name){
                            newPizzaList.add(j, i)
                            j++
                        }
                    }

                    adapter.update(newPizzaList)
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            })

        }


    }

    override fun onDestroy() {
        viewModel.clearComposite()
        super.onDestroy()
    }

    //обработка нажатия клавиши done на клавиатуре
    override fun onEditorAction(p0: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        val handled = false
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            if (p0?.id == R.id.searchPizza) {
                searchTextPizza.clearFocus()
                return handled
            }
        }
        return handled
    }


}