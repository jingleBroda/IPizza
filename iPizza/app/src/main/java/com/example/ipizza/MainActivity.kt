package com.example.ipizza

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.ipizza.contract.Navigator
import com.example.ipizza.fragment.FragmentMainMenu.FragmentMainMenu
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity(), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_IPizza)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)


        if (savedInstanceState == null) {

            val fragment = FragmentMainMenu.newInstance(false)

            replaceMainMenuFragment(fragment, true)

        }


    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .show(fragment)
            .commit()
    }

    private fun replaceMainMenuFragment(fragment: Fragment, tagShowFragment:Boolean) {

        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        if(tagShowFragment) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .show(fragment)
                .commit()
        }
        else{
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit()
        }

    }

    override fun goBack() {
        onBackPressed()
    }

    override fun showNewScreen(f: Fragment) {
        launchFragment(f)
    }


    override fun replaceMainMenu(f: Fragment, tagShowFragment:Boolean) {
        replaceMainMenuFragment(f, tagShowFragment)
    }




}