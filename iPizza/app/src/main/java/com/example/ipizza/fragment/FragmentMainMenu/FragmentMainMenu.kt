package com.example.ipizza.fragment.FragmentMainMenu

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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


class FragmentMainMenu() : Fragment(), TextView.OnEditorActionListener{

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

        //"cartNoNull" НУЖНО СДЕЛАТЬ КОНСТАНТОЙ

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

    fun initRecView(){
        //Тест использования предоставленной БД
        pizzaList = viewModel.liveData.value!!

        adapter = AdapterHomeMenuRecyclerView(pizzaList,requireContext())
        adapter.setOnImgItemClickListener{ listItem ->
            val bottomFragment: BottomFragment = BottomFragment.myNewInstance(
                listItem.id
            )

            bottomFragment.show(requireActivity().supportFragmentManager, "tag")
        }
        mainRecView.adapter =  adapter
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
        initRecView()

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

            /*
            Пока что, я решил отказаться от такой реализации, посколько во время тестов
            я не смог пофиксить баг, при котором во время поиска нужной пиццы и открытием экрана с детальной информацией,
            клавиатура не закрывалась. Из-за этого было принято решение пока оставить просто выделение фокуса на editText,
            чтобы пользователь сам по нажатию на него вызывал клавиатуру.
            //открытие клавиатуры
            val imgr = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            */
            //слушатель изменения текста в editText
            searchTextPizza.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    // Прописываем то, что надо выполнить после изменения текста
                    //поиск заданной пиццы
                    val newPizzaList:MutableList<PizzaEntity> = arrayListOf()
                    var j=0
                    for(i in pizzaList) {
                        if (searchTextPizza.text in i.name){
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