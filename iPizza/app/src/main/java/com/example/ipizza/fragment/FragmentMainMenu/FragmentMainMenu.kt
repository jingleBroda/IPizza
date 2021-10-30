package com.example.ipizza.fragment.FragmentMainMenu

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
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
import com.example.ipizza.dataBase.PizzaEntity
import com.example.ipizza.databinding.FragmentMainMenuBinding
import com.example.ipizza.fragment.CartFragment.CartFragment
import com.example.ipizza.recyclerView.AdapterHomeMenuRecyclerView
import com.example.ipizza.retrofit.PizzaModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class FragmentMainMenu() : Fragment(), TextView.OnEditorActionListener{

    //retrofite
    private var tmpList:List<PizzaModel> = ArrayList<PizzaModel>()
    private val compositeDisposable = CompositeDisposable()
    //

    private lateinit var binding:FragmentMainMenuBinding

    private lateinit var mainRecView:RecyclerView
    private lateinit var adapter:AdapterHomeMenuRecyclerView
    private lateinit var searchButton:Button
    private lateinit var searchTextPizza:EditText
    private lateinit var textMenu:TextView
    private lateinit var goCartButton:Button
    private lateinit var layoutButtonCart:LinearLayout

    private lateinit var root:View
    private lateinit var viewModel:FragmentMainMenuViewModel
    private lateinit var pizzaList:List<PizzaEntity>


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
        viewModel = ViewModelProvider(requireActivity()).get(FragmentMainMenuViewModel::class.java)
        binding = FragmentMainMenuBinding.bind(root)


        //проверка на наличие товаров в корзине, если они есть, то показываем экран с кнопкой перехода.
        if(cartNoNull == true) {

            layoutButtonCart = binding.layoutButtonCart
            //Я ПРОСТО НЕ ЗНАЛ ПРО VIEW,GONE, АХАХААХХА
            layoutButtonCart.visibility = View.VISIBLE

            goCartButton = binding.goToCartinMenu

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

        //TEST RETROFITE
        val retrofiteService =  viewModel.makeApiCallPizza()

        val localDisposable =  retrofiteService.getPizzaFullInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({pizza->

                tmpList = pizza
                //Log.d("PIZZAMODEL", tmpList[1].toString())

                adapter = AdapterHomeMenuRecyclerView(tmpList,requireContext())
                adapter.setOnImgItemClickListener{ listItem ->
                    val bottomFragment: BottomFragment? =
                        listItem.imageUrls?.get(0)?.let { imageUrlPizza ->
                            listItem.name?.let { namePizza ->
                                listItem.description?.let { descriptionPizza ->
                                    listItem.price?.let { pricePizza ->
                                        BottomFragment.myNewInstance(
                                            imageUrlPizza,
                                            namePizza,
                                            descriptionPizza,
                                            pricePizza
                                        )
                                    }
                                }
                            }
                        }


                    bottomFragment?.show(requireActivity().supportFragmentManager, "tag")
                }
                mainRecView.adapter =  adapter

            },{
                Log.d("Error", it.localizedMessage)
            })

        compositeDisposable.add(
            localDisposable
        )
        //
    }

    override fun onStart() {
        super.onStart()

        //нажатие на кнопку поиска
        searchButton.setOnClickListener(){
            //скрытие кнопки и надписи
            textMenu.isVisible = false
            searchButton.isVisible = false



            //показ editText, установка фокуса и привязка обработчика нажатия кнопки done
            searchTextPizza.isVisible = true
            searchTextPizza.requestFocus()
            searchTextPizza.setOnEditorActionListener(this)


            //слушатель изменения текста в editText
            searchTextPizza.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    // Прописываем то, что надо выполнить после изменения текста
                    //поиск заданной пиццы
                    val newPizzaList:MutableList<PizzaModel> = arrayListOf()
                    var j=0
                    for(i in tmpList) {
                        if (i.name?.let { it1 -> searchTextPizza.text?.contains(it1) } == true){
                            newPizzaList.add(j, i)
                            j++
                        }
                    }
                    //перерисовываем recycler с подходящими по названию пиццами
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
        compositeDisposable.clear()
        super.onDestroy()
    }

    //обработка нажатия клавиши done на клавиатуре
    //в данный момент используется чтобы убрать фокус с edittext
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